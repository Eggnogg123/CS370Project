import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
    //Test SurveyResponse
    @Test
    void surveyResponseTest() throws IOException{
        SurveyResponse scenA = new SurveyResponse("DataSetA.csv");
        SurveyResponse scenB = new SurveyResponse("DataSetB.csv");
        String table1[][] = { //This is what I want my output to be
            {"Often","27","1-5","Yes","Cis Female","Yes"},
            {"Sometimes","33","6-25","No","Cis Male","No"},
            {"NA","38","More than 1000","No","cis male","Yes"},
            {"Sometimes","43","100-500","Yes","Cis Male","Yes"},
            {"Sometimes","24","6-25","Yes","Cis Man","No"},
            {"NA","30","1-5","Yes","cis-female/female","No"},
            {"Never","31","More than 1000","No","Enby","Don't know"}
        };
        List<String> header1 = new LinkedList<String>();
        header1.add("Mental Ilnness");
        header1.add("Age");
        header1.add("no_employees");
        header1.add("remote_work");
        header1.add("Gender");
        header1.add("benefits");
        assertArrayEquals(table1, scenA.surveyResponses);
        assertIterableEquals(header1, scenA.columnHeaders); // both inputs have the same headers
        String table2[][] = { //This is what I want my output to be
            {"Often","27","1-5","Yes","Cis Female","Yes"},
            {"Sometimes","33","6-25","No","Cis Male",""},
            {"NA","38","More than 1000","No","cis male","Yes"},
            {"Sometimes","43","100-500","","Cis Male","Yes"},
            {"Sometimes","24","","Yes","Cis Man","No"},
            {"NA","30","1-5","Yes","cis-female/female",""},
            {"Never","","More than 1000","No","Enby",""}
        };
        assertArrayEquals(table2, scenB.surveyResponses);
        assertIterableEquals(header1, scenB.columnHeaders);
    }
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
//Test partitionTable method of DecisionTree
    @Test
    void decisionTreePartition(){
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
        String table2[][] = { //This is what I want my output to be
            
            {"Yes","33","6-25","No","Male","No"},
            {"No","38","More than 1000","No","Male","Yes"},
            {"Yes","43","100-500","Yes","Male","Yes"},
            
            {"No","30","1-5","Yes","Female","No"},
            {"Yes","31","More than 1000","No","Non-Binary","Don't know"}
        };
        String table3[][] = { //This is what I want my output to be
            {"Yes","27","1-5","Yes","Female","Yes"},
            {"Yes","24","6-25","Yes","Male","No"},
        };
        //Scenario 1
        assertArrayEquals(table2, tree.partitionTable(table1, 1, ">=30",true));
        assertArrayEquals(table3, tree.partitionTable(table1, 1, ">=30",false));
        //Scenario 2
        String table4[][] = { //This is what I want my output to be
            {"Yes","33","6-25","No","Male","No"},
            {"No","38","More than 1000","No","Male","Yes"},
            {"Yes","31","More than 1000","No","Non-Binary","Don't know"}
        };
        String table5[][] = { //This is what I want my output to be
            {"Yes","27","1-5","Yes","Female","Yes"},
            {"Yes","43","100-500","Yes","Male","Yes"},
            {"Yes","24","6-25","Yes","Male","No"},
            {"No","30","1-5","Yes","Female","No"},
        };
        assertArrayEquals(table4, tree.partitionTable(table1, 3, "No",true));
        assertArrayEquals(table4, tree.partitionTable(table1, 3, "Yes",false));
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
        choicesLIST.put(1,choice1);
        Set<String> choice2 = new HashSet<String>();
        choice2.add("1-5");
        choice2.add("6-25");
        choice2.add("100-500");
        choice2.add("More than 1000");
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
        //TEST THE TREE BELOW
        assertEquals(1, root.getColumn());
        assertEquals(">=30", root.getChoice());
        //Level-1
        DecisionTreeNode l1left = root.getChild("30");
        DecisionTreeNode l1right = root.getChild("29");
        assertEquals(1, l1left.getColumn());
        assertEquals(">=31", l1left.getChoice());
        assertEquals(-1, l1right.getColumn());
        assertEquals("Yes", l1right.getChoice());
        //Level-2
        DecisionTreeNode l2left = l1left.getChild("31");
        DecisionTreeNode l2right = l1left.getChild("30");
        assertEquals(1, l2left.getColumn());
        assertEquals(">=38", l2left.getChoice());
        assertEquals(-1, l2right.getColumn());
        assertEquals("No", l2right.getChoice());
        //Level-3
        DecisionTreeNode l3left = l2left.getChild("38");
        DecisionTreeNode l3right = l2left.getChild("37");
        assertEquals(1, l3left.getColumn());
        assertEquals(">=43", l3left.getChoice());
        assertEquals(-1, l3right.getColumn());
        assertEquals("Yes", l3right.getChoice());
        //Level-4
        DecisionTreeNode l4left = l3left.getChild("43");
        DecisionTreeNode l4right = l3left.getChild("42");
        assertEquals(-1, l4left.getColumn());
        assertEquals("Yes", l4left.getChoice());
        assertEquals(-1, l4right.getColumn());
        assertEquals("No", l4right.getChoice());

    }
//Test BUILD RandomForest User Requirement
    @Test   
    void randomForestALgorithmBuild() throws IOException{
        DataSetResponseParser p = new DataSetResponseParser("DataSetA.csv");
        RandomForestAlgorithm alg = new RandomForestAlgorithm(p);
        alg.buildRandomForest();
        assertEquals(alg.getNumTrees(), 7);
    }
//Test BUILD RandomForest survey size 200
    @Test   
    void randomForestALgorithmBuildA() throws IOException{
        DataSetResponseParser p = new DataSetResponseParser("TestsurveyA.csv");
        RandomForestAlgorithm alg = new RandomForestAlgorithm(p);
        alg.buildRandomForest();
        assertEquals(alg.getNumTrees(), 200);
    }
//Test BUILD RandomForest survey size 500
    @Test   
    void randomForestALgorithmBuildB() throws IOException{
        DataSetResponseParser p = new DataSetResponseParser("TestsurveyB.csv");
        RandomForestAlgorithm alg = new RandomForestAlgorithm(p);
        alg.buildRandomForest();
        assertEquals(alg.getNumTrees(), 500);
    }
//Test BUILD RandomForest survey size 1259
    @Test   
    void randomForestALgorithmBuildActual() throws IOException{
        DataSetResponseParser p = new DataSetResponseParser("survey.csv");
        RandomForestAlgorithm alg = new RandomForestAlgorithm(p);
        alg.buildRandomForest();
        assertEquals(alg.getNumTrees(), 1259);
    }
//Test BUILD RandomForest survey size 2000
    @Test   
    void randomForestALgorithmBuildC() throws IOException{
        DataSetResponseParser p = new DataSetResponseParser("TestsurveyC.csv");
        RandomForestAlgorithm alg = new RandomForestAlgorithm(p);
        alg.buildRandomForest();
        assertEquals(alg.getNumTrees(), 2000);
    }
}

    