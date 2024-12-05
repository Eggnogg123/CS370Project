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
    //gonna try and generalize this into an array
    
    private String ques1; 
    private String ques2; 
    private String ques3; 
    private String ques4; 
    private String ques5; 
    private String ques6; 
    private String ques7; 
    private String ques8; 
    private String ques9; 
    private String ques10; 
    private String ques11; 
    private String ques12; 
    private String ques13; 
    private String ques14;
    private String ques15; 
    private String ques16; 
    private String ques17; 
    private String ques18; 
    private String ques19; 
    private String ques20;  
    private String ques21; 
    private String ques22;
    private String ques23; 

    private String ques1option = "Hamburger";

    public String getques1option(){return ques1option;}
    public void setques1option(String ques1option){this.ques1option = ques1option;}

    public String getques1(){return ques1;}
    public void setques1(String ques1){this.ques1 = ques1;}

    public String getques2(){return ques2;}
    public void setques2(String ques2){this.ques2 = ques2;}

    public String getques3(){return ques3;}
    public void setques3(String ques3){this.ques3 = ques3;}

    public String getques4(){return ques4;}
    public void setques4(String ques4){this.ques4 = ques4;}

    public String getques5(){return ques5;}
    public void setques5(String ques5){this.ques5 = ques5;}

    public String getques6(){return ques6;}
    public void setques6(String ques6){this.ques6 = ques6;}

    public String getques7(){return ques7;}
    public void setques7(String ques7){this.ques7 = ques7;}

    public String getques8(){return ques8;}
    public void setques8(String ques8){this.ques8 = ques8;}

    public String getques9(){return ques9;}
    public void setques9(String ques9){this.ques9 = ques9;}

    public String getques10(){return ques10;}
    public void setques10(String ques10){this.ques10 = ques10;}

    public String getques11(){return ques11;}
    public void setques11(String ques11){this.ques11 = ques11;}

    public String getques12(){return ques12;}
    public void setques12(String ques12){this.ques12 = ques12;}

    public String getques13(){return ques13;}
    public void setques13(String ques13){this.ques13 = ques13;}

    public String getques14(){return ques14;}
    public void setques14(String ques14){this.ques14 = ques14;}

    public String getques15(){return ques15;}
    public void setques15(String ques15){this.ques15 = ques15;}
 
    public String getques16(){return ques16;}
    public void setques16(String ques16){this.ques16 = ques16;}

    public String getques17(){return ques17;}
    public void setques17(String ques17){this.ques17 = ques17;}

    public String getques18(){return ques18;}
    public void setques18(String ques18){this.ques18 = ques18;}

    public String getques19(){return ques19;}
    public void setques19(String ques19){this.ques19 = ques19;}
    
    public String getques20(){return ques20;}
    public void setques20(String ques20){this.ques20 = ques20;}

    public String getques21(){return ques21;}
    public void setques21(String ques21){this.ques21 = ques21;}

    public String getques22(){return ques22;}
    public void setques22(String ques22){this.ques22 = ques22;}

    public String getques23(){return ques23;}
    public void setques23(String ques23){this.ques23 = ques23;}


    @Override /* This is simply to print out the values the user chooses for each question */
    public String toString(){
        //return "User [fques=" + fques + "]";
        return "User [ques15=" + ques15 + ", ques22=" + ques22 + "]" ;
    }

    // public String toString(){ //gonna try to convert toString to print only the answers we want could be used to send data to other classes
        
    // }
}
