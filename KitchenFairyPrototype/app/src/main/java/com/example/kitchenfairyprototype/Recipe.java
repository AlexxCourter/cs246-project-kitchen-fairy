package com.example.kitchenfairyprototype;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe extends ItemModel implements Serializable {
    //ItemModel specifically for recipes
    /**
     * member data included in the ItemModel super() call
     * */

    /**
     * Constructor sets up all required data objects in the Recipe. simply calls super() to fill the data of an ItemModel
     * @param id : an integer representing a unique identifying number for the item.
     * @param name : a string representing the name of the item.
     * @param items : an arraylist of strings that represent the "ingredients".
     * @param instructions : an arraylist of strings representing the instructions of a recipe.
     * @param img : a byte array that contains data for a recipe image which can be converted to bitmap.
     * */
    public Recipe(int id, String name, ArrayList<String> items, ArrayList<String> instructions, byte[] img) {
        super(id, name, items, instructions, img);
    }

    /**
     * toString() returns the toString() call from ItemModel
     * */
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
