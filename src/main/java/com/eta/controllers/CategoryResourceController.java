package com.eta.controllers;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/categories")
public class CategoryResourceController {

    @GetMapping(value = "")
    public String getAllCategories(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        return "Authenticated user id: " + userId;
    }
}
