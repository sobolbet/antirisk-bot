package com.worldbet.antirisk_bot.db.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class CryptoPayDto {
    public record InvoiceResponse(
            boolean ok,
            InvoiceData result
    ) {}

    public record InvoicesListResponse(
            boolean ok,
            InvoicesListResult result
    ) {}

    public record InvoicesListResult(
            List<InvoiceData> items
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record InvoiceData(

            @JsonProperty("invoice_id")
            long invoiceId,

            @JsonProperty("bot_invoice_url")
            String botInvoiceUrl,

            String status,

            String payload,

            @JsonProperty("currency_type")
            String currencyType,

            String asset,

            String amount,

            @JsonProperty("fee_amount")
            BigDecimal feeAmount,

            @JsonProperty("fee_asset")
            String feeAsset,

            @JsonProperty("paid_at")
            String paidAt,

            @JsonProperty("created_at")
            String createdAt,

            @JsonProperty("expiration_date")
            String expirationDate
    ) {}
}
