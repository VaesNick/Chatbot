import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class ChatBot {
    private static final String FILENAME = "D://Database.txt";

    public static String keywords[][] = {{ "CODE" , "WHAT IS AVIO?"} , { "EASTER EGG" , "WHO CREATED THIS CHATBOT?" }};

    public static final String RED = "\033[0;31m";
    public static final String RESET = "\033[0m";
    public static final String YELLOW = "\033[0;33m";
    public static final String CYAN = "\033[0;36m";
    public static final String PURPLE = "\033[0;35m";
    public static ArrayList<String> POSSIBLEQUESTIONS;
    public static String keyword;
    public static boolean responseWasNull;

    static ArrayList<Response> KnowledgeBase = new ArrayList<Response>();
    public static Random random = new Random();
    static String findMatch(String str) {
        String result = "";
        for (int i = 0; i < KnowledgeBase.size(); ++i) {
            if (str.indexOf(KnowledgeBase.get(i).input) !=-1) {
                result = KnowledgeBase.get(i).getOutput().get(random.nextInt(KnowledgeBase.get(i).getOutput().size()));
                POSSIBLEQUESTIONS.add(KnowledgeBase.get(i).input);
            }
        }
        return result;
    }


    public void loadDatabase() {

    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = null;
        FileReader fr = null;
        try {
            System.out.println("Loading database");
            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;
            int i = 0;
            int j = 0;
            Response tempresponse = new Response();
            boolean isKeyWord = false;
            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.contains("#")){
                    KnowledgeBase.add(tempresponse);
                    j = 0;
                    System.out.println(tempresponse.getInput());
                    System.out.println(tempresponse.getOutput());
                    System.out.println(tempresponse.getKeywords());
                    tempresponse = new Response();
                    isKeyWord = false;
                }
                else if(sCurrentLine.contains("*")){
                    isKeyWord = true;
                }
                else if (isKeyWord == false){
                    if (j == 0) {
                        tempresponse.setInput(sCurrentLine);
                        j++;
                    } else{
                        tempresponse.addOutput(sCurrentLine);
                        j++;
                    }
                }else {
                    tempresponse.addKeywords(sCurrentLine);
                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }

        while (true) {
            System.out.print(">");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String sInput = in.readLine();
            sInput = sInput.toUpperCase();
            POSSIBLEQUESTIONS = new ArrayList<String>();
            if(! sInput.contains("?")){
                sInput = sInput + "?";
            }
            String sResponse = findMatch(sInput);
            responseWasNull = false;
            if (sInput.equalsIgnoreCase("BYE")) {
                System.out.println(CYAN + "IT WAS NICE TALKING TO YOU USER, SEE YOU NEXT TIME!" + RESET);
                break;
            }

                if (sResponse.length() == 0) {
                    responseWasNull = true;
                    for (int x = 0; x < KnowledgeBase.size(); x++){
                        for (int i = 0; i < KnowledgeBase.get(x).getKeywords().size(); i++) {
                            if (sInput.contains(KnowledgeBase.get(x).getKeywords().get(i))) {
                                sResponse = findMatch(KnowledgeBase.get(x).input);
                                keyword = KnowledgeBase.get(x).getKeywords().get(i);
                                //System.out.println("i'm still looking" + keyword);
                                //System.out.println(findMatch(KnowledgeBase.get(x).getKeywords().get(i)));
                            }
                    }
                    }
                    if(POSSIBLEQUESTIONS.size() == 0){
                        System.out.println(CYAN + "I'M NOT SURE IF I UNDERSTAND WHAT YOU  ARE TALKING ABOUT." + RESET);
                    }
                    if(POSSIBLEQUESTIONS.size() == 1) {
                        System.out.println("WE DIDN'T FIND AN EXACT MATCH FOR YOUR QUESTION BUT WE DID FIND A QUESTION MATICHING ONE BASED ON THE WORDS YOU USED, KEYWORD :" + RED + keyword + RESET);
                        System.out.println(PURPLE + POSSIBLEQUESTIONS.get(0) + RESET);
                        System.out.println(YELLOW + sResponse + RESET);

                    }
                    if(POSSIBLEQUESTIONS.size() >= 2) {
                        System.out.println("WE FOUND " + POSSIBLEQUESTIONS.size() + " QUESTIONS THAT WERE SIMULAR TO YOUR QUESION, PLEASE TYPE THE QUESTION THAT YOU WOULD LIKE TO BE ANSWERED : ");
                        for (int i = 0; POSSIBLEQUESTIONS.size() > i; i++) {
                            System.out.println(PURPLE + POSSIBLEQUESTIONS.get(i));
                        }
                    }

                }
                if (responseWasNull == false) {
                    System.out.println(YELLOW + sResponse + RESET);
                }
            }

        }
    }