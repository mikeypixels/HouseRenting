package com.reuben.company.houserenting.R_Class;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.InputStream;

public class Images extends AsyncTask<String,Void, Bitmap> {
    Bitmap bitmap;
    ImageView imageView;

    public Images(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String geturl = url[0];
        bitmap = null;
        try {
            InputStream stream = new java.net.URL(geturl).openStream();
            bitmap = BitmapFactory.decodeStream(stream);
        }catch (Exception e){
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
    }
}
