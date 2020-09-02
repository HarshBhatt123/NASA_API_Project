package com.example.NASAexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class first extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void toApod(View view) {
        Intent intent = new Intent(first.this,MainActivity.class);
        startActivity(intent);

    }

    public void ToVideolib(View view) {
        Intent intent = new Intent(first.this,ImageLib.class);
        startActivity(intent);
    }
}