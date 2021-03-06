package com.example.kitchenfairyprototype;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListViewAdapterCheck extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;

    /**
     * Constructor for ListViewAdapterCheck
     * extended from ArrayAdapter, so calls super() to execute parent constructor requirements
     * saves the context, and the list of items
     *
     * @param context : the context in which the adapter is being used
     * @param items : an arraylist of strings representing the items to be displayed in the listview
     * */
    public ListViewAdapterCheck(Context context, ArrayList<String> items) {
        super(context, R.layout.list_row_check, items);
        this.context = context;
        list = items;
    }

    /**
     * inflates the layout list_row and populates it with available data. Automatically increments a number next
     * to item names to count the number of items. Sets a checkbox at the right of the Item that can be checked.
     * Changes color of the item when checked.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutinflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutinflater.inflate(R.layout.list_row_check, null);

            TextView number = convertView.findViewById(R.id.itemNumber);
            number.setText(position + 1 + ".");

            TextView name = convertView.findViewById(R.id.itemName);
            name.setText(list.get(position));

            CheckBox check = convertView.findViewById(R.id.removeBtnEdit);

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    number.setTextColor(Color.LTGRAY);
                    name.setTextColor(Color.LTGRAY);
                }
            });

        }
        return convertView;
    }
}
