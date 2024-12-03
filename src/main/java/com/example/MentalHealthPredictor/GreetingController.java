package com.example.MentalHealthPredictor;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {
	CurrentSessionResponses controllerobject;
	DataSetResponseParser parser;
	RandomForestAlgorithm alg;
	String prediction; /*  */
	public GreetingController() throws IOException{
		controllerobject = new CurrentSessionResponses();
		parser = new DataSetResponseParser("survey.csv");
		alg = new RandomForestAlgorithm(parser);
		alg.buildRandomForest();
		prediction = alg.makePrediction();
		
	}
	
	/* 
	@GetMapping("/greeting")
	public String greetingForm(Model model) {
	  model.addAttribute("greeting", new Greeting());
	  return "greeting";
	}
  
	@PostMapping("/greeting")
	public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
	  model.addAttribute("oii", prediction);
	  return "result";
	}
	*/

	@GetMapping("/")
	public String greeting() {
	  
	  return "greeting";
	}
  
	@PostMapping("/register") 
	public String userRegistration(@ModelAttribute Greeting greet) { 
	  System.out.println(greet.toString());
	  Input newobject = new Input(greet.toString());
	  return "greeting";
	}
	
}

