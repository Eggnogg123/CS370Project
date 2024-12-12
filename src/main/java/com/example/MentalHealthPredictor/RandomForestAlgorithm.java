package com.example.MentalHealthPredictor;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class RandomForestAlgorithm {
    DataSetResponseParser parser;
    Map<Integer,Set<String>> allChoices;
    RealDecisionTree[] forest;

    public RandomForestAlgorithm(DataSetResponseParser p){
        allChoices = new HashMap<Integer,Set<String>>();
        parser = p;

    }
    public void buildRandomForest(){
        forest = new RealDecisionTree[parser.getRows()];
        Random rand = new Random();
        int colNotUsed;
        int correct = 0,wrong = 0;
        for(int i =0;i<parser.getRows();i++){
            colNotUsed = rand.nextInt(parser.getCols() - 1) + 1;
            String[][] dataset = bootStrapData(i, colNotUsed, parser);
            String[] colKey = parser.getSubCol(colNotUsed, parser.getCols());
            Map<Integer,Set<String>> choices = new HashMap<Integer,Set<String>>(allChoices);
            for(int j = 1,k = 0;j < parser.getCols() - 1;j++,k++){
                if(k == colNotUsed)k++;
                Set<String> currChoices = new HashSet<>(parser.getChoices(parser.getQuestion(k)));
                allChoices.put(j,currChoices);
            }
            choices.remove(colNotUsed);
            forest[i] = new RealDecisionTree(dataset,colKey,choices);
            CurrentSessionReponses sampleIn = new CurrentSessionReponses();
            sampleIn.setTest(parser,i);
            if(forest[i].makeDecision(sampleIn).equals(parser.getSample(i)[0]))correct++;
            else wrong++;
        }
        System.out.println(correct + " Correct " + wrong + " Wrong");
    }
    private String[][] bootStrapData(int rowNotUsed,int colNotUsed,DataSetResponseParser parser){
         
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

class RealDecisionTree{
    RealDecisionTreeNode root;
    private String columnKey[];
    public RealDecisionTree(String [][] dataset,String[] colKey,Map<Integer,Set<String>> choices){
        root = buildDecisionTree(dataset,choices);
        columnKey = colKey;
        //for(int i =0;i<columnKey.length;i++)System.out.println(columnKey[i]);
    }
    public String makeDecision(CurrentSessionReponses responses){
        RealDecisionTreeNode curr = root;
        
        while(!curr.isPrediction()){
            String question = columnKey[curr.getColumn()];
            String responseAns = responses.getAnswer(question);
            curr = curr.getChild(responseAns);
        }
        return curr.getChoice();
    }
    private RealDecisionTreeNode buildDecisionTree(String [][] dataset,Map<Integer,Set<String>> choices){
        String bestChoice = "";
        int bestColumn = 1;
        double BestInfoGain = 0.0;
        for(Map.Entry<Integer,Set<String>> Entry: choices.entrySet()){
            for(String choice:Entry.getValue()){
                double temp = getInfoGain(dataset,Entry.getKey() , choice);
                if(temp > BestInfoGain){
                    BestInfoGain = temp;
                    bestChoice = choice;
                    bestColumn = Entry.getKey();
                }
            }
        }
        //System.out.println(dataset[0].length + "HERE");
        //Set the current node to a leaf
        if(BestInfoGain == 0.0)
            return isPredictionNode(dataset);
        int countChoice = 0;
        //Now Create two partitioned tables for each child
        for(int i =0;i < dataset.length;i++){
            if(dataset[i][bestColumn].equals(bestChoice))countChoice++;
        }
        String trueArr[][] = new String[countChoice][dataset[0].length];
        String falseArr[][] = new String[dataset.length - countChoice][dataset[0].length];
        for(int i =0,trueC = 0,falseC = 0;i<dataset.length;i++){
            if(dataset[i][bestColumn].equals(bestChoice)){
                trueArr[trueC] = dataset[i];
                trueC++;
            }
            else{
                falseArr[falseC] = dataset[i];
                falseC++;
            }
        }
        Map<Integer,Set<String>> choicesN = new HashMap<Integer,Set<String>>(choices);
        choicesN.get(bestColumn).remove(bestChoice);
        //Create two Partitioned choices
        RealDecisionTreeNode trueN = buildDecisionTree(trueArr, choicesN);
        RealDecisionTreeNode falseN = buildDecisionTree(falseArr, choicesN);
        return new RealDecisionTreeNode(bestColumn,bestChoice,trueN,falseN);
    }
    public String[] getColKey(){
        return columnKey;
    }
    private double getInfoGain(String[][] dataset, int column,String choice){
        double infoGain = 0.0, yesC= 0.0,noC = 0.0;
        for(int i =0;i<dataset.length;i++){
            if(dataset[i][0].equals("Yes"))yesC++;
            else noC++;
        }
        infoGain = ((yesC / (yesC + noC)) * (1 - (yesC / (yesC + noC)))) + ((noC / (yesC + noC)) * (1 - (noC / (yesC + noC))));
        if(infoGain == 0.0)return infoGain;
        //Calculate index of left and right tables
        double yesLeftC = 0.0,noLeftC = 0.0, yesRightC = 0.0,noRightC = 0.0;
        for(int i =0;i<dataset.length;i++){
            if(dataset[i][column].equals(choice)){
                if(dataset[i][0].equals("Yes"))yesLeftC++;
                else noLeftC++;
            }
            else{
                if(dataset[i][0].equals("Yes"))yesRightC++;
                else noRightC++;
            }
        }
        double indexL = ((yesLeftC / (yesLeftC + noLeftC)) * (1 - (yesLeftC / (yesLeftC + noLeftC)))) + ((noLeftC / (yesLeftC + noLeftC)) * (1 - (noLeftC / (yesLeftC + noLeftC))));
        double indexR = ((yesRightC / (yesRightC + noRightC)) * (1 - (yesRightC / (yesRightC + noRightC)))) + ((noRightC / (yesRightC + noRightC)) * (1 - (noRightC / (yesRightC + noRightC))));
        //Avg out left and right index and subtract from infoGain
        infoGain -= (indexL * ((yesLeftC + noLeftC)/ dataset.length)) + (indexR * ((yesRightC + noRightC) / dataset.length));
        return infoGain;
    }
    private RealDecisionTreeNode isPredictionNode(String[][] dataset){
        int numYes = 0,numNo = 0;
        RealDecisionTreeNode node = new RealDecisionTreeNode();
        for(int i =0;i<dataset.length;i++){
            if(dataset[i][0].equals("Yes"))numYes++;
            else numNo++;
        }
        if(numYes > numNo)node.setPrediction("Yes");
        else node.setPrediction("No");
        return node;
    }

}
class RealDecisionTreeNode{
    private int column = -1;
    String trueChoice ="ERROR";
    private Boolean prediction = false;
    RealDecisionTreeNode trueC,falseC;

    public RealDecisionTreeNode(int c,String choice,RealDecisionTreeNode TrueC,RealDecisionTreeNode FalseC){
        trueC = TrueC;
        falseC = FalseC;
        column = c;
        trueChoice = choice;
    }
    public RealDecisionTreeNode(){
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
    public RealDecisionTreeNode getChild(String choice){
        if(choice.equals(trueChoice))
        return trueC;
        else return falseC;
    }
    public boolean isPrediction(){
        return prediction;
    }
}