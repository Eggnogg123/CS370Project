package com.example.MentalHealthPredictor;

public class CurrentSessionReponses { /*This is our model class */
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
    //gonna try and generalize this into an array
    private String ans[] = new String[23];
    private String ans1; 
    private String ans2; 
    private String ans3; 
    private String ans4; 
    private String ans5; 
    private String ans6; 
    private String ans7; 
    private String ans8; 
    private String ans9; 
    private String ans10; 
    private String ans11; 
    private String ans12; 
    private String ans13; 
    private String ans14;
    private String ans15; 
    private String ans16; 
    private String ans17; 
    private String ans18; 
    private String ans19; 
    private String ans20;  
    private String ans21; 
    private String ans22;
    private String ans23; 
    
    //Dont change 
    public void setans1(String ans1){ans[0] = ans1;}   
    public void setans2(String ans2){ans[1] = ans2;}   
    public void setans3(String ans3){ans[2] = ans3;} 
    public void setans4(String ans4){ans[3] = ans4;}   
    public void setans5(String ans5){ans[4] = ans5;}  
    public void setans6(String ans6){ans[5] = ans6;}  
    public void setans7(String ans7){ans[6] = ans7;}  
    public void setans8(String ans8){ans[7] = ans8;}  
    public void setans9(String ans9){ans[8] = ans9;}  
    public void setans10(String ans10){ans[9] = ans10;}  
    public void setans11(String ans11){ans[10] = ans11;} 
    public void setans12(String ans12){ans[11] = ans12;}  
    public void setans13(String ans13){ans[12]= ans13;}  
    public void setans14(String ans14){ans[13] = ans14;}  
    public void setans15(String ans15){ans[14] = ans15;}
    public void setans16(String ans16){ans[15] = ans16;}
    public void setans17(String ans17){ans[16] = ans17;}
    public void setans18(String ans18){ans[17] = ans18;}
    public void setans19(String ans19){ans[18] = ans19;}
    public void setans20(String ans20){ans[19] = ans20;}
    public void setans21(String ans21){ans[20] = ans21;}
    public void setans22(String ans22){ans[21] = ans22;}
    public void setans23(String ans23){ans[22] = ans23;}

    public String getAnswer(int index){
        return ans[index];
    }


    // @Override /* This is simply to print out the values the user chooses for each anstion */
    // public String toString(){
    //     return "User [ans1=" + ans1 + "]";
    //     //return "User [ans15=" + ans15 + ", ans22=" + ans22 + "]" ;
    // }

    // public String toString(){ //gonna try to convert toString to print only the answers we want could be used to send data to other classes
        
    // }
}
