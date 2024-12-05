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
		controllerobject = new CurrentSessionResponses(23);
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
	int numberofquestions = 23;
	int numberofchoices = 2;

	for(int i=1; i<=numberofquestions; i++){ //this for loop automates the process of pulling the answer choices per question for a specified number of questions
		for(int j = 1; j<= numberofchoices; j++){
			
			String choicename = "choice" + i + j;
			System.out.println();
			System.out.println(choicename);
			//secondquestion is our to be added attributes name, greet is our Greeting model object, and getques1option gets the value of ques1option which is an answer option for question 1
			model.addAttribute(choicename,greet.getques1option()); // gonna have to generalize this so that it can pull the answer options for each question	 
		}
	}//end of for loop

	for(int i=1; i<=numberofquestions; i++){ //this loop automates the process of pulling questions from a source for a specified number of questions
		String questionname = "question" + i;
		
		model.addAttribute(questionname,parser.getQuestion(i-1));
	}
	  return "greeting";
	}//end of GetMapping
  
	@PostMapping("/register") 
	public String userRegistration(@ModelAttribute Greeting greet, Model model) { //links to model class which is Greeting.java
	  System.out.println(greet.toString());
	  return "greeting";
	}

	
}

