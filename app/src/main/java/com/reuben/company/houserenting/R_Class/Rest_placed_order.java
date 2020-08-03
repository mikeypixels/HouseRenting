//package com.reuben.company.houserenting.R_Class;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.reuben.company.houserenting.R;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Rest_placed_order extends ArrayAdapter {
//    String[] prodname,prodprice,prodquant,prodstatus,proddescrpt,image_url,user;
//    String[] date,time;
//    String restlocation,restno;
//    int[] id,restid,supid;
//    int orderNo,getrest;
//    String restname;
//    Activity activity;
//    Bitmap bitmap;
//
////    }
//    public Rest_placed_order(Activity activity,int getrest,String[] prodname) {
//        super(activity , R.listof_house,prodname);
//        this.getrest = getrest;
//        this.activity = activity;
//        this.prodname = prodname;
//    }
//
//    @NonNull
//    @Override
//    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View v = convertView;
//        Viewholder viewholder = null;
//        if (v == null) {
//            LayoutInflater inflater = activity.getLayoutInflater();
//            v = inflater.inflate(R.layout.order_details_adapter, parent, false);
//            viewholder = new Viewholder(v);
//            v.setTag(viewholder);
//        }
//        else {
//            viewholder = (Viewholder)v.getTag();
//        }
//
//        getprod_det_requst(viewholder,position,);
//
//        if (viewholder.proddet.getText().toString().contains("Unpaid")){
//            viewholder.confimbtn.setHint("Buy Order No."+orderNo+"");
//        }else {
//            viewholder.confimbtn.setHint("Order No."+orderNo+" delivered?");
//        }
//
//        final Viewholder finalViewholder = viewholder;
//        viewholder.confimbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (finalViewholder.proddet.getText().toString().contains("Unpaid")){
//                    Intent intent = new Intent(activity, Product_Details.class);
//                    intent.putExtra("setid",id[position]);
//                    intent.putExtra("id",restid[position]);
//                    intent.putExtra("user",user[position]);
//                    intent.putExtra("supid",supid[position]);
//                    intent.putExtra("detail","placed");
//                    activity.startActivity(intent);
//                }else {
//                    int ida = id[position];
//                    orderIdrequst(ida,Procure_Url.confirm_derivered_order_URL);
//                }
//            }
//        });
//
//        return v;
//    }
//
//
//    public void orderIdrequst(final int orderid ,final String URL){
//
//        StringRequest request = new StringRequest(Request.Method.POST,URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        String location = "";
//                        String no = "";
//                        try{
//                            JSONArray array = new JSONArray(response);
//                            JSONObject object = null;
//                            for (int i = 0; i<array.length();i++){
//                                object = array.getJSONObject(i);
////                                no = object.getString("phone_no");
////                                location = object.getString("location");
//                            }
////                            setListView(id,no,location);
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(activity, "Aploaded error on catch order "+e.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(activity, "Aploaded error on responce "+error.toString(), Toast.LENGTH_LONG).show();
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("id",""+orderid+"");
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(activity);
//        requestQueue.add(request);
//    }
//
//    class Viewholder{
//        ImageView imageView;
//        TextView proddet;
//        TextView restdet;
//        TextView date;
//        TextView prodtitle;
//        TextView sup_title;
//        Button confimbtn;
//
//        Viewholder(View v){
//            imageView = v.findViewById(R.id.orderimgvw);
//            proddet = v.findViewById(R.id.productdet);
//            restdet = v.findViewById(R.id.restdetails);
//            date = v.findViewById(R.id.date);
//            confimbtn = v.findViewById(R.id.confirm_btn);
//            prodtitle = v.findViewById(R.id.prod);
//            sup_title = v.findViewById(R.id.sup_det);
//        }
//    }
//
//
//    public void getsup_det_requst(final Viewholder viewholder,final int position,final String[] time,final int[] supid,final String[] name,
//                                  final String[] quantity,final String[] status,final String[] amount,final String[] date,
//                                  final String[] descriptions,final String[] image_url,final String URL) {
//
//        StringRequest request = new StringRequest(Request.Method.POST, URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        String sup_name;
//                        String locat = "",sup_no = "";
//
//                        try {
//
//                            JSONArray array = new JSONArray(response);
//                            JSONObject object = null;
//                            sup_name = "";
//                            orderNo = 0;
//                            for (int i = 0; i < array.length(); i++) {
//                                object = array.getJSONObject(i);
//                                locat = object.getString("location");
//                                sup_no = object.getString("phone_no");
//                                sup_name = object.getString("user_name");
//                                orderNo++;
//                            }
//
//                            viewholder.proddet.setText("Product name :"+name[position]+"\n"+"Quantity :"+quantity[position]+"\n"+"Price :"+amount[position]+
//                                    "\n"+"Status :"+status[position]+"\n"+"Description :"+descriptions[position]);
//
//                            viewholder.restdet.setText("Restaurant name :"+sup_name+"\n"+"Location :"+locat+"\n"+"Phone no. "+sup_no);
//
//                            viewholder.date.setText(date[position]+"  "+time[position]);
//
//                            new getimage(viewholder.imageView).execute(image_url[position]);
//
//                            viewholder.sup_title.setText("Supplier Details");
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
////                            Toast.makeText(getApplicationContext(),"catch on getsup "+response+ e.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Toast.makeText(getApplicationContext(), "Aploaded error on responce " + error.toString(), Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("id",  ""+supid[position]+"");
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(activity);
//        requestQueue.add(request);
//    }
//
//    public void getprod_det_requst(final Viewholder viewholder, final int position, final String URL) {
//
//        StringRequest request = new StringRequest(Request.Method.POST, URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        String details = "";
//                        String[] name, quantity, status, amount, date,time, descriptions, image_url;
//
//                        try {
//
//                            JSONArray array = new JSONArray(response);
//                            JSONObject object = null;
//                            name = new String[array.length()];
//                            quantity = new String[array.length()];
//                            status = new String[array.length()];
//                            amount = new String[array.length()];
//                            date = new String[array.length()];
//                            time = new String[array.length()];
//                            descriptions = new String[array.length()];
//                            image_url = new String[array.length()];
//                            id = new int[array.length()];
//                            restid = new int[array.length()];
//                            user = new String[array.length()];
//                            supid = new int[array.length()];
//
//                            for (int i = 0; i < array.length(); i++) {
//                                object = array.getJSONObject(i);
//                                supid[i] = object.getInt("user_id");
//                                restid[i] = object.getInt("rest_id");
//                                id[i] = object.getInt("id");
//                                name[i] = object.getString("name");
//                                user[i] = object.getString("user");
//                                quantity[i] = object.getString("quantity");
//                                status[i] = object.getString("status");
//                                amount[i] = object.getString("amount");
//                                date[i] = object.getString("date");
//                                time[i] = object.getString("time");
//                                descriptions[i] = object.getString("descriptions");
//                                image_url[i] = "http://" + Procure_Url.ip + "/ProcureApload/uploads/Image/" + object.getString("name") + ".png";
//
//                            }
//                            getsup_det_requst(viewholder,position,time,supid,name,quantity,status,amount,date,descriptions,image_url,Procure_Url.sup_details_URL);
////                            Toast.makeText(getApplicationContext(), supid, Toast.LENGTH_LONG).show();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
////                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Toast.makeText(getApplicationContext(), "Aploaded error on responce on getprod" + error.toString(), Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("id", "" + getrest + "");
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(activity);
//        requestQueue.add(request);
//    }
//
//    public class getimage extends AsyncTask<String,Void, Bitmap> {
//
//        ImageView imageView;
//        public getimage(ImageView imageView) {
//            this.imageView = imageView;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... url) {
//            String geturl = url[0];
//            bitmap = null;
//            try {
//                InputStream stream = new java.net.URL(geturl).openStream();
//                bitmap = BitmapFactory.decodeStream(stream);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            imageView.setImageBitmap(bitmap);
//        }
//    }
//}
//
