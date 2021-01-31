package com.eta.repositories;

import com.eta.dto.Category;
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
public class CategoryRepositoryImplementation implements CategoryRepository {

    private static final String SQL_CREATE = "INSERT INTO et_categories(category_id, user_id, title, description) " +
            "                                             VALUES(NEXTVAL('et_categories_seq'), ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT c.category_id, c.user_id, c.title, c.description, " +
                                                 "COALESCE(SUM(t.amount), 0) total_expense " +
                                                 "FROM et_transactions t RIGHT OUTER JOIN et_categories c ON c.category_id = t.category_id " +
                                                 "WHERE c.user_id = ? AND c.category_id = ? GROUP BY c.category_id";
    private static final String SQL_FIND_ALL = "SELECT c.category_id, c.user_id, c.title, c.description, " +
                                                "COALESCE(SUM(t.amount), 0) total_expense " +
                                                "FROM et_transactions t RIGHT OUTER JOIN et_categories c ON c.category_id = t.category_id " +
                                                "WHERE c.user_id = ? GROUP BY c.category_id";
    private static final String SQL_UPDATE = "UPDATE et_categories SET title = ?, description = ? WHERE user_id = ? AND category_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(Integer userId, String title, String description) throws EtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, description);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("category_id");
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public List<Category> findAll(Integer userId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL, categoryRowMapper, userId);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Categories not found");
        }
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, categoryRowMapper, userId, categoryId);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Category not found");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, category.getTitle(), category.getDescription(), userId, categoryId);
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {

    }

    private RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
        return new Category(rs.getInt("category_id"),
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDouble("total_expense"));
    });
}
