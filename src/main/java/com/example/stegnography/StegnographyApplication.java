package com.example.stegnography;

import com.example.stegnography.service.StegnoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class StegnographyApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(StegnographyApplication.class, args);


	}

}
