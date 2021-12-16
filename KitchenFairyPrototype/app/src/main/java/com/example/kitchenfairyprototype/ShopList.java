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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShopList extends AppCompatActivity {

    static ListView listview;
    static ArrayList<ItemModel> lists;
    static ArrayAdapter<String> adapter;
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

        lists = new ArrayList<>();
        listsNames = new ArrayList<>();

        for (ItemModel s : user.shoppingLists){
            lists.add(s);
        }

        for (ItemModel s : lists) {
            listsNames.add(s.toString());
        }

        //set an onItemClickListener that catches clicks on the shopping lists.
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

        //onClickListener for the floating action button : creates a recipe
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

        /*
          Edit button allows user to delete recipes. Opens a fragment with the necessary views and functions to fill
          this task.
          */
        TextView editBtn = findViewById(R.id.editBtnShop);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show the new listview with remove buttons by inflating fragment
                EditScreen fragment = EditScreen.newInstance(false);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.shopListContainer, fragment);
                //hide the old edit button and listview until closed
                editBtn.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);

                transaction.commit();

                //ask the user if they are sure they wish to delete the items
                //if yes, the item is removed from the list by DataController
            }
        });
    }


    /**
     * removes an item from the visible listview
     * updates the adapter to reflect changes dynamically
     *
     * @param position : an integer representing the index position of the
     *                 item to be removed.
     * */
    public static void removeItem(int position){
        lists.remove(position);
        listview.setAdapter(adapter);
    }

}