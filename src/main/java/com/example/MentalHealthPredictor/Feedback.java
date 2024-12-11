package com.example.MentalHealthPredictor;

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Feedback {
    private DataSetResponseParser comparison;
    private CurrentSessionReponses userAnswers;
    private String prediction, feedbackArr[];

    public Feedback(String prediction, DataSetResponseParser d, CurrentSessionReponses c){
        comparison = d;
        userAnswers = c;
        feedbackArr = new String[18];
        for(int i =0,j = 0;i<22;i++,j++){
            
            while(comparison.getQuestion(i).equals("What is your Age?") ||
            comparison.getQuestion(i).equals("What is your Gender?") ||
            comparison.getQuestion(i).equals("If you live in one of these US States, what is your State of residence? Otherwise put NA.") ||
            comparison.getQuestion(i).equals("Do you have a family history of mental illness?")
            )i++;
            System.out.println(comparison.getQuestion(i));
            feedbackArr[j] =getHealthyPersonValue(i);
            if(j < 18)System.out.println(feedbackArr[j]);
            
        }
    }// end of Feedback() method

    public String getHealthyPersonValue(int index){//Dont care about the prediction right now, only need to get a healthy person from dataset

        Random random = new Random();
        
        int [] healthylist = new int[10];
        for(int i =0;i<10;i++){
            int nextHealthy = random.nextInt(comparison.getRows());
            while(getSimilar(nextHealthy, i)<10){
                nextHealthy = random.nextInt(comparison.getRows());
            }
            healthylist[i] = nextHealthy;
        }
       //  return getSimilar(healthylist, index);
       Map<String,Integer> answerCount = new HashMap<String,Integer>();
       String mostC = new String();
       for(int i=0;i<10;i++){
            String curr = new String(comparison.getSample(healthylist[i])[index]);
            if(!answerCount.containsKey(curr))
                answerCount.put(curr, 0);
            else answerCount.put(curr, answerCount.get(curr) + 1);
            mostC = curr;
        }
      
        for(Map.Entry<String, Integer> i:answerCount.entrySet()){
            if(answerCount.get(i.getKey()) > answerCount.get(mostC))mostC = i.getKey();
        }
        return mostC;
    }//end of getHealthyPerson() method

    private Integer getSimilar(int row, int indexNotUsed){// how many indices are common excluding the ones oyu shouldnt look at
        Integer ans = 0;
        String healthyPerson[] = comparison.getSample(row);//gets the data from the person who is healthy
        if(healthyPerson[0].equals("No"))return 0;
        String currentUser[] = userAnswers.getasArray();
        for(int i =0;i<currentUser.length;i++){
            if(i == indexNotUsed)continue;
            if(healthyPerson[i].equals(currentUser[i]))ans++;
        }
        return ans;
    }
}
//     public String[] getHealthyPersonValues(int rowNum){//Dont care about the prediction right now, only need to get a healthy person from dataset 
//         String healthyPerson[] = comparison.getSample(13);//gets the data from the person who is healthy

//         for(String i: healthyPerson){ //Just prints out all the values this healthy person has in the dataset to console
//             System.out.print(i);
//         }System.out.println();
        
//         return healthyPerson;
//     }//end of getHealthyPerson() method

//     public Double getPercentagetoHealthy(){
//         String healthyPerson[] = getHealthyPersonValues(getHealthyPerson());//gets the data from the person who is healthy
//         String currentUser[] = getHealthyPersonValues(getHealthyPerson()); //This will hold the String values of our current users inputs need to ask how
        
//         Double totalPoints = 0.0;

//         for(int i=0;i<23;i++){
//             if(healthyPerson[i].equals(currentUser[i]))
//                 totalPoints += 1;
//         }

//         totalPoints = totalPoints/23; //This gives the percentage 0-100% of similarity

//         return 0.0;
//     }

//     public boolean[] getSimilarities(){
        
//         String healthyPerson[] = getHealthyPersonValues(getHealthyPerson());gets the data from the person who is healthy
//         String currentUser[] = getHealthyPersonValues(getHealthyPerson()); //This will hold the String values of our current users inputs need to ask how
//         boolean[] similarityTracker = new boolean[23]; //This will be our reference to see which questions they shared similarities in.

//         for(int i=0;i<23;i++){
//             if(healthyPerson[i].equals(currentUser[i]))
//                 similarityTracker[i] = true;
//         }

//         return similarityTracker;
//     }

//     public String[] getHealthyandUsersAns(int colNum){ //returns the ans the healthy person and the user gave for this particular question and an array
//         String ans[] = {comparison.getvalueString(getHealthyPerson(), colNum),
//                         userAnswers.getAnswer(prediction) //will get the users answer for this question
                        
//         }; 
//         return ans;
//     }
// }
