package com.example.raul.photonotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.LanguagesListAdapter1;
import Adapters.RecycleViewCustomAdapterCourses;
import DB.Modelo.Materia;
import DB.Modelo.PhotosCRUD;

public class CoursesDay extends AppCompatActivity {

    ListView lv_languages;
    RecyclerView rvCoursesDay;

    LanguagesListAdapter1 list_adapter;
    private ArrayList<String> Nombre;
    private ArrayList<String> Fecha;
    private ArrayList<String> Horario;
    private  ArrayList<Materia> materiasDia =  new ArrayList<>();
    private PhotosCRUD crud;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_day);
        rvCoursesDay = (RecyclerView) findViewById(R.id.lv_languages1);
        Intent intent = getIntent();
        final String v_dia = intent.getStringExtra("dia");
        int n_dia = intent.getIntExtra("numerodia",0);
        getSupportActionBar().setTitle("Cursos: "+v_dia);
        crud = new PhotosCRUD(getApplicationContext());
        materiasDia = crud.getMateriasDia(n_dia);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCoursesDay.setLayoutManager(mLayoutManager);
        rvCoursesDay.setHasFixedSize(true);


        RecycleViewCustomAdapterCourses adapter = new RecycleViewCustomAdapterCourses(getApplicationContext(),materiasDia, new Adapters.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"Position: " +position, Toast.LENGTH_SHORT);
                Intent inte = new Intent(getApplicationContext(), ListPhotos.class);
                inte.putExtra("idMateria",materiasDia.get(position).getId_materia());
                startActivity(inte);
            }
        });
        rvCoursesDay.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
