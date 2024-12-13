package com.example.MentalHealthPredictor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DecisionTree{
    DecisionTreeNode root;
    private String columnKey[];
    //Empty Constructor For testing
    public DecisionTree(){
    };
    public DecisionTree(String [][] dataset,String[] colKey,Map<Integer,Set<String>> choices){
        root = buildDecisionTree(dataset,choices);
        columnKey = colKey;
    }
    public String makeDecision(CurrentSessionReponses responses){
        DecisionTreeNode curr = root;
        
        while(!curr.isPrediction()){
            String question = columnKey[curr.getColumn()];
            String responseAns = responses.getAnswer(question);
            curr = curr.getChild(responseAns);
        }
        return curr.getChoice();
    }
//Method to create Tree;
    public DecisionTreeNode buildDecisionTree(String [][] dataset,Map<Integer,Set<String>> choices){
        String bestChoice = "";
        int bestColumn = 1;
        double BestInfoGain = 0.0, temp = 0.0;
        Set<String> toBeRemoved = new HashSet<String>();
//Go through every possible choice from the set of questions to find the one with the best
//Information Gain
        for(Map.Entry<Integer,Set<String>> Entry: choices.entrySet()){
            for(String choice:Entry.getValue()){
                temp = getInfoGain(dataset,Entry.getKey() , choice);
                if(temp > BestInfoGain){
                    BestInfoGain = temp;
                    bestChoice = choice;
                    bestColumn = Entry.getKey();
                }
                if(temp == -1.0)toBeRemoved.add(choice);   
            }
// This section of the loop removes choices from the Age index when they become unusable
//Such as >10 when all values are already greater than 100
            if(toBeRemoved.size()>0){
                for(String remove:toBeRemoved)
                    Entry.getValue().remove(remove);
            }
        }
        //Set the current node to a leaf
        if(BestInfoGain == 0.0)
            return isPredictionNode(dataset);
        int countChoice = 0;
        //Now Create two partitioned tables as input for each child
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
        DecisionTreeNode trueN = buildDecisionTree(trueArr, choicesN);
        DecisionTreeNode falseN = buildDecisionTree(falseArr, choicesN);
        return new DecisionTreeNode(bestColumn,bestChoice,trueN,falseN);
    }
    public String[] getColKey(){
        return columnKey;
    }
    public double getInfoGain(String[][] dataset, int column,String choice){
        double infoGain = 0.0, yesC= 0.0,noC = 0.0;
        for(int i =0;i<dataset.length;i++){
            if(dataset[i][0].equals("Yes"))yesC++;
            else noC++;
        }
        infoGain = ((yesC / (yesC + noC)) * (1 - (yesC / (yesC + noC)))) + ((noC / (yesC + noC)) * (1 - (noC / (yesC + noC))));
        if(infoGain == 0.0)return infoGain;
        //Calculate index of left and right tables
        double yesLeftC = 0.0,noLeftC = 0.0, yesRightC = 0.0,noRightC = 0.0;
        if(choice.charAt(0) == '>'){
            for(int i =0;i<dataset.length;i++){
                Long curr = Long.valueOf(dataset[i][column]);
                if(choice.charAt(0) == '>'){
                    Long choiceIntValue = Long.valueOf(choice.substring(2));
                    if(curr >= choiceIntValue){
                        if(dataset[i][0].equals("Yes"))yesLeftC++;
                        else noLeftC++;
                    }
                    else{
                        if(dataset[i][0].equals("Yes"))yesRightC++;
                        else noRightC++;
                    }
                }
            }
        }
        else for(int i =0;i<dataset.length;i++){
            if(dataset[i][column].equals(choice)){
                if(dataset[i][0].equals("Yes"))yesLeftC++;
                else noLeftC++;
            }
            else{
                if(dataset[i][0].equals("Yes"))yesRightC++;
                else noRightC++;
            }
        }
        if(yesLeftC + noLeftC == 0.0 || yesRightC + noRightC == 0.0){
            return -1.0;
        }
        double indexL = ((yesLeftC / (yesLeftC + noLeftC)) * (1 - (yesLeftC / (yesLeftC + noLeftC)))) + ((noLeftC / (yesLeftC + noLeftC)) * (1 - (noLeftC / (yesLeftC + noLeftC))));
        double indexR = ((yesRightC / (yesRightC + noRightC)) * (1 - (yesRightC / (yesRightC + noRightC)))) + ((noRightC / (yesRightC + noRightC)) * (1 - (noRightC / (yesRightC + noRightC))));
        //Avg out left and right index and subtract from infoGain
        infoGain -= (indexL * ((yesLeftC + noLeftC)/ dataset.length)) + (indexR * ((yesRightC + noRightC) / dataset.length));
        return infoGain;
    }
    private DecisionTreeNode isPredictionNode(String[][] dataset){
        int numYes = 0,numNo = 0;
        DecisionTreeNode node = new DecisionTreeNode();
        for(int i =0;i<dataset.length;i++){
            if(dataset[i][0].equals("Yes"))numYes++;
            else numNo++;
        }
        if(numYes > numNo)node.setPrediction("Yes");
        else node.setPrediction("No");
        return node;
    }

}

