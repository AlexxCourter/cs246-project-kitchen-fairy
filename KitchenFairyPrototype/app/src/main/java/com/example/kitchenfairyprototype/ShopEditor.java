package com.example.kitchenfairyprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;


/** ShopEditor activity edits and saves a shopping list
 * called by ShopList create new list button
 *
 * */
public class ShopEditor extends AppCompatActivity {

    UserReference user = UserReference.getInstance();
    static ListView listview;
    static ArrayList<String> items;
    static ListViewAdapter adapter;

    EditText input;
    ImageView enter;
    ImageView saveList;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_editor);

        listview = findViewById(R.id.lvListEditor);
        input = findViewById(R.id.input);
        enter = findViewById(R.id.add);
        saveList = findViewById(R.id.saveList);

        //create list for items to be stored
        items = new ArrayList<>();

        //checks for items sent to the editor
        if (getIntent() != null){
            Intent i = getIntent();
            bundle = i.getParcelableExtra("ingredient_bundle");
        }

        //adds sent items to the shopping list if available
        if (bundle != null){
            ArrayList<String> ingredients = bundle.getStringArrayList("ingredients");
            for (String ing : ingredients){
                items.add(ing);
            }
        }

        //onItemClickListener for items of listview items
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = items.get(position);
                makeToast(name);
            }
        });

        //onItemLongClickListener removes an item from the list
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                makeToast("Removed: " + items.get(position));
                removeItem(position);
                return false;
            }
        });

        //set the adapter to the listview to display dynamically
        adapter = new ListViewAdapter(getApplicationContext(), items);
        listview.setAdapter(adapter);

        //onclick for the button which adds the item to the list
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if(text == null || text.length() == 0) {
                    makeToast("Enter an item");
                } else {
                    addItem(text);
                    input.setText("");
                    makeToast("Added: " + text);
                }
            }
        });

        //onclick for the saveList button
        saveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the function that converts the data here to a Shopping() object
                Shopping shopping = makeShoppingObject();
                //call the methods that add a Shopping() to the current UserReference and saves
                user.shoppingLists.add(shopping);
                UserReference.updateReferenceShopping(user);
                DataController dc = new DataController();
                dc.saveReference(getApplicationContext());
                //display a toast "Shopping list saved"
                makeToast("Shopping list saved");
                //use an intent to go back to the ShopList activity
                Intent intent = new Intent(ShopEditor.this, ShopList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    } //end onCreate

    /**
     * creates a new shopping object. Sets the name to the current date.
     * returns a shopping object if successful. returns null if failed.
     * */
    public Shopping makeShoppingObject(){
        //prepare the data to be saved in a new Shopping() object.
        //use the date as the name of the list
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate time = LocalDate.now();
            int shoppingID = user.getShoppingIDHolder();
            Shopping shopping = new Shopping(shoppingID, String.valueOf(time), items);
            return shopping;
        }
        return null;
    }



    /**
     * adds an item to the visible listview
     * updates the adapter so changes are dynamically visible
     *
     * @param item : A String representing the name of the shopping list item
     * */
    public static void addItem(String item){
        items.add(item);
        listview.setAdapter(adapter);
    }

    /**
     * removes an item from the visible listview
     * updates the adapter to reflect changes dynamically
     *
     * @param position : an integer representing the index position of the
     *                 item to be removed.
     * */
    public static void removeItem(int position){
        items.remove(position);
        listview.setAdapter(adapter);
    }

    Toast t;

    /**
     * Helper function that makes and displays a toast
     *
     * @param s : String representing the message displayed on the toast
     * */
    private void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.show();
    }
}