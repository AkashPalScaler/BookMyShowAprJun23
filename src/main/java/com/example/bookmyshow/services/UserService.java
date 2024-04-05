package com.example.bookmyshow.services;

import com.example.bookmyshow.dtos.SignUpUserRequestDto;
import com.example.bookmyshow.exceptions.InvalidPassword;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User signUp(SignUpUserRequestDto request) {
        // we want to make sure this is a new user.
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new RuntimeException();
        }

        User newUser = new User();

        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        newUser.setPassword(encoder.encode(request.getPassword()));

        return userRepository.save(newUser);
    }

    public boolean login(String email, String password) throws InvalidPassword {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException();
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = optionalUser.get();
        if(!encoder.matches(password, user.getPassword())){
            throw new InvalidPassword("Password is invalid");
        }
        return true;
    }
}
