import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Database {
    private ArrayList<Response> KnowledgeBase = new ArrayList<Response>();

    public ArrayList<Response> loudDatabase(String FILENAME) {
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
                if (sCurrentLine.contains("#")) {
                    KnowledgeBase.add(tempresponse);
                    j = 0;
                    System.out.println(tempresponse.getInput());
                    System.out.println(tempresponse.getOutput());
                    System.out.println(tempresponse.getKeywords());
                    tempresponse = new Response();
                    isKeyWord = false;
                } else if (sCurrentLine.contains("*")) {
                    isKeyWord = true;
                } else if (isKeyWord == false) {
                    if (j == 0) {
                        tempresponse.setInput(sCurrentLine);
                        j++;
                    } else {
                        tempresponse.addOutput(sCurrentLine);
                        j++;
                    }
                } else {
                    tempresponse.addKeywords(sCurrentLine);
                }
            }

        } catch (Exception e) {

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
            return KnowledgeBase;
        }
    }
}
