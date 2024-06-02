package com.example.UserModule.service.serviceImpl;

import com.example.UserModule.entity.UserInsights;
import com.example.UserModule.helper.Constants;
import com.example.UserModule.helper.MessageStrings;
import com.example.UserModule.dto.*;
import com.example.UserModule.entity.AuthenticationToken;
import com.example.UserModule.entity.UserTable;
import com.example.UserModule.exceptions.CustomException;
import com.example.UserModule.helper.ResponseMessages;
import com.example.UserModule.helper.UserActivity;
import com.example.UserModule.repo.TokenRepository;
import com.example.UserModule.repo.UserInsightsRepository;
import com.example.UserModule.repo.UserTableRepository;
import com.example.UserModule.service.serviceImpl.service.GeoIPLocationService;
import com.example.UserModule.service.serviceImpl.service.UserService;
import com.maxmind.geoip2.exception.GeoIp2Exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

@Lazy
@Slf4j
@Primary
@Service
public class UserServiceImplementation implements UserService {

  @Autowired
  UserTableRepository userRepository;
  @Autowired
  TokenRepository tokenRepository;
  @Autowired
  AuthenticationService authenticationService;
  @Autowired
  UserInsightsRepository userInsightsRepository;
  @Autowired
  private JavaMailSender javaMailSender;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Value("${spring.mail.username}")
  private String sender;
  @Autowired
  GeoIPLocationService geoIPLocationService;


  Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

  public SignUpResponseDto signUp(SignupDto signupDto) throws CustomException {
    SignUpResponseDto signUpResponseDto = new SignUpResponseDto();

    // Check to see if the current email address has already been registered.
    if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
      // If the email address has been registered then throw an exception.
      return new SignUpResponseDto(ResponseMessages.EXISTING_USER, ResponseMessages.TRY_LOGIN, null);
    }
    // first encrypt the password
    String encryptedPassword = signupDto.getPassword();
    encryptedPassword = hashPassword(signupDto.getPassword());
    UserTable user = new UserTable();
    BeanUtils.copyProperties(signupDto, user, signupDto.getPassword());
    user.setPassword(encryptedPassword);

    if (isValidPassword(signupDto.getPassword())) {
      try {
        // save the User
        userRepository.save(user);
        // generate token for user
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        // save token in database
        authenticationService.saveConfirmationToken(authenticationToken);

        UserInsights userInsights = new UserInsights(user);
        userInsightsRepository.save(userInsights);

        signUpResponseDto.setStatus(Constants.SUCCESS);
        signUpResponseDto.setMessage(ResponseMessages.USER_CREATED);
        signUpResponseDto.setValidPassword(true);
      } catch (Exception e) {
        // handle signup error
        throw new CustomException(e.getMessage());
      }
    } else {
      signUpResponseDto.setStatus(Constants.FAILURE);
      signUpResponseDto.setMessage(ResponseMessages.INVALID_PASSWORD);
      signUpResponseDto.setValidPassword(false);
    }
    return signUpResponseDto;
  }


  private boolean USNValidAndWithinActiveYear(String usn) {
    // Define the regular expression pattern for USN format
    final String USN_PATTERN = "^\\d{1}[A-Z]{2}\\d{2}[A-Z]{2}\\d{3}$";
    // Check if the USN format matches the pattern
    if (!Pattern.matches(USN_PATTERN, usn)) {
      return false;
    }
    // Extract the year portion from the USN (e.g., "19" from "1NH19IS009")
    int year = Integer.parseInt(usn.substring(3, 5));
    // Get the current year
    int currentYear = Year.now().getValue();
    // Calculate the active year range
    int activeYearStart = currentYear - 4;
    int activeYearEnd = currentYear;
    // Check if the year falls within the active range
    boolean isWithinActiveYearRange = year >= (activeYearStart % 100) && year <= (activeYearEnd % 100);
    return isWithinActiveYearRange;
  }


  private static boolean isValidPassword(String password) {

    // Regex to check valid password.
    String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

    // Compile the ReGex
    Pattern p = Pattern.compile(regex);

    // If the password is empty
    // return false
    if (password == null) {
      return false;
    }

    // Pattern class contains matcher() method
    // to find matching between given password
    // and regular expression.
    Matcher m = p.matcher(password);

    // Return if the password
    // matched the ReGex
    return m.matches();
  }

  private String hashPassword(String password) {
    String salt = BCrypt.gensalt();
    String hashedPassword = BCrypt.hashpw(password, salt);
    return hashedPassword;
  }

  public SignInResponseDto signIn(SignInDto signInDto, String ip, HttpServletRequest request) throws CustomException {
    // first find User by email
    UserTable user = userRepository.findByEmail(signInDto.getEmail());
    GeoIP geoIP = new GeoIP();

    if (!Objects.nonNull(user)) {
      return new SignInResponseDto(ResponseMessages.USER_UNAVAILABLE, MessageStrings.TRY_SIGN_UP_TOKEN);
    }

    // Use BCrypt's built-in method to verify the password
    if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
      return new SignInResponseDto(MessageStrings.WRONG_PASSWORD, MessageStrings.WRONG_PASSWORD_TOKEN);
    }

    AuthenticationToken token = authenticationService.getToken(user);
    if (!Objects.nonNull(token)) {
      // token not present
      throw new CustomException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
    }

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
      updateLastLoginTime(user.getId(), new Date());
    });
    executorService.shutdown();

    if (StringUtils.isNotEmpty(ip)) {
      try {
        geoIP = geoIPLocationService.getIpLocation(ip, request);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (GeoIp2Exception e) {
        throw new RuntimeException(e);
      }
      updateFirstLoginTime(user.getId(), new Date());
      userInsightsRepository.updateLocationAndDeviceInformation(user.getId(),
          geoIP.getFullLocation(),
          geoIP.getDevice());
      userInsightsRepository.updateLiveUserFlag(user.getId(), USNValidAndWithinActiveYear(user.getUsn()));
    }
    return new SignInResponseDto(Constants.SUCCESS, token.getToken());
  }

  private void updateLastLoginTime(Integer userId, Date dateTime) {
    userInsightsRepository.updateLastLoginDate(userId, dateTime);
  }

  private void updateFirstLoginTime(Integer userId, Date dateTime) {
    if (userInsightsRepository.getFirstLoginDateUsingId(userId) == null) {
      userInsightsRepository.updateFirstLoginDate(userId, dateTime);
    }
  }

  @Override
  public PasswordResetLinkDto passwordResetLinkFunction(String email) {
    UserTable userDetails = userRepository.findByEmail(email);
    AuthenticationToken authenticationToken = (tokenRepository.findTokenByUser(userDetails));
    String userToken = authenticationToken.getToken();

    EmailDetails emailDetails = new EmailDetails();
    emailDetails.setRecipient(email);
    emailDetails.setSubject("Password Reset Token - Blicommerce");
    emailDetails.setMsgBody("The User Token for Password Reset is : " + userToken);
    PasswordResetLinkDto passwordResetLinkDto = new PasswordResetLinkDto();
    passwordResetLinkDto.setResponseMessage("Authentication Token Sent Successfully");
    int attemptsLeft = userInsightsRepository.getPasswordResetAttemptsById(userDetails.getId());

    if (attemptsLeft <= 0) {
      passwordResetLinkDto.setAttemptsLeft(0);
      passwordResetLinkDto.setResponseMessage(ResponseMessages.ATTEMPTS_EXHAUSTED + " " + LocalDateTime.now());
    } else {
      // Create an ExecutorService to run the email sending task asynchronously
      ExecutorService executorService = Executors.newSingleThreadExecutor();
      executorService.submit(() -> {
        // Send the email in a separate thread
        sendSimpleMail(emailDetails);
      });
      executorService.shutdown(); // Shutdown the executor when done

      // Reduce password attempts in the main thread
      userInsightsRepository.reducePasswordAttempts(userDetails.getId());
    }

    passwordResetLinkDto.setAttemptsLeft(attemptsLeft);
    return passwordResetLinkDto;
  }

  @Override
  public PasswordChangeResponseDto passwordChangeFunction(PasswordChangeDto passwordChangeDto) {
    if (!isValidPassword(passwordChangeDto.getNewPassword())) {
      return PasswordChangeResponseDto.builder()
          .response(ResponseMessages.INVALID_PASSWORD)
          .update(false)
          .token(passwordChangeDto.getUniqueTokenId())
          .build();
    }
    try {
      UserTable user = authenticationService.getUser(passwordChangeDto.getUniqueTokenId());
      String hashedPassword = hashPassword(passwordChangeDto.getNewPassword());
      userRepository.updatePassword(user.getId(), hashedPassword);
      return PasswordChangeResponseDto.builder()
          .token(passwordChangeDto.getUniqueTokenId())
          .response("Password Changed Successfully !")
          .update(true)
          .build();
    } catch (Exception e) {
      log.warn("Authentication Failed for User with Token : {} TimeStamp : {}",
          passwordChangeDto.getUniqueTokenId(),
          new Date());
      return PasswordChangeResponseDto.builder()
          .token(passwordChangeDto.getUniqueTokenId())
          .response(ResponseMessages.AUTHENTICATION_ERROR)
          .update(false)
          .build();
    }
  }

  @Override
  public AccountDeletionResponseDto accountDeletionFunction(String authenticationToken) {
    AccountDeletionResponseDto accountDeletionResponseDto = new AccountDeletionResponseDto();
    try {
      UserTable user = authenticationService.getUser(authenticationToken);
      deleteUserAndRelatedRecords(user.getId());
      accountDeletionResponseDto.setAccountDeleted(true);
      accountDeletionResponseDto.setResponse("Account Deleted Successfully !");
    } catch (Exception e) {
      accountDeletionResponseDto.setAccountDeleted(false);
      accountDeletionResponseDto.setResponse("User Not Found !");
    }
    return accountDeletionResponseDto;
  }

  @Override
  public AccountDeactivationResponseDto accountActivationDeactivationFunction(String authenticationToken,
      String action) {
    AccountDeactivationResponseDto accountDeactivationResponseDto = new AccountDeactivationResponseDto();
    UserTable user = authenticationService.getUser(authenticationToken);

    // Get the UserInsights directly without using Optional
    UserInsights userInsights = userInsightsRepository.findById(user.getId())
        .orElseGet(() -> new UserInsights(user)); // Create new UserInsights if not found

    if (userInsights.getUserActivity().equals(action)) {
      accountDeactivationResponseDto.setAccountDeactivated(false);
      accountDeactivationResponseDto.setResponse(ResponseMessages.ACCOUNT_ALREADY_DEACTIVATED);
    } else {
      userInsightsRepository.updateUserActivity(user.getId(), action);
      accountDeactivationResponseDto.setAccountDeactivated(true);
      if (action.equals(UserActivity.DEACTIVATED_ACCOUNT.getName())) {
        accountDeactivationResponseDto.setResponse(ResponseMessages.ACCOUNT_DEACTIVATED);
      } else if (action.equals(UserActivity.ACTIVE.getName())) {
        accountDeactivationResponseDto.setResponse(ResponseMessages.ACCOUNT_ACTIVATED);
      }
    }
    return accountDeactivationResponseDto;
  }


  @Transactional
  private void deleteUserAndRelatedRecords(Integer userId) {
    // Delete related records in AuthenticationToken
    userRepository.deleteRelatedRecordsInAuthenticationToken(userId);

    // Delete related records in SellerInsights
    userRepository.deleteRelatedRecordsInUserInsights(userId);

    // Delete the user from UserTable
    userRepository.deleteUserAndRelatedRecords(userId);
  }

  private String sendSimpleMail(EmailDetails details) {

    try {
      // Creating a simple mail message
      SimpleMailMessage mailMessage = new SimpleMailMessage();

      // Setting up necessary details
      mailMessage.setFrom(sender);
      mailMessage.setTo(details.getRecipient());
      mailMessage.setText(details.getMsgBody());
      mailMessage.setSubject(details.getSubject());

      // Sending the mail
      javaMailSender.send(mailMessage);
      return ResponseMessages.MAIL_SENT_SUCCESS;
    }

    // Catch block to handle the exceptions
    catch (Exception e) {
      return ResponseMessages.MAIL_SENT_FAILURE;
    }
  }
}
