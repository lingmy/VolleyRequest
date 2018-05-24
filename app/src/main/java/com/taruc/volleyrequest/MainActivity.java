package com.taruc.volleyrequest;

import android.graphics.Bitmap;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private Button buttonRequest;
    private ImageView imageView;

    RequestQueue requestQueue;

   // String url ="https://api.myjson.com/bins/n0skq";
   String url ="https://api.myjson.com/bins/1191xm";
    String imageurl="https://amyling.000webhostapp.com/images/apple.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        buttonRequest = (Button)findViewById(R.id.buttonRequest);
        imageView = (ImageView)findViewById(R.id.imageView);
        buttonRequest.setOnClickListener(this);


        requestQueue = Volley.newRequestQueue(this);



    }

    @Override
    public void onClick(View v) {
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jobj = null;
                try{
                jobj = new JSONObject(response);
                String c= jobj.getString("user");
                    JSONArray jArray = new JSONArray(c);

                    for (int i = 0; i < jArray.length(); i++) {
                        String id = jArray.getJSONObject(i).getString("id");
                        String name = jArray.getJSONObject(i).getString("name");
                        String age = jArray.getJSONObject(i).getString("age");
                        String married = jArray.getJSONObject(i).getString("married");
                        textView.append(id+ "   " + name + "    "+age+"    "+married+"\n");

                    }

            }catch (JSONException e){
                    e.printStackTrace();
                    Log.d("Main activity",e.getMessage());
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //image rewuest from 000webhost.com
        ImageRequest imageRequest = new ImageRequest(imageurl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
        // image request is successful
                imageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

stringRequest.setTag("REQUEST");


// add stringRequest into requestQuest
        requestQueue.add(stringRequest);
        requestQueue.add(imageRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(requestQueue != null);
        requestQueue.cancelAll("REQUEST");

    }
}
