package com.example.fitnesscenter.Model;

public class User {
    String name ,email,wic,status,date,starttime,endtime;
    public User(String name,String email ,String wic,String status){
        this.email=email;
        this.name=name;
        this.wic=wic;
        this.status=status;
    }

    public User(String name,String date ,String starttime,String endtime,String status){
        this.date=date;
        this.name=name;
        this.starttime=starttime;
        this.status=status;
        this.endtime=endtime;
    }

    public String getDate() {
        return date;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getWic() {
        return wic;
    }

    public String getStatus() {
        return status;
    }
}
