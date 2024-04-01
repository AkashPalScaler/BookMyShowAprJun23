package com.example.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel{
    private Integer number;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Format> format;

    @OneToMany(mappedBy = "screen")
    private List<Seat> seatList;
}
