package com.example.UserModule.service.serviceImpl.service;

import javax.servlet.http.HttpServletRequest;


import com.example.UserModule.dto.AccountDeactivationResponseDto;
import com.example.UserModule.dto.AccountDeletionResponseDto;
import com.example.UserModule.dto.PasswordChangeDto;
import com.example.UserModule.dto.PasswordChangeResponseDto;
import com.example.UserModule.dto.PasswordResetLinkDto;
import com.example.UserModule.dto.SignInDto;
import com.example.UserModule.dto.SignInResponseDto;
import com.example.UserModule.dto.SignUpResponseDto;
import com.example.UserModule.dto.SignupDto;
import com.example.UserModule.exceptions.AuthenticationFailException;
import com.example.UserModule.exceptions.CustomException;

public interface UserService {
  /**
   * @param signupDto
   * @return
   * @throws CustomException
   */
  public SignUpResponseDto signUp(SignupDto signupDto) throws CustomException;

  /**
   * @param signInDto
   * @param request
   * @return
   * @throws AuthenticationFailException
   * @throws CustomException
   */
  public SignInResponseDto signIn(SignInDto signInDto, String ip, HttpServletRequest request)
      throws AuthenticationFailException, CustomException;

  /**
   * @param email
   * @return
   */
  public PasswordResetLinkDto passwordResetLinkFunction(String email);

  /**
   * @param passwordChangeDto
   * @return
   */
  public PasswordChangeResponseDto passwordChangeFunction(PasswordChangeDto passwordChangeDto);

  /**
   * @param authenticationToken
   * @return
   */
  public AccountDeletionResponseDto accountDeletionFunction(String authenticationToken);


  /**
   * @param authenticationToken
   * @param action
   * @return
   */
  public AccountDeactivationResponseDto accountActivationDeactivationFunction(String authenticationToken,
      String action);

}
