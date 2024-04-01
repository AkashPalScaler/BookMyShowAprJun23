package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Show extends BaseModel {
    private Date startTime;
    private Date endTime;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Screen screen;
}
