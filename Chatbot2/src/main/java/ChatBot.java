import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class ChatBot {
    private static final String FILENAME = "D://Database.txt";

    public static String keywords[][] = {{"CODE", "WHAT IS AVIO?"}, {"EASTER EGG", "WHO CREATED THIS CHATBOT?"}};

    public static final String RED = "\033[0;31m";
    public static final String RESET = "\033[0m";
    public static final String YELLOW = "\033[0;33m";
    public static final String CYAN = "\033[0;36m";
    public static final String PURPLE = "\033[0;35m";
    public static boolean giveBestAnswer;
    public static ArrayList<Response> POSSIBLEQUESTIONS;
    public static String sInput;
    public static String sResponse;
    public static String keyword;
    public static boolean responseWasNull;
    public static Database database;

    static ArrayList<Response> KnowledgeBase = new ArrayList<Response>();
    public static Random random = new Random();

    static String findMatch(String str) {
        String result = "";
        for (int i = 0; i < KnowledgeBase.size(); ++i) {
            if (str.indexOf(KnowledgeBase.get(i).input) != -1) {
                result = KnowledgeBase.get(i).getOutput().get(random.nextInt(KnowledgeBase.get(i).getOutput().size()));
                POSSIBLEQUESTIONS.add(KnowledgeBase.get(i));
            }
        }
        return result;
    }


    public void loadDatabase() {

    }

    public static void main(String[] args) throws Exception {
        database = new Database();
        KnowledgeBase = database.loudDatabase(FILENAME);
        System.out.println(CYAN + "YOU CAN TYPE '/HELP' TO GET MORE INFORMATION ON HOW TO CHANGE THE CHATBOTS SETTINGS." + RESET);
        while (true) {
            System.out.print(">");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            sInput = in.readLine();
            sInput = sInput.toUpperCase();
            POSSIBLEQUESTIONS = new ArrayList<Response>();
            if (sInput.equalsIgnoreCase("BYE")) {
                System.out.println(CYAN + "IT WAS NICE TALKING TO YOU USER, SEE YOU NEXT TIME!" + RESET);
                break;
            }
            responseWasNull = false;
            checkInput();
            sResponse = findMatch(sInput);
            if (sResponse.length() == 0) {
                responseWasNull = true;
                checkKeywords();
            }
            if (responseWasNull == false) {
                System.out.println(YELLOW + sResponse + RESET);
            }
        }

    }
    public static void checkInput(){
        if (!sInput.contains("?")) {
            sInput = sInput + "?";
        }
        if (sInput.equalsIgnoreCase("/TRUE")) {
            giveBestAnswer = true;
            responseWasNull = true;
        }
        if (sInput.equalsIgnoreCase("/FALSE")) {
            giveBestAnswer = false;
            responseWasNull = true;
        }
        if (sInput.equalsIgnoreCase("/HELP")) {
            System.out.println(CYAN + "YOU CAN CHOOSE TO GET THE BEST POSSIBLE ANSWER OR TO GET A LIST OF POSSIBLE ANSWERS YOU CAN CHANGE THIS SETTING BY TYPING '/TRUE' OR '/FALSE'" + RESET);
            responseWasNull = true;
        }
    }

    public static void checkKeywords(){
        for (int x = 0; x < KnowledgeBase.size(); x++) {
            for (int i = 0; i < KnowledgeBase.get(x).getKeywords().size(); i++) {
                if (sInput.contains(KnowledgeBase.get(x).getKeywords().get(i))) {
                    sResponse = findMatch(KnowledgeBase.get(x).input);
                    keyword = KnowledgeBase.get(x).getKeywords().get(i);
                    //System.out.println("i'm still looking" + keyword);
                    //System.out.println(findMatch(KnowledgeBase.get(x).getKeywords().get(i)));
                }
            }
        }
        if (POSSIBLEQUESTIONS.size() == 0) {
            System.out.println(CYAN + "I'M NOT SURE IF I UNDERSTAND WHAT YOU  ARE TALKING ABOUT." + RESET);
        }
        if (POSSIBLEQUESTIONS.size() == 1) {
            System.out.println("WE DIDN'T FIND AN EXACT MATCH FOR YOUR QUESTION BUT WE DID FIND A QUESTION MATICHING ONE BASED ON THE WORDS YOU USED, KEYWORD :" + RED + keyword + RESET);
            System.out.println(PURPLE + POSSIBLEQUESTIONS.get(0).input + RESET);
            System.out.println(YELLOW + sResponse + RESET);

        }
        if (POSSIBLEQUESTIONS.size() >= 2) {
            if(giveBestAnswer){
                System.out.println("BASED ON YOUR KEYWORDS WE SELECTED THIS ANSWER");
                Response bestResponse = new Response();
                Response tempResponse;
                ArrayList<String> Goodkeywords;
                String keyword = "";
                for (int i = 0; POSSIBLEQUESTIONS.size() > i; i++) {
                    tempResponse = new Response();
                    Goodkeywords = new ArrayList<String>();
                    for (int j = 0; POSSIBLEQUESTIONS.get(i).Keywords.size() > j; j++) {
                        if (sInput.contains(POSSIBLEQUESTIONS.get(i).Keywords.get(j))){
                            Goodkeywords.add(POSSIBLEQUESTIONS.get(i).Keywords.get(j));
                        }
                    }
                    tempResponse.setInput(POSSIBLEQUESTIONS.get(i).getInput());
                    tempResponse.setOutput(POSSIBLEQUESTIONS.get(i).getOutput());
                    tempResponse.setKeywords(Goodkeywords);
                    if (Goodkeywords.size() > bestResponse.Keywords.size()){
                        bestResponse = tempResponse;
                    }
                    if (Goodkeywords.size() == bestResponse.Keywords.size()){
                        String goodkeywordsLength = "";
                        String bestResponseLength = "";
                        for (int x =0; Goodkeywords.size() > x; x++){
                            goodkeywordsLength = goodkeywordsLength + Goodkeywords.get(x);
                        }
                        for (int x =0; bestResponse.Keywords.size() > x; x++){
                            bestResponseLength = bestResponseLength + bestResponse.Keywords.get(x);
                        }
                        if (bestResponseLength.length() < goodkeywordsLength.length()){
                             bestResponse = tempResponse;
                            System.out.println(bestResponseLength.length());
                            System.out.println(goodkeywordsLength.length());

                        }
                    }
                }
                System.out.println(PURPLE + bestResponse.input + RESET );
                System.out.println(YELLOW + bestResponse.Output.get(random.nextInt(bestResponse.Output.size())) + RESET);
            }else{
                System.out.println("WE FOUND " + POSSIBLEQUESTIONS.size() + " QUESTIONS THAT WERE SIMULAR TO YOUR QUESION, PLEASE TYPE THE QUESTION THAT YOU WOULD LIKE TO BE ANSWERED : ");
                for (int i = 0; POSSIBLEQUESTIONS.size() > i; i++) {
                    System.out.println(PURPLE + POSSIBLEQUESTIONS.get(i).input + RESET);
                }
            }
        }
    }
}
