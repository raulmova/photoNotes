package Azure;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.ArrayList;

import DB.Modelo.Cursando;
import DB.Modelo.Materia;
import DB.Modelo.Photo;
import DB.Modelo.Usuario;

/**
 * Created by pacod on 17/11/2017.
 */

public class CRUDAzure {



    private MobileServiceClient mClient;
    private MobileServiceTable<Cursando> tablaCursando;
    public MobileServiceTable<Materia> tablaMateria;
    private MobileServiceTable<Photo> tablaPhoto;
    private MobileServiceTable<Usuario> tablaUsuario;
    private Activity activity;




    private ArrayList<Cursando> itemsCursando;
    public ArrayList<Materia> itemsMateria;
    private ArrayList<Photo> itemsPhoto;
    private ArrayList<Usuario> itemsUsuario;



    public CRUDAzure(Activity activity) {

        this.activity= activity;
    }



    public void initAzureClient(){

            try {
                mClient= new MobileServiceClient("https://photonotesbeta.azurewebsites.net",activity);
                tablaMateria = mClient.getTable(Materia.class);

            }
            catch (MalformedURLException e){
                Log.d("InitClientAzure", e.getMessage());

            }

    }




/*
* Metodos para insertar en la base de datos Global
* */
    public void newMateria(final Materia materia){

        new AsyncTask<Void, Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                Materia res= null;
                try {
                    res = mClient.getTable(Materia.class).insert(materia).get();
                    itemsMateria.add(res);
                    activity.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {

                        }
                    });
                }
                catch (Exception e){
                    Log.d("nuevo Item", e.getMessage());
                }
                return null;
            }
        }.execute();

    }
    public void newCursando(final Cursando cursando){

        new AsyncTask<Void, Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                Cursando res= null;
                try {
                    res = mClient.getTable(Cursando.class).insert(cursando).get();
                    itemsCursando.add(res);
                    activity.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {

                        }
                    });
                }
                catch (Exception e){
                    Log.d("nuevo Item", e.getMessage());
                }
                return null;
            }
        }.execute();

    }
    public void newPhoto(final Photo photo){

        new AsyncTask<Void, Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                Photo res= null;
                try {
                    res = mClient.getTable(Photo.class).insert(photo).get();
                    itemsPhoto.add(res);
                    activity.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {

                        }
                    });
                }
                catch (Exception e){
                    Log.d("nuevo Item", e.getMessage());
                }
                return null;
            }
        }.execute();

    }

    public void newUsuario(final Usuario usuario){

        new AsyncTask<Void, Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                Usuario res= null;
                try {
                    res = mClient.getTable(Usuario.class).insert(usuario).get();
                    itemsUsuario.add(res);
                    activity.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {

                        }
                    });
                }
                catch (Exception e){
                    Log.d("nuevo Item", e.getMessage());
                }
                return null;
            }
        }.execute();

    }


    /*
    * MEtodos para Obtener datos de la base de datos de Azure
    *
    * */

    public void obtenerMateria(){

        new AsyncTask<Void, Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    //itemsMateria.clear();
                    ArrayList<Materia> query=tablaMateria.execute().get();


                    for(Materia item: query){
                        Log.d("ObtenerITems",item.getNombre());
                        itemsMateria.add(item);
                    }
                }
                catch (Exception e){
                    Log.d("Obtener Items", e.getMessage());
                }
                activity.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {

                    }
                });
                return null;
            }
        }.execute();
    }





}
