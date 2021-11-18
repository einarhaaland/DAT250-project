package Model;

import com.google.gson.Gson;


public class Result {

    /**
     * Hente id fra poll? så slipper vi generere nye
     */

    //private int id;
    private String question;
    private int yes;
    private int no;

    public Result(String question, int yes, int no){
  //      this.id = id;
        this.question = question;
        this.yes = yes;
        this.no = no;
    }


    public int getYes() {
        return yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNo() {
        return no;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String toJson() {
        Gson gson = new Gson();
        String representation = gson.toJson(this);
        return representation;
    }
}
