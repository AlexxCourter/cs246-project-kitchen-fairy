package com.example.kitchenfairyprototype;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListViewAdapterEdit extends ArrayAdapter<ItemModel> {
    ArrayList<ItemModel> list;
    Context context;
    boolean isRecipe;

    /**
     * Constructor for ListViewAdapterEdit
     * extended from ArrayAdapter, so calls super() to execute parent constructor requirements
     * saves the context, the list of items, and a boolean value that is true in the case of Recipe objects.
     *
     * @param context : the context in which the adapter is being used
     * @param items : an arraylist of strings representing the items to be displayed in the listview
     * @param isRecipe : a boolean representing if the ItemModel present is a Recipe or Shopping object. True for recipes
     * */
    public ListViewAdapterEdit(Context context, ArrayList<ItemModel> items, boolean isRecipe) {
        super(context, R.layout.list_row_edit, items);
        this.context = context;
        list = items;
        this.isRecipe = isRecipe;
    }

    /**
     * inflates the layout list_row and populates it with available data. Sets the name and a button for removal of the item.
     * Includes an onClickListener that when a remove button is tapped, calls the deleteRecipeById function that removes
     * the recipe at the specified position.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutinflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutinflater.inflate(R.layout.list_row_edit, null);

            DataController dc = new DataController();

            TextView name = convertView.findViewById(R.id.itemName);
            ItemModel item = list.get(position);
            name.setText(item.name);

            ImageView remove = convertView.findViewById(R.id.removeBtnEdit);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //initiate sequence for deletion of item
                    ItemModel deletion = list.get(position);
                    if(isRecipe){
                        dc.deleteRecipeById(deletion.id);
                        RecipeList.removeItem(position);

                    } else {
                        dc.deleteShoppingById(deletion.id);
                        ShopList.removeItem(position);

                    }
                    dc.saveReference(getContext());
                }
            });

        }
        return convertView;
    }
}
