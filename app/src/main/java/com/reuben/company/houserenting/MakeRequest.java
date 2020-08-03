package com.reuben.company.houserenting;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MakeRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}