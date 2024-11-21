package com.example.MentalHealthPredictor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MentalHealthPredictor {

	public static void main(String[] args) throws IOException {
		String documentsPath = System.getProperty("user.dir")+ "/src/main/resources/static";
		//System.out.println(documentsPath);
		//C:\Users\denni\370 project\CS370Project\src\main\resources\static
    String filename = "survey.csv";

    Path documentsDirectory = Paths.get(documentsPath);
    Path csvPath = documentsDirectory.resolve(filename);
		System.out.println(csvPath.toString());
    // CSVParser csvParser = CSVParser.parse(csvPath, Charset.defaultCharset(), CSVFormat.DEFAULT);
    // csvParser.forEach(csvRecord -> {
    //      System.out.println(csvRecord.toMap());
    //  }
    
    //  );
		SpringApplication.run(MentalHealthPredictor.class, args);
		
	}

}

