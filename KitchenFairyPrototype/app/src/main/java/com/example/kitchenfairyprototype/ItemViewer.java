package com.example.kitchenfairyprototype;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class ItemViewer extends AppCompatActivity {

    ArrayAdapter<String> itemAdapter;
    FloatingActionButton itemFab;
    FloatingActionButton fabToList;
    Bitmap bmpLogo, scaledBmpLogo; //for applying the logo to PDFs
    Bitmap recipeImg;
    Uri uri;
    ItemModel model;
    Bitmap ScaledImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get intent and import the ItemModel sent by last activity
        Intent i = getIntent();
        Bundle bundle = i.getParcelableExtra("ItemModel_bundle");
        model = (ItemModel) bundle.getSerializable("ItemModel");



        if (model.notes == null) {
            setContentView(R.layout.activity_items_shop);

            viewShopping((Shopping) model);
        } else {
            setContentView(R.layout.activity_items);
            itemFab = findViewById(R.id.fabItems);
            fabToList = findViewById(R.id.fabToList);

            itemFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createPdf(model);

                }
            });

            fabToList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //package the ingredients to be sent via bundle
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("ingredients", model.items);
                    //start shopping list editor by intent
                    Intent intent = new Intent(getApplicationContext(), ShopEditor.class);
                    intent.putExtra("ingredient_bundle", bundle);
                    startActivity(intent);
                }
            });

            if (model.img != null) {
                Bitmap visImg = BitByteAdapter.toBitmap(model.img);
                recipeImg = visImg;
            }

            ImageView img;
            img = findViewById(R.id.itemImg);

            if (recipeImg != null){
                ScaledImg = Bitmap.createScaledBitmap(recipeImg, 90,90,true);
                img.setImageBitmap(ScaledImg);
                System.out.println("scaledimg data:" + ScaledImg.toString());
                System.out.println("recipeimg data:" + recipeImg.toString());
            }
            viewRecipe((Recipe) model);
        }


        bmpLogo = BitmapFactory.decodeResource(getResources(), R.drawable.kitchen_fairy_circle);
        scaledBmpLogo = Bitmap.createScaledBitmap(bmpLogo, 120, 120, false);

        ActivityCompat.requestPermissions(this,new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        //code to view items





    } //end oncreate


    //view shopping lists
    public void viewShopping(Shopping model) {
        //find each view to populate. excludes img and notes
        ListView items;
        items = findViewById(R.id.items);
        //set the adapter to fill items
        itemAdapter = new ListViewAdapterCheck(getApplicationContext(), model.items);
        items.setAdapter(itemAdapter);
    }

    public void viewRecipe(Recipe model) {
        //find each view to populate.
        ListView items;
        items = findViewById(R.id.items);
        TextView name;
        name = findViewById(R.id.itemName);
        ImageView img;
        img = findViewById(R.id.itemImg);
        ListView notes;
        notes = findViewById(R.id.notes);

        name.setText(model.name);
        itemAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, model.items);
        items.setAdapter(itemAdapter);
        itemAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, model.notes);
        notes.setAdapter(itemAdapter);
        //set the visible image from the model bitmap.
    }

    private void createPdf(ItemModel recipe) {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        String pdfName = recipe.name + ".pdf";
        File file = new File(pdfPath, pdfName); //replace placeholder with generated string from recipe name

        String name = recipe.name;
        ArrayList<String> ingredients = recipe.items;
        ArrayList<String> instructions = recipe.notes;

        PdfDocument pdfDocument = new PdfDocument();
        Paint pdfPaint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(400,600,1).create();
        PdfDocument.Page page1 = pdfDocument.startPage(pageInfo);

        Canvas canvas = page1.getCanvas();

        int pw = page1.getInfo().getPageWidth();
        int ph = page1.getInfo().getPageHeight();

        pdfPaint.setColor(Color.rgb(219,169,112));
        canvas.drawRect(0,0,pw,35,pdfPaint);
        canvas.drawRect(0,ph - 35,pw,ph,pdfPaint);

        pdfPaint.setColor(Color.rgb(0,0,0));

        pdfPaint.setTextSize(15.0f);
        canvas.drawText(name, 30,70, pdfPaint);
        //set starting Y for ingredients
        pdfPaint.setTextSize(11.0f);
        int Y = 90;
        //print ingredients
        for(String ing : ingredients){
            canvas.drawText(ing, 30, Y, pdfPaint);
            Y += 12;
        }
        //add space
        Y += 20;
        int listNum = 1;
        //print instructions
        for(String ins : instructions){
            String item = listNum + ". " + ins;
            canvas.drawText(item, 30, Y, pdfPaint);
            Y += 14;
            listNum += 1;
        }

        //draw logo
        canvas.drawBitmap(scaledBmpLogo, pw - 130, 10, pdfPaint);


        //Finish page 1
        pdfDocument.finishPage(page1);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e){
            e.printStackTrace();
        }

        pdfDocument.close();
    }

}
