package com.eta.controllers;
import com.eta.dto.Category;
import com.eta.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryResourceController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "")
    public ResponseEntity<Category> addCategory (HttpServletRequest request, @RequestBody Map<String, Object> requestMap) {
        Integer userId = (Integer) request.getAttribute("userId");
        String title = (String) requestMap.get("title");
        String description = (String) requestMap.get("description");
        Category category = categoryService.addCategory(userId, title, description);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        List<Category> categories = categoryService.fetchAllCategories(userId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(HttpServletRequest request, @PathVariable("categoryId") Integer categoryId) {
        Integer userId = (Integer) request.getAttribute("userId");
        Category category = categoryService.fetchCategoryById(userId, categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
