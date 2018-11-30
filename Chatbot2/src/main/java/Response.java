import java.util.ArrayList;

public class Response {

    public String input;
    public ArrayList<String> Output;

    public Response(){
        Output = new ArrayList<String>();
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

}
