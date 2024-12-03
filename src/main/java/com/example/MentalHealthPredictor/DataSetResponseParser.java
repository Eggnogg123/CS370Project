package com.example.MentalHealthPredictor;

import org.apache.commons.csv.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.io.IOException;

import java.nio.charset.*;




public class DataSetResponseParser{
    private SurveyResponse data;
    private int numResponses,predictedVarCol = -1;
    private String parsedData[][],parsedColumnName[],parsedColumnQuestions[];

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
        buildQuestions(parsedColumnName.length - 1);
        for(int i =0;i<parsedColumnQuestions.length;i++)System.out.println(parsedColumnQuestions[i]);
        
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
// Get a subtable of the parsed Dataset, used for bootstrap
    public String[][] getSubTable(int rowNotUsed,int featureSelectedOut,int rows,int cols){
        String[][] table = new String[rows][cols - 1];
        Random rand = new Random();
        int responseR = rowNotUsed;
        for(int i =0;i<rows;i++){
            responseR = rand.nextInt(rows);
            while(responseR == rowNotUsed)
                responseR = rand.nextInt(rows);
            for(int j = 0,x = 0;j<table[0].length;j++,x++){
                if(x == featureSelectedOut ){
                    x++;
                }
                table[i][j] = parsedData[responseR][x];
            }
        }
        return table;
    }
    public String[] getSubCol(int featureSelectedOut, int cols){
        String[] table = new String[cols - 1];
        for(int i =0,j =0;i<cols - 1;i++,j++){
            if(j == featureSelectedOut){
                j++;
            } 
            table[i] = parsedColumnName[j];
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
        parsedData = new String[numResponses][data.getNumCol() - 3];
        parsedColumnName = new String[data.getNumCol() - 3];
        for(int i =0,x = 0;i < parsedColumnName.length;i++,x++){
            String s = data.getColumnName(x);
            while(s.equals("Timestamp") || s.equals("treatment")|| s.equals("comments")){
                x++;
                s = data.getColumnName(x);
            }
            parsedColumnName[i] = data.getColumnName(x);
        }
        for(int i =0,x = 0;i<data.getNumRows();i++){
            if(data.getRecord(i,predictedVarCol).equals(""))continue;
            for(int j =0,y = 0;j< data.getNumCol();j++){
                String s = data.getColumnName(j);
                if(s.equals("Timestamp") || s.equals("treatment")|| s.equals("comments")){
                    continue;
                }
                if(s.equals("Gender")){
                    parsedData[x][y] = sortGender(data.getRecord(i, j));    
                }
                else parsedData[x][y] = data.getRecord(i, j);
                y++;
            }
            x++;
        }
    }
//CHANGES TO THE QUESTIONS GO HERE
    private void buildQuestions(int columns){
        parsedColumnQuestions = new String[columns];
//IMPORTANT- IN THE EVENT THAT SURVEY COLUMN ORDER IS CHANGED THIS WILL OUTPUT AN ERROR
        for(int i = 0,j = 1;i< parsedColumnQuestions.length;i++,j++){
            switch (parsedColumnName[j]) {
                case "Age":
                parsedColumnQuestions[i] = "What is your Age?";
                break;
                case "Gender":
                parsedColumnQuestions[i] = "What is your Gender?";
                break;
                case "Country":
                parsedColumnQuestions[i] = "What is your Country of Residence?";
                break;
                case "state":
                parsedColumnQuestions[i] = "If you live in the US, what is your State of residence?";
                break;
                case "self_employed":
                parsedColumnQuestions[i] = "Are you currently self-employed or not?";
                break;
                case "family_history":
                parsedColumnQuestions[i] = "Do you have a family history of mental illness?";
                break;
                case "no_employees":
                parsedColumnQuestions[i] = "How many employees does your company or organization have?";
                break;
                case "remote_work":
                parsedColumnQuestions[i] = "Do you work remotely (outside of an office) at least 50% of the time?";
                break;
                case "tech_company":
                parsedColumnQuestions[i] = "Is your employer primarily a tech company/organization?";
                break;
                case "benefits":
                parsedColumnQuestions[i] = "Does your employer provide mental health benefits?";
                break;
                case "care_options":
                parsedColumnQuestions[i] = "Do you know the options for mental health care your employer provides?";
                break;
                case "wellness_program":
                parsedColumnQuestions[i] = "Has your employer ever discussed mental health as part of an employee wellness program?";
                break;
                case "seek_help":
                parsedColumnQuestions[i] = "Does your employer provide resources to learn more about mental health issues and how to seek help?";
                break;
                case "anonymity":
                parsedColumnQuestions[i] = "Is your anonymity protected if you choose to take advantage of mental health or substance abuse treatment resources?";
                break;
                case "leave":
                parsedColumnQuestions[i] = "How easy is it for you to take medical leave for a mental health condition?";
                break;
                case "mental_health_consequence":
                parsedColumnQuestions[i] = "Do you think that discussing a mental health issue with your employer would have negative consequences?";
                break;
                case "phys_health_consequence":
                parsedColumnQuestions[i] = "Do you think that discussing a physical health issue with your employer would have negative consequences?";
                break;
                case "coworkers":
                parsedColumnQuestions[i] = "Would you be willing to discuss a mental health issue with your coworkers?";
                break;
                case "supervisor":
                parsedColumnQuestions[i] = "Would you be willing to discuss a mental health issue with your direct supervisor(s)?";
                break;
                case "mental_health_interview":
                parsedColumnQuestions[i] = "Would you bring up a mental health issue with a potential employer in an interview?";
                break;
                case "phys_health_interview":
                parsedColumnQuestions[i] = "Would you bring up a physical health issue with a potential employer in an interview?";
                break;
                case "mental_vs_physical":
                parsedColumnQuestions[i] = "Do you feel that your employer takes mental health as seriously as physical health?";
                break;
                case "obs_consequence":
                parsedColumnQuestions[i] = "Have you heard of or observed negative consequences for coworkers with mental health conditions in your workplace?";
                break;
                
                default:
                System.out.println("ERROR MISSING COLUMN: " + parsedColumnName[j]);
                System.exit(0);
            }
        }
    }
//Helps with parsing through the Gender Column of the survey
    private String sortGender(String in){
        switch (in) {
            case "A little about you":
                return "";
            case "Nah":
                return "";
            case "p":
                return "";
            case "Agender":
                return "Non-Binary";
            case "Neuter":
                return "Non-Binary";
            case "non-binary":
                return "Non-Binary";
            case "All":
                return "";
            case "Androgyne":
                return "";
            case "Enby":
                return "Non-Binary";
            case "fluid":
                return "";
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



class SurveyResponse{
    List<String> columnHeaders;
    String surveyResponses[][], filename;
    public SurveyResponse(String fname) throws IOException{
        String documentsPath = System.getProperty("user.dir")  ;
        filename = fname;

        Path documentsDirectory = Paths.get(documentsPath,"src/main/resources/static");
        Path csvPath = documentsDirectory.resolve(filename);

	    CSVParser csvParser = CSVParser.parse(csvPath,Charset.defaultCharset(),CSVFormat.EXCEL.withHeader());
        columnHeaders = csvParser.getHeaderNames();
        

        List<CSVRecord> records = csvParser.getRecords();
        surveyResponses = new String[records.size()][records.get(0).size()];
        for(int i =0;i<records.size();i++){
            CSVRecord curr = records.get(i);
            for(int j =0;j<records.get(i).size();j++){
                surveyResponses[i][j] = curr.get(j);
            }
        }
        
    }

    String getRecord(int row,int col){
        if(row >= surveyResponses.length || col >= surveyResponses[0].length
           || row < 0 || col < 0)return "INVALID RECORD";
        return surveyResponses[row][col];
    }
    String getColumnName(int col){
        if(col < 0 || col > columnHeaders.size())return "INVALID VARIABLE COLUMN";
        return columnHeaders.get(col);
    }
    int getNumRows(){
        return surveyResponses.length;
    }
    int getNumCol(){
        return surveyResponses[0].length;
    }
}