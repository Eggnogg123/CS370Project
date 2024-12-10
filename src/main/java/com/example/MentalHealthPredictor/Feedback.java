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
        int rowIndex = 13; //Row 13 is a confirmed healthy person

        // String healthyPerson[] = comparison.getSample(13);//gets the data from the person who is healthy

        // for(String i: healthyPerson){ //Just prints out all the values this healthy person has in the dataset
        //     System.out.print(i);
        // }System.out.println();
        
        //comparison.    //.getrows() gets number of rows in dataset
        return rowIndex;
    }//end of getHealthyPerson() method

    public String[] getHealthyPersonValues(int rowNum){//Dont care about the prediction right now, only need to get a healthy person from dataset 
        String healthyPerson[] = comparison.getSample(13);//gets the data from the person who is healthy

        for(String i: healthyPerson){ //Just prints out all the values this healthy person has in the dataset to console
            System.out.print(i);
        }System.out.println();
        
        return healthyPerson;
    }//end of getHealthyPerson() method

    public Double getPercentagetoHealthy(){
        String healthyPerson[] = getHealthyPersonValues(getHealthyPerson());//gets the data from the person who is healthy
        String currentUser[] = getHealthyPersonValues(getHealthyPerson()); //This will hold the String values of our current users inputs need to ask how
        
        Double totalPoints = 0.0;

        for(int i=0;i<23;i++){
            if(healthyPerson[i].equals(currentUser[i]))
                totalPoints += 1;
        }

        totalPoints = totalPoints/23; //This gives the percentage 0-100% of similarity

        return 0.0;
    }

    public boolean[] getSimilarities(){
        String healthyPerson[] = getHealthyPersonValues(getHealthyPerson());//gets the data from the person who is healthy
        String currentUser[] = getHealthyPersonValues(getHealthyPerson()); //This will hold the String values of our current users inputs need to ask how
        boolean[] similarityTracker = new boolean[23]; //This will be our reference to see which questions they shared similarities in.

        for(int i=0;i<23;i++){
            if(healthyPerson[i].equals(currentUser[i]))
                similarityTracker[i] = true;
        }

        return similarityTracker;
    }

    public String[] getHealthyandUsersAns(int colNum){ //returns the ans the healthy person and the user gave for this particular question and an array
        String ans[] = {comparison.getvalueString(getHealthyPerson(), colNum),
                        userAnswers.getAnswer(prediction) //will get the users answer for this question
                        
        }; 
        return ans;
    }
}
