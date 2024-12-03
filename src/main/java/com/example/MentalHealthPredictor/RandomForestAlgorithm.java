package com.example.MentalHealthPredictor;

import java.util.Random;

public class RandomForestAlgorithm {
    DataSetResponseParser data;
    String[][] table;
    String[] variables;
    DecisionTree[] forest;
    public RandomForestAlgorithm(DataSetResponseParser input){
        data = input;
    }
    public void buildRandomForest(){
        Random rand = new Random();
        for(int i = 0;i< data.getRows();i++){
            int col = rand.nextInt(data.getCols() - 1) + 1;
            bootStrapData(i, col);
            
            //This CODE below prints out all the bootstrapped Datasets
            // for(int j =0;j<table.length;j++){
            //     for(int x =0;x<table[0].length;x++){
            //         System.out.print(table[j][x] + "  ");
            //     }
            //     System.out.println();
            // }
            // for(int j =0;j<variables.length;j++){
            //     System.out.print(variables[j] + "  ");
            // }
            // System.out.println(variables.length);
            // System.out.println(data.getParsedColName(col));
        }   

    }
    private void bootStrapData(int rowNotUsed,int colNotUsed){
        table = data.getSubTable(rowNotUsed, colNotUsed, data.getRows(), data.getCols());
        variables = data.getSubCol(colNotUsed,data.getCols());
        return;
    }
}

class DecisionTree{

}

class DecisionTreeNode{
    
}