package com.example.MentalHealthPredictor;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
    String[][] dataset; //dataset that his tree has to use col1 should be age col
    DataSetResponseParser choiceSource; 

    public DecisionTree(String[] variableNames,String[][] ds,DataSetResponseParser cs){
        columnNames = variableNames;
        dataset = ds;
        choiceSource = cs;
        Set<Integer> used = new HashSet<Integer>();
        DecisionTreeNode root = new DecisionTreeNode();
        makeTree(used,root);
    }//end of constructor

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
    }//end of makeTree()

    //normalize age
    public int normalize(int row, int col){
        int stringtoInt = Integer.parseInt(dataset[row][col]);
        int normalizedAge = stringtoInt % 100;
        return normalizedAge;
    }

    public int array(Set<Integer> usedQuestions, Set<Integer> usedValues){//Find out the next question to use //ignore col 0 because target feature
        Map<Integer,Double> ratio = new HashMap<Integer,Double>(); 
        ratio.put(0,0.0);
        int bestColumn = 0;

        for(int i=1; i<columnNames.length; i++){ //if index is in usedQuestions then skip the question in the set
            if(usedQuestions.contains(i)){
                continue; //skip it
            }
            ratio.put(i,0.0);
            Set<String> choices = choiceSource.getChoices(columnNames[i]);
            Map<String,Double> choiceAmount = new HashMap<String,Double>(); 
            for(String j: choices){
                choiceAmount.put(j+"YES", 0.0); //In our hashmap we have every single choice for the current question. Will count how many of the responses pick that choice
                choiceAmount.put(j+"NO", 0.0);
            }
            for(int k=0;k<dataset.length; k++){
                if(usedValues.contains(k)){
                    continue;
                }
                if(dataset[k][0].equals("Yes")){ //Tells you mental illness of that row if its a yes increment choiceAmount of that choice + Yes
                    choiceAmount.put(dataset[k][i] + "YES", choiceAmount.get(dataset[k][i] + "YES") + 1);
                }
                if(dataset[k][0].equals("No")){ //Tells you mental illness of that row if its a yes increment choiceAmount of that choice + Yes
                    choiceAmount.put(dataset[k][i] + "NO", choiceAmount.get(dataset[k][i] + "NO") + 1);
                }
            }
            
            for(String j: choices){ // for each string in choices //Tells us the yes' and nos'
                Double maxChoiceRatio= 0.0;
                Double yesCount = choiceAmount.get(j + "YES");
                Double noCount = choiceAmount.get(j + "NO");

                if(yesCount / (yesCount + noCount) > maxChoiceRatio) //gets max ratio for this choice
                    maxChoiceRatio = yesCount / (yesCount + noCount);

                if(noCount / (yesCount + noCount) > maxChoiceRatio)
                    maxChoiceRatio = noCount / (yesCount + noCount);
                
                //divide ratio my number
                maxChoiceRatio /= choices.size();

                //if you have 4 choices and one of your chocies has 100% ratio,
                //then that ratio is 100% because each choice contributes 25%. This gives priority to questions with more choices.
                ratio.put(i, maxChoiceRatio + ratio.get(i));  
                
            } 
        }
        for (Map.Entry<Integer,Double> entry : ratio.entrySet()){
            if(entry.getValue() > ratio.get(bestColumn))
                bestColumn = entry.getKey();
        } 
        return bestColumn;
    }
    
    public String makeDecision(){
        return "PLACHOLDER";
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