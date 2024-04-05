package com.example.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpUserRequestDto {

    private String name;
    private String email;
    private String password;
}