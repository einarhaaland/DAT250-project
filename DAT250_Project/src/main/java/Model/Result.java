package Model;

import com.google.gson.Gson;


public class Result {

    /**
     * Hente id fra poll? så slipper vi generere nye
     */

    private int id;
    private int yes;
    private int no;

    public Result(int id, int yes, int no){
        this.id = id;
        this.yes = yes;
        this.no = no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setNo(int no) {
        this.no = no;
    }

    public String toJson() {
        Gson gson = new Gson();
        String representation = gson.toJson(this);
        return representation;
    }
}