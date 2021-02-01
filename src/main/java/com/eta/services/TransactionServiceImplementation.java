package com.eta.services;

import com.eta.dto.Transaction;
import com.eta.exceptions.EtBadRequestException;
import com.eta.exceptions.EtResourceNotFoundException;
import com.eta.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImplementation implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        Integer transactionId = transactionRepository.create(userId, categoryId, amount, note, transactionDate);
        return transactionRepository.findById(userId, categoryId, transactionId);
    }

    @Override
    public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        return transactionRepository.findAll(userId, categoryId);
    }

    @Override
    public Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        return transactionRepository.findById(userId, categoryId, transactionId);
    }

    @Override
    public void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException {
        transactionRepository.update(userId, categoryId, transactionId, transaction);
    }

    @Override
    public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtBadRequestException {

    }
}
