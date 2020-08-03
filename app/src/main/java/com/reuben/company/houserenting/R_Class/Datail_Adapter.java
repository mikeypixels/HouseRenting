package com.reuben.company.houserenting.R_Class;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.reuben.company.houserenting.MyOrders;
import com.reuben.company.houserenting.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Datail_Adapter extends ArrayAdapter{

        String[] phone;
        int[] id;
        Activity activity;
        Bitmap bitmap;

    public Datail_Adapter  (Activity activity,String[] phone,int[] id) {
        super(activity , R.layout.listof_house,phone);
        this.phone = phone;
        this.id = id;
        this.activity = activity;
    }
//        public Datail_Adapter  (Activity activity,String [] image_Url,String[] room,String[] kitchen,String[] descripion,String[] locat,String[] price,String[] phone,String[] parking) {
//            super(activity , R.layout.listof_house,room);
//            this.parking = parking;
//            this.room = room;
//            this.kitchen = kitchen;
//            this.descripion = descripion;
//            this.price = price;
//            this.phone = phone;
//            this.locat = locat;
//            this.image_url = image_Url;
//            this.activity = activity;
//        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            Viewholder viewholder = null;
            if (v == null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                v = inflater.inflate(R.layout.listof_house, parent, false);
                viewholder = new Viewholder(v);
                v.setTag(viewholder);
            }
            else {
                viewholder = (Viewholder)v.getTag();
            }
            get_H_det(viewholder,position,Renting_URL.Get_house_det_URL);

            final Viewholder finalViewholder = viewholder;
            viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity,MyOrders.class);
                    intent.putExtra("id",id[position]);
                    intent.putExtra("price",finalViewholder.price.getText().toString());
                    intent.putExtra("room",finalViewholder.room.getText().toString());
                    intent.putExtra("kitchen",finalViewholder.location.getText().toString());
                    intent.putExtra("location",finalViewholder.parking.getText().toString());
                    activity.startActivity(intent);
                }
            });

            return v;
        }


        class Viewholder{
        TextView room,price,kitche,location,parking,kitchen;
        ImageView imageView;

            Viewholder(View v){
                imageView = v.findViewById(R.id.rImgV);
                room = v.findViewById(R.id.rum);
                location = v.findViewById(R.id.rTv);
                kitchen = v.findViewById(R.id.kicheni);
                price = v.findViewById(R.id.price);
                parking = v.findViewById(R.id.parking);


            }
        }

        public class getimage extends AsyncTask<String,Void, Bitmap> {

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


    public void get_Image(final Viewholder viewholder,final String pho_no[], final int[] id,final String[] locat,final String[] room,
                          final String[] parking,final String[] price,final String[] kitchen,final String[] descripion,
                          final int position, String URL) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(activity, response, Toast.LENGTH_LONG).show();
                        String details = "";
                        String[] image;
                        try {
                            JSONArray array = new JSONArray(response);
                            image = new String[array.length()];
                            JSONObject object = null;
                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                image[i] = "http://" +Renting_URL.ip+ "/House_Ranting/uploads/Image/" + object.getString("image")+".png";

                            }

                            viewholder.price.setText(price[position]);
                            viewholder.parking.setText(parking[position]);
//                            viewholder.kitche.setText(kitchen[position]);
                            viewholder.location.setText(locat[position]);
                            viewholder.room.setText(room[position]);
                            viewholder.price.setText(price[position]);
                            new getimage(viewholder.imageView).execute(image[position]);
                            Toast.makeText(activity, image[position], Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
//                            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(activity, "Aploaded error on responce on getprod" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",""+id[position]+"");
//                params.put("id",""+17+"")/
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    public void get_H_det(final Viewholder viewholder, final int position, String URL) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(activity, response, Toast.LENGTH_LONG).show();
                        int[] h_id;
                        String[] loc,roo,park,pric,kitch,dec,phon;
                        String name;
                        try {
                            JSONArray array = new JSONArray(response);
                            h_id = new int[array.length()];
                            loc = new String[array.length()];
                            roo = new String[array.length()];
//                            pas = new String[array.length()];
                            dec = new String[array.length()];
                            kitch = new String[array.length()];
                            pric = new String[array.length()];
                            park = new String[array.length()];
                            phon = new String[array.length()];

                            JSONObject object = null;
                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                h_id[i] = object.getInt("id");
                                roo[i] = object.getString("room");
                                loc[i] = object.getString("location");
                                pric[i] = object.getString("price");
                                park[i] = object.getString("parking");
                                kitch[i] = object.getString("kitchen");
                                dec[i] = object.getString("descriptions");
                                phon[i] = object.getString("phone");

                            }
                            get_Image(viewholder,phon,h_id,loc,roo,park,pric,kitch,dec,position,Renting_URL.Get_image_URL);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Aploaded error on responce on getprod" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }
    }

