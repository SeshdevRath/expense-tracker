package com.eta.repositories;

import com.eta.dto.Transaction;
import com.eta.exceptions.EtBadRequestException;
import com.eta.exceptions.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionRepositoryImplementation implements TransactionRepository {

    private static final String SQL_CREATE     = "INSERT INTO et_transactions(transaction_id, category_id, user_id, amount, note, transaction_date) " +
                                                                      "VALUES(NEXTVAL('et_transactions_seq'), ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT transaction_id, category_id, user_id, amount, note, transaction_date FROM et_transactions " +
                                                 "WHERE user_id = ? AND category_id = ? AND transaction_id = ?";
    private static final String SQL_FIND_ALL   = "SELECT transaction_id, category_id, user_id, amount, note, transaction_date FROM et_transactions " +
                                                 "WHERE user_id = ? AND category_id = ?";

    private static final String SQL_UPDATE     = "UPDATE et_transactions SET amount = ?, note = ?, transaction_date = ? " +
                                                 "WHERE user_id = ? AND category_id = ? AND transaction_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, categoryId);
                ps.setInt(2, userId);
                ps.setDouble(3, amount);
                ps.setString(4, note);
                ps.setLong(5, transactionDate);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("transaction_id");
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL, transactionRowMapper, userId, categoryId);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Transactions not found");
        }
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, transactionRowMapper, userId, categoryId, transactionId);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Transaction not found.");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, transaction.getAmount(), transaction.getNote(), transaction.getTransactionDate(), userId, categoryId, transactionId);
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId, Integer transactionId) {

    }

    private final RowMapper<Transaction> transactionRowMapper = ((resultSet, rowNum) -> {
        return new Transaction(resultSet.getInt("transaction_id"),
                resultSet.getInt("category_id"),
                resultSet.getInt("user_id"),
                resultSet.getDouble("amount"),
                resultSet.getString("note"),
                resultSet.getLong("transaction_date"));
    });
}
