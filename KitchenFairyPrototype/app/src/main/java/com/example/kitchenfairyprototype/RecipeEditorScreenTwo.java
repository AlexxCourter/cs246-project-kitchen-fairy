package com.example.kitchenfairyprototype;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeEditorScreenTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeEditorScreenTwo extends Fragment {

    UserReference user = UserReference.getInstance();
    //The keys used in the bundle to put arguments
    private static final String ARG_INGR_KEY = "ingredient";
    private static final String ARG_NAME_KEY = "name";
    private static final String ARG_IMG_KEY = "img";
    private static final String ARG_NOTE_KEY = "notes";
    private static final String ARG_ID_KEY = "id";

    public static ArrayList<String> getArgIngredient() {
        return ARG_INGREDIENT;
    }

    public static String getArgName() {
        return ARG_NAME;
    }

    public static byte[] getArgImg() {
        return ARG_IMG;
    }

    //the data objects that collect the arguments
    private static ArrayList<String> ARG_INGREDIENT;
    private static String ARG_NAME;
    private static byte[] ARG_IMG;
    private static ArrayList<String> ARG_NOTE;
    private static int ARG_ID;

    //Bitmap converted from the byte array ARG_IMG after arguments are gathered.
    Bitmap recipeImg;
    byte[] recipeImgByte;

    EditText instructInput;
    ImageView saveRecipe;
    ImageView instructionAdd;

    public static ArrayList<String> getArgNote() {
        return ARG_NOTE;
    }

    static RecipeListViewAdapter adapterB;
    static ListView lvInstructions;
    static ArrayList<String> notes = new ArrayList<>();

    public RecipeEditorScreenTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeEditorScreenTwo.
     */
    public static RecipeEditorScreenTwo newInstance(int ARG_ID, ArrayList<String> ARG_INGR, ArrayList<String> ARG_NOTE, String ARG_NAME, byte[] ARG_IMG) {
        RecipeEditorScreenTwo fragment = new RecipeEditorScreenTwo();
        Bundle args = new Bundle();

        args.putInt(ARG_ID_KEY, ARG_ID);
        args.putStringArrayList(ARG_NOTE_KEY, ARG_NOTE);
        args.putStringArrayList(ARG_INGR_KEY, ARG_INGR);
        args.putString(ARG_NAME_KEY, ARG_NAME);
        args.putByteArray(ARG_IMG_KEY, ARG_IMG);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ARG_INGREDIENT = getArguments().getStringArrayList(ARG_INGR_KEY);
            ARG_NAME = getArguments().getString(ARG_NAME_KEY);
            ARG_IMG = getArguments().getByteArray(ARG_IMG_KEY);
            ARG_NOTE = getArguments().getStringArrayList(ARG_NOTE_KEY);
            ARG_ID = getArguments().getInt(ARG_ID_KEY);
        }

        adapterB = new RecipeListViewAdapter(getContext(), getArgNote(), false);
        if(getArgImg() != null){
            recipeImg = BitByteAdapter.toBitmap(getArgImg());
            recipeImgByte = BitByteAdapter.toByteArray(recipeImg);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_editor_screen_two, container, false);

        saveRecipe = (ImageView) view.findViewById(R.id.saveRecipe);
        instructionAdd = (ImageView) view.findViewById(R.id.instructionAdd);
        instructInput = (EditText) view.findViewById(R.id.instructionInput);
        lvInstructions = (ListView) view.findViewById(R.id.lvInstructionEditor);

        lvInstructions.setAdapter(adapterB);

        //onclick for the button which adds the item to the list
        instructionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = instructInput.getText().toString();
                if(text == null || text.length() == 0) {
                    makeToast("Enter an item");
                } else {
                    addInstruction(text);
                    instructInput.setText("");
                    makeToast("Added: " + text);
                }
            }
        });

        /*
          Click Listeners for the Instruction editor listviews.
          */
        //onItemClickListener for items of listview items
        lvInstructions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ARG_NOTE.get(position);
                makeToast(name);
            }
        });

        //onItemLongClickListener removes an item from the list
        lvInstructions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                makeToast("Removed: " + ARG_NOTE.get(position));
                removeInstruction(position);
                return false;
            }
        });

        //onclick for the saveRecipe button
        saveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataController dc = new DataController();
                if (dc.findRecipeById(ARG_ID)){
                    Recipe recipe = updateRecipeObject(ARG_ID);
                    dc.updateRecipeById(recipe);
                } else {
                    Recipe recipe = makeNewRecipeObject();
                    user.recipes.add(recipe);
                    UserReference.updateReferenceRecipe(user);
                }
                //call the methods that add a Recipe() to the current UserReference and saves
                dc.saveReference(getContext());
                //display a toast "Recipe saved"
                makeToast("Recipe saved");
                //use an intent to go back to the RecipeList activity
                Intent intent = new Intent(getContext(), RecipeList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });




        return view;

    }

    public static void addInstruction(String item){
        ARG_NOTE.add(item);
        lvInstructions.setAdapter(adapterB);
    }

    public static void removeInstruction(int position){
        ARG_NOTE.remove(position);
        lvInstructions.setAdapter(adapterB);
    }

    /**
     * updateRecipeObject creates a recipe using the data contained in the editor and returns it to calling function
     * the calling function uses this function to create a new recipe object contained in the UserReference.
     * gets the id to be used by finding the current recipeIdHolder in the UserReference.
     *
     * @return recipe object
     * */
    public Recipe makeNewRecipeObject(){
        //prepare the data to be saved in a new Recipe() object.
        int recipeID = user.getRecipeIDHolder();
        Recipe recipe = new Recipe(recipeID, getArgName(), getArgIngredient(), getArgNote(), recipeImgByte);
        return recipe;
    }

    /**
     * updateRecipeObject creates a recipe using the data contained in the editor and returns it to calling function
     * the calling function uses this function to update a recipe object contained in the UserReference.
     *
     * @param id : an integer representing the unique id of the recipe.
     *
     * @return recipe object
     * */
    public Recipe updateRecipeObject(int id){
        Recipe recipe = new Recipe(id, getArgName(), getArgIngredient(), getArgNote(), recipeImgByte);
        return recipe;
    }


    Toast t;

    /**
     * Helper function that makes and displays a toast
     *
     * @param s : String representing the message displayed on the toast
     * */
    private void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
        t.show();
    }

}