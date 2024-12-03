package com.example.MentalHealthPredictor;

public class Greeting { /*This is our model class */
    /* 
    private long id;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    */
    
    private String fques; 

    public String getFques(){
        return fques;
    }

    public void setFques(String fques){
        this.fques = fques;
    }

    @Override
    public String toString(){
        //return "User [fques=" + fques + "]";
        return fques ;
    }
}
