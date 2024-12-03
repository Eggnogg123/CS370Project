package com.example.MentalHealthPredictor;

import org.apache.commons.csv.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;

import java.nio.charset.*;




public class DataSetResponseParser{
    SurveyResponse data;
    int numResponses,predictedVarCol = -1;
    String parsedData[][];
    String parsedColumnQuestion[];
    public DataSetResponseParser(SurveyResponse d){
        data = d;
        numResponses = data.getNumRows();
        for(int i =0;i<data.getNumCol();i++){
            if(data.getColumnName(i).equals("Mental Ilnness")){
                predictedVarCol = i;
                break;
            }
        }
        //Check for number of usable responses in csv. Responses that have a answer for the predicted variable
        for(int i = 0;i<data.getNumRows();i++){
            if(data.getRecord(i, predictedVarCol).equals(""))numResponses--;
        }
        //The parsed data array is smaller than the one in SurveyResponse, since 3 columns of data not
        //used in the algorithm get removed, invalid responses are also skipped over
        parsedData = new String[numResponses][data.getNumCol() - 3];
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
        System.out.println(parsedData[parsedData.length - 1][0]);
        parsedColumnQuestion = new String[data.getNumCol() - 3];
        
    }

    String sortGender(String in){
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