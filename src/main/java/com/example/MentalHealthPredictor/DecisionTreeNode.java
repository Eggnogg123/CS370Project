package com.example.MentalHealthPredictor;

public class DecisionTreeNode{
    private int column = -1;
    String trueChoice ="ERROR";
    private Boolean prediction = false;
    DecisionTreeNode trueC,falseC;

    public DecisionTreeNode(int c,String choice,DecisionTreeNode TrueC,DecisionTreeNode FalseC){
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
        return;
    }
    public void setPrediction(String input){
        prediction = true;
        trueChoice = input;
        return;
    }
    public DecisionTreeNode getChild(String choice){
        if(choice.equals(trueChoice))
        return trueC;
        else return falseC;
    }
    public boolean isPrediction(){
        return prediction;
    }
}
