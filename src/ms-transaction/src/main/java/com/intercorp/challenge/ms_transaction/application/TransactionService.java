package com.intercorp.challenge.ms_transaction.application;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.intercorp.challenge.ms_transaction.domain.Transaction;
import com.intercorp.challenge.ms_transaction.domain.TransactionRepository;
import com.intercorp.challenge.ms_transaction.domain.TransactionStatus;
import com.intercorp.challenge.ms_transaction.domain.TransactionType;
import com.intercorp.challenge.ms_transaction.infrastructure.messaging.TransactionEventPublisher;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionEventPublisher transactionEventPublisher;

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setCreatedAt(LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.save(transaction);
        transactionEventPublisher.publishTransactionCreated(savedTransaction);
        return savedTransaction;
    }

    public Optional<Transaction> getTransactionByExternalId(UUID transactionExternalId) {
        return transactionRepository.findByTransactionExternalId(transactionExternalId);
    }

    public Transaction updateTransactionStatus(UUID transactionExternalId, TransactionStatus status) {
        Transaction transaction = transactionRepository.findByTransactionExternalId(transactionExternalId).orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setTransactionStatus(status);
        return transactionRepository.update(transaction);
    }
}