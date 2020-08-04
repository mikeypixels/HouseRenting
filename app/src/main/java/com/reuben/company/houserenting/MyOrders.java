package com.reuben.company.houserenting;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.reuben.company.houserenting.Model.RecyclerAdapter;
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
    Bundle bundle;
    Bitmap bitmap;
    Button loc;
    ImageView img1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;
    public static final String ip = "192.168.43.201";
    public static final String get_placed_order_URL = "http://"+ip+"/ProcureApload/get_placed_order.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
//        setContentView(R.layout.getslide_image);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pager = findViewById(R.id.vwpger);
        loc = findViewById(R.id.loc);
        TextView rTv = findViewById(R.id.rTv); //location
        TextView price = findViewById(R.id.price);
        TextView rum = findViewById(R.id.rum);
        TextView kicheni = findViewById(R.id.kicheni);
        TextView paking = findViewById(R.id.paking);
        img1 = findViewById(R.id.img1);
        Button renT = findViewById(R.id.renT);
//        imageView = findViewById(R.id.slideimg);

        rTv.setText(RecyclerAdapter.location);
        price.setText(RecyclerAdapter.price);
        rum.setText(RecyclerAdapter.room);
        kicheni.setText(RecyclerAdapter.kitchen);
        paking.setText(RecyclerAdapter.parking);
        list = new ArrayList<>();
//        list.add(R.drawable.phone_focused);
//        list.add(R.drawable.home);
//        list.add(R.drawable.person);
//        list.add(R.drawable.person_focused);
//        list.add(R.drawable.phone);
        getprod_det_requst(Renting_URL.Get_image_URL);
//        Button button = findViewById(R.id.map);
        final Bundle bundleloc;
        bundleloc = getIntent().getExtras();
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("''''''''''''''''''''''''''''''''''''''''''''''''" + bundleloc.getString("latitude") + "---" + bundleloc.getString("longitude"));
                Intent intent = new Intent(MyOrders.this,Live_map.class);
                intent.putExtra("latitude", bundleloc.getString("latitude"));
                intent.putExtra("longitude", bundleloc.getString("longitude"));
                startActivity(intent);
            }
        });
        renT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_sms();
            }
        });
    }

    public void getprod_det_requst(final String URL) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
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
//                            Toast.makeText(getApplicationContext(), list.get(0), Toast.LENGTH_LONG).show();
                            pager.setAdapter(new Image_Slide(list,MyOrders.this));
                            new getimage(img1).execute();
                        } catch (Exception e) {
                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Aploaded error on responce on getprod" + error.toString(), Toast.LENGTH_LONG).show();
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

    public void send_sms(){
        bundle = getIntent().getExtras();
        String phone = RecyclerAdapter.phoneNumber;
        String kitchen = RecyclerAdapter.kitchen;
        String room = RecyclerAdapter.room;
        String location = RecyclerAdapter.location;
        String price = RecyclerAdapter.price;
        String sms ="Your house with "+ kitchen +"kitchen ,"+room+" room"+". Located at "+location+"with price of Tsh"+price+"/=";
        PendingIntent pendingIntent = null, deliveryIntent = null;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone,null,sms,pendingIntent,deliveryIntent);
//        Toast.makeText(MyOrders.this, "SMS sent.",Toast.LENGTH_LONG).show();
    }
    protected void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            } else {

            }
        }
    }

    public void phone_call(View v){
        bundle = getIntent().getExtras();
        String phone = RecyclerAdapter.phoneNumber;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phone));
//        if (ActivityCompat.checkSelfPermission(Raustarant_Activities_page.this,
//                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        }
    }

}