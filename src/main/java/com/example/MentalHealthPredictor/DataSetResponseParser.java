package com.example.MentalHealthPredictor;

import org.apache.commons.csv.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.*;




public class DataSetResponseParser{
    
}



class SurveyResponse{
    public SurveyResponse(String filename) throws IOException{
        String documentsPath = System.getProperty("user.dir")  ;
        

        Path documentsDirectory = Paths.get(documentsPath,"src/main/resources/static");
        Path csvPath = documentsDirectory.resolve(filename);

	    CSVParser csvParser = CSVParser.parse(csvPath,Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());
        csvParser.forEach(csvRecord -> {
          System.out.println(csvRecord.toMap());
      }
    
      );
    }
}