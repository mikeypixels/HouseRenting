package com.reuben.company.houserenting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.reuben.company.houserenting.R_Class.Image_Slide;
import com.reuben.company.houserenting.R_Class.Renting_URL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOrders extends AppCompatActivity {
    private ViewPager pager;
    private List<String> list;
    Bitmap bitmap;
    private ImageView imageView;
    public static final String ip = "192.168.43.201";
    public static final String get_placed_order_URL = "http://"+ip+"/ProcureApload/get_placed_order.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
//        setContentView(R.layout.getslide_image);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pager = findViewById(R.id.viewpager);
//        imageView = findViewById(R.id.slideimg);
        list = new ArrayList<>();
//        list.add(R.drawable.phone_focused);
//        list.add(R.drawable.home);
//        list.add(R.drawable.person);
//        list.add(R.drawable.person_focused);
//        list.add(R.drawable.phone);
        getprod_det_requst(Renting_URL.Get_image_URL);
        Button button = findViewById(R.id.map);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyOrders.this,Map_Way.class);
                startActivity(intent);
            }
        });
    }

    public void getprod_det_requst(final String URL) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        String details = "";
                        String name;
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                name = "http://" +ip+ "/House_Ranting/uploads/Image/" + object.getString("image")+".png";
                                list.add(name);
//                                Toast.makeText(getApplicationContext(), list.get(i), Toast.LENGTH_LONG).show();
//                               new getimage(imageView).execute(name);
                            }
                            Toast.makeText(getApplicationContext(), list.get(0), Toast.LENGTH_LONG).show();
                            pager.setAdapter(new Image_Slide(list,MyOrders.this));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Aploaded error on responce on getprod" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", "" +17+ "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    protected List<Bitmap> bitmap_image(List<String> url) {
        List<Bitmap> image = new ArrayList<>();
        bitmap = null;
        for (int i = 0;i<url.size();i++){
            String geturl = url.get(i);
            try {
                InputStream stream = new java.net.URL(geturl).openStream();
                bitmap = BitmapFactory.decodeStream(stream);
            }catch (Exception e){
                e.printStackTrace();
            }
            image.add(bitmap);
        }
        return image;
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

    public int ownid(){
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("id");
    }
}