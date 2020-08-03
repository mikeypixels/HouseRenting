package com.reuben.company.houserenting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class Map_Way extends AppCompatActivity {
private static final int ERROR_DIALOG_REQUST = 9001;
private Button map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        map = findViewById(R.id.map);

        if (isService()){
            map_nav();
        }
        homePage();
    }

    public boolean isService(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Map_Way.this);
        if (available == ConnectionResult.SUCCESS){
            return true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Map_Way.this,available,ERROR_DIALOG_REQUST);
            dialog.show();
        }else {
            Toast.makeText(this,"you cant access map",Toast.LENGTH_LONG).show();
        }
        return false;
    }
    private void map_nav(){
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Map_Way.this, Live_map.class);
                startActivity(intent);
            }
        });
    }

    public void homePage(){
        Button button = findViewById(R.id.home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Map_Way.this,Home_page.class);
                startActivity(intent);
            }
        });
    }
}