package com.example.bookmyshow;

import com.example.bookmyshow.controllers.UserController;
import com.example.bookmyshow.dtos.SignUpUserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookmyshowApplication implements CommandLineRunner{

	@Autowired
	private UserController userController;

	public static void main(String[] args) {

		SpringApplication.run(BookmyshowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		SignUpUserRequestDto request = new SignUpUserRequestDto();
//		request.setEmail("Sardesh@gmail.com");
//		request.setName("Sardesh");
//		request.setPassword("SardeshSecret123");
//		userController.signUp(request);

		System.out.println("Login is successful : "+ userController.login("Sardesh@gmail.com",
				"SardeshSecret123"));

	}
}
