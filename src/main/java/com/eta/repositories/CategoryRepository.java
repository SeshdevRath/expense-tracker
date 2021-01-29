package com.eta.repositories;

import com.eta.dto.Category;
import com.eta.exceptions.EtBadRequestException;
import com.eta.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface CategoryRepository {
    Integer create(Integer userId, String title, String description) throws EtBadRequestException;
    List<Category> findAll(Integer userId) throws EtResourceNotFoundException;
    Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
    void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException;
    void removeById(Integer userId, Integer categoryId);
}
