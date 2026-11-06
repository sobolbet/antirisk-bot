package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.*;
import com.worldbet.antirisk_bot.db.models.CryptoPayDto.InvoiceData;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentProcessingService {

    private static final String PAID_STATUS = "paid";
    private static final String USDT = "USDT";

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final LocaleMessageService localeMessageService;
    private final ApplicationEventPublisher applicationEventPublisher;


    public PaymentProcessingService(
            PaymentRepository paymentRepository,
            UserRepository userRepository, LocaleMessageService localeMessageService, ApplicationEventPublisher applicationEventPublisher
    ) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.localeMessageService = localeMessageService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public void processInvoice(InvoiceData invoice) {

        if (invoice == null) {
            return;
        }

        // Обрабатываем только оплаченные счета
        if (!PAID_STATUS.equalsIgnoreCase(invoice.status())) {
            return;
        }

        // 1. Ищем платёж по ID invoice Crypto Pay
        Optional<PaymentEntity> paymentOptional =
                paymentRepository.findByCryptoInvoiceId(invoice.invoiceId());

        if (paymentOptional.isEmpty()) {
            throw new IllegalStateException(
                    "Платёж не найден для Crypto Pay invoice ID: "
                            + invoice.invoiceId()
            );
        }

        PaymentEntity payment = paymentOptional.get();

        // 2. Проверяем, не был ли платёж обработан ранее
        if (payment.getStatus() == PaymentStatus.PAID) {
            return;
        }

        // 3. Проверяем payload
        if (!payment.getId().toString().equals(invoice.payload())) {
            throw new IllegalStateException(
                    "Payload invoice не совпадает с ID платежа"
            );
        }

        // 4. Проверяем валюту
        if (!USDT.equals(invoice.asset())) {
            throw new IllegalStateException(
                    "Неожиданная валюта invoice: " + invoice.asset()
            );
        }

        // 5. Проверяем сумму
        BigDecimal invoiceAmount = new BigDecimal(invoice.amount());

        if (payment.getInvoiceAmount().compareTo(invoiceAmount) != 0) {
            throw new IllegalStateException(
                    "Сумма invoice не совпадает с суммой платежа"
            );
        }

        // 6. Получаем комиссию
        BigDecimal feeAmount = invoice.feeAmount() == null
                ? BigDecimal.ZERO
                : invoice.feeAmount();

        if (feeAmount.compareTo(BigDecimal.ZERO) < 0
                || feeAmount.compareTo(invoiceAmount) > 0) {
            throw new IllegalStateException(
                    "Некорректное значение комиссии: " + feeAmount
            );
        }

        // Комиссию можно вычитать только в той же валюте
        if (feeAmount.compareTo(BigDecimal.ZERO) > 0
                && !USDT.equals(invoice.feeAsset())) {
            throw new IllegalStateException(
                    "Комиссия указана в неожиданной валюте: "
                            + invoice.feeAsset()
            );
        }

        BigDecimal actualAmount = invoiceAmount.subtract(feeAmount);

        // 7. Получаем пользователя
        UserEntity user = payment.getUserEntity();

        if (user == null) {
            throw new IllegalStateException(
                    "У платежа отсутствует связанный пользователь"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        // 8. Рассчитываем период подписки
        LocalDateTime subscribeStart;

        if (user.getTypeToUse()!= null) {

            if (user.getTypeToUse().equals(TypeToUse.SUBSCRIBE)) {

                if (user.getSubscribeEndDt() != null
                        && user.getSubscribeEndDt().isAfter(now)) {
                    subscribeStart = user.getSubscribeEndDt();
                } else {
                    subscribeStart = now;
                }

            } else {
                if (user.getTrialEndDt() != null
                        && user.getTrialEndDt().isAfter(now)) {
                    subscribeStart = user.getTrialEndDt();
                } else {
                    subscribeStart = now;
                }
            }

        } else {
            subscribeStart = now;
        }



        LocalDateTime subscribeEnd =
                subscribeStart.plusMonths(payment.getCountOfMonths());

        // 9. Обновляем платёж
        payment.setStatus(PaymentStatus.PAID);
        payment.setSumPay(actualAmount);
        payment.setFeeAmount(feeAmount);
        payment.setSubscribeStartDt(subscribeStart);
        payment.setSubscribeEndDt(subscribeEnd);

        paymentRepository.save(payment);

        // 10. Обновляем пользователя
        user.setSubscribeStartDt(subscribeStart);
        user.setSubscribeEndDt(subscribeEnd);
        user.setTypeToUse(TypeToUse.SUBSCRIBE);

        userRepository.save(user);

        Object [] args = new Object[] {subscribeStart,subscribeEnd};

        Optional<String> optLocale = Optional.ofNullable(user.getLocale());
        Locale userLocale = Locale.forLanguageTag(optLocale.orElse("ru-RU"));

        String messageText = localeMessageService.getMessage("reply.paySuccess", userLocale,args);
        MessageToSendEvent message = new MessageToSendEvent(this, Long.valueOf(user.getChatId()), messageText);
        applicationEventPublisher.publishEvent(message);
    }
}
