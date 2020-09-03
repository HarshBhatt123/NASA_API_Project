package com.example.NASAexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static com.example.NASAexplorer.ImageLib.EXTRA_DESCRIPTION;
import static com.example.NASAexplorer.ImageLib.EXTRA_ID;
import static com.example.NASAexplorer.ImageLib.EXTRA_MEDIA;
import static com.example.NASAexplorer.ImageLib.EXTRA_TITLE;
import static com.example.NASAexplorer.ImageLib.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
       String id = intent.getStringExtra(EXTRA_ID);
       String title = intent.getStringExtra(EXTRA_TITLE);
       String description = intent.getStringExtra(EXTRA_DESCRIPTION);
       String type = intent.getStringExtra(EXTRA_MEDIA);

        ImageView imageView = findViewById(R.id.ImgV);
      //  WebView webV = findViewById(R.id.webview);
        TextView titleTv = findViewById(R.id.titleTv);
        TextView details = findViewById(R.id.description);

        details.setMovementMethod(new ScrollingMovementMethod());
        titleTv.setMovementMethod(new ScrollingMovementMethod());


        Picasso.get().load(imageUrl).into(imageView);

        if(type.equals("image")){
            //webV.setVisibility(View.GONE);
           //imageView.setVisibility(View.VISIBLE);
           // Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
            Toast.makeText(this, "Loading... "+type+" wait ", Toast.LENGTH_SHORT).show();
        }
        else if (type.equals("video")){
           // webV.setVisibility(View.VISIBLE);
//            imageView.setVisibility(View.GONE);
//            webV.getSettings().setJavaScriptEnabled(true);
//            //mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
//            webV.setWebChromeClient(new WebChromeClient());
//            webV.loadUrl(imageUrl);
        //   Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "media file can't be determined", Toast.LENGTH_SHORT).show();
        }



        titleTv.setText(title);
        details.setText(description);







    }
}