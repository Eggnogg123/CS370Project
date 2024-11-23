package com.example.MentalHealthPredictor;

import org.apache.commons.csv.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import java.nio.charset.*;




public class DataSetResponseParser{
    
}



class SurveyResponse{
    List<String> columnHeaders;
    String surveyData[][];
    public SurveyResponse(String filename) throws IOException{
        String documentsPath = System.getProperty("user.dir")  ;
        

        Path documentsDirectory = Paths.get(documentsPath,"src/main/resources/static");
        Path csvPath = documentsDirectory.resolve(filename);

	    CSVParser csvParser = CSVParser.parse(csvPath,Charset.defaultCharset(),CSVFormat.EXCEL.withHeader());
        columnHeaders = csvParser.getHeaderNames();
        

        List<CSVRecord> records = csvParser.getRecords();
        surveyData = new String[records.size()][records.get(0).size()];
        for(int i =0;i<records.size();i++){
            CSVRecord curr = records.get(i);
            for(int j =0;j<records.get(i).size();j++){
                surveyData[i][j] = curr.get(j);
            }
        }
        System.out.println("Demo Row 1 Col 1");
        System.out.println(columnHeaders.get(0) + ": " + surveyData[0][0] );
    }
}