package com.intercorp.challenge.ms_anti_fraud.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class Transaction {
    private UUID transactionExternalId;
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private Integer transferTypeId;
    private BigDecimal value;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private LocalDateTime createdAt;
}
