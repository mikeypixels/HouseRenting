package com.reuben.company.houserenting;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Live_map extends AppCompatActivity implements OnMapReadyCallback {
    private static final String FINE_LOC = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COUS_LOC = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOC_PERMIS_REQUEST_CODE = 1234;
    private GoogleMap gMap;
    private static final float DEFAUT_ZOOM = 15f;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Boolean map_permis_granted = false;
    private TextView search;
    private ImageView imageView;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_LONG).show();
        gMap = googleMap;
        if (map_permis_granted) {
            device_location();

            //for merking location
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            gMap.setMyLocationEnabled(true);
            //to remove gps btm
            gMap.getUiSettings().setMyLocationButtonEnabled(false);
            onsearch();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_map);
        search = findViewById(R.id.txt_search);
        getlocation_permis();
        onsearch();
    }

   public void onsearch(){
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        ||actionId == EditorInfo.IME_ACTION_DONE
                        ||event.getAction() == event.ACTION_DOWN
                        ||event.getAction() == event.KEYCODE_NUMPAD_ENTER){
                    //code for search location
                    geoLocation();
                }
                return false;
            }
        });
   }

   private void geoLocation(){
        String searchString  = search.getText().toString();
        Geocoder geocoder = new Geocoder(Live_map.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list.size() > 0){
            Address address = list.get(0);
            Toast.makeText(Live_map.this,address.toString(),Toast.LENGTH_LONG).show();
            search.setText("");
        }
    }

    private void device_location(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (map_permis_granted){
                 Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Live_map.this,"Location found",Toast.LENGTH_LONG).show();
                            Location current_loc = (Location) task.getResult();

//                            System.out.println("roja"+Objects.requireNonNull(task.getResult()).toString());

                            moving_camera(new LatLng(current_loc.getLatitude(),current_loc.getLongitude()),DEFAUT_ZOOM);

                        }else {
                            Toast.makeText(Live_map.this,"Unable to get current Location",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            e.getMessage();
        }
    }

    public void moving_camera(LatLng latLng,float zoom){
        Toast.makeText(Live_map.this,"Lat :"+latLng.latitude+", Long :"+latLng.longitude,Toast.LENGTH_LONG).show();
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    private void getlocation_permis(){
        String[] permis = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOC)== PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),COUS_LOC)== PackageManager.PERMISSION_GRANTED){
                map_permis_granted = true;
                setmap();
            }
        }else {
            ActivityCompat.requestPermissions(this,permis,LOC_PERMIS_REQUEST_CODE);
        }
    }

    private void setmap(){
         SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
         mapFragment.getMapAsync(Live_map.this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    map_permis_granted = false;
    switch (requestCode){
        case LOC_PERMIS_REQUEST_CODE:{
            if (grantResults.length > 0){
                for (int i = 0;i<grantResults.length;i++){
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"Permission denied",Toast.LENGTH_LONG).show();
                        map_permis_granted = false;
                        return;
                    }
                }
                Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show();
                map_permis_granted = true;
                //start map
                setmap();
            }
        }
    }
    }

}











