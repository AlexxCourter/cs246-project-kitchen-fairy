package com.example.prove06;

public class Weather {

    //contains the id, weather info "main", description, icon, which are part of the weather list
    int id;
    String main;
    String description;
    String icon;

    @Override
    public String toString() {
        return  "\n|" + "Condition: " +
                description +
                "\n";
    }
}
