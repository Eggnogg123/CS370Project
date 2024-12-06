package com.example.MentalHealthPredictor;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class RandomForestAlgorithm {
    DataSetResponseParser data;
    String[][] table;
    String[] variables;
    DecisionTree[] forest;
    int numTrees;
//Constructor
    public RandomForestAlgorithm(DataSetResponseParser input){
        data = input;
    }
//BUILD the random forest by bootstrapping data.getRows times, testing for accuracy can also be done here
    public void buildRandomForest(){
        Random rand = new Random();
        forest = new DecisionTree[data.getRows()];
        numTrees = data.getRows();
        for(int i = 0;i<numTrees ;i++){
            int ignoreCol = rand.nextInt(data.getCols() - 1);
            bootStrapData(i, ignoreCol);
            forest[i] = new DecisionTree(variables,table,data);
            //This CODE below prints out all the bootstrapped Datasets
            // for(int j =0;j<1;j++){
            //     for(int x =0;x<table[0].length;x++){
            //         System.out.print(table[j][x] + "  ");
            //     }
            //     System.out.println();
            // }
            // for(int j =0;j<variables.length;j++){
            //     System.out.print(variables[j] + "  ");
            // }
            // System.out.println(variables.length);
            // System.out.println(data.getParsedColName(ignoreCol));
        }   
        for(int i = 0;i<table[0].length;i++){
            System.out.println(variables[i]);
            System.out.println(table[0][i]);
        }
    }
    public String makePrediction(CurrentSessionReponses current){
        int YesC = 0, NoC = 0;
        for(int i = 0;i< forest.length;i++){
            if(forest[i].makeDecision().equals("PLACHOLDER"))YesC++;
            else NoC++;
        }
        if(YesC > NoC) return "YES";
        else return "NO";
    }
//This bootstrap method uses the dataSetResponseParser to create a subtable of parsed data, as well as a subtable
// of column names(variables)
    private void bootStrapData(int rowNotUsed,int colNotUsed){
        table = data.getSubTable(rowNotUsed, colNotUsed, data.getRows(), data.getCols());
        variables = data.getSubCol(colNotUsed,data.getCols());
        return;
    }
    
}

class DecisionTree{
    String[] columnNames;
    String[][] dataset;
    DataSetResponseParser choiceSource;
    public DecisionTree(String[] variableNames,String[][] ds,DataSetResponseParser cs){
        columnNames = variableNames;
        dataset = ds;
        choiceSource = cs;
        Set<Integer> used = new HashSet<Integer>();
        DecisionTreeNode root = new DecisionTreeNode();
        makeTree(used,root);
    }
    public String makeDecision(){
        return "PLACHOLDER";
    }
    private void makeTree(Set<Integer> used,DecisionTreeNode currNode){
        // int use = nextTreeSplit(used,currNode);
        // if(used.size() == 22)
        // currNode.setQuestion(columnNames[use]);
        // Set<String> choices = new HashSet<String>(choiceSource.getChoices(columnNames[use]));
        // for(String i: choices){
        //     DecisionTreeNode curr = new DecisionTreeNode();
        //     curr.addChild(i,curr);
        //     used.add(use);
        //     makeTree(used, curr);
        // }
        return;
    }
}

class DecisionTreeNode{
    private String question;
    private Map<String,DecisionTreeNode> children;

    public DecisionTreeNode(){
        children = new HashMap<String,DecisionTreeNode>();
    }
    public void setQuestion(String input){
        question = input;
        return;
    }
    public String getQuestion(){ 
        return question;
    }
    public void addChild(String ans,DecisionTreeNode input){
        children.put(ans, input);
        return;
    }
}