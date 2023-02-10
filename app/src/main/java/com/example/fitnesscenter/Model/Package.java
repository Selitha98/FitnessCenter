package com.example.fitnesscenter.Model;

public class Package {
    String name ,starting_time,Price;
    public Package(String name,String starting_time,String price){
        this.name=name;
        this.starting_time=starting_time;
        this.Price=price;
    }

    public String getPrice() {
        return Price;
    }

    public String getName() {
        return name;
    }

    public String getStarting_time() {
        return starting_time;
    }
}
