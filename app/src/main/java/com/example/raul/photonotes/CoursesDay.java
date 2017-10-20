package com.example.raul.photonotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import Adapters.LanguagesListAdapter1;

public class CoursesDay extends AppCompatActivity {

    ListView lv_languages;


    LanguagesListAdapter1 list_adapter;
    private ArrayList<String> Nombre;
    private ArrayList<String> Fecha;
    private ArrayList<String> Horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_day);
        Intent intent = getIntent();
        final String v_dia = intent.getStringExtra("dia");
        getSupportActionBar().setTitle("Cursos: "+v_dia);





        Nombre = new ArrayList<String>();
        Nombre.add("Bases de Datos Avanzadas");
        Nombre.add("Graficas Computacionales");

        Fecha = new ArrayList<String>();
        Fecha.add("Lunes y Jueves");
        Fecha.add("Martes y Viernes");

        Horario = new ArrayList<String>();
        Horario.add("11:30/1:00");
        Horario.add("8:30/10:00");



        init();
        lv_languages.setAdapter(list_adapter);




    }

    private void init() {

        list_adapter = new LanguagesListAdapter1(getApplicationContext(),Nombre,Fecha,Horario);
        lv_languages = (ListView) findViewById(R.id.lv_languages1);

    }

}