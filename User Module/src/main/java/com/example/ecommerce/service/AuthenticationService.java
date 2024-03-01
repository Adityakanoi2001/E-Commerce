package com.example.ecommerce.service;


import com.example.ecommerce.config.MessageStrings;
import com.example.ecommerce.entity.AuthenticationToken;
import com.example.ecommerce.entity.UserTable;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.repo.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository repository;

    // save the confirmation token
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    // get token of the User
    public AuthenticationToken getToken(UserTable user) {
        return repository.findTokenByUser(user);
    }

    // get Uer from the token
    public UserTable getUser(String token) {
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
        if (Objects.nonNull(authenticationToken)) {
            if (Objects.nonNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    // check if the token is valid
    public void authenticate(String token) throws AuthenticationFailException {
        if (!Objects.nonNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_PRESENT);
        }
        if (!Objects.nonNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_VALID);
        }
    }
}
