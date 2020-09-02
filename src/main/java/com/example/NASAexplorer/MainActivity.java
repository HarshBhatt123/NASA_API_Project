package com.example.NASAexplorer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    private static final String TAG = "MainAct";
    private TextView tv ,dateShow  ;
    private ImageView im;
    private Button select , show;
    SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
    Date todayDate = new Date();
    private String selectedDate, aajkadin;

    private String strDate  = currentDate.format(todayDate);
    //private VideoView videoView;
    private WebView mWebView ;
    //private  MediaController mc;





    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv2);
        mWebView = findViewById(R.id.webv);
         im = findViewById(R.id.iv);
          dateShow = findViewById(R.id.tvdate);
       // videoView =  findViewById(R.id.vv);
         select = findViewById(R.id.Btn);
         show = findViewById(R.id.Btn1);
        dateShow.setText(strDate);
        aajkadin=currentDate.format(todayDate);






            process();


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to open date picker
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date Picker");


            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //to set date in text view

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        Date date = new Date(DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime()));
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");

        selectedDate= DateFor.format(date);
        if(selectedDate.compareTo(aajkadin) >0){
            Toast.makeText(this, "Don't Select FUTURE date", Toast.LENGTH_LONG).show();
        }
        if(selectedDate.compareTo("1995-06-16")<0){
            Toast.makeText(this, "No data available", Toast.LENGTH_LONG).show();
        }
         strDate= DateFor.format(date);     //string

       // sx = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());


        Log.d(TAG, "onDateSet: chalu ");
        //dateShow.setText(sx);
        dateShow.setText(strDate);
    }

    private void process(){
        strDate = dateShow.getText().toString();
        RequestQueue requestQueue ;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://api.nasa.gov/planetary/apod?"+"date="+strDate+
                        "&hd=true&api_key=eryBeVeUDS48toEdybO37w5p1qXeFELfuCP07WzE"
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String st = response.getString("title");

                    String type = response.getString("media_type");
                    tv.setText(st);

                    if(type.equals("image")) {
                        mWebView.setVisibility(View.GONE);
                        im.setVisibility(View.VISIBLE);
                        String url = response.getString("url");
                        Picasso.get().load(url).into(im);
                        Toast.makeText(MainActivity.this, "Loading... image wait", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        mWebView.setVisibility(View.VISIBLE);
                        im.setVisibility(View.GONE);
                        String LINK = response.getString("url");

                        mWebView.getSettings().setJavaScriptEnabled(true);
                        //mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
                        mWebView.setWebChromeClient(new WebChromeClient());
                        mWebView.loadUrl(LINK);

//                        mc = new MediaController(MainActivity.this);
//                        mc.setAnchorView(videoView);
//                        mc.setMediaPlayer(videoView);
//                        Uri video = Uri.parse(LINK);
//                        videoView.setMediaController(mc);
//                        videoView.setVideoURI(video);
//                        videoView.start();
                       // Toast.makeText(MainActivity.this, "!!! Video file can't be played !!!  " +"Choose another date", Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "Loading... video wait", Toast.LENGTH_SHORT).show();
                    }
                  //  Toast.makeText(MainActivity.this, strDate, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: working"+ response.getString("title"));
                    //Log.d(TAG, "onResponse: working");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something is  wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: Something wrong");
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}