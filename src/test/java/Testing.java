import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.example.MentalHealthPredictor.DataSetResponseParser;
import com.example.MentalHealthPredictor.DecisionTree;
import com.example.MentalHealthPredictor.DecisionTreeNode;
import com.example.MentalHealthPredictor.RandomForestAlgorithm;
import com.example.MentalHealthPredictor.SurveyResponse;



class Testing {
    DataSetResponseParser p;
    SurveyResponse a;
    //Test the parseResponse and getSubCol
    @Test
    void parserTestA() throws IOException{ //
        String title1[] = {"Mental Ilnness","Age","no_employees","remote_work","Gender","benefits"};
        String table1[][] = { //This is what I want my output to be
                             {"Yes","27","1-5","Yes","Female","Yes"},
                             {"Yes","33","6-25","No","Male","No"},
                             {"No","38","More than 1000","No","Male","Yes"},
                             {"Yes","43","100-500","Yes","Male","Yes"},
                             {"Yes","24","6-25","Yes","Male","No"},
                             {"No","30","1-5","Yes","Female","No"},
                             {"Yes","31","More than 1000","No","Non-Binary","Don't know"}
        };// end of table1

        //CurrentSessionReponses cs = new CurrentSessionReponses(); //Dont need right now
        p = new DataSetResponseParser("datasetA.csv"); 
    //Testing if Parser Parses Data Correctly
        assertArrayEquals(table1,p.getParsedData()); 
        assertArrayEquals(title1,p.getParsedColumnName());
    //Testing both Scenarios of the getSubCol() method
        String title2[] = {"Mental Ilness","How many employees does your company or organization have?",
                            "Do you work remotely (outside of an office) at least 50% of the time?",
                            "What is your Gender?","Does your employer provide mental health benefits?"};
        String title3[] = {"Mental Ilness","What is your Age?",
                            "How many employees does your company or organization have?",
                            "Do you work remotely (outside of an office) at least 50% of the time?"};
        assertArrayEquals(title2, p.getSubCol(0, 6));
        assertArrayEquals(title3, p.getSubCol(3, 5));
    }
    @Test
    void parserTestB() throws IOException{ //
        String title1[] = {"Mental Ilnness","Age","no_employees","remote_work","Gender","benefits"};
        String table1[][] = { //This is what I want my output to be
                             {"Yes","27","1-5","Yes","Female","Yes"},
                             {"Yes","33","6-25","No","Male",""},
                             {"No","38","More than 1000","No","Male","Yes"},
                             {"Yes","43","100-500","","Male","Yes"},
                             {"Yes","24","","Yes","Male","No"},
                             {"No","30","1-5","Yes","Female",""},
                             {"Yes","","More than 1000","No","Non-Binary",""}
        };// end of table1

        //CurrentSessionReponses cs = new CurrentSessionReponses(); //Dont need right now
        p = new DataSetResponseParser("datasetB.csv"); 
        
        assertArrayEquals(table1,p.getParsedData()); 
        assertArrayEquals(title1,p.getParsedColumnName());
        
    }
    @Test
    void parserTestC() throws IOException{ //
        String title1[] = {"Mental Ilnness","Age","no_employees","remote_work","Gender","benefits"};
        String table1[][] = { //This is what I want my output to be
                             {"Yes","27","1-5","Yes","Female","Yes"},
                             {"Yes","33","6-25","No","Male","No"},
                             {"No","5","More than 1000","No","Male","Yes"},
                             {"Yes","0","100-500","Yes","Male","Yes"},
                             {"Yes","204","6-25","Yes","Male","No"},
                             {"No","30","1-5","Yes","Female","No"},
                             {"Yes","120","More than 1000","No","Non-Binary","Don't know"}
        };// end of table1

        //CurrentSessionReponses cs = new CurrentSessionReponses(); //Dont need right now
        p = new DataSetResponseParser("datasetC.csv"); 
        
        assertArrayEquals(table1,p.getParsedData()); 
        assertArrayEquals(title1,p.getParsedColumnName());
        
    }
//test the bootStrapData
    @Test 
    void randomForestBootStrapper() throws IOException{
        p = new DataSetResponseParser("datasetA.csv"); 
        RandomForestAlgorithm alg = new RandomForestAlgorithm(p);
        //Scenario 1
        assertEquals(7, alg.bootStrapData(0, 0, p).length);
        assertEquals(5, alg.bootStrapData(0, 0, p)[0].length);
        //Scenario 2
        assertEquals(7, alg.bootStrapData(2, 3, p).length);
        assertEquals(5, alg.bootStrapData(2, 3, p)[0].length);
    }
//test the getInfoGain method of decisionTree
    @Test
    void decisionTreeInfoGain() throws IOException{
        DecisionTree tree = new DecisionTree();
        String table1[][] = { //This is what I want my output to be
            {"Yes","27","1-5","Yes","Female","Yes"},
            {"Yes","33","6-25","No","Male","No"},
            {"No","38","More than 1000","No","Male","Yes"},
            {"Yes","43","100-500","Yes","Male","Yes"},
            {"Yes","24","6-25","Yes","Male","No"},
            {"No","30","1-5","Yes","Female","No"},
            {"Yes","31","More than 1000","No","Non-Binary","Don't know"}
        };
        double infoGain = tree.getInfoGain(table1,2,"1-5");
        double correctInfoGain = 9.0/245.0;
        DecimalFormat df_obj = new DecimalFormat("#.#####"); // format answers to 5 digits after .
        assertEquals(df_obj.format(correctInfoGain), df_obj.format(infoGain));

        infoGain = tree.getInfoGain(table1,1,">=31");
        correctInfoGain = 1.0/294.0;
        assertEquals(df_obj.format(correctInfoGain), df_obj.format(infoGain));

        infoGain = tree.getInfoGain(table1, 1, ">=45");
        correctInfoGain = -1.0;
        assertEquals(df_obj.format(correctInfoGain), df_obj.format(infoGain));
    }
//Test buildDecisionTree method of DecisionTree
    @Test
    void decisionTreeBuild(){
        DecisionTree tree = new DecisionTree();
        String table1[][] = { //This is what I want my output to be
            {"Yes","27","1-5","Yes","Female","Yes"},
            {"Yes","33","6-25","No","Male","No"},
            {"No","38","More than 1000","No","Male","Yes"},
            {"Yes","43","100-500","Yes","Male","Yes"},
            {"Yes","24","6-25","Yes","Male","No"},
            {"No","30","1-5","Yes","Female","No"},
            {"Yes","31","More than 1000","No","Non-Binary","Don't know"}
        };
        Map<Integer,Set<String>> choicesLIST = new HashMap<Integer,Set<String>>();
        Set<String> choice1 = new HashSet<String>();
        choice1.add(">=27");
        choice1.add(">=33");
        choice1.add(">=43");
        choice1.add(">=24");
        choice1.add(">=31");
        choice1.add(">=38");
        choice1.add(">=30");
        double max = 0.0;
        String yes ="";
        for(String i:choice1){
            if( tree.getInfoGain(table1, 1, i) > max){
                 max = tree.getInfoGain(table1, 1, i);
                 yes = i;
            }
        }
        assertEquals(tree.getInfoGain(table1, 1, ">=30"),max,yes);
        choicesLIST.put(1,choice1);
        Set<String> choice2 = new HashSet<String>();
        choice2.add("1-5");
        choice2.add("6-25");
        choice2.add("100-500");
        choice2.add("More than 1000");
        max = 0.0;
        yes ="";
        for(String i:choice1){
            if( tree.getInfoGain(table1, 1, i) > max){
                 max = tree.getInfoGain(table1, 1, i);
                 yes = i;
            }
        }
        assertEquals(tree.getInfoGain(table1, 1, ">=30"),max,yes);
        choicesLIST.put(2,choice2);
        Set<String> choice3 = new HashSet<String>();
        choice3.add("Yes");
        choice3.add("No");
        choicesLIST.put(3,choice3);
        Set<String> choice4 = new HashSet<String>();
        choice4.add("Male");
        choice4.add("Female");
        choice4.add("Non-Binary");
        choicesLIST.put(4,choice4);
        Set<String> choice5 = new HashSet<String>();
        choice5.add("Yes");
        choice5.add("No");
        choice5.add("Don't know");
        choicesLIST.put(5,choice5);
        DecisionTreeNode root = tree.buildDecisionTree(table1,choicesLIST);
        //root = root.getChild("No");
        //root = root.getChild("19");
        //assertEquals(0, root.getColumn());
        //assertEquals("Y", root.getChoice());
    }

}//end class Testing 
    



