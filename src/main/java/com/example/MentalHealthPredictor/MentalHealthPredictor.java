package com.example.MentalHealthPredictor;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MentalHealthPredictor {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MentalHealthPredictor.class, args);
		openHomePage();
	}

	private static void openHomePage() throws IOException { //Automatically runs localhost8080 to open the webpage
        String url = "http://localhost:8080/";
		String osName = getOperatingSystem();
        Runtime rt = Runtime.getRuntime();

		if(osName.contains("win")){
			rt.exec("rundll32 url.dll,FileProtocolHandler " + url); //For Windows
		}
		else if(osName.contains("mac")){ 
			rt.exec("open " + url); //For macOS
		}
		else if(osName.contains("nix") || osName.contains("nux") || osName.contains("mac")){ 
			rt.exec("xdg-open " + url); //For unix based systems
		}
		else{
			System.out.println("Unsupported OS, Auto Local8080 does not work.");
		}
        
    }

	public static String getOperatingSystem() {
		String os = System.getProperty("os.name").toLowerCase();
		System.out.println("Using System Property: " + os);
		return os;
	}
}

