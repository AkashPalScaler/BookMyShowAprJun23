package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Movie extends BaseModel{
    private String name;
    private Double rating;
    private List<String> actors;
}
