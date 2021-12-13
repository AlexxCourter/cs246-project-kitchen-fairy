package com.example.kitchenfairyprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecipeList extends AppCompatActivity {

    ListView listview;
    ArrayList<ItemModel> lists;
    ArrayAdapter<String> adapter;
    UserReference user; //ideally this is where access to current lists would come from
    ArrayList<String> listsNames;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        user = UserReference.getInstance();

        //target the listview layout in ShopList activity
        listview = findViewById(R.id.lvRecipeList);
        //target the floating action button
        fab = findViewById(R.id.faCreateRecipe);

        //instantiate a list of ingredients
        ArrayList<String> testIngredients = new ArrayList<>();
        //and a list of instructions
        ArrayList<String> testInstructions = new ArrayList<>();
        //add items to lists

        //code between these comments is debug only
//        testIngredients.add("2 cups Almond Milk");
//        testIngredients.add("2 Tbsp Peanut Butter");
//        testIngredients.add("1 tsp Cinnamon");
//        testIngredients.add("1 Banana");
//        testInstructions.add("Combine ingredients in blender.");
//        testInstructions.add("blend until smooth.");
//        testInstructions.add("Serve topped with whipped cream and nutmeg.");
        lists = new ArrayList<ItemModel>();
//        Recipe test = new Recipe(1,"Peanut Butter Banana Shake", testIngredients, testInstructions, null);
//        lists.add(test);

        for (ItemModel r : user.recipes){
            lists.add(r);
        }

        listsNames = new ArrayList<>();
        for (ItemModel s : lists) {
            listsNames.add(s.toString());
        }
        //the code between these comments is debug only. Remove at next phase

        //set an onitemclicklistener that catches clicks on the shopping lists.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                String name = listsNames.get(position);
                ItemModel item = lists.get(position); //access item and send to viewing activity
                Log.d("RecipeList", "" + name);
                //bundle the object. Helps protect it from becoming null when passed
                Bundle bundle = new Bundle();
                bundle.putSerializable("ItemModel", item);


                //create intent to open viewer activity for the list. Pass item to intent using extra for serializable items
                Intent intent = new Intent(getApplicationContext(), ItemViewer.class);
                intent.putExtra("ItemModel_bundle", bundle);
                startActivity(intent);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItemModel item = lists.get(position);
                System.out.println(item.toString());

                Bundle bundle = new Bundle();
                bundle.putSerializable("ItemModel", item);

                Intent intent = new Intent(getApplicationContext(), RecipeEditor.class);
                intent.putExtra("ItemModel_bundle", bundle);
                startActivity(intent);
                return false;
            }
        });

        //onclicklistener for the floating action button : creates a recipe
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call RecipeEditor to prepare to edit and save recipe
                String MESSAGE = "new recipe";
                Intent intent = new Intent(getApplicationContext(), RecipeEditor.class);
                intent.putExtra(MESSAGE, true);
                startActivity(intent);
            }
        });

        ImageView homeBtn = findViewById(R.id.homeBtnRecipe);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call RecipeEditor to prepare to edit and save recipe
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //instantiate and set the array adapter
        adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, listsNames);
        listview.setAdapter(adapter);


        TextView editBtn = findViewById(R.id.editBtnRecipe);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show the new listview with remove buttons by inflating fragment
                EditScreen fragment = EditScreen.newInstance(true);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.editScreenContainer, fragment);
                //hide the old edit button and listview until closed
                editBtn.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);

                transaction.commit();

                //ask the user if they are sure they wish to delete the items
                //if yes, the item is removed from the list by DataController
            }
        });
    }
}