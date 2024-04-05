package com.example.bookmyshow.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpUserDtoResponse {

    private Long userId;
    private ResponseStatus responseStatus;
}
