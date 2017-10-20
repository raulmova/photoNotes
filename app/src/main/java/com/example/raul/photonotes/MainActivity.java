package com.example.raul.photonotes;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.internal.Utility;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Adapters.RecycleViewCustomAdapterCourses;
import DB.Modelo.Cursando;
import DB.Modelo.Materia;
import DB.Modelo.Photo;
import DB.Modelo.PhotosCRUD;
import DB.Modelo.Usuario;
import Fragments.CalendarFragment;
import Fragments.HomeFragment;
import Fragments.SubjectFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener,
        SubjectFragment.OnFragmentInteractionListener, CalendarFragment.OnFragmentInteractionListener{

    private static final int SELECT_PHOTO = 100;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static String photoUrl;
    private static final int REQUEST_CAMERA = 200;
    private static final String FILE_PROVIDER = "com.example.raul.photonotes.fileprovider";
    String userID;
    ImageView ivProfile, ivCover;
    TextView tvName, tvDetail;
    Uri profile;
    Profile p, p2;
    private int nav_header_main;
    String URL;
    String finalCoverPhoto;
    FloatingActionButton fab;
    public static int navItemIndex = 0;
    PhotosCRUD photoCRUD;

    private static final String TAG_HOME = "home";
    private static final String TAG_SUBJECTS = "subjects";
    private static final String TAG_CALENDAR = "calendar";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private  DrawerLayout drawer;
    private NavigationView navigationView;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        photoCRUD = new PhotosCRUD(getApplicationContext());
        Log.d("PROFILE: ", Profile.getCurrentProfile().getName());

        p2 = Profile.getCurrentProfile();

        if(!photoCRUD.usuarioExists(p2.getName())){
            photoCRUD.newUsuario(new Usuario(0,p2.getName(),p2.getFirstName(),p2.getLastName()));
            Log.d("USER:","nuevo");
        }
        else{
            Log.d("USER:","existe");
        }


        //photoCRUD.newMateria(new Materia(1,"Programación Avanzada"));
        //photoCRUD.newUsuario(new Usuario(1, "Raul2", "Admin", "raul@raul.com"));
        //photoCRUD.newCursando(new Cursando(1,1,1,"Programación Avanzada","Bla Bla","Miercoles","7:00","8:30"));

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        mHandler = new Handler();

        fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                validarPermisosCamara();
            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        ivCover = (ImageView)hView.findViewById(R.id.ivCover);

        /* COVER PHOTO*/

        AccessToken token = AccessToken.getCurrentAccessToken();
        URL = "https://graph.facebook.com/" + token.getUserId() + "?fields=cover&access_token=" + token.getToken();
        Log.d("String ID: ",URL);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject JODetails = new JSONObject(response);

                            if (JODetails.has("cover")) {
                                String getInitialCover = JODetails.getString("cover");

                                if (getInitialCover.equals("null")) {
                                    finalCoverPhoto = null;
                                } else {
                                    JSONObject JOCover = JODetails.optJSONObject("cover");

                                    if (JOCover.has("source")) {
                                        finalCoverPhoto = JOCover.getString("source");
                                        Log.d("COVER: ", finalCoverPhoto);
                                        Picasso.with(getApplicationContext()).load(finalCoverPhoto).into(ivCover);
                                    } else {
                                        finalCoverPhoto = null;
                                    }
                                }
                            }
                        }
                        catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON:" + response);
                            Toast.makeText(getApplicationContext(), " Error retrieving data " , Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", "Bad");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        /*//////////////////////////////////////////////*/
        ivProfile = (ImageView)hView.findViewById(R.id.ivProfile);
        tvName = (TextView)hView.findViewById(R.id.tvName);
        tvDetail = (TextView)hView.findViewById(R.id.tvDetail);
        p = new Profile(token.getUserId(),null,null,null,null,null);
        profile = p.getProfilePictureUri(202,202);
        Picasso.with(this).load(profile.toString()).into(ivProfile);

        tvName.setText(p2.getFirstName());
        String middleName;
        if(p2.getMiddleName() == "null") {
            middleName = "";
        }
        else
        {
            middleName = p2.getMiddleName();
        }
        tvDetail.setText(middleName +" "+p2.getLastName());

        ///////////////////
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Snackbar.make(this.findViewById(android.R.id.content), "Building", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
        } else if (id == R.id.nav_courses) {
            navItemIndex = 1;
            CURRENT_TAG = TAG_SUBJECTS;
        } else if (id == R.id.nav_calendar) {
            navItemIndex = 2;
            CURRENT_TAG = TAG_CALENDAR;
        }  else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            startActivity(new Intent(MainActivity.this, Login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);

        loadHomeFragment();
        return true;
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // subjects
                SubjectFragment subjectFragment = new SubjectFragment();
                return subjectFragment;
            case 2:
                // subjects
                CalendarFragment calendarFragment = new CalendarFragment();
                return calendarFragment;
            default:
                return new HomeFragment();
        }
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }


    public void validarPermisosStorage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Debemos mostrar un mensaje?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Mostramos una explicación de que no aceptó dar permiso para acceder a la librería
            } else {
                // Pedimos permiso
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);
            }
        }else{
            iniciarIntentSeleccionarFotos();
        }
    }

    private void iniciarIntentSeleccionarFotos() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                // Si la petición se cancela se regresa un arreglo vacío
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido
                    iniciarIntentSeleccionarFotos();
                } else {
                    // Permiso negado
                }
                return;
            // TODO 14.- Validamos el permiso para el acceso a la cámara
            case REQUEST_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Se concedió acceso
                    iniciarIntentTomarFoto();
                } else {
                    // permiso negado
                }
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            //TODO: 8.- Si obtuvimos una imagen entonces la procesamos
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri imagenSeleccionada = imageReturnedIntent.getData();
                    try {
                        InputStream imagenStream = getContentResolver().openInputStream(imagenSeleccionada);
                        Bitmap imagen = BitmapFactory.decodeStream(imagenStream);

                    } catch (FileNotFoundException fnte) {
                        Toast.makeText(this, fnte.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                // TODO 15.- Si obtuvimos la imagen y la guardamos la mostramos
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    /*
                    photoCRUD.newPhoto(new Photo(0,photoCRUD.getCursandos().get(0).getId_cursando(),photoCRUD.getUsuarios().get(0).getId_user(),photoCRUD.getMaterias().get(0).id_materia,photoUrl,photoCRUD.getCursandos().get(0).getHorario()));
                    Fragment frg = null;
                    frg = getSupportFragmentManager().findFragmentByTag(TAG_HOME);
                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();
                    */
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.materiaspinner, null);

                    mBuilder.setTitle("Materia: ");
                    final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);
                    final ArrayAdapter<Materia> adapter = new ArrayAdapter<Materia>(MainActivity.this,
                            android.R.layout.simple_spinner_item, photoCRUD.getMaterias());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner.setAdapter(adapter);
                    mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            photoCRUD.newPhoto(new Photo(0,photoCRUD.getCursandos().get(0).getId_cursando(),photoCRUD.getUsuarios().get(0).getId_user(),photoCRUD.getMaterias().get(0).id_materia,photoUrl,photoCRUD.getCursandos().get(0).getHorario()));
                            Fragment frg = null;
                            frg = getSupportFragmentManager().findFragmentByTag(TAG_HOME);
                            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.detach(frg);
                            ft.attach(frg);
                            ft.commit();
                        }
                    });
                    mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    mBuilder.show();
                }


                return;
        }
    }

    public void validarPermisosCamara() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }
        }else{
            iniciarIntentTomarFoto();
        }
    }

    private  void iniciarIntentTomarFoto(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Validamos que hay una actividad de cámara
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Creamos un nuevo objeto para almacenar la foto
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error creando archivo
            }
            // Si salió bien
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, FILE_PROVIDER, photoFile);
                // Mandamos llamar el intent
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Creamos el archivo
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreImagen = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                nombreImagen,  /* prefijp */
                ".jpg",         /* sufijo */
                storageDir      /* directorio */
        );

        // Obtenemos la URL
        photoUrl = "file://" + image.getAbsolutePath();

       // tvUrl.setText(urlName);
        return image;
    }
}
