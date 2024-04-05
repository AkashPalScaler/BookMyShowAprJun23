package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.dtos.SignUpUserDtoResponse;
import com.example.bookmyshow.dtos.SignUpUserRequestDto;
import com.example.bookmyshow.exceptions.InvalidPassword;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    public SignUpUserDtoResponse signUp(SignUpUserRequestDto request){
        User user = userService.signUp(request);
        return new SignUpUserDtoResponse(user.getId(),
                ResponseStatus.SUCCESS);
    }

    public boolean login(String email, String password) throws InvalidPassword {
        return userService.login(email, password);
    }
}
