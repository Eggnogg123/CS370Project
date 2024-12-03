package com.example.MentalHealthPredictor;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MentalHealthPredictor {

	public static void main(String[] args) throws IOException {
		DataSetResponseParser parser = new DataSetResponseParser("survey.csv");
		RandomForestAlgorithm alg = new RandomForestAlgorithm(parser);
		alg.buildRandomForest();
		SpringApplication.run(MentalHealthPredictor.class, args);
		
	}

}

