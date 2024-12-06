package com.example.MentalHealthPredictor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
	public String greeting(Model model,@ModelAttribute Greeting greet) {
	
	
	for(int i=1; i<=parser.getCols()-1; i++){ //this loop automates the process of pulling questions from a source for a specified number of questions
		String questionname = "question" + i;
		
		model.addAttribute(questionname,parser.getQuestion(i-1));
		model.addAttribute("options" + i, parser.getChoices(parser.getQuestion(i-1)));
	}		
	    return "greeting";
	}//end of GetMapping
  
	@PostMapping("/register") 
	public String userRegistration(@ModelAttribute Greeting greet, Model model) { //links to model class which is Greeting.java
	  System.out.println(greet.toString());
	  return "greeting";
	}

	
}

