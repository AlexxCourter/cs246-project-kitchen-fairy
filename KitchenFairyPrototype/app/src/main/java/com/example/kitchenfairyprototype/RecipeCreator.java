package com.example.kitchenfairyprototype;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Editor for existing Recipes that displays similar to a shopping list editor.
 * this makes it easy to add/remove items from both ingredients and instructions
 *
 * */
public class RecipeCreator extends AppCompatActivity {

    private static final int PICK_IMAGE = 123192;

    UserReference user = UserReference.getInstance();
    static ListView lvIngredients;
    static ListView lvInstructions;
    static ArrayList<String> items;
    static ArrayList<String> notes;
    static RecipeListViewAdapter adapterA;

    Uri itemUri;
    Bitmap itemBit;
    byte[] byteArray;

    EditText ingInput;

    ImageView ingEnter;
    ImageView saveRecipe;
    EditText editName;
    ImageView nextScreen;
    ImageView itemImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_editor);

        Intent i = getIntent();
        Bundle bundle = i.getParcelableExtra("ItemModel_bundle");
        Recipe model = (Recipe) bundle.getSerializable("ItemModel");

        //get all the views in the editor
        lvIngredients = findViewById(R.id.lvIngredientEditor);
        lvInstructions = findViewById(R.id.lvInstructionEditor);
        ingInput = findViewById(R.id.ingredientInput);
        ingEnter = findViewById(R.id.ingredientAdd);

        nextScreen = findViewById(R.id.nextScreen);

        editName = findViewById(R.id.editItemName);

        itemImg = findViewById(R.id.editItemImg);

        //create list for items(ingredients) and notes(instructions) to be stored
        items = new ArrayList<>();
        notes = new ArrayList<>();

        //prepopulate data
        editName.setText(model.name);

        for (String it : model.items){
            items.add(it);
        }

        for (String n : model.notes){
            notes.add(n);
        }

        if (model.img != null){
            Bitmap visImg = BitByteAdapter.toBitmap(model.img);
            itemImg.setImageBitmap(visImg);
            byteArray = model.img;
        }

        /**
         * Click Listeners for the Ingredient editor listviews.
         * */
        //onitemclick for items of listview items
        lvIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = items.get(position);
                makeToast(name);
            }
        });

        //onitemlongclick removes an item from the list
        lvIngredients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                makeToast("Removed: " + items.get(position));
                removeIngredient(position);
                return false;
            }
        });


        //set the adapter to the listview to display dynamically
        adapterA = new RecipeListViewAdapter(getApplicationContext(), items, true);
        lvIngredients.setAdapter(adapterA);


        //onclick for the button which adds the item to the list
        ingEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ingInput.getText().toString();
                if(text == null || text.length() == 0) {
                    makeToast("Enter an item");
                } else {
                    addIngredient(text);
                    ingInput.setText("");
                    makeToast("Added: " + text);
                }
            }
        });


        itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        //show the next screen with the instruction data entry
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = String.valueOf(editName.getText());
                String uriRef = null;
                if(itemUri != null){
                    uriRef = itemUri.toString();
                } else {
                    uriRef = null;
                }

                if (itemBit != null){
                    byteArray = BitByteAdapter.toByteArray(itemBit);
                } else {
                    byteArray = null;
                }
                //use converted byteArray in the fragment arguments. Then, convert back to bitmap
                RecipeEditorScreenTwo fragment = RecipeEditorScreenTwo.newInstance(model.id, items, notes, name, byteArray);
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainContent);
                layout.setVisibility(View.GONE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.container, fragment);
                transaction.commit();
            }
        });



    } //end onCreate





    /**
     * adds an item to the visible listview
     * updates the adapter so changes are dynamically visible
     *
     * @param item : A String representing the name of the shopping list item
     * */
    public static void addIngredient(String item){
        items.add(item);
        lvIngredients.setAdapter(adapterA);
    }



    /**
     * removes an item from the visible listview
     * updates the adapter to reflect changes dynamically
     *
     * @param position : an integer representing the index position of the
     *                 item to be removed.
     * */
    public static void removeIngredient(int position){
        items.remove(position);
        lvIngredients.setAdapter(adapterA);
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



    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            itemImg.setImageURI(imageUri);
            itemUri = data.getData();
            captureBitmap(itemUri);
        }
    }

    /**
     * captureBitmap is a workaround function for a bug experienced trying to gather persistent uri permissions. Due to
     * changes in activity sessions, it appears that persistent permissions would not apply correctly. Therefore, until
     * a more stable build is created, this function will allow the image uri captured to be converted to a locally stored
     * bitmap which will then serve as the recipe image data. This may pose a high memory cost. Precautions will be taken to
     * minimize this.
     * */
    private void captureBitmap(Uri uri){
        //use the uri to convert to bitmap
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            //set the bitmap to the local recipeImg object
            itemBit = image;
            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ensure that the bitmap object is saved to the recipe on finish of task
    }


}