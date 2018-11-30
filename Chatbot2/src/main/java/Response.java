import java.util.ArrayList;

public class Response {

    public String input;
    public ArrayList<String> Output;
    public ArrayList<String> Keywords;

    public Response(){
        Output = new ArrayList<String>();
        Keywords = new ArrayList<String>();
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public ArrayList<String> getOutput() {
        return Output;
    }

    public void setOutput(ArrayList<String> output) {
        this.Output = output;
    }
    public void addOutput(String output){
        Output.add(output);
    }

    public void addKeywords(String keyword){
        Keywords.add(keyword);
    }

    public ArrayList<String> getKeywords() {
        return Keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        Keywords = keywords;
    }

}
