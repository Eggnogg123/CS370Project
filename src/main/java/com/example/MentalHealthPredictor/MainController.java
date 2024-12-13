package com.example.MentalHealthPredictor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller //This effectively acts as our brain. This thing will pull data from files, read and write inputs to the website, and everything in between
public class MainController {
	DataSetResponseParser parser;
	RandomForestAlgorithm alg;
	String prediction; 
	Feedback test;

	public MainController() throws IOException{
		parser = new DataSetResponseParser("survey.csv");
		alg = new RandomForestAlgorithm(parser);
		alg.buildRandomForest();
	}

	public void newData(CurrentSessionReponses in) throws IOException{
		File filename = in.getFile();
		parser = new DataSetResponseParser(filename);
		alg = new RandomForestAlgorithm(parser);
		alg.buildRandomForest();
	}
 
	@GetMapping("/") //gets initial landing page
		public String GiveLandingPage(Model model,@ModelAttribute CurrentSessionReponses user) {
			return "landingpage";
		}//end of GiveLandingPage()
  
	@PostMapping("/feedback") //outputs feedback and result page
		public String FeedbackResults(Model model, @ModelAttribute CurrentSessionReponses user) { //links to model class which is Greeting.java
			String predictionforWebpage = ""; //The actual prediction is YES or NO so to make it more traditional we will output it as NEGATIVE or POSITIVE
			
			// for(int i=0;i<23;i++){ //prints out users inputs just to make sure it works 
			// 	System.out.println(user.getAnswer(Integer.toString(i)));
			// }

			user.setQuestions(parser); //Makes sure whatever the user inputs is stored
			//System.out.println(alg.makePrediction(user));

			/* This section sends out the predictions and its accompanying definition to the webpage*/
			if(alg.makePrediction(user).equals("YES")){
				predictionforWebpage = "Positive";
				//Send out definition of positive result
				model.addAttribute("predictionDefinition", "This means that you  may have a previously undetected mental health condition that should be verified by professionals"); 
			}
			else{
				predictionforWebpage = "Negative";
				//Send out definition of negative result
				model.addAttribute("predictionDefinition", "This means that you probably do not have a mental health condition. However you should still take care to maintain your healthy mind!"); 
			}
			test = new Feedback(prediction, parser, user);
			model.addAttribute("prediction", predictionforWebpage); //Send out the prediction result to the webpage

			/* This section sends out the healthy persons results to the webpage */
			for(int i=0; i<18; i++){ //this loop automates the process of pulling questions from a source for a specified number of question
				model.addAttribute("question" + (i + 1),test.getFeedbackQuestion(i));
				//System.out.println(test.getFeedbackQuestion(i));
				model.addAttribute("feedback" + (i + 1), test.getFeedbackValue(i));
			}


			return "result";
		}//end of FeedbackResults()

	@PostMapping("/survey") //Outputs survey page
		public String GiveSurvey(Model model,@ModelAttribute CurrentSessionReponses user) {// Handles fetching questions and options per question
			for(int i=1; i<=parser.getCols()-1; i++){ //this loop automates the process of pulling questions from a source for a specified number of question
				model.addAttribute("question" + i,parser.getQuestion(i-1));
				model.addAttribute("options" + i, parser.getChoices(parser.getQuestion(i-1)));
			}

			return "survey"; // returns an html file of the same name
		}//end of GiveSurvey()

	@PostMapping("/submission") //Outputs page that lets user submit a csv file
		public String userTraining(Model model,@ModelAttribute CurrentSessionReponses user) throws IOException {// Handles fetching questions and options per question
			newData(user);

			return "landingpage"; // returns an html file of the same name
		}//end of GiveSurvey()

}// end of class MainController

