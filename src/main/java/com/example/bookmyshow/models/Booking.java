package com.example.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Booking extends BaseModel {
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
    @OneToMany
    private List<ShowSeat> showSeatList;
    @OneToMany(mappedBy="booking")
    private List<Payment> payments;
    private Double amount;
    @ManyToOne
    private User user;
}
