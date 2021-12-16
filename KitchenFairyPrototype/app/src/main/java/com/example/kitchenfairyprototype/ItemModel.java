package com.example.kitchenfairyprototype;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemModel implements Serializable {
    //an abstract representation of a recipe book item and data

    protected int id;
    protected String name;
    protected ArrayList<String> items;
    protected ArrayList<String> notes;
    protected byte[] img;

    /**
     * Constructor sets up all required data objects in the ItemModel
     * @param id : an integer representing a unique identifying number for the item.
     * @param name : a string representing the name of the item.
     * @param items : an arraylist of strings that represent the "ingredients" or "shopping items".
     * @param notes : an arraylist of strings representing the instructions of a recipe.
     * @param img : a byte array that contains data for a recipe image which can be converted to bitmap.
     * */
    //constructor
    public ItemModel(int id, String name, ArrayList<String> items, ArrayList<String> notes, byte[] img) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.notes = notes;
        this.img = img;
    }

    /**
     * Simply returns the name of the ItemModel
     * @return string ItemModel name
     * */
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
