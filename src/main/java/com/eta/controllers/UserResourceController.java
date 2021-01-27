package com.eta.controllers;

import com.eta.dto.User;
import com.eta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResourceController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> usrMap) {
        String firstName = (String) usrMap.get("firstName");
        String lastName = (String) usrMap.get("lastName");
        String email = (String) usrMap.get("email");
        String password = (String) usrMap.get("password");

        User user = userService.registerUser(firstName, lastName, email, password);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Registered successfully");
        return new ResponseEntity<>(responseMap, HttpStatus.OK);

//        return firstName + ", " + lastName + ", " + email + ", " + password;
    }
}
