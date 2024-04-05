package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.BookShowRequestDto;
import com.example.bookmyshow.dtos.BookShowResponseDto;
import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.exceptions.UserNotPresentError;
import com.example.bookmyshow.models.Booking;
import com.example.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    public BookShowResponseDto bookShow(BookShowRequestDto requestDto){
        try{
            Booking booking = bookingService.bookShow(requestDto.getUserId(), requestDto.getShowSeatIds(), requestDto.getShowId());
            return new BookShowResponseDto(booking.getId(), booking.getAmount(), ResponseStatus.SUCCESS, "");
        }
        catch(UserNotPresentError e){
            System.out.println(e.getMessage());
            return new BookShowResponseDto(null, null, ResponseStatus.FAILURE, e.getMessage());
        }
        catch(Exception e){
            return new BookShowResponseDto(null, null, ResponseStatus.FAILURE, "Internal server error");
        }

    }
}
