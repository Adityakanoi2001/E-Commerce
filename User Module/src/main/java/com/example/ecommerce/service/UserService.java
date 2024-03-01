package com.example.ecommerce.service;

import com.example.ecommerce.config.MessageStrings;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.entity.AuthenticationToken;
import com.example.ecommerce.entity.UserTable;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.repo.UserTableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserTableRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;


    Logger logger = LoggerFactory.getLogger(UserService.class);

    public SignUpResponseDto signUp(SignupDto signupDto)  throws CustomException {

        // Check to see if the current email address has already been registered.
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail())))
        {
            // If the email address has been registered then throw an exception.
            return new SignUpResponseDto("User already exists","Try Login");
        }

        // first encrypt the password
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        UserTable user = new UserTable(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(),encryptedPassword ,signupDto.getCity(),signupDto.getState(),signupDto.getPhoneNumber());
        try
        {
            // save the User
            userRepository.save(user);
            // generate token for user
            final AuthenticationToken authenticationToken = new AuthenticationToken(user);
            // save token in database
            authenticationService.saveConfirmationToken(authenticationToken);
            // success in creating
            return new SignUpResponseDto("success", "user created successfully");
        } catch (Exception e) {

            // handle signup error
            throw new CustomException(e.getMessage());
        }
    }

    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws AuthenticationFailException, CustomException {
        // first find User by email
        UserTable user = userRepository.findByEmail(signInDto.getEmail());
        if(!Objects.nonNull(user)){
//            return new SignInResponseDto("")
            return new SignInResponseDto("user not present","Try Sign-UP !");
        }
        try {
            // check if password is right
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                // passwords do not match
                throw  new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if(!Objects.nonNull(token)) {
            // token not present
            throw new CustomException(MessageStrings.AUTH_TOEKN_NOT_PRESENT);
        }

        return new SignInResponseDto ("success",token.getToken());
    }

    public UserTable getAllDetails(String email)
    {
        return userRepository.findByEmail(email);
    }
}
