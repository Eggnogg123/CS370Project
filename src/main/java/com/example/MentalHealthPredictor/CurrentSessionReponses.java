package com.example.MentalHealthPredictor;

import java.util.HashMap;
import java.util.Map;

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
    private Map<String,String> ans = new HashMap<String,String>();
    private String ques[] = new String[23];
    


    //Dont change 
    public void setans1(String ans1){ans.put("0", ans1);}   
    public void setans2(String ans2){ans.put("1", ans2);}   
    public void setans3(String ans3){ans.put("2", ans3);} 
    public void setans4(String ans4){ans.put("3", ans4);}   
    public void setans5(String ans5){ans.put("4", ans5);}  
    public void setans6(String ans6){ans.put("5", ans6);}  
    public void setans7(String ans7){ans.put("6", ans7);}  
    public void setans8(String ans8){ans.put("7", ans8);}  
    public void setans9(String ans9){ans.put("8", ans9);}  
    public void setans10(String ans10){ans.put("9", ans10);}  
    public void setans11(String ans11){ans.put("10", ans11);} 
    public void setans12(String ans12){ans.put("11", ans12);}  
    public void setans13(String ans13){ans.put("12", ans13);}  
    public void setans14(String ans14){ans.put("13", ans14);}  
    public void setans15(String ans15){ans.put("14", ans15);}
    public void setans16(String ans16){ans.put("15", ans16);}
    public void setans17(String ans17){ans.put("16", ans17);}
    public void setans18(String ans18){ans.put("17", ans18);}
    public void setans19(String ans19){ans.put("18", ans19);}
    public void setans20(String ans20){ans.put("19", ans20);}
    public void setans21(String ans21){ans.put("20", ans21);}
    public void setans22(String ans22){ans.put("21", ans22);}
    public void setans23(String ans23){ans.put("22", ans23);}

    public String getAnswer(String question){
        return ans.get(question);
    }
    public void setQuestions(DataSetResponseParser input){
        for(int i =0;i<23;i++){
            ques[i] = input.getQuestion(i);
            ans.put(ques[i], ans.get(Integer.toString(i)));
            //System.out.println(ques[i]);
        }
    }
    public String getQuestion(int index){
        return ques[index];
    }

    // @Override /* This is simply to print out the values the user chooses for each anstion */
    // public String toString(){
    //     return "User [ans1=" + ans1 + "]";
    //     //return "User [ans15=" + ans15 + ", ans22=" + ans22 + "]" ;
    // }

    // public String toString(){ //gonna try to convert toString to print only the answers we want could be used to send data to other classes
        
    // }
}
