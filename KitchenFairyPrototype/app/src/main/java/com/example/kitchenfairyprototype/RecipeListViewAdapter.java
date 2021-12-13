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

    public RecipeListViewAdapter(Context context, ArrayList<String> items, boolean isIngredients) {
        super(context, R.layout.recipe_list_row, items);
        this.context = context;
        list = items;
        this.isIngredients = isIngredients;
    }

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
