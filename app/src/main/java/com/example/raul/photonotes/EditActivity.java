package com.example.raul.photonotes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.simplify.ink.InkView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import DB.Modelo.Materia;
import DB.Modelo.Photo;
import DB.Modelo.PhotosCRUD;
import Fragments.HomeFragment;

public class EditActivity extends AppCompatActivity {

    private ImageView ivEdit;
    InkView ink;
    PhotosCRUD photoCRUD;
    Materia materia;
    Bitmap mbitmap;
    private static final String TAG_HOME = "home";

    private static final String FILE_PROVIDER = "com.example.raul.photonotes.fileprovider";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        photoCRUD = new PhotosCRUD(getApplicationContext());
        ivEdit = (ImageView) findViewById(R.id.ivEdit);
        String url = getIntent().getStringExtra("url");
        int id = Integer.parseInt(getIntent().getStringExtra("materia"));
        Log.d("ID", ""+id);
        materia = photoCRUD.getMaterias(id).get(0);

        Log.d("URL:", url);
        Glide.with(this).load(url).into(ivEdit);

        ink = (InkView) findViewById(R.id.ink);
        ink.setColor(getResources().getColor(android.R.color.holo_red_dark));
        ink.setMinStrokeWidth(1.5f);
        ink.setMaxStrokeWidth(6f);
        ink.bringToFront();

        //Bitmap something = ink.getBitmap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_two, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                Bitmap b = getScreenShot(rootView);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        Log.d("View Width:", ""+view.getWidth());
        Log.d("View Height:", ""+view.getHeight());
        bitmap = cropBitmap(bitmap,view);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreImagen = "JPEG_" + timeStamp + "_" + ".jpg";
        store(bitmap,nombreImagen);
        return bitmap;
    }
    private Bitmap cropBitmap(Bitmap bmp2, View view) {
        //Bitmap bmp2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.image1);
        int fromHere = (int) (bmp2.getHeight() * 0.125);
        int fromHereTwo = (int) (bmp2.getHeight() * 0.15);
        int some = (int) (bmp2.getHeight()* 0.875);
        Bitmap bmOverlay = Bitmap.createBitmap(bmp2, 0, fromHere, bmp2.getWidth(), bmp2.getHeight()-fromHere);
        Bitmap croppedBitmap = Bitmap.createBitmap(bmOverlay, 0, 0, bmOverlay.getWidth(), some-fromHereTwo);
        return croppedBitmap;
    }

    public void store(Bitmap bm, String fileName){
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(storageDir, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
            String photoUrl = "file://" + file.getAbsolutePath();
            photoCRUD.newPhoto(new Photo(0,photoCRUD.getCursandos().get(0).getId_cursando(),photoCRUD.getUsuarios().get(0).getId_user(),materia.getId_materia(),photoUrl,photoCRUD.getCursandos().get(0).getHorario()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
