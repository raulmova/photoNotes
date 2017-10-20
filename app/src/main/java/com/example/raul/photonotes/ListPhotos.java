package com.example.raul.photonotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.RecycleViewCustomAdapter;
import DB.Modelo.Photo;
import DB.Modelo.PhotosCRUD;

public class ListPhotos extends AppCompatActivity {

    private RecyclerView rvListPhotos;
    protected RecycleViewCustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private PhotosCRUD crud;
    private ArrayList<Photo> photos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos);
        //int materia = getIntent().getIntExtra("idMateria",0);
        int mater = getIntent().getIntExtra("idMateria",1);
        //String mat = getIntent().getStringExtra("idMateria");
        //int mate = Integer.parseInt(mat);
        rvListPhotos = (RecyclerView) findViewById(R.id.rvListPhotos);
        Log.d("IDMat fotos por mat:",""+mater);

        rvListPhotos.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        rvListPhotos.setLayoutManager(mLayoutManager);

        photos = new ArrayList<Photo>();

        crud = new PhotosCRUD(getApplicationContext());
       // photos = crud.getPhotos();
        photos = crud.getPhotos(mater);
        //crud.getMaterias(materia);


        RecycleViewCustomAdapter adapter = new RecycleViewCustomAdapter(getApplicationContext(),photos, new Adapters.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });

        rvListPhotos.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
