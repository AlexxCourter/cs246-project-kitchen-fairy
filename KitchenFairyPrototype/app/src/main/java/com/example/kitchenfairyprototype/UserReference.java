package com.example.kitchenfairyprototype;

import androidx.annotation.NonNull;

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

    /**
     * @return instance : the current global instance of the UserReference singleton.
     * */
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

    /**
     * updates the current reference based on a UserReference with updated values.
     * @param user : the updated UserReference with new recipe information.
     * */
    public static void updateReferenceRecipe(UserReference user) {
        //updates current reference with a new UserReference containing recipe updates
        instance.setRecipeIDHolder(instance.getRecipeIDHolder() + 1); //add 1 to recipe ID
        instance.recipes = user.recipes;

    }

    /**
     * updates the current reference based on a UserReference with updated values.
     * @param user : the updated UserReference with new Shopping object information.
     * */
    public static void updateReferenceShopping(UserReference user) {
        //updates current reference with a new UserReference containing Shopping updates
        instance.setShoppingIDHolder(instance.getShoppingIDHolder() + 1); //add 1 to shopping ID
        instance.shoppingLists = user.shoppingLists;
    }

    /**
     * updates the current reference based on a UserReference with updated values.
     * @param user : the updated UserReference with new information.
     * */
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

    @NonNull
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
