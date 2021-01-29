package com.eta.services;

import com.eta.dto.User;
import com.eta.exceptions.EtAuthException;
import com.eta.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if (email != null) { email = email.toLowerCase(); }
        User user = userRepository.findByEmailAndPassword(email, password);
        return user;
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        Pattern emialPattern = Pattern.compile("^(.+)@(.+)$");

        if (email != null) { email = email.toLowerCase(); }
        if (!emialPattern.matcher(email).matches()) { throw  new EtAuthException("Invalid email.");}

        Integer emailCount = userRepository.getCountByEmail(email);
        if (emailCount > 0) { throw new EtAuthException("Email already in use"); }

        Integer userId = userRepository.create(firstName, lastName, email, password);

        return userRepository.findById(userId);
    }
}
