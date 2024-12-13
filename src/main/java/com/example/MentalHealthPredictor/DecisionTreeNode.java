package com.example.MentalHealthPredictor;

public class DecisionTreeNode{
    private int column = -1;
    String trueChoice ="ERROR";
    private Boolean prediction = false;
    private Boolean number = false;
    Long num = (long) 0;
    DecisionTreeNode trueC,falseC;

    public DecisionTreeNode(int c,String choice,DecisionTreeNode TrueC,DecisionTreeNode FalseC){
        if(choice.charAt(0) == '>'){
            number = true;
            num = Long.valueOf(choice.substring(2));
        }
        trueC = TrueC;
        falseC = FalseC;
        column = c;
        trueChoice = choice;
    }
    public DecisionTreeNode(){
    }
    public int getColumn(){
        return column;
    }
    public String getChoice(){
        return trueChoice;
    }
    public void setQuestion(int c,String choice){
        column = c;
        trueChoice = choice;
        if(trueChoice.charAt(0) == '>'){
            number = true;
            num = Long.valueOf(trueChoice.substring(2));
        }
        return;
    }
    public void setPrediction(String input){
        prediction = true;
        trueChoice = input;
        return;
    }
    public DecisionTreeNode getChild(String choice){
        if(number){
            if(Long.valueOf(choice) >= num)return trueC;
            return falseC;
        }
        else if(choice.equals(trueChoice))
        return trueC;
        else return falseC;
    }
    public boolean isPrediction(){
        return prediction;
    }
}
