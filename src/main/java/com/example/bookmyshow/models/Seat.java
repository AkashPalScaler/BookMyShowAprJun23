package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seat extends BaseModel{
    private String number;
    @ManyToOne
    private Screen screen;
    @ManyToOne
    private SeatType seatType;
    private Integer rowNum;
    private Integer colNum;
}
