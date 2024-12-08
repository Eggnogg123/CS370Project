package com.example.MentalHealthPredictor;

public class Feedback {
    private DataSetResponseParser comparison;
    private CurrentSessionReponses userAnswers;
    private String prediction;

    public Feedback(String prediction, DataSetResponseParser d, CurrentSessionReponses C){

    }// end of Feedback() method

    public int getHealthyPerson(){//Dont care about the prediction right now, only need to get a healthy person from dataset
        int rowIndex = 0;
        String dataset[][] = 
        {   {"John", "Healthy", "Yes"},
            {"Jane", "Healthy", "No"},
            {"Alice", "Sick", "No"},
            {"Bob", "Healthy", "Yes"},
            {"Eve", "Sick", "Yes"}      };
        String healthyPerson[] = new String[dataset[3].length];

        for(int i=0; i<dataset[3].length; i++){ //This will store the healthy users responses so we can compare them to our current users
            healthyPerson[i] = dataset[3][i];
        }

        //comparison.    //.getrows() gets number of rows in dataset
        return rowIndex;
    }//end of getHealthyPerson() method
}
