package com.example.kitchenfairyprototype;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Shopping extends ItemModel implements Serializable {

    /**
     * member data included in the ItemModel super() call
     * */

    /**
     * Constructor sets up all required data objects in the Recipe. simply calls super() to fill the data of an ItemModel
     * @param id : an integer representing a unique identifying number for the item.
     * @param name : a string representing the name of the item.
     * @param items : an arraylist of strings that represent the "ingredients".
     * */
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
