package com.example.MentalHealthPredictor;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MentalHealthPredictor {

	public static void main(String[] args) throws IOException {
		SurveyResponse responseObj = new SurveyResponse("survey.csv");
		DataSetResponseParser parser = new DataSetResponseParser(responseObj);
		SpringApplication.run(MentalHealthPredictor.class, args);
		
	}

}

