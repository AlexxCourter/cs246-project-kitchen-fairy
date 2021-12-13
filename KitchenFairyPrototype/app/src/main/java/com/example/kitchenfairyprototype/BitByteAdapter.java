package com.example.kitchenfairyprototype;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BitByteAdapter {

    /**
     * Converts a bitmap to a byte array. Used to save the bitmap to a recipe object.
     * Since recipes must be serializable, it is required to convert the bitmap to a byte array
     * which can be serialized. Bitmaps cannot be serialized.
     *
     * @param bit : a Bitmap to be converted to a byte array. The byte array can then be saved
     *            to the recipe object in the calling method.
     *
     * @return byteArray : a byte[] that can be saved to serializable recipes.
     * */
    public static byte[] toByteArray(Bitmap bit){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG,80,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    /**
     * Converts a byte array to a Bitmap. Used when the byte array contained in a recipe
     * needs to be converted to a viewable bitmap.
     *
     * @param data : a byte[] to be converted to bitmap.
     *
     * @return Bitmap converted by decoding the ByteArray (data) provided.
     */
    public static Bitmap toBitmap(byte[] data){
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inSampleSize = 2;
        return BitmapFactory.decodeByteArray(data, 0, data.length, op);
    }
}
