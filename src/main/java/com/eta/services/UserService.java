package com.eta.services;

import com.eta.dto.User;
import com.eta.exceptions.EtAuthException;

public interface UserService {

    User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;
    User validateUser(String email, String password) throws EtAuthException;
}
