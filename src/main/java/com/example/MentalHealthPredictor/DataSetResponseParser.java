package com.example.MentalHealthPredictor;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;




public class DataSetResponseParser{
    private SurveyResponse data;
    private int numResponses,predictedVarCol = -1;
    private String parsedData[][], //Holds the parsed data from the csv file
                   parsedColumnName[],
                   parsedColumnQuestions[];
    private Map<String,Set<String>> parsedQuestionChoices;

//Constructor
    public DataSetResponseParser(String filename) throws IOException{
        data = new SurveyResponse(filename);
        numResponses = data.getNumRows();
        for(int i =0;i<data.getNumCol();i++){
            if(data.getColumnName(i).equals("Mental Ilnness")){
                predictedVarCol = i;
                break;
            }
        }
        parseResponse();
        buildQuestionsChoices(parsedColumnName.length - 1);          
    }

    public String[][] getParsedData(){ //Returns the 2d array of the parsed data.
        return parsedData;
    }

    public String[] getSample(int row){
        return parsedData[row];
    }

    public String getvalueString(int row, int col){ //added this 12.9.24 William
        return parsedData[row][col];
    }

// Get Number of Rows or Columns of parsed Dataset
    public int getRows(){
        return numResponses;
    }
    public int getCols(){
        return parsedData[0].length;
    }
    public String getParsedColName(int in){
        return parsedColumnName[in];
    }
// Get a subtable of the parsed Dataset, used for bootstrap,
//column  0 of the subtable is the MentalIllness Column for the Yes or No Question
    public String[][] getSubTable(int rowNotUsed,int featureSelectedOut,int rows,int cols){
        String[][] table = new String[rows][cols - 1];
        Random rand = new Random();
        int responseR = rowNotUsed;
        for(int i =0;i<rows;i++){
            responseR = rand.nextInt(rows);
            while(responseR == rowNotUsed)
                responseR = rand.nextInt(rows);
            for(int j = 0,x = 0;j<table[0].length;j++,x++){
                if(x == featureSelectedOut + 1){
                    x++;
                }
                table[i][j] = parsedData[responseR][x];
            }
        }
        return table;
    }
//Get a array of Variable Names/Questions for bootstrapped data
//Column 0 is the Mentall Illness Column, IF WE CHANGE THE ORDER OF THE SURVEY FILE VARIABLES THIS MAY BREAK
    public String[] getSubCol(int featureSelectedOut, int cols){
        String[] table = new String[cols - 1];
        table[0] = "Mental Illness";
        for(int i =1,j = 0 ;i<cols - 1;i++,j++){
            if(j == featureSelectedOut){
                j++;
            } 
            table[i] = parsedColumnQuestions[j];
        }
        return table;
    }
//Parse the survey
    private void parseResponse(){
        //Check for number of usable responses in csv. Responses that have a answer for the predicted variable
        for(int i = 0;i<data.getNumRows();i++){
            if(data.getRecord(i, predictedVarCol).equals(""))numResponses--;
        }
        //The parsed data array is smaller than the one in SurveyResponse, since 3 columns of data not
        //used in the algorithm get removed, invalid responses are also skipped over
        parsedData = new String[numResponses][data.getNumCol() - 4];
        parsedColumnName = new String[data.getNumCol() - 4];
        for(int i =0,x = 0;i < parsedColumnName.length;i++,x++){
            String s = data.getColumnName(x);
            while(s.equals("Timestamp") || s.equals("treatment")|| s.equals("comments") || s.equals("Country")){
                x++;
                s = data.getColumnName(x);
            }
            parsedColumnName[i] = data.getColumnName(x);
        }
        for(int i =0,x = 0;i<data.getNumRows();i++){
            if(data.getRecord(i,predictedVarCol).equals(""))continue;
            for(int j =0,y = 0;j< data.getNumCol();j++){
                String s = data.getColumnName(j);
                if(s.equals("Timestamp") || s.equals("treatment")|| s.equals("comments") ||s.equals("Country")){
                    continue;
                }
                if(s.equals("Gender")){
                    parsedData[x][y] = sortGender(data.getRecord(i, j));    
                }
                else if(s.equals("Age")){
                    long index = Long.valueOf(data.getRecord(i, j)) % 100;
                    parsedData[x][y] = Long.toString(index / 10);    
                }
                else if(s.equals("Mental Ilnness")){
                    parsedData[x][y] = sortMentalIllness(data.getRecord(i, j));
                }
                else parsedData[x][y] = data.getRecord(i, j);
                y++;
            }
            x++;
        }
    }
    public String getQuestion(int num){
        if( num < 0 || num > 21){
            System.out.println("INVALID QUESTION QUERY");
            System.exit(0);
        }
        return parsedColumnQuestions[num];
    }
    public Set<String> getChoices(String question){
        return parsedQuestionChoices.get(question);
    }
//CHANGES TO THE QUESTIONS GO HERE and ANSWERS GO HERE
    private void buildQuestionsChoices(int columns){
        parsedColumnQuestions = new String[columns];
        parsedQuestionChoices = new HashMap<>();
//IMPORTANT- IN THE EVENT THAT SURVEY COLUMN ORDER IS CHANGED THIS WILL OUTPUT AN ERROR
        for(int i = 0,j = 1;i< parsedColumnQuestions.length;i++,j++){
            Set<String> temp = new HashSet<>();
            switch (parsedColumnName[j]) {
                case "Age":
                parsedColumnQuestions[i] = "What is your Age?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "Gender":
                parsedColumnQuestions[i] = "What is your Gender?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "state":
                parsedColumnQuestions[i] = "If you live in one of these US States, what is your State of residence? Otherwise put NA.";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "self_employed":
                parsedColumnQuestions[i] = "Are you currently self-employed or not?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "family_history":
                parsedColumnQuestions[i] = "Do you have a family history of mental illness?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                parsedQuestionChoices.get(parsedColumnQuestions[i]).add("Don't Know");
                break;
                case "no_employees":
                parsedColumnQuestions[i] = "How many employees does your company or organization have?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "remote_work":
                parsedColumnQuestions[i] = "Do you work remotely (outside of an office) at least 50% of the time?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "tech_company":
                parsedColumnQuestions[i] = "Is your employer primarily a tech company/organization?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                parsedQuestionChoices.get(parsedColumnQuestions[i]).add("Don't Know");
                break;
                case "benefits":
                parsedColumnQuestions[i] = "Does your employer provide mental health benefits?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "care_options":
                parsedColumnQuestions[i] = "Do you know the options for mental health care your employer provides?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "wellness_program":
                parsedColumnQuestions[i] = "Has your employer ever discussed mental health as part of an employee wellness program?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "seek_help":
                parsedColumnQuestions[i] = "Does your employer provide resources to learn more about mental health issues and how to seek help?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "anonymity":
                parsedColumnQuestions[i] = "Is your anonymity protected if you choose to take advantage of mental health or substance abuse treatment resources?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "leave":
                parsedColumnQuestions[i] = "How easy is it for you to take medical leave for a mental health condition?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "mental_health_consequence":
                parsedColumnQuestions[i] = "Do you think that discussing a mental health issue with your employer would have negative consequences?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "phys_health_consequence":
                parsedColumnQuestions[i] = "Do you think that discussing a physical health issue with your employer would have negative consequences?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "coworkers":
                parsedColumnQuestions[i] = "Would you be willing to discuss a mental health issue with your coworkers?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "supervisor":
                parsedColumnQuestions[i] = "Would you be willing to discuss a mental health issue with your direct supervisor(s)?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "mental_health_interview":
                parsedColumnQuestions[i] = "Would you bring up a mental health issue with a potential employer in an interview?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "phys_health_interview":
                parsedColumnQuestions[i] = "Would you bring up a physical health issue with a potential employer in an interview?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "mental_vs_physical":
                parsedColumnQuestions[i] = "Do you feel that your employer takes mental health as seriously as physical health?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                case "obs_consequence":
                parsedColumnQuestions[i] = "Have you heard of or observed negative consequences for coworkers with mental health conditions in your workplace?";
                parsedQuestionChoices.put(parsedColumnQuestions[i],temp);
                for(int x =0;x< getRows();x++){
                    if(parsedQuestionChoices.get(parsedColumnQuestions[i]).contains(parsedData[x][j]))continue;
                    parsedQuestionChoices.get(parsedColumnQuestions[i]).add(parsedData[x][j]);
                }
                break;
                
                default:
                System.out.println("ERROR MISSING COLUMN: " + parsedColumnName[j]);
                System.exit(0);
            }
        }
    }
//Helps with parsing through the Gender Column of the survey
    private String sortMentalIllness(String in){
        switch (in) {
            case "NA":
                return "No";
            case "Sometimes":
                return "Yes";
            case "Often":
                return "Yes";
            case "Rarely":
                return "Yes";
            case "Never":
                return "Yes";
            default:
                System.out.println("Error in Determining Mentall Illness for all Survey Responses");
                System.exit(0);
        }
        return "NULL";
    }
    private String sortGender(String in){
        switch (in) {
            case "A little about you":
                return "Other";
            case "Nah":
                return "Other";
            case "p":
                return "Other";
            case "Agender":
                return "Non-Binary";
            case "Neuter":
                return "Non-Binary";
            case "non-binary":
                return "Non-Binary";
            case "All":
                return "Other";
            case "Androgyne":
                return "Other";
            case "Enby":
                return "Non-Binary";
            case "fluid":
                return "Other";
            case "Genderqueer":
                return "Non-Binary";
            case "queer":
                return "Non-Binary";
            case "queer/she/they":
                return "Non-Binary";
            case "f":
                return "Female";
            case "female":
                return "Female";
            case "Femake":
                return "Female";
            case "Female": 
                return "Female";
            case "Female ": 
                return "Female";
            case "F":
                return "Female";
            case "femail":
                return "Female";
            case "Cis Female":
                return "Female";
            case "cis-female/femme":
                return "Female";
            case "Female (cis)":
                return "Female";
            case "Female (trans)":
                return "Female";
            case "Trans woman":
                return "Female";
            case "Trans-female":
                return "Female";
            case "Woman":
                return "Female";
            case "woman":
                return "Female";
            case "Guy (-ish) ^_^":
                return "Male";
            case "Cis Male":
                return "Male";
            case "cis male":
                return "Male";
            case "Cis Man":
                return "Male";
            case "M":
                return "Male";
            case "m":
                return "Male";
            case "Mail":
                return "Male";
            case "maile":
                return "Male";
            case "make":
                return "Male";
            case "Make":
                return "Male";
            case "Mal":
                return "Male";
            case "male":
                return "Male";
            case "Male":
                return "Male";
            case "Male ":
                return "Male";
            case "Male (CIS)":
                return "Male";
            case "male leaning androgynous":
                return "Male";
            case "Male-ish":
                return "Male";
            case "Malr":
                return "Male";
            case "Man":
                return "Male";
            case "msle":
                return "Male"; 
            case "ostensibly male, unsure what that really means":
                return "Male";
            case "something kinda male?":
                return "Male";

            default:
                System.out.println("ERROR MISSING SORT GENDER CASE: " + in);
                System.exit(0);
        }
        return "NULL";
    }
    
}



