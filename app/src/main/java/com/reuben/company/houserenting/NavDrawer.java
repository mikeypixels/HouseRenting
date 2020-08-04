package com.reuben.company.houserenting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.core.Context;
import com.reuben.company.houserenting.Model.RecyclerAdapter;
import com.reuben.company.houserenting.Model.RecyclerViewModel;
import com.reuben.company.houserenting.R_Class.Datail_Adapter;
import com.reuben.company.houserenting.R_Class.Renting_URL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ConstraintLayout mainlayout;
    TextView name, mail;
    TextView txtPhone;

    //Firebase
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<RecyclerViewModel> options;
    // FirebaseRecyclerAdapter<RecyclerViewModel, RecyclerAdapter> adapter;
    private DatabaseReference mDatabaseRef;

    //Variables
    private ArrayList<RecyclerViewModel> messageslist;
    private ArrayList<RecyclerViewModel> messagesArraylist = new ArrayList<>();
    private ArrayList<RecyclerViewModel> messagesSubArraylist = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private ProgressBar mProgressCircle;
    private Spinner spinner;
    private Context mContext;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressCircle = findViewById(R.id.progress_circle);
        spinner = findViewById(R.id.spinner);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        get_house_det(Renting_URL.Get_house_det_URL);

        final ArrayList<String> priceRange = new ArrayList<>();
        priceRange.add("0 - 49,999 Tshs");
        priceRange.add("50,000 - 99,999 Tshs");
        priceRange.add("100,000 - 200,000 Tshs");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, priceRange);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!messagesArraylist.isEmpty()){
                    if(!messagesSubArraylist.isEmpty()){
                        messagesSubArraylist.clear();
                    }
                    if(priceRange.get(position).equals("0 - 49,999 Tshs")){
                        for(int i=0; i<messagesArraylist.size(); i++){
                            if(Double.parseDouble(messagesArraylist.get(i).getPrice().replaceAll(",", "")) >= 0 && Double.parseDouble(messagesArraylist.get(i).getPrice().replaceAll(",", "")) < 50000){
                                messagesSubArraylist.add(messagesArraylist.get(i));
                                System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + messagesSubArraylist.get(i).getPrice());
                            }
                        }
                    }else if(priceRange.get(position).equals("50,000 - 99,999 Tshs")){
                        for(int i=0; i<messagesArraylist.size(); i++){
                            if(Double.parseDouble(messagesArraylist.get(i).getPrice().replaceAll(",", "")) >= 50000 && Double.parseDouble(messagesArraylist.get(i).getPrice().replaceAll(",", "")) < 100000){
                                messagesSubArraylist.add(messagesArraylist.get(i));
                            }
                        }
                    }else if(priceRange.get(position).equals("100,000 - 200,000 Tshs")){
                        for(int i=0; i<messagesArraylist.size(); i++){
                            if(Double.parseDouble(messagesArraylist.get(i).getPrice().replaceAll(",", "")) >= 100000 && Double.parseDouble(messagesArraylist.get(i).getPrice().replaceAll(",", "")) <= 200000){
                                messagesSubArraylist.add(messagesArraylist.get(i));
                            }
                        }
                    }
                    recyclerAdapter = new RecyclerAdapter(NavDrawer.this, messagesSubArraylist);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                }else{
                    if(!messagesSubArraylist.isEmpty()){
                        messagesSubArraylist.clear();
                    }
                    if(priceRange.get(position).equals("0 - 49,999 Tshs")){
                        for(int i=0; i<messageslist.size(); i++){
                            if(Double.parseDouble(messageslist.get(i).getPrice().replaceAll(",", "")) >= 0 && Double.parseDouble(messageslist.get(i).getPrice().replaceAll(",", "")) < 50000){
                                messagesSubArraylist.add(messageslist.get(i));
                            }
                        }
                    }else if(priceRange.get(position).equals("50,000 - 99,999 Tshs")){
                        for(int i=0; i<messageslist.size(); i++){
                            if(Double.parseDouble(messageslist.get(i).getPrice().replaceAll(",", "")) >= 50000 && Double.parseDouble(messageslist.get(i).getPrice().replaceAll(",", "")) < 100000){
                                messagesSubArraylist.add(messageslist.get(i));
                            }
                        }
                    }else if(priceRange.get(position).equals("100,000 - 200,000 Tshs")){
                        for(int i=0; i<messageslist.size(); i++){
                            if(Double.parseDouble(messageslist.get(i).getPrice().replaceAll(",", "")) >= 100000 && Double.parseDouble(messageslist.get(i).getPrice().replaceAll(",", "")) <= 200000){
                                messagesSubArraylist.add(messageslist.get(i));
                            }
                        }
                    }
                    recyclerAdapter = new RecyclerAdapter(NavDrawer.this, messagesSubArraylist);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Firebase
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        //ArrayList
        messageslist = new ArrayList<>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

//        Toast.makeText(this, preferences.getString("phoneNumber", ""), Toast.LENGTH_SHORT).show();

        //Clear ArrayList
        //learAll();

        //Get Data
//        GetDataFromFirebase();

        //Change text color in tab
        toolbar.setTitleTextColor(Color.WHITE);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Change color of the toggle button
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));


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
                                messageslist.add(new RecyclerViewModel(id[i], phone[i], roo[i], loc[i], pric[i], park[i], kitch[i], dec[i]));
                                System.out.println("=======================================" + phone[i]);
                            }

                            recyclerAdapter = new RecyclerAdapter(NavDrawer.this, messageslist);
                            recyclerView.setAdapter(recyclerAdapter);
                            recyclerAdapter.notifyDataSetChanged();

                            mProgressCircle.setVisibility(View.GONE);

//                            Toast.makeText(getApplicationContext(),"phone"+phone[1], Toast.LENGTH_LONG).show();
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

//    private void GetDataFromFirebase() {
//        Query query = mDatabaseRef.child("uploads");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //learAll();
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                    RecyclerViewModel recyclerViewModel = new RecyclerViewModel();
//
//                    recyclerViewModel.setImageURL(snapshot1.child("phone").getValue().toString());
//                    recyclerViewModel.setImagePrice(snapshot1.child("parking").getValue().toString());
//                    recyclerViewModel.setImageName(snapshot1.child("imageURL").getValue().toString());
//                    recyclerViewModel.setImageLoc(snapshot1.child("imageLoc").getValue().toString());
//
//                    //Card Footer
//                    recyclerViewModel.setHouseRoom(snapshot1.child("imagePrice").getValue().toString());
//                    recyclerViewModel.setHouseKitchen(snapshot1.child("room").getValue().toString());
//                    recyclerViewModel.setHouseParking(snapshot1.child("kitchen").getValue().toString());
//                    messageslist.add(recyclerViewModel);
//
//                    //lower card
////                    recyclerViewModel.setBedroom(snapshot1.child("imageName").getValue().toString());
////                    recyclerViewModel.setDinningroom(snapshot1.child("imageURL").getValue().toString());
////                    recyclerViewModel.setLivingroom(snapshot1.child("imageLoc").getValue().toString());
////                    messageslist.add(recyclerViewModel);
//                }
//                recyclerAdapter = new RecyclerAdapter(mContext, messageslist);
//                recyclerView.setAdapter(recyclerAdapter);
//                recyclerAdapter.notifyDataSetChanged();
//
//                mProgressCircle.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(NavDrawer.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                mProgressCircle.setVisibility(View.INVISIBLE);
//            }
//        });
//    }

//    private void ClearAll() {
//        if (messageslist != null) {
//            messageslist.clear();
//
//            if (recyclerAdapter != null) {
//                recyclerAdapter.notifyDataSetChanged();
//            }
//        }
//        messageslist = new ArrayList<>();
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setIcon(R.mipmap.ic_launcher);
            alertDialog.setTitle("Confirm Logging Out..!");
            alertDialog.setMessage("Are You Sure You Want To Return Home?");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    signOut();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog1 = alertDialog.create();
            alertDialog1.show();
        }
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_drawer, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!messageslist.isEmpty()){
                    for(int i=0; i<messageslist.size(); i++){
                        if(messageslist.get(i).getLocation().equals(query)){
                            messagesArraylist.add(messageslist.get(i));
                        }
                    }
                    recyclerAdapter = new RecyclerAdapter(NavDrawer.this, messagesArraylist);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!messageslist.isEmpty()) {
                    for (int i = 0; i < messageslist.size(); i++) {
                        if (messageslist.get(i).getLocation().equals(newText)) {
                            messagesArraylist.add(messageslist.get(i));
                        }
                    }
                    recyclerAdapter = new RecyclerAdapter(NavDrawer.this, messagesArraylist);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sigIn_out) {
            showPopup();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.aboutUs:
                Intent i = new Intent(NavDrawer.this, AboutUs.class);
                startActivity(i);
                break;

//            case R.id.request:
//                Intent intent = new Intent(NavDrawer.this, MakeRequest.class);
//                startActivity(intent);
//                break;
//
//            case R.id.myorder:
//                Intent in = new Intent(NavDrawer.this, MyOrders.class);
//                startActivity(in);
//                break;

            case R.id.share:
                Intent shareintent = new Intent();
                shareintent.setAction(Intent.ACTION_SEND);
                shareintent.putExtra(Intent.EXTRA_TEXT, "https//play.google.com/store/apps/details?id=h1");
                shareintent.setType("text/plain");
                startActivity(Intent.createChooser(shareintent, "Share House Renting Via..."));
                break;

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(NavDrawer.this, SiginAs.class));
                        finish();
                    }
                });
    }

    // first step helper function
    private void showPopup() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setTitle("Confirm Logging Out..!");
        alertDialog.setMessage("Are You Sure You Want To LogOut?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOut();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }
}
