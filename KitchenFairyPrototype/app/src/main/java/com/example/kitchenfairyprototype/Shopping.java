package com.example.kitchenfairyprototype;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Shopping extends ItemModel implements Serializable {


    public Shopping(int id, String name, ArrayList<String> items) {
        super(id, name, items, null, null);
    }
    //ItemModel specifically for shopping lists


    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
