package com.example.kitchenfairyprototype;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataController {
    //Save the UserReference
    /**
     * method for saving the UserReference object to JSON.
     * The JSON file is saved to app files to be accessed later.
     * Allows data to be preserved between application life cycles.
     *
     * @param context : the application context. used to get root file path
     *                where the user reference will be saved
     * */
    public void saveReference(Context context) {
        UserReference instance = UserReference.getInstance();
        File directory = context.getFilesDir();
        File file = new File(directory, "UserReference.json");
        //check if file exists. If not, initialize the file as an empty .json
        if(!file.exists()){
            try{
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                BufferedWriter br = new BufferedWriter(fileWriter);
                br.write("{}");
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        //save UserReference as a JSON to key specified location in shared preference data.
        try {
            //creates a new FileWriter which can write the JSON file.
            //The path should be retrieved with getAbsolutePath()
            FileWriter writer = new FileWriter(file.getAbsolutePath());
            gson.toJson(instance, writer);
            //make sure to close the writer!
            writer.close();
            //catch any input/output exceptions related to writing the file.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //load the UserReference
    /**
     * loadReference function takes a file name and the application context to find
     * and read the UserReference.json stored on the device. Once loaded, the UserReference
     * object's update() function should be called to transfer data from the loaded reference
     * to the currently active UserReference singleton.
     *
     * @param fileName : the name of the file. Should be "UserReference.json"
     * @param context : the application context. Needed to run the file and directory searches.
     *                Passed into the loadReference() function from the calling activity which
     *                should have the application context stored.
     * */
    public static UserReference loadReference(String fileName, Context context) {
        //create new GSON instance.
        Gson gson = new Gson();

        //finds the File folder where the .json file is saved
        File directory = context.getFilesDir();
        //finds the .json file stored in the folder found above
        File file = new File(directory, "UserReference.json");
        //loads a file
        try {
            //create a buffered file reader. Uses a FileReader to find the file to read.
            //the absolute path of the file found above is passed to get access to the .json file
            BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));

            //uses the buffered reader to deserialize the .json file into a UserReference
            UserReference loadReference = gson.fromJson(br, UserReference.class);
            System.out.println("Data loaded successfully.");
            br.close(); //close the reader
            //call update on UserReference in the calling activity.

            return loadReference;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("There was an error with loading data.");
        //returns the default or previous instance instead of the loaded one
        return UserReference.getInstance();
    }

    /**
     * updateRecipeById finds a recipe by iterating through the recipes contained in the UserReference data object.
     * Changes the recipe saved in the UserReference to the new recipe passed in to this function
     * saveReference() must be called in the activity that uses this method in order to make sure that the update is saved.
     *
     * @param recipe : a Recipe object that will update the recipe with the same id in the UserReference
     * */
    public void updateRecipeById(Recipe recipe){
        UserReference ur = UserReference.getInstance();

        int id = recipe.id;

        for(Recipe r: ur.recipes){
            int dex = ur.recipes.indexOf(r);
            if(id == r.id){
                ur.recipes.set(dex, recipe);
                //saveReference() should be called in the calling activity after successfully updating the recipe
                return;
            }
        }
    }

    /**
     * updateShoppingById finds a shopping list by iterating through the lists contained in the UserReference data object.
     * Changes the shopping list saved in the UserReference to the new shopping list passed in to this function
     * saveReference() must be called in the activity that uses this method in order to make sure that the update is saved.
     *
     * @param shopping : a Shopping object that will update the shopping list with the same id in the UserReference
     * */
    public void updateShoppingById(Shopping shopping){
        UserReference ur = UserReference.getInstance();

        int id = shopping.id;

        for(Shopping s: ur.shoppingLists){
            int dex = ur.shoppingLists.indexOf(s);
            if(id == s.id){
                ur.shoppingLists.set(dex, shopping);
                //saveReference() should be called in the calling activity after successfully updating the recipe
                return;
            }
        }
    }

    /**
     * findRecipeById finds a recipe by iterating through the recipes contained in the UserReference data object.
     * If the id is found in the UserReference, returns true. If not, returns false. This check ensures that changes always point
     * to a recipe that exists.
     *
     * @param id : an id that will be searched to find if a recipe with the same id exists in UserReference
     * @return boolean
     * */
    public boolean findRecipeById(int id){
        UserReference ur = UserReference.getInstance();
        for (Recipe r : ur.recipes){
            if (id == r.id){
                return true;
            }
        }
        return false;
    }

    /**
     * findShoppingById finds a shopping list by iterating through the lists contained in the UserReference data object.
     * If the id is found in the UserReference, returns true. If not, returns false. This check ensures that changes always point
     * to a shopping list that exists.
     *
     * @param id : an id that will be searched to find if a shopping list with the same id exists in UserReference
     * @return boolean
     * */
    public boolean findShoppingById(int id){
        UserReference ur = UserReference.getInstance();
        for (Shopping s : ur.shoppingLists){
            if (id == s.id){
                return true;
            }
        }
        return false;
    }

    /**
     * deleteRecipeById deletes a recipe by iterating through the recipes contained in the UserReference data object.
     * If the id is found in the UserReference, the recipe containing that id is deleted.
     * this is done by repopulating a new list that excludes the selected recipe by id.
     * the new list is then saved to the UserReference, and the recipeIdHolder is updated with the proper number of items
     *
     * saveReference() must be called by the calling activity in order to ensure that changes to the data are saved.
     *
     * @param id : an id that will be searched to find if it exists in UserReference. If it exists, creates a new list excluding that recipe
     * */
    public void deleteRecipeById(int id){
        UserReference ur = UserReference.getInstance();
        if(findRecipeById(id)){
            ArrayList<Recipe> newList = new ArrayList<>();
            for (Recipe r : ur.recipes){
                if(r.id != id){
                    newList.add(r);
                }
            }
            ur.recipes = newList;
            ur.setRecipeIDHolder(newList.size() + 1);
            //call saveReference from calling activity
        }
    }

    /**
     * deleteShoppingById deletes a shopping list by iterating through the lists contained in the UserReference data object.
     * If the id is found in the UserReference, the shopping list containing that id is deleted.
     * this is done by repopulating a new list that excludes the selected shopping list by id.
     * the new list is then saved to the UserReference, and the shoppingIdHolder is updated with the proper number of items
     *
     * saveReference() must be called by the calling activity in order to ensure that changes to the data are saved.
     *
     * @param id : an id that will be searched to find if it exists in UserReference. If it exists, creates a new list excluding that shopping list
     * */
    public void deleteShoppingById(int id){
        UserReference ur = UserReference.getInstance();
        if(findShoppingById(id)){
            ArrayList<Shopping> newList = new ArrayList<>();
            for (Shopping s : ur.shoppingLists){
                if(s.id != id){
                    newList.add(s);
                }
            }
            ur.shoppingLists = newList;
            ur.setRecipeIDHolder(newList.size() + 1);
            //call saveReference from calling activity
        }
    }

}
