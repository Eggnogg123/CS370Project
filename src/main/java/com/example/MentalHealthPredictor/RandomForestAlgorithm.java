package com.example.MentalHealthPredictor;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class RandomForestAlgorithm {
    private DataSetResponseParser parser;
    private DecisionTree[] forest;

    public RandomForestAlgorithm(DataSetResponseParser p){
        parser = p;

    }
    public void buildRandomForest(){
        forest = new DecisionTree[parser.getRows()];
        Random rand = new Random();
        int colNotUsed;
        int correct = 0,wrong = 0;
        for(int i =0;i<parser.getRows();i++){
            colNotUsed = rand.nextInt(parser.getCols() - 1) + 1;
            //BootStrapping
            String[][] dataset = bootStrapData(i, colNotUsed, parser);
            String[] colKey = parser.getSubCol(colNotUsed, parser.getCols());
            Map<Integer,Set<String>> choices = new HashMap<Integer,Set<String>>();
            for(int j = 1,k = 0;j < parser.getCols() - 1;j++,k++){
                if(k == colNotUsed)k++;
                Set<String> currChoices = new HashSet<>(parser.getChoices(parser.getQuestion(k)));
                choices.put(j,currChoices);
            }
            //choices.remove(colNotUsed);
            forest[i] = new DecisionTree(dataset,colKey,choices);
            CurrentSessionReponses sampleIn = new CurrentSessionReponses();
            sampleIn.setTest(parser,i);
            if(forest[i].makeDecision(sampleIn).equals(parser.getSample(i)[0]))correct++;
            else wrong++;
        }
        System.out.println(correct + " Correct " + wrong + " Wrong");
    }
    public int getNumTrees(){
        return forest.length;
    }
// All  the bootstrapper does is call the subtable method of the parser and return it
    public String[][] bootStrapData(int rowNotUsed,int colNotUsed,DataSetResponseParser parser){
         
        return parser.getSubTable(rowNotUsed, colNotUsed, parser.getRows(), parser.getCols());
    }
    public String makePrediction(CurrentSessionReponses current){
        int YesC = 0, NoC = 0;
        for(int i = 0;i< forest.length;i++){
            if(forest[i].makeDecision(current).equals("Yes"))YesC++;
            else NoC++;
        }
        System.out.println("Prediction Result Voters \n" +YesC + " Yes " + NoC + " No");
        if(YesC > NoC) return "YES";
        else return "NO";
    }
}


