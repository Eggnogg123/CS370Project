package com.example.MentalHealthPredictor;

public class Input {
    private String input;
    private String currentQuestion;

    public Input(String in, String question) {
        this.input = in;
        this.currentQuestion = question;
    }

    public String getCurrentQuestion() {
        return currentQuestion;
    }
 
    public String getCurrentInput() {
        return input;
    }
}
    //private the variables
    //expand the constructor to take and also store the current question 
    //make an method that returns the current question and the method that returns the current input
    
    
 