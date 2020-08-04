package com.reuben.company.houserenting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.Arrays;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {
    private final int REQUEST_LOGIN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        FirebaseApp.initializeApp(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //This is if the user is already login
        if (auth.getCurrentUser() != null) {
            if (!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()) {
                startActivity(new Intent(this, NavDrawer.class)
                        .putExtra("Phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty())
                );
                finish();
            }
        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder().setAvailableProviders(
                            Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                            )).build(), REQUEST_LOGIN);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            //Successfully signIn
            if (resultCode == RESULT_OK) {
                if (!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()) {
                    startActivity(new Intent(this, NavDrawer.class).putExtra("Phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()));
                    finish();

                    //Sigin Failed
                } else {
                    if (response == null) {
//                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                        //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        //SweetDialog
                        SweetAlertDialog waitingDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE);
                        waitingDialog.setTitleText("NO INTERNET");
                        //waitingDialog.getProgressHelper().setBarColor(Color.parseColor("#2680EB"));
                        waitingDialog.setContentText("No Internet Connection!!! Please Make Sure Your Online");
                        waitingDialog.setCancelable(true);
                        waitingDialog.setCancelText("DISMISS");
                        waitingDialog.show();
                        return;
                    }

                    if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                        Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(this, "Unknown Sig in Error", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setIcon(R.drawable.power);
        alertDialog.setTitle("Confirm Exit..!");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Are You Sure You Want To Cancel Phone Registration Process?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Login.this, SiginAs.class));
                finish();
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
