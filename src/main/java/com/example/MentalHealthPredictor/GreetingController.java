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
	DataSetResponseParser parser;
	RandomForestAlgorithm alg;
	String prediction; /*  */
	Feedback test;

	public GreetingController() throws IOException{
		parser = new DataSetResponseParser("survey.csv");
		alg = new RandomForestAlgorithm(parser);
		alg.buildRandomForest();
		test = new Feedback("Yes", parser, null);
		System.out.println(parser.getSample(0)[0] + "\n dashodhfiuasfgdsuhgdoshogjsogsd");
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
	public String greeting(Model model,@ModelAttribute CurrentSessionReponses greet) {
		
	    return "index";
	}//end of GetMapping
  
	@PostMapping("/feedback") 
	public String userRegistration(@ModelAttribute CurrentSessionReponses greet, Model model) { //links to model class which is Greeting.java

	//   for(int i=0;i<23;i++){
	// 	System.out.println(parser.getQuestion(i));
	// 	System.out.println(greet.getAnswer(greet.getQuestion(i)));
	//   }

	  greet.setQuestions(parser);
	  System.out.println(alg.makePrediction(greet));
	  String prediction = "";
      if(alg.makePrediction(greet).equals("YES")){
        prediction = "Positive";
		//Send out definition of positive result
        model.addAttribute("predictionDefinition", "This means that you  may have a previously undetected mental health condition that should be verified by professionals"); 
      }
      else{
        prediction = "Negative";
		//Send out definition of negative result
        model.addAttribute("predictionDefinition", "This means that you probably do not have a mental health condition. However you should still take care to maintain your healthy mind!"); 
      }

      model.addAttribute("prediction", prediction); //Print out the prediction result on the webpage
	  return "result";
	}

	@PostMapping("/survey") 
	public String getgreeting(Model model,@ModelAttribute CurrentSessionReponses greet) {// Handles fetching questions and options per question
		for(int i=1; i<=parser.getCols()-1; i++){ //this loop automates the process of pulling questions from a source for a specified number of question
			
			model.addAttribute("question" + i,parser.getQuestion(i-1));
			model.addAttribute("options" + i, parser.getChoices(parser.getQuestion(i-1)));
		}		
		
			return "greeting";
		}//end of GetMapping

	
}

