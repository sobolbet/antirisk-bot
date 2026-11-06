package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.PaymentEntity;

import com.worldbet.antirisk_bot.db.PaymentRepository;
import com.worldbet.antirisk_bot.db.PaymentStatus;
import com.worldbet.antirisk_bot.db.UserEntity;
import com.worldbet.antirisk_bot.db.models.CryptoPayDto;
import com.worldbet.antirisk_bot.db.models.CryptoPayDto.*;
import com.worldbet.antirisk_bot.services.CryptoPayService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CryptoPayService cryptoPayService;

    public PaymentService(
            PaymentRepository paymentRepository,
            CryptoPayService cryptoPayService
    ) {
        this.paymentRepository = paymentRepository;
        this.cryptoPayService = cryptoPayService;
    }

    public PaymentEntity createSubscriptionPayment(
            UserEntity user,
            int countMonths,
            BigDecimal amountInUsdt
    ) {
        // 1. Проверяем входные данные
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        if (countMonths <= 0) {
            throw new IllegalArgumentException(
                    "Количество месяцев должно быть больше нуля"
            );
        }

        if (amountInUsdt == null ||
                amountInUsdt.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Сумма платежа должна быть больше нуля"
            );
        }

        // 2. Создаём внутренний платёж
        PaymentEntity payment = new PaymentEntity();

        payment.setUserEntity(user);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreateDt(LocalDateTime.now());
        payment.setCountOfMonths(countMonths);
        payment.setInvoiceAmount(amountInUsdt);

        // 3. Сохраняем платёж в БД
        payment = paymentRepository.save(payment);

        try {
            // 4. Создаём invoice в Crypto Pay
            CryptoPayDto.InvoiceResponse response = cryptoPayService.createSubscriptionInvoice(
                    payment.getId(),
                    countMonths,
                    amountInUsdt
            );

            if (response == null ||
                    !response.ok() ||
                    response.result() == null) {
                throw new IllegalStateException(
                        "Crypto Pay не вернул данные созданного invoice"
                );
            }

            InvoiceData invoice = response.result();

            // 5. Проверяем полученный invoice
            if (!"USDT".equals(invoice.asset())) {
                throw new IllegalStateException(
                        "Неожиданный asset invoice: " + invoice.asset()
                );
            }

            if (!payment.getId().toString().equals(invoice.payload())) {
                throw new IllegalStateException(
                        "Payload invoice не совпадает с ID платежа"
                );
            }

            if (invoice.botInvoiceUrl() == null ||
                    invoice.botInvoiceUrl().isBlank()) {
                throw new IllegalStateException(
                        "Crypto Pay не вернул ссылку на оплату"
                );
            }

            if (invoice.amount() == null ||
                    new BigDecimal(invoice.amount()).compareTo(amountInUsdt) != 0) {
                throw new IllegalStateException(
                        "Сумма invoice не совпадает с запрошенной суммой"
                );
            }

            // 6. Сохраняем данные invoice
            payment.setCryptoInvoiceId(invoice.invoiceId());
            payment.setPaymentUrl(invoice.botInvoiceUrl());

            return paymentRepository.save(payment);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Не удалось создать платёж в Crypto Pay",
                    e
            );
        }
    }

    public void saveCountOfMonths (Long invoiceId, Integer countOfMonths) {
        PaymentEntity payment = paymentRepository.findByCryptoInvoiceId(invoiceId).orElse(null);
        payment.setCountOfMonths(countOfMonths);
        paymentRepository.save(payment);
    }

}