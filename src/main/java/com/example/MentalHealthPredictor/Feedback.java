package com.example.MentalHealthPredictor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Feedback {
    private DataSetResponseParser comparison;
    private CurrentSessionReponses userAnswers;
    private String prediction, feedbackArr[], feedbackQuestions[];

    public Feedback(String prediction, DataSetResponseParser d, CurrentSessionReponses c){
        comparison = d;
        userAnswers = c;
        feedbackArr = new String[18];
        feedbackQuestions = new String[18];
        for(int i =1 ,j = 0;i<23;i++,j++){
            
            while(comparison.getQuestion(i - 1).equals("What is your Age?") ||
            comparison.getQuestion(i - 1).equals("What is your Gender?") ||
            comparison.getQuestion(i - 1).equals("If you live in one of these US States, what is your State of residence? Otherwise put NA.") ||
            comparison.getQuestion(i - 1).equals("Do you have a family history of mental illness?")
            )i++; //Skips first 3 questions excluding country
            feedbackQuestions[j] = comparison.getQuestion(i - 1);
            feedbackArr[j] =getHealthyPersonValue(i);
            
        }
    }// end of Feedback() method

    public String getFeedbackValue(int index){
        return feedbackArr[index];
    }

    public String getFeedbackQuestion(int index){
        return feedbackQuestions[index];
    }

    public String getHealthyPersonValue(int index){//Dont care about the prediction right now, only need to get a healthy person from dataset
    
        Random random = new Random();
        Set<Integer> healthylist = getSimilar(index);
       Map<String,Integer> answerCount = new HashMap<String,Integer>();
       String mostC = new String();
       System.out.print("Used Healthy People at Responses: ");
       for(int i:healthylist){
            System.out.print(i + 2 + " ");
            String curr = new String(comparison.getSample(i)[index]);
            if(!answerCount.containsKey(curr))
                answerCount.put(curr, 0);
            else answerCount.put(curr, answerCount.get(curr) + 1);
            mostC = curr;
        }
        System.out.println();
        
        for(Map.Entry<String, Integer> i:answerCount.entrySet()){
            if(answerCount.get(i.getKey()) > answerCount.get(mostC))mostC = i.getKey();
        }
        System.out.println(mostC);
        return mostC;
    }//end of getHealthyPerson() method

    private Set<Integer> getSimilar(int index){// how many indices are common excluding the ones oyu shouldnt look at
        Set<Integer> ans = new HashSet<Integer>();
        Map<Integer,Integer> key = new HashMap<Integer,Integer>();
        for(int i =0;i<comparison.getRows();i++){
            String user[] = userAnswers.getasArray();
            String survey[] = comparison.getSample(i);
            if(survey[0].equals("Yes"))continue;
            int count = 0;
            for(int j = 1;j<survey.length;j++){
                if(j == index)continue;
                if(user[j-1].equals(survey[j]))count++;
            }
            key.put(i,count);
            if(ans.size() < 10)
                ans.add(i);
            else{
                int min = i;
                for(Integer k:ans){
                    if(key.get(k) < key.get(min)){
                        min = k;
                    }
                }
                if(min != i){
                    ans.remove(min);
                    ans.add(i);
                }
            }

        }
        return ans;
    }

    public String[] getLinks(){
        String[] links = new String[2];
        String state = userAnswers.getAnswer("If you live in one of these US States, what is your State of residence? Otherwise put NA.");
        switch(state){
            case "AL":
                links[0] = "https://mh.alabama.gov/";
                links[1] = "https://www.crisiscenterbham.org/";
                return links;
            case "AK":
                links[0] = "https://alaskamentalhealthtrust.org/";
                links[1] = "https://namialaska.org/";
                return links;
            case "AZ":
                links[0] = "https://directorsblog.health.azdhs.gov/category/behavioralhealth/";
                links[1] = "https://www.mhaarizona.org/";
                return links;
            case "AR":
                links[0] = "https://humanservices.arkansas.gov/divisions-shared-services/aging-adult-behavioral-health-services/";
                links[1] = "https://namiarkansas.org/";       
                return links;     
            case "CA":
                links[0] = "https://www.dhcs.ca.gov/services/Pages/MentalHealthPrograms-Svcs.aspx";
                links[1] = "https://www.mentalhealthca.org/";
                return links;
            case "CO":
                links[0] = "https://bha.colorado.gov/";
                links[1] = "https://www.mentalhealthcolorado.org/";
                return links;
            case "CT":
                links[0] = "https://portal.ct.gov/dmhas";
                links[1] = "https://namict.org/";
                return links;
            case "DE":
                links[0] = "https://dhss.delaware.gov/dhss/dsamh/";
                links[1] = "https://www.mhainde.org/";
                return links;
            case "FL":
                links[0] = "https://www.myflfamilies.com/services/substance-abuse-and-mental-health";
                links[1] = "https://namiflorida.org/";
                return links;
            case "GA":
                links[0] = "https://dbhdd.georgia.gov/";
                links[1] = "https://www.mhageorgia.org/";
                return links;
            case "HI":
                links[0] = "https://health.hawaii.gov/amhd/";
                links[1] = "https://mentalhealthhawaii.org/";
                return links;
            case "ID":
                links[0] = "https://healthandwelfare.idaho.gov/services-programs/behavioral-health";
                links[1] = "https://namiidaho.org/";
                return links;
            case "IL":
                links[0] = "https://www.dhs.state.il.us/page.aspx?item=29735";
                links[1] = "https://www.mhai.org/";
                return links;
            case "IN":
                links[0] = "https://www.in.gov/fssa/dmha/";
                links[1] = "https://mhai.net/";
                return links;
            case "IA":
                links[0] = "https://hhs.iowa.gov/disability-services";
                links[1] = "https://namiiowa.org/";
                return links;
            case "KS":
                links[0] = "https://www.kdads.ks.gov/";
                links[1] = "https://mhah.org/";
                return links;
            case "KY":
                links[0] = "https://www.chfs.ky.gov/agencies/dbhdid/Pages/default.aspx";
                links[1] = "https://www.namiky.org/";
                return links;
            case "LA":
                links[0] = "https://ldh.la.gov/subhome/10";
                links[1] = "https://louisianamha.org/";
                return links;
            case "ME":
                links[0] = "https://www.maine.gov/dhhs/obh";
                links[1] = "https://www.namimaine.org/";
                return links;
            case "MD":
                links[0] = "https://health.maryland.gov/bha/Pages/Index.aspx";
                links[1] = "https://www.mhamd.org/";
                return links;
            case "MA":
                links[0] = "https://www.mass.gov/orgs/massachusetts-department-of-mental-health";
                links[1] = "https://namimass.org/";
                return links;
            case "MI":
                links[0] = "https://www.michigan.gov/mdhhs/keep-mi-healthy/mentalhealth";
                links[1] = "https://mhami.org/";
                return links;
            case "MN":
                links[0] = "https://mn.gov/dhs/partners-and-providers/program-overviews/behavioral-health/";
                links[1] = "https://mentalhealthmn.org/";
                return links;
            case "MS":
                links[0] = "https://www.dmh.ms.gov/";
                links[1] = "https://namims.org/";
                return links;
            case "MO":
                links[0] = "https://dmh.mo.gov/";
                links[1] = "https://mha-em.org/";
                return links;
            case "MT":
                links[0] = "https://dphhs.mt.gov/bhdd/";
                links[1] = "https://namimt.org/";
                return links;
            case "NE":
                links[0] = "https://dhhs.ne.gov/Pages/behavioral-health.aspx";
                links[1] = "https://naminebraska.org/";
                return links;
            case "NV":
                links[0] = "https://dpbh.nv.gov/";
                links[1] = "https://www.namiwesternnevada.org/";
                return links;
            case "NH":
                links[0] = "https://www.dhhs.nh.gov/programs-services/health-care/behavioral-health";
                links[1] = "https://www.naminh.org/";
                return links;
            case "NJ":
                links[0] = "https://www.nj.gov/humanservices/dmhas/home/";
                links[1] = "https://www.mhanj.org/";
                return links;
            case "NM":
                links[0] = "https://www.hca.nm.gov/about_the_department/behavioral-health-collaborative/";
                links[1] = "https://www.naminm.org/";
                return links;
            case "NY":
                links[0] = "https://omh.ny.gov/";
                links[1] = "https://mhanys.org/";
                return links;
            case "NC":
                links[0] = "https://www.ncdhhs.gov/divisions/mhddsus";
                links[1] = "https://naminc.org/";
                return links;
            case "ND":
                links[0] = "https://www.hhs.nd.gov/behavioral-health";
                links[1] = "https://www.mhand.org/";
                return links;
            case "OH":
                links[0] = "https://mha.ohio.gov/";
                links[1] = "https://www.mhaohio.org/";
                return links;
            case "OK":
                links[0] = "https://oklahoma.gov/odmhsas.html";
                links[1] = "https://mhaok.org/";
                return links;
            case "OR":
                links[0] = "https://www.oregon.gov/oha/hsd/amh/pages/index.aspx";
                links[1] = "https://namior.org/";
                return links;
            case "PA":
                links[0] = "https://www.pa.gov/agencies/dhs/resources/mental-health-substance-use-disorder/mental-health.html";
                links[1] = "https://mhapa.org/";
                return links;
            case "RI":
                links[0] = "https://bhddh.ri.gov/";
                links[1] = "https://namirhodeisland.org/";
                return links;
            case "SC":
                links[0] = "https://scdmh.net/";
                links[1] = "https://namisc.org/";
                return links;
            case "SD":
                links[0] = "https://dss.sd.gov/behavioralhealth/";
                links[1] = "https://namisouthdakota.org/";
                return links;
            case "TN":
                links[0] = "https://www.tn.gov/behavioral-health.html";
                links[1] = "https://namitn.org/";
                return links;
            case "TX":
                links[0] = "https://www.hhs.texas.gov/about/process-improvement/improving-services-texans/behavioral-health-services";
                links[1] = "https://mhahouston.org/";
                return links;
            case "UT":
                links[0] = "https://sumh.utah.gov/";
                links[1] = "https://namiut.org/";
                return links;
            case "VT":
                links[0] = "https://mentalhealth.vermont.gov/";
                links[1] = "https://namivt.org/";
                return links;
            case "VA":
                links[0] = "https://dbhds.virginia.gov/";
                links[1] = "https://mhav.org/";
                return links;
            case "WA":
                links[0] = "https://www.hca.wa.gov/about-hca/programs-and-initiatives/behavioral-health-and-recovery";
                links[1] = "https://namiwa.org/";
                return links;
            case "WV":
                links[0] = "https://dhhr.wv.gov/bbh/Pages/default.aspx";
                links[1] = "https://www.nami.org/affiliate/west-virginia/";
                return links;
            case "WI":
                links[0] = "https://www.dhs.wisconsin.gov/mh/index.htm";
                links[1] = "https://namiwisconsin.org/";
                return links;
            case "WY":
                links[0] = "https://health.wyo.gov/behavioralhealth/";
                links[1] = "https://namiwyoming.com/";
                return links;
            case "NA":
                links[0] = "https://unitedgmh.org/support/";
                links[1] = "https://www.helpguide.org/mental-health";
                return links;
            default:
            System.out.println("ERROR IN FINDING STATE: " + state );
            System.exit(0);
        }
        return links;
    }
}