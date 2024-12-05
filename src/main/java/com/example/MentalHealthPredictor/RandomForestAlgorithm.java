package com.example.MentalHealthPredictor;

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
            int ignoreCol = rand.nextInt(data.getCols() - 1) + 1;
            bootStrapData(i, ignoreCol);
            forest[i] = new DecisionTree(variables,table);
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

    }
    public String makePrediction(){
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
    public DecisionTree(String[] variableNames,String[][] ds){
        columnNames = variableNames;
        dataset = ds;
        makeTree();
    }
    private void makeTree(){
        //System.out.println(dataset[0][0]);
    }
    public String makeDecision(){
        return "PLACHOLDER";
    }
}

class DecisionTreeNode{
    
}