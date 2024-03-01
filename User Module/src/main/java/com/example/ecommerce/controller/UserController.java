package com.example.ecommerce.controller;


import com.example.ecommerce.dto.*;
import com.example.ecommerce.entity.UserTable;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignInResponseDto> Signup(@RequestBody SignupDto signupDto) throws CustomException {
        return new ResponseEntity(userService.signUp(signupDto),HttpStatus.OK) ;
    }

    @PostMapping("/signIn")
    public SignInResponseDto Signup(@RequestBody SignInDto signInDto) throws CustomException, AuthenticationFailException {
        return userService.signIn(signInDto);
    }

    @GetMapping("/getDetails/{email}")
    public ResponseEntity<DeatilsDto> details (@PathVariable("email") String email)
    {
        return new ResponseEntity(userService.getAllDetails(email),HttpStatus.OK);
    }

}