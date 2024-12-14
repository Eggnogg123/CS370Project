package com.example.MentalHealthPredictor;

import org.apache.commons.csv.*;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.nio.charset.*;

public class SurveyResponse{
    public List<String> columnHeaders;
    public String surveyResponses[][];
    String filename;
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

    public SurveyResponse(File filename) throws IOException{
        String documentsPath = System.getProperty("user.dir")  ;
        Path documentsDirectory = Paths.get(documentsPath,"src/main/resources/static");
        Path csvPath = documentsDirectory.resolve(filename.getPath());
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

    public String getRecord(int row,int col){
        if(row >= surveyResponses.length || col >= surveyResponses[0].length
           || row < 0 || col < 0)return "INVALID RECORD";
        return surveyResponses[row][col];
    }
    public String getColumnName(int col){
        if(col < 0 || col > columnHeaders.size())return "INVALID VARIABLE COLUMN";
        return columnHeaders.get(col);
    }
    public int getNumRows(){
        return surveyResponses.length;
    }
    public int getNumCol(){
        return surveyResponses[0].length;
    }
}
