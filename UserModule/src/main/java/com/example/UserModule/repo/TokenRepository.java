package com.example.UserModule.repo;


import com.example.UserModule.entity.AuthenticationToken;
import com.example.UserModule.entity.UserTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
  AuthenticationToken findTokenByUser(UserTable user);

  AuthenticationToken findTokenByToken(String token);
}
