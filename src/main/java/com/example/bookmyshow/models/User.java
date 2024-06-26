package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user_bms")
public class User extends BaseModel {
    private String name;
    private String email;
    private String phone;
    private String password;
}
