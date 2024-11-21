package com.example.MentalHealthPredictor;

import org.apache.commons.csv.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.*;




public class DataSetResponseParser{
    
}



class SurveyResponse{
    public SurveyResponse(){

    }
    public void printSer() throws IOException{
    String documentsPath = System.getProperty("user.home") + "/Documents";
    String filename = "survey.csv";

    Path documentsDirectory = Paths.get(documentsPath);
    Path csvPath = documentsDirectory.resolve(filename);

    @SuppressWarnings("deprecation")
    CSVParser csvParser = CSVParser.parse(
        csvPath, 
        Charset.defaultCharset(),
        CSVFormat.DEFAULT.withHeader(
        "Timestamp", "Mental Ilnness", "Age", "Gender", "Country", "state", "self_employed", 
            "family_history", "treatment", "no_employees", "remote_work", "tech_company",
            "benefits", "care_options", "wellness_program", "seek_help", "anonymity", "leave",
            "mental_health_consequence", "phys_health_consequence", "coworkers", "supervisor",
            "mental_health_interview", "phys_health_interview", "mental_vs_physical","obs_consequence" ,
            "comments"));
    csvParser.forEach(csvRecord -> {
        System.out.println(csvRecord.toMap());
    }
    
    );
    }
}