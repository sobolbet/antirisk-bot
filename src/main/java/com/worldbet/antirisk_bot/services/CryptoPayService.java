package com.worldbet.antirisk_bot.services;

import com.worldbet.antirisk_bot.db.models.CryptoPayDto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CryptoPayService {

    private final String API_URL = "https://pay.crypt.bot/api/";
    private final String TEST_API_URL = "https://testnet-pay.crypt.bot/api/";

    @Value("${cryptopay.testToken}")
    private String token;

    private final RestClient restClient = RestClient.create();

    public InvoiceResponse createSubscriptionInvoice(UUID invoiceId, int countMonths, BigDecimal amountInUsdt) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("currency_type", "crypto");
        requestBody.put("asset", "USDT");
        requestBody.put("amount", amountInUsdt.toPlainString());
        requestBody.put("description", "Подписка на прогнозы бота " + countMonths + " месяцев");
        requestBody.put("payload", String.valueOf(invoiceId));
        requestBody.put("paid_btn_name", "openBot");
        requestBody.put("paid_btn_url", "https://t.me/fatdogon_bot");


        // Отключаем комментарии к платежу от греха подальше
        requestBody.put("allow_comments", false);

        return restClient.post()
                .uri(TEST_API_URL + "createInvoice")
                .header("Crypto-Pay-API-Token", token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(InvoiceResponse.class);
    }

    public InvoicesListResponse checkInvoicesStatus(String invoiceIds) {
        return restClient.get()
                .uri(TEST_API_URL + "getInvoices?invoice_ids=" + invoiceIds)
                .header("Crypto-Pay-API-Token", token)
                .retrieve()
                .body(InvoicesListResponse.class);
    }

}
