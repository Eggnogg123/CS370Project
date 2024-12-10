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
        }   
    }
    public String makePrediction(CurrentSessionReponses current){
        int YesC = 0, NoC = 0;
        for(int i = 0;i< forest.length;i++){
            if(forest[i].makeDecision(current).equals("Yes"))YesC++;
            else NoC++;
        }
        System.out.println(YesC + " " + NoC);
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
    DecisionTreeNode root;

    public DecisionTree(String[] variableNames,String[][] ds,DataSetResponseParser cs){
        columnNames = variableNames;
        dataset = ds;
        choiceSource = cs;
        Set<Integer> usedQ = new HashSet<Integer>();
        usedQ.add(1);
        usedQ.add(2);
        usedQ.add(3);
        usedQ.add(4);
        Set<Integer> usedR = new HashSet<Integer>();
        root = new DecisionTreeNode();
        makeTree(usedQ,usedR,root);
        //System.out.println(root.getQuestion());
        return;
    }//end of constructor

    private void makeTree(Set<Integer> usedQ,Set<Integer> usedR,DecisionTreeNode currNode){
        int use = nextTreeSplit(usedQ,usedR,currNode);
        
        if(use == 0 ){
            int noC = 0,yesC = 0;
            for(int i = 0;i<dataset.length;i++){
                if(usedR.contains(i))continue;
                if(dataset[i][0] == "Yes")yesC++;
                if(dataset[i][0] == "No")noC++;
            }
            if(yesC > noC) currNode.setPrediction("Yes");
            currNode.setPrediction("No");
            
           return;
        }
        
        if(use >= 100){
            use -= 100;
            return;
        }
        Set<Integer> usedQuestionChild = new HashSet<>(usedQ);
        usedQuestionChild.add(use);
        //if(usedQuestionChild.size() == 22)return;
        Set<String> choices = new HashSet<String>(choiceSource.getChoices(columnNames[use]));
        currNode.setQuestion(columnNames[use]);    
        //System.out.println(currNode.getQuestion());
        for(String i: choices){
            Set<Integer> usedRowChild = new HashSet<Integer>(usedR);
            if(usedQuestionChild.size() != 22)
            for(Integer j =0;j<dataset.length;j++){
                if(usedRowChild.contains(j))continue;
                if(!dataset[j][use].equals(i)){
                    usedRowChild.add(j);
                }
            }
            DecisionTreeNode curr = new DecisionTreeNode();
            currNode.addChild(i,curr);
            //System.out.println(usedQuestionChild.size());
            makeTree(usedQuestionChild,usedRowChild, curr);
        }
        return;
    }//end of makeTree()

    //normalize age

    public int nextTreeSplit(Set<Integer> usedQuestions, Set<Integer> usedValues,DecisionTreeNode currNode){//Find out the next question to use //ignore col 0 because target feature
        Map<Integer,Double> ratio = new HashMap<Integer,Double>(); 
        Map<Integer,Integer> numGood = new HashMap<Integer,Integer>();
        Map<Integer,Set<String>> whichGood = new HashMap<Integer,Set<String>>();
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
                //This was used to test for mistakes
                // if(choiceAmount.get(dataset[k][i] + "YES") == null){
                // System.out.println(columnNames[i]);
                // System.out.println(choices + "\n" + dataset[k][i]);
                // System.exit(0);
                // }
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
                
                if(maxChoiceRatio >= 1.0){
                    if(!numGood.containsKey(i))numGood.put(i,0);
                    numGood.put(i,numGood.get(i) + 1);
                    if(!whichGood.containsKey(i)){
                        Set<String> temp= new HashSet<String>();
                        temp.add(j);
                        whichGood.put(i,temp);
                    }
                    else whichGood.get(i).add(j);
                }
                //divide ratio my number
                maxChoiceRatio /= choices.size();

                //if you have 4 choices and one of your chocies has 100% ratio,
                //then that ratio is 100% because each choice contributes 25%. This gives priority to questions with more choices.
                ratio.put(i, maxChoiceRatio + ratio.get(i));  
                
            }
            bestColumn = i; 
        }
        boolean containsPerfect = false;
        for (Map.Entry<Integer,Double> entry : ratio.entrySet()){
            if(numGood.containsKey(entry.getKey()) || numGood.containsKey(bestColumn)){
                if(numGood.getOrDefault(entry.getKey(),0) > numGood.getOrDefault(bestColumn,0))
                bestColumn = entry.getKey();
                //System.out.println(numGood.get(bestColumn));
                containsPerfect = true;
                continue;
            }
            if(entry.getValue() > ratio.get(bestColumn))
                bestColumn = entry.getKey();
        }
        //System.out.println(usedValues.size());
        if(containsPerfect || (usedValues.size() > 1250 && bestColumn != 0)){
            Set<String> Finalchoices = new HashSet<String>(choiceSource.getChoices(columnNames[bestColumn]));
            currNode.setQuestion(columnNames[bestColumn]);
            usedQuestions.add(bestColumn);
            for(String i:Finalchoices){
                if(usedValues.size() > 1250 || whichGood.get(bestColumn).contains(i)){
    
                    DecisionTreeNode finalNode = new DecisionTreeNode();
                    int noC = 0,yesC = 0;
                    for(int k =0;k<dataset.length;k++){
                        if(usedValues.contains(k))continue;
                        if(dataset[k][bestColumn].equals(i)){
                            if(dataset[k][0] == "Yes")yesC++;
                            else noC++;
                            break;
                        }
                    }
                    currNode.addChild(i,finalNode);
                    if(yesC > noC){
                        finalNode.setPrediction("Yes");    
                    }
                    else finalNode.setPrediction("No");
                }
                 else{
                    
                    Set<Integer> usedRowChild = new HashSet<Integer>(usedValues);
                    if(usedQuestions.size() != 22)
                    for(Integer j =0;j<dataset.length;j++){
                        if(usedRowChild.contains(j))continue;
                        if(!dataset[j][bestColumn].equals(i)){
                            usedRowChild.add(j);
                        }
                    }
                    DecisionTreeNode curr = new DecisionTreeNode();
                    currNode.addChild(i,curr);
                //    curr.setPrediction("No");
                    makeTree(usedQuestions,usedRowChild, curr);
                }   
            }
            bestColumn += 100;
        }
        return bestColumn;
    }
    
    public String makeDecision(CurrentSessionReponses responses){
        DecisionTreeNode curr = root;
        while(!curr.isPrediction()){
            // System.out.println(curr.getQuestion());
            DecisionTreeNode next =curr.getChild(responses.getAnswer(curr.getQuestion()));
            
            // if(next == null){
            //     System.exit(0);
            //     System.out.println(curr.getQuestion() + responses.getAnswer(curr.getQuestion()));
                
            // }
            curr = next;
        }
        return curr.getQuestion();
    }
}

class DecisionTreeNode{
    private String question = "CATASTROPHIC ERROR";
    private Boolean prediction = false;
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
    public void setPrediction(String input){
        prediction = true;
        question = input;
        return;
    }
    public DecisionTreeNode getChild(String input){
        return children.get(input);
    }
    public boolean isPrediction(){
        return prediction;
    }
}