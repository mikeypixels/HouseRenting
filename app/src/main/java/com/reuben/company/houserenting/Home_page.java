package com.reuben.company.houserenting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.reuben.company.houserenting.R_Class.Datail_Adapter;
import com.reuben.company.houserenting.R_Class.Renting_URL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Home_page extends AppCompatActivity {
private ListView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView = findViewById(R.id.listv);
        get_house_det(Renting_URL.Get_house_det_URL);
    }

    public void get_house_det(final String URL) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        String[] phone;
                        String[] loc,roo,park,pric,kitch,dec,phon;
                        int id[];
                        try {
                            JSONArray array = new JSONArray(response);
                            loc = new String[array.length()];
                            roo = new String[array.length()];
//                            pas = new String[array.length()];
                            dec = new String[array.length()];
                            kitch = new String[array.length()];
                            pric = new String[array.length()];
                            park = new String[array.length()];
                            phon = new String[array.length()];
                            phone = new String[array.length()];
                            id = new int[array.length()];
                            JSONObject object = null;
                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                phone[i] = object.getString("phone");
                                id[i] = object.getInt("id");
                                roo[i] = object.getString("room");
                                loc[i] = object.getString("location");
                                pric[i] = object.getString("price");
                                park[i] = object.getString("parking");
                                kitch[i] = object.getString("kitchen");
                                dec[i] = object.getString("descriptions");
                                phon[i] = object.getString("phone");
                            }
//                            Toast.makeText(getApplicationContext(),"phone"+phone[1], Toast.LENGTH_LONG).show();
                            Datail_Adapter adapter = new Datail_Adapter(Home_page.this,phone,id);
                            recyclerView.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(),"on adapter"+ e.toString(), Toast.LENGTH_LONG).show();
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }




}