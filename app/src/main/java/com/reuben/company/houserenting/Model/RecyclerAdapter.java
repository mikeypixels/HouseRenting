package com.reuben.company.houserenting.Model;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.core.Context;
import com.reuben.company.houserenting.Home_page;
import com.reuben.company.houserenting.MyOrders;
import com.reuben.company.houserenting.R;
import com.reuben.company.houserenting.R_Class.Datail_Adapter;
import com.reuben.company.houserenting.R_Class.Renting_URL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHollder> {
    private static final String Tag = "RecyclerView";
    public Activity mContext;
    private ArrayList<RecyclerViewModel> messagesList;
    Bitmap bitmap;
    public static LatLng latLng;
    public static String phoneNumber;
    public static String kitchen;
    public static String room;
    public static String location;
    public static String price;
    public static String parking;

    public RecyclerAdapter(Activity mContext, ArrayList<RecyclerViewModel> messagesList) {
        this.mContext = mContext;
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHollder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new ViewHollder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHollder holder, final int position) {
        //TextViews
        holder.textView.setText(messagesList.get(position).getDescriptions());
        holder.price.setText(messagesList.get(position).getPrice());
        holder.location.setText(messagesList.get(position).getLocation());

        holder.room.setText(messagesList.get(position).getRoom());
        holder.kitchen.setText(messagesList.get(position).getKitchen());
        holder.parking.setText(messagesList.get(position).getParking());

        get_H_det(holder,position,Renting_URL.Get_house_det_URL);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MyOrders.class);
                intent.putExtra("id", messagesList.get(position).id);
                intent.putExtra("price", holder.price.getText().toString());
                intent.putExtra("room", holder.room.getText().toString());
                intent.putExtra("kitchen", holder.location.getText().toString());
                intent.putExtra("location", holder.parking.getText().toString());
                String[] loc = messagesList.get(position).getLocation().split("/");
//                System.out.println("''''''''''''''''''''''''''''''''''''''''''''''''" + loc[1] + "---" + loc[2]);
                if (loc.length > 1)
                    latLng = new LatLng(Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
                phoneNumber = messagesList.get(position).getPhone();
                kitchen = messagesList.get(position).getKitchen();
                room = messagesList.get(position).getRoom();
                location = messagesList.get(position).getLocation();
                price = messagesList.get(position).getPrice();
                parking = messagesList.get(position).getParking();
                intent.putExtra("phone", messagesList.get(position).getPhone());
                mContext.startActivity(intent);
            }
        });

//        holder.bedroom.setText(messagesList.get(position).getBedroom());
//        holder.dinningroom.setText(messagesList.get(position).getDinningroom());
//        holder.livingroom.setText(messagesList.get(position).getLivingroom());
//        Glide.with(holder.itemView.getContext())
//                .load(messagesList.get(position).getImageURL())
//                .fitCenter()
//                .into(holder.imageView);
        //Picasso.get().load(messagesList.get(position).getImageURL()).into(holder.imageView);
    }

    public void get_H_det(final ViewHollder viewholder, final int position, String URL) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();
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
                            Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public void get_Image(final ViewHollder viewholder, final String pho_no[], final int[] id, final String[] locat, final String[] room,
                          final String[] parking, final String[] price, final String[] kitchen, final String[] descripion,
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
                                image[i] = "http://" + Renting_URL.ip+ "/House_Ranting/uploads/Image/" + object.getString("image")+".png";

                            }

                            viewholder.price.setText(price[position]);
                            viewholder.parking.setText(parking[position]);
//                            viewholder.kitche.setText(kitchen[position]);
                            viewholder.location.setText(locat[position]);
                            viewholder.room.setText(room[position]);
                            viewholder.price.setText(price[position]);
                            new getimage(viewholder.imageView).execute(image[position]);
//                            Toast.makeText(mContext, image[position], Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
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

    public class ViewHollder extends RecyclerView.ViewHolder {
        //Widgets
        public ImageView imageView;
        public TextView textView;
        public TextView price;
        public TextView location;

        public TextView room;
        public TextView kitchen;
        public TextView parking;

        public TextView bedroom;
        public TextView dinningroom;
        public TextView livingroom;

        public CardView card;

        public ViewHollder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rImgV);
            textView = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.rTv);
            //Footer of Card
            room = itemView.findViewById(R.id.rum);
            kitchen = itemView.findViewById(R.id.kicheni);
            parking = itemView.findViewById(R.id.paking);
            card = itemView.findViewById(R.id.card);
            //layer below
//            bedroom = itemView.findViewById(R.id.bedroom);
//            dinningroom = itemView.findViewById(R.id.dinningRoom);
//            livingroom = itemView.findViewById(R.id.sitting);
        }
    }
}