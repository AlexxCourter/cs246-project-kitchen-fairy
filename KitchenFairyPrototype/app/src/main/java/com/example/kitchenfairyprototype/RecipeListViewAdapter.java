package com.example.kitchenfairyprototype;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecipeListViewAdapter extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;
    boolean isIngredients;

    /**
     * Constructor for RecipeListViewAdapter
     * extended from ArrayAdapter, so calls super() to execute parent constructor requirements
     * saves the context, and the list of items.
     *
     * @param context : the context in which the adapter is being used
     * @param items : an arraylist of strings representing the items to be displayed in the listview
     * @param isIngredients : a boolean value that tells the adapter the array items are ingredients. Allows differentiation
     *                      in order to delete or update the proper data.
     * */
    public RecipeListViewAdapter(Context context, ArrayList<String> items, boolean isIngredients) {
        super(context, R.layout.recipe_list_row, items);
        this.context = context;
        list = items;
        this.isIngredients = isIngredients;
    }

    /**
     * inflates the layout list_row and populates it with available data. Adds a duplicate or copy button and a remove button
     * for the items displayed. Onclick functions for both are created. Copy duplicates the selected item at the end of the list,
     * remove removes the item from the list.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutinflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutinflater.inflate(R.layout.recipe_list_row, null);

            TextView number = convertView.findViewById(R.id.itemNumber);
            number.setText(position + 1 + ".");

            TextView name = convertView.findViewById(R.id.itemName);
            name.setText(list.get(position));

            ImageView copy = convertView.findViewById(R.id.copy);
            ImageView remove = convertView.findViewById(R.id.remove);

            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isIngredients){
                        RecipeEditor.addIngredient(list.get(position));
                    } else {
                        RecipeEditorScreenTwo.addInstruction(list.get(position));
                    }

                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isIngredients){
                        RecipeEditor.removeIngredient(position);
                    } else {
                        RecipeEditorScreenTwo.removeInstruction(position);
                    }
                }
            });
        }
        return convertView;
    }
}
