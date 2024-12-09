package com.example.MentalHealthPredictor;

public class Feedback {
    private DataSetResponseParser comparison;
    private CurrentSessionReponses userAnswers;
    private String prediction;

    public Feedback(String prediction, DataSetResponseParser d, CurrentSessionReponses c){
        comparison = d;
        userAnswers = c;
        getHealthyPerson();
    }// end of Feedback() method

    public int getHealthyPerson(){//Dont care about the prediction right now, only need to get a healthy person from dataset
        int rowIndex = 0;
        String healthyPerson[] = comparison.getSample(13);//gets the data from the person who is healthy

        for(String i: healthyPerson){
            System.out.print(i);
        }System.out.println();
        
        

        //comparison.    //.getrows() gets number of rows in dataset
        return rowIndex;
    }//end of getHealthyPerson() method
}
