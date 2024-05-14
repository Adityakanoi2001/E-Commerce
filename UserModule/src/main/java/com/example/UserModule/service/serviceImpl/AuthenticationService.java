package com.example.UserModule.service.serviceImpl;


import com.example.UserModule.helper.MessageStrings;
import com.example.UserModule.entity.AuthenticationToken;
import com.example.UserModule.entity.UserTable;
import com.example.UserModule.exceptions.AuthenticationFailException;
import com.example.UserModule.repo.TokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Lazy
@Service
public class AuthenticationService {

  @Autowired
  TokenRepository repository;

  public void saveConfirmationToken(AuthenticationToken authenticationToken) {
    repository.save(authenticationToken);
  }

  public AuthenticationToken getToken(UserTable user) {
    return repository.findTokenByUser(user);
  }

  public UserTable getUser(String token) {
    AuthenticationToken authenticationToken = repository.findTokenByToken(token);
    if (Objects.nonNull(authenticationToken)) {
      if (Objects.nonNull(authenticationToken.getUser())) {
        return authenticationToken.getUser();
      }
    }
    return null;
  }

  public void authenticate(String token) throws AuthenticationFailException {

    if (!Objects.nonNull(token)) {
      throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
    }
    if (!Objects.nonNull(getUser(token))) {
      throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
    }
  }
}
