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

public class ListViewAdapter extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;

    /**
     * Constructor for ListViewAdapter
     * extended from ArrayAdapter, so calls super() to execute parent constructor requirements
     * saves the context, and the list of items
     *
     * @param context : the context in which the adapter is being used
     * @param items : an arraylist of strings representing the items to be displayed in the listview
     * */
    public ListViewAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.list_row, items);
        this.context = context;
        list = items;
    }


    /**
     * inflates the layout list_row and populates it with available data.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutinflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutinflater.inflate(R.layout.list_row, null);

            TextView number = convertView.findViewById(R.id.itemNumber);
            number.setText(position + 1 + ".");

            TextView name = convertView.findViewById(R.id.itemName);
            name.setText(list.get(position));

            ImageView copy = convertView.findViewById(R.id.copy);
            ImageView remove = convertView.findViewById(R.id.remove);

            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopEditor.addItem(list.get(position));
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopEditor.removeItem(position);
                }
            });
        }
        return convertView;
    }
}
