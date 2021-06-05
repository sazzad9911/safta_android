package www.scientistx.saftafood;

import java.util.Date;

public class vp_message {
    private String name;
    private String body;
    private long time;

    public vp_message(String name,String body){
        this.name=name;
        this.body=body;
        time=new Date().getTime();
    }
    public vp_message(){

    }
    public String getName(){
        return name;
    }
    public  String getBody(){
        return body;
    }
    public long getTime(){
        return time;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setBody(String body){
        this.body=body;
    }
}
