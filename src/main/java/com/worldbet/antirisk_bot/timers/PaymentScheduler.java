package com.worldbet.antirisk_bot.timers;

import com.worldbet.antirisk_bot.db.PaymentEntity;
import com.worldbet.antirisk_bot.db.PaymentRepository;
import com.worldbet.antirisk_bot.db.PaymentStatus;
import com.worldbet.antirisk_bot.services.LocaleMessageService;
import com.worldbet.antirisk_bot.services.PaymentProcessingService;
import com.worldbet.antirisk_bot.db.models.CryptoPayDto.InvoiceData;
import com.worldbet.antirisk_bot.db.models.CryptoPayDto.InvoicesListResponse;
import com.worldbet.antirisk_bot.services.CryptoPayService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(PaymentScheduler.class);

    private final PaymentRepository paymentRepository;
    private final CryptoPayService cryptoPayService;
    private final PaymentProcessingService paymentProcessingService;
    private final LocaleMessageService localeMessageService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PaymentScheduler(
            PaymentRepository paymentRepository,
            CryptoPayService cryptoPayService,
            PaymentProcessingService paymentProcessingService, LocaleMessageService localeMessageService, ApplicationEventPublisher applicationEventPublisher
    ) {
        this.paymentRepository = paymentRepository;
        this.cryptoPayService = cryptoPayService;
        this.paymentProcessingService = paymentProcessingService;
        this.localeMessageService = localeMessageService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // Запуск каждые 5 минут
    @Scheduled(fixedDelay = 300000)
    public void checkPendingPayments() {

        log.info("Начинаем проверку неоплаченных платежей");

        // 1. Получаем все платежи со статусом PENDING
        List<PaymentEntity> pendingPayments =
                paymentRepository.findAllByStatus(PaymentStatus.PENDING);

        if (pendingPayments.isEmpty()) {
            log.info("Неоплаченных платежей нет");
            return;
        }

        // 2. Получаем ID счетов Crypto Pay
        List<Long> invoiceIds = pendingPayments.stream()
                .map(PaymentEntity::getCryptoInvoiceId)
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());

        if (invoiceIds.isEmpty()) {
            log.info("У ожидающих платежей пока нет Crypto Pay invoice ID");
            return;
        }

        // 3. Разбиваем ID на группы по 100
        int batchSize = 100;

        for (int i = 0; i < invoiceIds.size(); i += batchSize) {

            List<Long> batch = invoiceIds.subList(
                    i,
                    Math.min(i + batchSize, invoiceIds.size())
            );

            String invoiceIdsString = batch.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            checkInvoiceBatch(invoiceIdsString);
        }

        log.info("Проверка неоплаченных платежей завершена");
    }

    private void checkInvoiceBatch(String invoiceIds) {

        try {
            // 4. Запрашиваем статусы счетов в Crypto Pay
            InvoicesListResponse response =
                    cryptoPayService.checkInvoicesStatus(invoiceIds);

            if (response == null || !response.ok()
                    || response.result() == null
                    || response.result().items() == null) {

                log.error("Crypto Pay вернул некорректный ответ getInvoices");
                return;
            }

            // 5. Передаём каждый invoice на обработку
            for (InvoiceData invoice : response.result().items()) {

                try {
                    paymentProcessingService.processInvoice(invoice);

                } catch (Exception e) {
                    log.error(
                            "Ошибка обработки invoice ID: {}",
                            invoice.invoiceId(),
                            e
                    );
                }
            }

        } catch (Exception e) {
            log.error("Ошибка при запросе invoice из Crypto Pay", e);
        }
    }
}