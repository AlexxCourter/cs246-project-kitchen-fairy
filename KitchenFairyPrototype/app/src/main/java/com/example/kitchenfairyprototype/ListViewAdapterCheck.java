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

    public ListViewAdapterCheck(Context context, ArrayList<String> items) {
        super(context, R.layout.list_row_check, items);
        this.context = context;
        list = items;
    }

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
