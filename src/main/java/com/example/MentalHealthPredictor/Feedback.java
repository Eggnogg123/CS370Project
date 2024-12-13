package com.example.MentalHealthPredictor;

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Feedback {
    private DataSetResponseParser comparison;
    private CurrentSessionReponses userAnswers;
    private String prediction, feedbackArr[], feedbackQuestions[];

    public Feedback(String prediction, DataSetResponseParser d, CurrentSessionReponses c){
        comparison = d;
        userAnswers = c;
        feedbackArr = new String[18];
        feedbackQuestions = new String[18];
        for(int i =1 ,j = 0;i<23;i++,j++){
            
            while(comparison.getQuestion(i - 1).equals("What is your Age?") ||
            comparison.getQuestion(i - 1).equals("What is your Gender?") ||
            comparison.getQuestion(i - 1).equals("If you live in one of these US States, what is your State of residence? Otherwise put NA.") ||
            comparison.getQuestion(i - 1).equals("Do you have a family history of mental illness?")
            )i++; //Skips first 3 questions excluding country
            // System.out.println(comparison.getQuestion(i - 1));
            // System.out.println(i - 1);
            feedbackQuestions[j] = comparison.getQuestion(i - 1);
            feedbackArr[j] =getHealthyPersonValue(i);
            //if(j < 18)System.out.println(feedbackArr[j]);
            
        }
    }// end of Feedback() method

    public String getFeedbackValue(int index){
        return feedbackArr[index];
    }

    public String getFeedbackQuestion(int index){
        return feedbackQuestions[index];
    }

    public String getHealthyPersonValue(int index){//Dont care about the prediction right now, only need to get a healthy person from dataset
    
        Random random = new Random();
        Set<Integer> healthylist = getSimilar(index);
       //  return getSimilar(healthylist, index);
       Map<String,Integer> answerCount = new HashMap<String,Integer>();
       String mostC = new String();
       System.out.print("Used Healthy People at Responses: ");
       for(int i:healthylist){
            System.out.print(i + 2 + " ");
            String curr = new String(comparison.getSample(i)[index]);
            if(!answerCount.containsKey(curr))
                answerCount.put(curr, 0);
            else answerCount.put(curr, answerCount.get(curr) + 1);
            mostC = curr;
        }
        System.out.println();
        
        for(Map.Entry<String, Integer> i:answerCount.entrySet()){
            if(answerCount.get(i.getKey()) > answerCount.get(mostC))mostC = i.getKey();
        }
        System.out.println(mostC);
        return mostC;
    }//end of getHealthyPerson() method

    private Set<Integer> getSimilar(int index){// how many indices are common excluding the ones oyu shouldnt look at
        Set<Integer> ans = new HashSet<Integer>();
        Map<Integer,Integer> key = new HashMap<Integer,Integer>();
        for(int i =0;i<comparison.getRows();i++){
            String user[] = userAnswers.getasArray();
            String survey[] = comparison.getSample(i);
            int count = 0;
            for(int j = 1;j<survey.length;j++){
                if(j == index)continue;
                if(user[j-1].equals(survey[j]))count++;
            }
            key.put(i,count);
            if(ans.size() < 10)
                ans.add(i);
            else{
                int min = i;
                for(Integer k:ans){
                    if(key.get(k) < key.get(min)){
                        min = k;
                    }
                }
                if(min != i){
                    ans.remove(min);
                    ans.add(i);
                }
            }

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
