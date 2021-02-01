package com.eta.repositories;

import com.eta.dto.Transaction;
import com.eta.exceptions.EtBadRequestException;
import com.eta.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface TransactionRepository {
    Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;
    List<Transaction> findAll(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
    Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;
    void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException;
    void removeById(Integer userId, Integer categoryId, Integer transactionId);
}
