package com.eta.controllers;

import com.eta.Constants;
import com.eta.dto.User;
import com.eta.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userService.validateUser(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRETE_KEY)
                        .setIssuedAt(new Date(timestamp))
                        .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                        .claim("userId", user.getUserId())
                        .claim("email", user.getEmail())
                        .claim("firstName", user.getFirstName())
                        .claim("lastName", user.getLastName())
                        .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return map;
    }
}
