package com.example.MentalHealthPredictor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CurrentSessionReponses { /*This is our model class */
    
    private Map<String,String> ans = new HashMap<String,String>();
    private String feedbackans[] = new String[22];
    private String ques[] = new String[22];
    

    public void setTest(DataSetResponseParser parser,int row){
        String response[] = parser.getSample(row);
        for(int i = 1;i<response.length;i++){
            if(i == 1){
                try {
                    long index = (Long.valueOf(response[i]) % 100) / 10;
                    ans.put("0", Long.toString(index));
                } catch (NumberFormatException e) {
                    ans.put("0","0");
                } 
                feedbackans[i-1] = response[i];
            }
            else if (i== 5){
                if(response[5].equals("Don't Know")){
                    Random rand = new Random();
                    int check = rand.nextInt() % 2;
                    if(check == 0)ans.put("4", "Yes");
                    else ans.put("4", "No");
                }
                else ans.put("4", response[i]);
                feedbackans[i - 1] = response[i];
            }
            else if (i == 8){
                if(response[8].equals("Don't Know")){
                    Random rand = new Random();
                    int check = rand.nextInt() % 2;
                    if(check == 0)ans.put("7", "Yes");
                    else ans.put("7", "No");
                }
                ans.put("7", response[8]);
                feedbackans[i -1 ] = response[8];
            }
            else{
                ans.put(Integer.toString(i-1),response[i]);
                feedbackans[i-1] = response[i];
            }
            
        }
        setQuestions(parser);
    }
    //Dont change 
    public void setans1(String ans1){
        try {
            long index = (Long.valueOf(ans1) % 100) / 10;
            ans.put("0", Long.toString(index));
        } catch (NumberFormatException e) {
            ans.put("0","0");
        } 
        feedbackans[0] = ans1;
    }   
    public void setans2(String ans2){ans.put("1", ans2);
        feedbackans[1] = ans2;}   
    public void setans3(String ans3){ans.put("2", ans3);
        feedbackans[2] = ans3;} 
    public void setans4(String ans4){ans.put("3", ans4);
        feedbackans[3] = ans4;}   
    public void setans5(String ans5){
        if(ans5.equals("Don't Know")){
            Random rand = new Random();
            int check = rand.nextInt() % 2;
            if(check == 0)ans.put("4", "Yes");
            else ans.put("4", "No");
        }
        else ans.put("4", ans5);
        feedbackans[4] = ans5;
    }  
    public void setans6(String ans6){ans.put("5", ans6);
        feedbackans[5] = ans6;}  
    public void setans7(String ans7){ans.put("6", ans7);
        feedbackans[6] = ans7;}  
    public void setans8(String ans8){
        if(ans8.equals("Don't Know")){
            Random rand = new Random();
            int check = rand.nextInt() % 2;
            if(check == 0)ans.put("7", "Yes");
            else ans.put("7", "No");
        }
        ans.put("7", ans8);
        feedbackans[7] = ans8;
    }  
    public void setans9(String ans9){ans.put("8", ans9);
        feedbackans[8] = ans9;}  
    public void setans10(String ans10){ans.put("9", ans10);
        feedbackans[9] = ans10;}  
    public void setans11(String ans11){ans.put("10", ans11);
        feedbackans[10] = ans11;} 
    public void setans12(String ans12){ans.put("11", ans12);
        feedbackans[11] = ans12;}  
    public void setans13(String ans13){ans.put("12", ans13);
        feedbackans[12] = ans13;}  
    public void setans14(String ans14){ans.put("13", ans14);
        feedbackans[13] = ans14;}  
    public void setans15(String ans15){ans.put("14", ans15);
        feedbackans[14] = ans15;}
    public void setans16(String ans16){ans.put("15", ans16);
        feedbackans[15] = ans16;}
    public void setans17(String ans17){ans.put("16", ans17);
        feedbackans[16] = ans17;}
    public void setans18(String ans18){ans.put("17", ans18);
        feedbackans[17] = ans18;}
    public void setans19(String ans19){ans.put("18", ans19);
        feedbackans[18] = ans19;}
    public void setans20(String ans20){ans.put("19", ans20);
        feedbackans[19] = ans20;}
    public void setans21(String ans21){ans.put("20", ans21);
        feedbackans[20] = ans21;}
    public void setans22(String ans22){ans.put("21", ans22);
        feedbackans[21] = ans22;}
    // public void setans23(String ans23){ans.put("22", ans23);}

    public String[] getasArray(){
        return feedbackans;
    }
    public String getAnswer(String question){
        return ans.get(question);
    }
    public void setQuestions(DataSetResponseParser input){
        for(int i =0;i<input.getCols() - 1;i++){
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
