package com.reuben.company.houserenting;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SiginAs extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    Button user, owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin_as);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        user = findViewById(R.id.user);
        owner = findViewById(R.id.owner);

        //NO INTERNET DIALOG
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        //Get active network info
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Check network status
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            //When internet is inActive
            //Initialize dialog
            Dialog dialog = new Dialog(this);
            //set ContextView
            dialog.setContentView(R.layout.internet);
            //set outsize touch
            dialog.setCanceledOnTouchOutside(false);
            //set dialog width and height
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //Set animation
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            //Initialize dialog variable
            Button btnTry = dialog.findViewById(R.id.try_again);
            btnTry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Call recreate method
                    recreate();
                }
            });
            dialog.show();
        }else {

        }

//        if (ConnectivityReceiver.isConnected(getApplicationContext())){
//
//        }else{
//            //NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(this).build();
//            final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SiginAs.this, SweetAlertDialog.WARNING_TYPE);
//            sweetAlertDialog.setTitle("You're Offline..!!");
//            sweetAlertDialog.setContentText("Please Make Sure That your Connected to Internet Access");
//            sweetAlertDialog.setCancelable(false);
//            sweetAlertDialog.setConfirmText("Ok");
//            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                @Override
//                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                    finish();
//                }
//            });
//            sweetAlertDialog.dismiss();
//
//        }

//        user.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SiginAs.this, Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiginAs.this, NavDrawer.class);
                startActivity(intent);
                finish();
            }
        });

//        owner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SiginAs.this, RegisterActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiginAs.this, Upload.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View layout_pwd = inflater.inflate(R.layout.alert_dialog, null);

            dialog.setView(layout_pwd);
            dialog.setCancelable(false);
            //SET POSITIVE BUTTON
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            //NEGATIVE BUTTON
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.show();
        }
    }
