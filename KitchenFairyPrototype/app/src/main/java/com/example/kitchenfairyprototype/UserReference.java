package com.example.kitchenfairyprototype;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * UserReference data model
 * Controls all the data related to the user
 * Holds the following member data:
 * int RecipeIDHolder - an index number for the next recipe to be created. Adds the id number to the recipe on creation and then increments +1
 * int shoppingIDHolder - an index number for the next Shopping(). Adds the id to the Shopping() on creation, then increments +1
 * String username - a name chosen by the user. Can be changed at any time
 * shoppingLists - an ArrayList of the currently saved Shopping() objects
 * recipes - an ArrayList of the currently saved Recipe() objects
 * */
public class UserReference {
    private static UserReference instance = null;

    //member variables
    private int recipeIDHolder = 1;
    private int shoppingIDHolder = 1;
    private String username = "default";
    protected ArrayList<Shopping> shoppingLists = new ArrayList<>();
    protected ArrayList<Recipe> recipes = new ArrayList<>();
    protected static boolean NEW_USER = true;
    //gson for save data
    Gson gson;

    //constructor
    private UserReference() {
    }


    //getters and setters
    public int getRecipeIDHolder() {
        return recipeIDHolder;
    }

    public void setRecipeIDHolder(int recipeIDHolder) {
        this.recipeIDHolder = recipeIDHolder;
    }

    public int getShoppingIDHolder() {
        return shoppingIDHolder;
    }

    public void setShoppingIDHolder(int shoppingIDHolder) {
        this.shoppingIDHolder = shoppingIDHolder;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void reset() {
        //display a message, then reset the user data if the user affirms
        String TAG = "Reset";
        Log.d(TAG, "About to reset user data");

    }



    public static UserReference getInstance() {
        if (instance == null) {
            synchronized(UserReference.class) {
                if (instance == null) {
                    instance = new UserReference();
                }
            }
        }
        return instance;
    }

    public static void updateReferenceRecipe(UserReference user) {
        //updates current reference with a new UserReference containing recipe updates
        instance.setRecipeIDHolder(instance.getRecipeIDHolder() + 1); //add 1 to recipe ID
        instance.recipes = user.recipes;

    }

    public static void updateReferenceShopping(UserReference user) {
        //updates current reference with a new UserReference containing Shopping updates
        instance.setShoppingIDHolder(instance.getShoppingIDHolder() + 1); //add 1 to shopping ID
        instance.shoppingLists = user.shoppingLists;
    }

    public static void loadUpdate(UserReference user){
        //wholesale update which changes all data to match a loaded reference
        if (user != null) {
        instance.setUsername(user.getUsername());
        instance.setRecipeIDHolder(user.getRecipeIDHolder());
        instance.setShoppingIDHolder(user.getShoppingIDHolder());
        instance.shoppingLists = user.shoppingLists;
        instance.recipes = user.recipes;
        instance.NEW_USER = false;
        } else {
            System.out.println("Error: passed user reference is null");
        }
    }

    @Override
    public String toString() {
        return "UserReference{" +
                "recipeIDHolder=" + recipeIDHolder +
                ", shoppingIDHolder=" + shoppingIDHolder +
                ", username='" + username + '\'' +
                ", shoppingLists=" + shoppingLists +
                ", recipes=" + recipes +
                '}';
    }
}