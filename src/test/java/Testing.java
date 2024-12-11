import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import com.example.MentalHealthPredictor.CurrentSessionReponses;
import com.example.MentalHealthPredictor.DataSetResponseParser;
import com.example.MentalHealthPredictor.Display;
//import com.example.MentalHealthPredictor.SurveyResponse;

import net.bytebuddy.asm.MemberSubstitution.Current;


class Testing {
    @Test
    void DataSetResponseParserTest() throws IOException{
        Display addtest = new Display();
        String table1[][] = {{"Mental Ilnness","Age","no_employees","remote_work","Gender","benefits"}, //This is what I want my output to be
                             {"Yes","27","1-5","Yes","Female","Yes"},
                             {"Yes","33","6-25","No","Male","No"},
                             {"No","38",">1000","No","Male","Yes"},
                             {"Yes","43","100-500","Yes","Male","Yes"},
                             {"Yes","24","6-25","Yes","Male","No"},
                             {"No","30","1-5","Yes","Female","No"},
                             {"No","31",">1000","No","Non-Binary","Don't know"}
        };// end of table1

        //CurrentSessionReponses cs = new CurrentSessionReponses(); //Dont need right now
        DataSetResponseParser p = new DataSetResponseParser("DataSeta.csv"); //This is the data we are running through the parser
        

        assertArrayEquals(table1,p.getParsedData()); // This will determine if the two arrays values are equal to each other

    }//end void DataSetResponseParserTest()
}//end class Testing 
    



