package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.InvalidShowError;
import com.example.bookmyshow.exceptions.SeatNotAvailableError;
import com.example.bookmyshow.exceptions.UserNotPresentError;
import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.repositories.ShowSeatRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private BookingRepository bookingRepository;


    public Booking bookShow(Long userId, List<Long> showSeatIds, Long showId) throws UserNotPresentError, SeatNotAvailableError, InvalidShowError {

        // Get User from user id
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotPresentError("User is not present");
        }
        User user = optionalUser.get();


        // Get show from show id
        Optional<Show> optionalShow = showRepository.findById(showId);
        if(optionalShow.isEmpty()){
            throw new InvalidShowError("Show selected is invalid");
        }

        Show show = optionalShow.get();

        //Reserve the available seats
        List<ShowSeat> reservedSeats = reserveSeats(showSeatIds, showId);

        //Create a booking
        Booking booking = createBooking(reservedSeats, user, show);

        return booking;
    }
    //Start transaction with isolation level serializable
    //Get show seats from show seat ids
    //Check availability
    // If !AVAILABLE or (LOCKED && Duration of last lock is < 10)
    //    throw Seat not available error
    //Else
    //    Update Locked Status
    //    Update Locked At time
    // End transaction
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<ShowSeat> reserveSeats(List<Long> showSeatIds, Long showId) throws SeatNotAvailableError {

        //Get show seats from show seat ids
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        //Check if any of the show seat is unavailable
        for(ShowSeat showSeat: showSeats){
            seatNotAvaliableForBooking(showSeat);
        }

        //We lock the seats for booking
        List<ShowSeat> reservedSeats = new ArrayList<>();
        for(ShowSeat showSeat: showSeats){
            if(!showSeat.getShow().getId().equals(showId)){
                continue;
            }
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
            showSeat.setLockedAt(new Date());
            reservedSeats.add(showSeat);
        }
        return reservedSeats;
    }

    private Double priceCalculator(List<ShowSeat> reservedSeats, Show show){
        //Add logic for calculating price
        return 0.0;
    }

    private Booking createBooking(List<ShowSeat> reservedSeats, User user, Show show) {
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(priceCalculator(reservedSeats, show));
        booking.setUser(user);
        booking.setShowSeatList(reservedSeats);
        booking.setPayments(new ArrayList<>());

        return bookingRepository.save(booking);
    }


    private void seatNotAvaliableForBooking(ShowSeat showSeat) throws SeatNotAvailableError {
        if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
            if(showSeat.getShowSeatStatus().equals(ShowSeatStatus.UNOPERATIONAL)){
                throw new SeatNotAvailableError("Seat is not operational");
            }
            if(showSeat.getShowSeatStatus().equals(ShowSeatStatus.BOOKED)){
                throw new SeatNotAvailableError("Seat is already booked");
            }
            if(showSeat.getShowSeatStatus().equals(ShowSeatStatus.LOCKED)){
                Long lockedDuration = Duration.between(new Date().toInstant(), showSeat.getLockedAt().toInstant()).toMinutes();
                if(lockedDuration < 10){
                    throw new SeatNotAvailableError("Seat is not available to book");
                }
            }
        }
    }
}
