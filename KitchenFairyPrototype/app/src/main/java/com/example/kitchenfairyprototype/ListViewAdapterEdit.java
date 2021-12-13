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

    public ListViewAdapterEdit(Context context, ArrayList<ItemModel> items, boolean isRecipe) {
        super(context, R.layout.list_row_edit, items);
        this.context = context;
        list = items;
        this.isRecipe = isRecipe;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutinflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutinflater.inflate(R.layout.list_row_edit, null);

            DataController dc = new DataController();
            UserReference ur = UserReference.getInstance();

            TextView name = convertView.findViewById(R.id.itemName);
            ItemModel item = list.get(position);
            name.setText(item.name);

            ImageView remove = convertView.findViewById(R.id.removeBtnEdit);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //initiate sequence for deletion of item
                    if(isRecipe){
                        dc.deleteRecipeById(position);
                    } else {
                        dc.deleteShoppingById(position);
                    }

                }
            });

        }
        return convertView;
    }
}
