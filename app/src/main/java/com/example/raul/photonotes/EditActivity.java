package com.example.raul.photonotes;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.simplify.ink.InkView;

public class EditActivity extends AppCompatActivity {

    private ImageView ivEdit;
    InkView ink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ivEdit = (ImageView) findViewById(R.id.ivEdit);
        String url = getIntent().getStringExtra("url");
        Log.d("URL:", url);
        Glide.with(this).load(url).into(ivEdit);

        ink = (InkView) findViewById(R.id.ink);
        ink.setColor(getResources().getColor(android.R.color.black));
        ink.setMinStrokeWidth(1.5f);
        ink.setMaxStrokeWidth(6f);

        ink.bringToFront();
    }
}
