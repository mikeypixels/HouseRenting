package com.reuben.company.houserenting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.reuben.company.houserenting.R_Class.Image_Slide;
import com.reuben.company.houserenting.R_Class.Renting_URL;
import com.reuben.company.houserenting.R_Class.Selected_image_ad;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Upload extends AppCompatActivity{
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    TextView info;
    RelativeLayout rootLayout;
    private ImageView HouseImage;
    private static final int result_load = 1000;
    //EditTexts
    EditText loc, desc, price, room, kitchen, parking, phone;
    ProgressDialog progressDialog;
    private Uri mImageUri;
    private RecyclerView recyclerView;
    private Button button;
    private List<String> imagename;
    private List<Bitmap> img;
    Selected_image_ad selected_image_ad;
    Button button2;
    TextView imgvname;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    LocationManager locationManager;
    private List<String> imgstore;

    AppLocationService appLocationService;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_uploader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout)
            showPopup();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        intinials();
//        setContentView(R.layout.getslide_image);
//        button = findViewById(R.id.btn);
//        button2 = findViewById(R.id.ap);
        recyclerView = findViewById(R.id.slider);
        imagename = new ArrayList<>();
        img = new ArrayList<>();
        selected_image_ad = new Selected_image_ad(imagename, img);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(selected_image_ad);
        appLocationService = new AppLocationService(
                Upload.this);
        Location location = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getApplicationContext(), new GeocoderHandler());
        } else {
            showSettingsAlert();
        }
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loc.getText().toString().isEmpty()){
                    Toast.makeText(Upload.this,"fill all space first",Toast.LENGTH_LONG).show();
                }else {
                    if (ActivityCompat.checkSelfPermission(Upload.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Upload.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                        pickImageFromGalleery();
                        upload_det(Renting_URL.Insert_House_details_URL);
                        return;
                    }

                    pickImageFromGalleery();
                    upload_det(Renting_URL.Insert_House_details_URL);

                }
            }
        });
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Upload.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        Upload.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            loc.setText(locationAddress);
        }
    }

    public void intinials() {
        imgstore = new ArrayList<>();
        //Init
        mButtonChooseImage = findViewById(R.id.mButtonChooseImage);
        mButtonUpload = findViewById(R.id.mButtonUpload);
        HouseImage = findViewById(R.id.HouseImage);
        imgvname = findViewById(R.id.imgvname);

        //house Location
        loc = findViewById(R.id.loc);

        //House Description
        desc = findViewById(R.id.Dedc);

        //House Price
        price = findViewById(R.id.pricee);
        phone = findViewById(R.id.phone);
        info = findViewById(R.id.info);
        //House rooms
        room = findViewById(R.id.rum);

        //House Kitchen
        kitchen = findViewById(R.id.kicheni);

        //House parking
        parking = findViewById(R.id.paking);


        //Last Floor
//        bed = findViewById(R.id.bed);
//        dinning = findViewById(R.id.dinning);
//        living = findViewById(R.id.living);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setIcon(R.drawable.power);
        alertDialog.setTitle("Confirm Exit..!");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Are You Sure You Want To Back Home?");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Toast.makeText(this,"req"+requestCode,Toast.LENGTH_LONG).show();
//        Toast.makeText(this,"ok"+resultCode,Toast.LENGTH_LONG).show();
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data.getClipData() == null) {
//              final int sel_no = data.getClipData().getItemCount();
//              //for single image
//              mImageUri = data.getData();
                convertBitmapToString(0);
                Uri uri = data.getData();
                String name = name_uri(uri);
                imgvname.setText(name);
                Toast.makeText(this, "One Item", Toast.LENGTH_LONG).show();
                try {
                    InputStream stream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    HouseImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (data.getClipData() != null) {
                //for multimage
//              Toast.makeText(this,"Many Item",Toast.LENGTH_LONG).show();
                 int sel_no = data.getClipData().getItemCount();

                for (int i = 0; i < sel_no; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    String name = name_uri(uri);
                    imagename.add(name);
                    try {
                        InputStream stream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(stream);
                        img.add(bitmap);
                        selected_image_ad.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < sel_no; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    try {
                        InputStream stream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(stream);
                        img.add(bitmap);
                        selected_image_ad.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            } else {
                Toast.makeText(this, "no Item selected", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "no action", Toast.LENGTH_LONG).show();
        }
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sel_no = data.getClipData().getItemCount();
                convertBitmapToString(sel_no);
                for (int i = 0; i < sel_no; i++) {
                    getid(Renting_URL.Get_house_det_URL,phone.getText().toString(),i);
//                    Toast.makeText(Upload.this,imgstore.get(Integer.parseInt(loc.getText().toString())), Toast.LENGTH_LONG).show();
//                    HouseImage.setImageBitmap(img.get(Integer.parseInt(loc.getText().toString())));
                }
            }
        });
    }

    public void upload_det(final String URL) {
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
                            }

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
                //params.put("id", "" + 49 + "");
                params.put("location", loc.getText().toString());
                params.put("description",desc.getText().toString() );
                params.put("room", room.getText().toString());
                params.put("kitchen",kitchen.getText().toString() );
//                params.put("coordinate", co.getText().toString());
                params.put("price",price.getText().toString() );
                params.put("parking", parking.getText().toString());
                params.put("phone",phone.getText().toString() );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void upload_image(final String URL, final int id, final List<String>name, final List<String>image, final int position) {
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
//                                name = "http://" + ip + "/ProcureApload/uploads/Image/" + object.getString("name") + ".png";
//                                list.add(name);
//                                Toast.makeText(getApplicationContext(), list.get(i), Toast.LENGTH_LONG).show();
                            }

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
                params.put("name",name.get(position));
                params.put("image",image.get(position));
                params.put("id",""+id+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void getid(final String URL, final String no, final int postion) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        String details = "";
                        int id = 0;
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                id = object.getInt("id") ;
                            }
                            upload_image(Renting_URL.House_Image_URL,id,imagename,imgstore,postion);

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
                //params.put("id", "" + 49 + "");
                params.put("phone", no);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void convertBitmapToString(int count) {
        for (int i = 0;i<count;i++){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.get(i).compress(Bitmap.CompressFormat.PNG, 100, stream); //compress to which format you want.
            byte[] byte_arr = stream.toByteArray();
            String imageStr = Base64.encodeToString(byte_arr, Base64.DEFAULT);
            imgstore.add(imageStr);
        }
    }

    private void pickImageFromGalleery() {
        //Intent to pick Image
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
//        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    public String name_uri(Uri uri) {
        String imgname = null;
        if (uri.getScheme().equals("content")) {
            Cursor c = getContentResolver().query(uri, null, null, null, null);

            try {
                if (c != null && c.moveToFirst()) {
                    int id = c.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    imgname = c.getString(id);
//
                }
            } finally {
                c.close();
            }
        }

        if (imgname == null) {
            imgname = uri.getPath();
            int cut = imgname.lastIndexOf('/');
            if (cut != -1) {
                imgname = imgname.substring(cut + 1);
            }
        }
        return imgname;
    }

    private void signOut() {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(Upload.this, SiginAs.class));
                        finish();
                    }
                });
    }

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

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(Upload.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();

            loc.setText(obj.getLocality());

            String TAG = Upload.class.getSimpleName();

            Log.v(TAG, "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}