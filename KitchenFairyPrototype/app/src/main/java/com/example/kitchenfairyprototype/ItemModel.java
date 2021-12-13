package com.example.kitchenfairyprototype;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemModel implements Serializable {
    //an abstract representation of a recipe book item and data

    protected int id;
    protected String name;
    protected ArrayList<String> items;
    protected ArrayList<String> notes;
    protected byte[] img;

    //constructor
    public ItemModel(int id, String name, ArrayList<String> items, ArrayList<String> notes, byte[] img) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.notes = notes;
        this.img = img;
    }

    @Override
    public String toString() {
        return name;
    }
}
