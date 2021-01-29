package com.eta.services;

import com.eta.dto.Category;
import com.eta.exceptions.EtBadRequestException;
import com.eta.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface CategoryService {
    Category addCategory(Integer userId, String title, String description) throws EtBadRequestException;
    List<Category> fetchAllCategories(Integer userId);
    Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
    void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException;
    void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
}
