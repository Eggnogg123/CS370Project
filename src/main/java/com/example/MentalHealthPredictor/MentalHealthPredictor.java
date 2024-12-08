package com.example.MentalHealthPredictor;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MentalHealthPredictor {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MentalHealthPredictor.class, args);
		openHomePage();
	}

	private static void openHomePage() throws IOException {
        String url = "http://localhost:8080/";
        Runtime rt = Runtime.getRuntime();
        rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
    }
}

