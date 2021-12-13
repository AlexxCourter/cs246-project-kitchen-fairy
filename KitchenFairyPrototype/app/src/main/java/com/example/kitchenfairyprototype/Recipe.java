package com.example.kitchenfairyprototype;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe extends ItemModel implements Serializable {
    //ItemModel specifically for recipes

    public Recipe(int id, String name, ArrayList<String> items, ArrayList<String> instructions, byte[] img) {
        super(id, name, items, instructions, img);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
