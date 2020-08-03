package com.reuben.company.houserenting.R_Class;

import android.app.slice.SliceItem;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.reuben.company.houserenting.R;

import java.io.InputStream;
import java.util.List;

public class Image_Slide extends PagerAdapter {
    private List<String> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public Image_Slide(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.selected_image,container,false);
        ImageView imageView = view.findViewById(R.id.slideimg);
//        imageView.setImageBitmap(list.get(position));
        new Images(imageView).execute(list.get(position));
//        imageView.setImageDrawable(context.getDrawable(list.get(position)));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    public class getimage extends AsyncTask<String,Void, Bitmap> {
           Bitmap bitmap;
        ImageView imageView;
        public getimage(ImageView imageView) {
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
}
