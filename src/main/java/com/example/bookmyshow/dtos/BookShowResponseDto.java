package com.example.bookmyshow.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookShowResponseDto {

    private Long bookingId;
    private Double amount;
    private ResponseStatus responseStatus;
    private String failureReason;


}