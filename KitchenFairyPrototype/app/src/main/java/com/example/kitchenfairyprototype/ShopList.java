package com.example.kitchenfairyprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShopList extends AppCompatActivity {

    ListView listview;
    ArrayList<ItemModel> lists;
    ArrayAdapter<String> adapter;
    UserReference user; //ideally this is where access to current lists would come from
    ArrayList<String> listsNames;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        //target the listview layout in ShopList activity
        listview = findViewById(R.id.lvShopList);
        fab = findViewById(R.id.faCreateShop);
        user = UserReference.getInstance();

        //instantiate a list of items
//        ArrayList<String> testItems = new ArrayList<>();
        //add items to list

        //code between these comments is debug only
//        testItems.add("mountain dew");
//        testItems.add("Doritos");
//        testItems.add("Mochi");
        lists = new ArrayList<>();
//        Shopping test = new Shopping(1,"test list", testItems);
//        lists.add(test);
        listsNames = new ArrayList<>();

        for (ItemModel s : user.shoppingLists){
            lists.add(s);
        }

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
                Log.d("ShopList", "" + name);
                //bundle the object. Helps protect it from becoming null when passed
                Bundle bundle = new Bundle();
                bundle.putSerializable("ItemModel", item);


                //create intent to open viewer activity for the list. Pass item to intent using extra for serializable items
                Intent intent = new Intent(getApplicationContext(), ItemViewer.class);
                intent.putExtra("ItemModel_bundle", bundle);
                startActivity(intent);
            }
        });

        //onclicklistener for the floating action button : creates a recipe
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call RecipeEditor to prepare to edit and save recipe
                Intent intent = new Intent(getApplicationContext(), ShopEditor.class);
                startActivity(intent);
            }
        });

        ImageView homeBtn = findViewById(R.id.homeBtnShop);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call RecipeEditor to prepare to edit and save recipe
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //instantiate and set the adapter
        adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, listsNames);
        listview.setAdapter(adapter);
    }

}