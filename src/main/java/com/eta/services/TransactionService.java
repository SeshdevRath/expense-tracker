package com.eta.services;

import com.eta.dto.Transaction;
import com.eta.exceptions.EtBadRequestException;
import com.eta.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface TransactionService {
    Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;
    List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
    Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;
    void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException;
    void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtBadRequestException;
}
