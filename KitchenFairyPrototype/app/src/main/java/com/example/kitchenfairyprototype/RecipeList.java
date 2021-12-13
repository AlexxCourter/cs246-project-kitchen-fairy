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
    UserReference user;
    ArrayList<String> listsNames;
    FloatingActionButton fab;


    /**
     * onCreate sets the layout to the recipe list layout.
     * holds a reference to the UserReference object
     * gets the views in the recipe list layout and populates them with available data.
     * uses an ArrayAdapter to display the recipes contained in the UserReference in a listview.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        user = UserReference.getInstance();

        //target the listview layout in ShopList activity
        listview = findViewById(R.id.lvRecipeList);
        //target the floating action button
        fab = findViewById(R.id.faCreateRecipe);

        //add items to lists
        lists = new ArrayList<ItemModel>();

        for (ItemModel r : user.recipes){
            lists.add(r);
        }

        listsNames = new ArrayList<>();
        for (ItemModel s : lists) {
            listsNames.add(s.toString());
        }
        //the code between these comments is debug only. Remove at next phase

        /**
         * this onItemClickListener takes the recipe selected by user and opens it in the ItemViewer activity.
         * */
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                String name = listsNames.get(position);
                ItemModel item = lists.get(position); //access item and send to viewing activity
                //bundle the object. Helps protect it from becoming null when passed
                Bundle bundle = new Bundle();
                bundle.putSerializable("ItemModel", item);

                //create intent to open viewer activity for the list. Pass item to intent using extra for serializable items
                Intent intent = new Intent(getApplicationContext(), ItemViewer.class);
                intent.putExtra("ItemModel_bundle", bundle);
                startActivity(intent);
            }
        });

        /**
         * this onItemLongClickListener takes the recipe selected by user and opens it in the RecipeEditor activity.
         * */
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

        /**
         * this onClickListener for the floating action button is for creating a new recipe.
         * opens the RecipeEditor to create a new recipe.
         * */
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


        /**
         * Edit button allows user to delete recipes. Opens a fragment with the necessary views and functions to fill
         * this task.
         * */
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