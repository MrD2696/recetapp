package com.recetapp.recetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Main class for running the Recetapp application.
 * This class bootstraps the Spring Boot application and starts the application context.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@SpringBootApplication
public class RecetappApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecetappApplication.class, args);
	}

}
