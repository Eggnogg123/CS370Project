package com.example.MentalHealthPredictor;

import java.util.HashMap;
import java.util.Map;

public class CurrentSessionResponses {      
    private Map<String,String> QuestionInputs;

    public CurrentSessionResponses(){
        QuestionInputs = new HashMap<String,String>();

    }

    public void recordInput(String Question,String Answer){
        QuestionInputs.put(Question,Answer);
    }



}

