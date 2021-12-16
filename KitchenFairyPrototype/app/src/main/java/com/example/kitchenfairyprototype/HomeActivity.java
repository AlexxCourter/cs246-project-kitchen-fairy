package com.example.kitchenfairyprototype;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    final String TAG = "MainActivity";
    protected UserReference user;


    /**
     * The HomeActivity onCreate initializes new user data if a save file cannot be found.
     * displays the home screen layout.
     * contains two button click listeners to open either the recipe list, or shopping lists activities.
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = UserReference.getInstance();
        DataController dc = new DataController();

        //read saved data and apply to UserReference
        try {
            UserReference loader = dc.loadReference(this.getApplicationContext());
            user = loader;
            UserReference.loadUpdate(user);
            Log.d(TAG, user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(user.NEW_USER == true) {
            //new user creation
            //inflate form for entering name here
            //for now, set to a test default until version 2
            String username = "Test User";
            user.setUsername(username);
            Log.d(TAG, "onCreate: test user initialized");
            user.setRecipeIDHolder(1);
            user.setShoppingIDHolder(1);
            Log.d(TAG, user.toString());
            Log.d(TAG, "Direct reference: ");
            Log.d(TAG, UserReference.getInstance().toString());
            user.NEW_USER = false;
        }



        //button event listeners. These need to be instantiated somewhere with activity callback, like the onCreate method
        /**
         * Finds the Recipes button view by id
         * sets an onclicklistener that starts the RecipeList activity with intent.
         * */
        //Recipes button
        Button btnRecipe = findViewById(R.id.btnRecipe);
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Opening RecipeList activity");
                Log.d(TAG, "About to create intent with Recipe Lists");

                Intent intent = new Intent(HomeActivity.this, RecipeList.class);
                startActivity(intent);
            }
        });
        /**
         * Finds the Shopping Lists button view by id
         * sets an onclicklistener that starts the ShopList activity with intent.
         * */
        //Shopping Lists button
        Button btnShopping = findViewById(R.id.btnShopping);
        btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Opening ShoppingLists activity");
                Log.d(TAG, "About to create intent with Shopping Lists");

                Intent intent = new Intent(HomeActivity.this, ShopList.class);
                startActivity(intent);
            }
        });
    }

}