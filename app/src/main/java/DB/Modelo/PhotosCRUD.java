package DB.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import DB.Contract.Photo_Contract;
import DB.DatabaseHelper.DataBaseHelper;

/**
 * Created by Raul on 19/10/2017.
 */


public class PhotosCRUD {
    private DataBaseHelper helper;

    interface Tablas {
        String CURSANDO = "cursando";
        String MATERIA = "materia";
        String PHOTO = "photo";
        String USUARIO= "usuario";

    }


    public PhotosCRUD(Context context) {
        helper = new DataBaseHelper(context);
    }

    public int newUsuario(Usuario usuario) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Photo_Contract.Usuario.ID_USUARIO, usuario.getId_user());
        values.put(Photo_Contract.Usuario.NOMBRE,usuario.getNombre());
        values.put(Photo_Contract.Usuario.CONTRA,usuario.getContra());
        values.put(Photo_Contract.Usuario.CORREO,usuario.getContra());

        long newRowId = db.insert(Tablas.USUARIO,null,values);
        db.close();
        return (int)newRowId;
    }

    public int newMateria(Materia materia) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Photo_Contract.Materia.ID_MATERIA, materia.getId_materia());
        values.put(Photo_Contract.Materia.NOMBRE, materia.getNombre());

        long newRowId = db.insert(Tablas.MATERIA,null,values);
        db.close();
        return (int)newRowId;
    }

    public int newCursando(Cursando cursando) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Photo_Contract.Cursando.ID_CURSANDO,cursando.getId_cursando());
        values.put(Photo_Contract.Cursando.ID_USUARIO,cursando.getId_usuario());
        values.put(Photo_Contract.Cursando.ID_MATERIA,cursando.getId_materia());
        values.put(Photo_Contract.Cursando.NOMBRE,cursando.getNombre());
        values.put(Photo_Contract.Cursando.DESCRIPCION,cursando.getDescripcion());
        values.put(Photo_Contract.Cursando.HORARIO,cursando.getHorario());
        values.put(Photo_Contract.Cursando.HORA_ENTRADA, cursando.getHora_entrada());
        values.put(Photo_Contract.Cursando.HORARIO_SALIDA, cursando.getHora_salida());

        long newRowId = db.insert(Tablas.CURSANDO,null,values);
        db.close();
        return (int)newRowId;
    }

    public void newPhoto(Photo photo) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Photo_Contract.Photos.ID_PHOTOS,photo.getId_photos());
        values.put(Photo_Contract.Photos.ID_CURSANDO,photo.getId_cursando());
        values.put(Photo_Contract.Photos.ID_USUARIO,photo.getId_usuario());
        values.put(Photo_Contract.Photos.ID_MATERIA,photo.getId_materia());
        values.put(Photo_Contract.Photos.PATH,photo.getPath());
        values.put(Photo_Contract.Photos.FECHA,photo.getFecha());

        long newRowId = db.insert(Tablas.PHOTO,null,values);
        db.close();
    }

    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

        String []columnas = {
                Photo_Contract.Usuario.ID_USUARIO,
                Photo_Contract.Usuario.NOMBRE,
                Photo_Contract.Usuario.CONTRA,
                Photo_Contract.Usuario.CORREO
        };

        Cursor cursor = sqLiteDatabase.query(
                Tablas.USUARIO,
                columnas,
                null, //texto para filtrar
                null, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        while (cursor.moveToNext()) {
            usuarios.add(new Usuario(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.ID_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.NOMBRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.CONTRA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.CORREO))
            ));
        }
        return usuarios;
    }

    public ArrayList<Materia> getMaterias() {
        ArrayList<Materia> materias = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

        String []columnas = {
                Photo_Contract.Materia.ID_MATERIA,
                Photo_Contract.Materia.NOMBRE
        };

        Cursor cursor = sqLiteDatabase.query(
                Tablas.MATERIA,
                columnas,
                null, //texto para filtrar
                null, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        while (cursor.moveToNext()) {
            materias.add(new Materia(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Materia.ID_MATERIA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Materia.NOMBRE))
            ));
        }
        return materias;
    }


    public ArrayList<Materia> getMaterias(int id) {
        ArrayList<Materia> materias = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

        String []columnas = {
                Photo_Contract.Materia.ID_MATERIA,
                Photo_Contract.Materia.NOMBRE
        };

        Cursor cursor = sqLiteDatabase.query(
                Tablas.MATERIA,
                columnas,
                Photo_Contract.Materia.ID_MATERIA + " = ?", //texto para filtrar
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        while (cursor.moveToNext()) {
            materias.add(new Materia(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Materia.ID_MATERIA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Materia.NOMBRE))
            ));
        }
        return materias;
    }

    public ArrayList<Cursando> getCursandos() {
        ArrayList<Cursando> cursandos = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

        String []columnas = {
                Photo_Contract.Cursando.ID_CURSANDO,
                Photo_Contract.Cursando.ID_USUARIO,
                Photo_Contract.Cursando.ID_MATERIA,
                Photo_Contract.Cursando.NOMBRE,
                Photo_Contract.Cursando.DESCRIPCION,
                Photo_Contract.Cursando.HORARIO,
                Photo_Contract.Cursando.HORA_ENTRADA,
                Photo_Contract.Cursando.HORARIO_SALIDA
        };

        Cursor cursor = sqLiteDatabase.query(
                Tablas.CURSANDO,
                columnas,
                null, //texto para filtrar
                null, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        while (cursor.moveToNext()) {
            cursandos.add(new Cursando(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.ID_CURSANDO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.ID_USUARIO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.ID_MATERIA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.NOMBRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.DESCRIPCION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.HORARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.HORA_ENTRADA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.HORARIO_SALIDA))
            ));
        }
        return cursandos;
    }

    public ArrayList<Photo> getPhotos() {
        ArrayList<Photo> photos = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

        String []columnas = {
                Photo_Contract.Photos.ID_PHOTOS,
                Photo_Contract.Photos.ID_CURSANDO,
                Photo_Contract.Photos.ID_USUARIO,
                Photo_Contract.Photos.ID_MATERIA,
                Photo_Contract.Photos.PATH,
                Photo_Contract.Photos.FECHA,

        };

        Cursor cursor = sqLiteDatabase.query(
                Tablas.PHOTO,
                columnas,
                null, //texto para filtrar
                null, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        while (cursor.moveToNext()) {
            photos.add(new Photo(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_PHOTOS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_CURSANDO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_USUARIO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_MATERIA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.FECHA))
            ));
        }
        return photos;
    }

    public ArrayList<Photo> getPhotos(int id) {
        ArrayList<Photo> photos = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

        String []columnas = {
                Photo_Contract.Photos.ID_PHOTOS,
                Photo_Contract.Photos.ID_CURSANDO,
                Photo_Contract.Photos.ID_USUARIO,
                Photo_Contract.Photos.ID_MATERIA,
                Photo_Contract.Photos.PATH,
                Photo_Contract.Photos.FECHA,

        };

        Cursor cursor = sqLiteDatabase.query(
                Tablas.PHOTO,
                columnas,
                Photo_Contract.Photos.ID_MATERIA + " = ?", //texto para filtrar
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        while (cursor.moveToNext()) {
            photos.add(new Photo(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_PHOTOS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_CURSANDO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_USUARIO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_MATERIA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.FECHA))
            ));
        }
        return photos;
    }

    public void updateUsuario (Usuario usuario) {
        //Obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombrre de columna
        ContentValues values = new ContentValues();
        values.put(Photo_Contract.Usuario.ID_USUARIO,usuario.getId_user());
        values.put(Photo_Contract.Usuario.NOMBRE,usuario.getNombre());
        values.put(Photo_Contract.Usuario.CONTRA,usuario.getContra());
        values.put(Photo_Contract.Usuario.CORREO,usuario.getCorreo());

        //TODO 19: Actualizamos fila
        //Insterta la nueva fila, regresando el valor de la primary key
        sqLiteDatabase.update(
                Tablas.USUARIO,
                values,
                Photo_Contract.Usuario.ID_USUARIO+" = ?",
                new String[]{String.valueOf(usuario.getId_user())}
        );

        //cierra conexion
        sqLiteDatabase.close();
    }

    public void updateMateria (Materia materia) {
        //Obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombrre de columna
        ContentValues values = new ContentValues();
        values.put(Photo_Contract.Materia.ID_MATERIA,materia.getId_materia());
        values.put(Photo_Contract.Materia.NOMBRE,materia.getNombre());


        //TODO 19: Actualizamos fila
        //Insterta la nueva fila, regresando el valor de la primary key
        sqLiteDatabase.update(
                Tablas.MATERIA,
                values,
                Photo_Contract.Materia.ID_MATERIA + " = ?",
                new String[]{String.valueOf(materia.getId_materia())}
        );

        //cierra conexion
        sqLiteDatabase.close();
    }

    public void updateCursando (Cursando cursando) {
        //Obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombrre de columna
        ContentValues values = new ContentValues();
        values.put(Photo_Contract.Cursando.ID_CURSANDO,cursando.getId_cursando());
        values.put(Photo_Contract.Cursando.ID_USUARIO,cursando.getId_usuario());
        values.put(Photo_Contract.Cursando.ID_MATERIA,cursando.getId_materia());
        values.put(Photo_Contract.Cursando.NOMBRE,cursando.getNombre());
        values.put(Photo_Contract.Cursando.DESCRIPCION,cursando.getDescripcion());
        values.put(Photo_Contract.Cursando.HORARIO,cursando.getHorario());
        values.put(Photo_Contract.Cursando.HORA_ENTRADA,cursando.getHora_entrada());
        values.put(Photo_Contract.Cursando.HORARIO_SALIDA,cursando.getHora_salida());

        //TODO 19: Actualizamos fila
        //Insterta la nueva fila, regresando el valor de la primary key
        sqLiteDatabase.update(
                Tablas.CURSANDO,
                values,
                Photo_Contract.Cursando.ID_CURSANDO + " = ?",
                new String[]{String.valueOf(cursando.getId_cursando())}
        );

        //cierra conexion
        sqLiteDatabase.close();
    }

    public void updatePhoto (Photo photo) {
        //Obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombrre de columna
        ContentValues values = new ContentValues();
        values.put(Photo_Contract.Photos.ID_PHOTOS,photo.getId_photos());
        values.put(Photo_Contract.Photos.ID_CURSANDO, photo.getId_cursando());
        values.put(Photo_Contract.Photos.ID_USUARIO, photo.getId_usuario());
        values.put(Photo_Contract.Photos.ID_MATERIA, photo.getId_materia());
        values.put(Photo_Contract.Photos.PATH,photo.getPath());
        values.put(Photo_Contract.Photos.FECHA, photo.getFecha());

        //TODO 19: Actualizamos fila
        //Insterta la nueva fila, regresando el valor de la primary key
        sqLiteDatabase.update(
                Tablas.PHOTO,
                values,
                Photo_Contract.Photos.ID_PHOTOS + " = ?",
                new String[]{String.valueOf(photo.getId_photos())}
        );

        //cierra conexion
        sqLiteDatabase.close();
    }

    public void deleteUsuario(Usuario usuario) {
        //obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //TODO 20: Eliminamos fila
        //inserta la nueva, regresando el valor de la primary key
        sqLiteDatabase.delete(
                Tablas.USUARIO,
                Photo_Contract.Usuario.ID_USUARIO+" = ?",
                new String[]{String.valueOf(usuario.getId_user())}
        );

        sqLiteDatabase.close();
    }

    public void deleteMateria(Materia materia) {
        //obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //TODO 20: Eliminamos fila
        //inserta la nueva, regresando el valor de la primary key
        sqLiteDatabase.delete(
                Tablas.MATERIA,
                Photo_Contract.Materia.ID_MATERIA+" = ?",
                new String[]{String.valueOf(materia.getId_materia())}
        );

        sqLiteDatabase.close();
    }

    public void deleteCursando(Cursando cursando) {
        //obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //TODO 20: Eliminamos fila
        //inserta la nueva, regresando el valor de la primary key
        sqLiteDatabase.delete(
                Tablas.CURSANDO,
                Photo_Contract.Cursando.ID_CURSANDO +" = ?",
                new String[]{String.valueOf(cursando.getId_cursando())}
        );

        sqLiteDatabase.close();
    }

    public void deletePhoto(Photo photo) {
        //obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //TODO 20: Eliminamos fila
        //inserta la nueva, regresando el valor de la primary key
        sqLiteDatabase.delete(
                Tablas.PHOTO,
                Photo_Contract.Photos.ID_PHOTOS +" = ?",
                new String[]{String.valueOf(photo.getId_photos())}
        );

        sqLiteDatabase.close();
    }

    public Usuario selectUsuario(int id) {

        String nombre = "";
        String contra = "";
        String correo = "";
        Usuario usuario = new Usuario(id,nombre,contra,correo);
        SQLiteDatabase db = helper.getReadableDatabase();
        String []columnas = {
                Photo_Contract.Usuario.ID_USUARIO,
                Photo_Contract.Usuario.NOMBRE,
                Photo_Contract.Usuario.CONTRA,
                Photo_Contract.Usuario.CORREO
        };
        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = db.query(
                Tablas.USUARIO,
                columnas,
                Photo_Contract.Usuario.ID_USUARIO + " = ?", //texto para filtrar
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        //TODO 16: Se recorren los resultados y se añaden a la lista
        while (cursor.moveToNext()) {
            usuario = new Usuario(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.ID_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.NOMBRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.CONTRA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.CORREO))
            );
        }

        cursor.close();
        db.close();
        return usuario;
    }

    public Usuario selectUsuario(String name) {

        String nombre = "";
        String contra = "";
        String correo = "";
        Usuario usuario = new Usuario(0,nombre,contra,correo);
        SQLiteDatabase db = helper.getReadableDatabase();
        String []columnas = {
                Photo_Contract.Usuario.ID_USUARIO,
                Photo_Contract.Usuario.NOMBRE,
                Photo_Contract.Usuario.CONTRA,
                Photo_Contract.Usuario.CORREO
        };
        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = db.query(
                Tablas.USUARIO,
                columnas,
                Photo_Contract.Usuario.NOMBRE + " = ?", //texto para filtrar
                new String[]{name}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        //TODO 16: Se recorren los resultados y se añaden a la lista
        while (cursor.moveToNext()) {
            usuario = new Usuario(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.ID_USUARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.NOMBRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.CONTRA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Usuario.CORREO))
            );
        }

        cursor.close();
        db.close();
        return usuario;
    }

    public boolean usuarioExists(String name) {

        String nombre = "";
        String contra = "";
        String correo = "";
        Usuario usuario = new Usuario(0,nombre,contra,correo);
        SQLiteDatabase db = helper.getReadableDatabase();
        String []columnas = {
                Photo_Contract.Usuario.ID_USUARIO,
                Photo_Contract.Usuario.NOMBRE,
                Photo_Contract.Usuario.CONTRA,
                Photo_Contract.Usuario.CORREO
        };
        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = db.query(
                Tablas.USUARIO,
                columnas,
                Photo_Contract.Usuario.NOMBRE + " = ?", //texto para filtrar
                new String[]{name}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        if(cursor.getCount() <= 0){
            cursor.close();
            db.close();
            return false;
        }
            cursor.close();
            db.close();
            return true;
    }

    public Materia selectMateria(int id) {

        String nombre="";
        String descripcion="";
        Materia materia = new Materia(id,nombre);
        SQLiteDatabase db = helper.getReadableDatabase();
        String []columnas = {
                Photo_Contract.Materia.ID_MATERIA,
                Photo_Contract.Materia.NOMBRE,
        };

        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = db.query(
                Tablas.MATERIA,
                columnas,
                Photo_Contract.Materia.ID_MATERIA + " = ?", //texto para filtrar
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        //TODO 16: Se recorren los resultados y se añaden a la lista
        while (cursor.moveToNext()) {
            materia = new Materia(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Materia.ID_MATERIA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Materia.NOMBRE))
            );
        }

        cursor.close();
        db.close();
        return materia;
    }

    public Materia selectMateria(String name) {

        String nombre="";
        String descripcion="";
        Materia materia = new Materia(0,nombre);
        SQLiteDatabase db = helper.getReadableDatabase();
        String []columnas = {
                Photo_Contract.Materia.ID_MATERIA,
                Photo_Contract.Materia.NOMBRE,
        };

        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = db.query(
                Tablas.MATERIA,
                columnas,
                Photo_Contract.Materia.NOMBRE + " = ?", //texto para filtrar
                new String[]{name}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        //TODO 16: Se recorren los resultados y se añaden a la lista
        while (cursor.moveToNext()) {
            materia = new Materia(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Materia.ID_MATERIA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Materia.NOMBRE))
            );
        }

        cursor.close();
        db.close();
        return materia;
    }

    public ArrayList<Cursando> selectCursandoArray(int id, String field) {
        //TODO 14: Crear una lista para almacenar elementos, llamamos Db y definimos columnas
        ArrayList<Cursando> cursandos = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        //Especificamos las columnas a usar
        String []columnas = {
                Photo_Contract.Cursando.ID_CURSANDO,
                Photo_Contract.Cursando.ID_USUARIO,
                Photo_Contract.Cursando.ID_MATERIA,
                Photo_Contract.Cursando.NOMBRE,
                Photo_Contract.Cursando.DESCRIPCION,
                Photo_Contract.Cursando.HORARIO,
                Photo_Contract.Cursando.HORA_ENTRADA,
                Photo_Contract.Cursando.HORARIO_SALIDA
        };


        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = sqLiteDatabase.query(
                Tablas.CURSANDO,
                columnas,
               /*Photo_Contract.Cursando.ID_CURSANDO+" = ?", //texto para filtrar*/
                field + " = ?",
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        //TODO 16: Se recorren los resultados y se añaden a la lista
        while (cursor.moveToNext()) {
            cursandos.add(new Cursando(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.ID_CURSANDO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.ID_USUARIO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.ID_MATERIA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.NOMBRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.DESCRIPCION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.HORARIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.HORA_ENTRADA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Cursando.HORARIO_SALIDA))
            ));
        }

        //TODO 17: Cerramos conexión y regresamos elementos
        cursor.close();
        return cursandos;

    }

    public ArrayList<Photo> selectPhotoArray(int id, String field) {
        //TODO 14: Crear una lista para almacenar elementos, llamamos Db y definimos columnas
        ArrayList<Photo> photos = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        //Especificamos las columnas a usar
        String []columnas = {
                Photo_Contract.Photos.ID_PHOTOS,
                Photo_Contract.Photos.ID_CURSANDO,
                Photo_Contract.Photos.ID_USUARIO,
                Photo_Contract.Photos.ID_MATERIA,
                Photo_Contract.Photos.PATH,
                Photo_Contract.Photos.FECHA,

        };

        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = sqLiteDatabase.query(
                Tablas.PHOTO,
                columnas,
                /*Photo_Contract.Photos.ID_PHOTOS + " = ?", //texto para filtrar*/
                field + " = ?",
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        //TODO 16: Se recorren los resultados y se añaden a la lista
        while (cursor.moveToNext()) {
            photos.add(new Photo(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_PHOTOS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_CURSANDO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_USUARIO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.ID_MATERIA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Photo_Contract.Photos.FECHA))
            ));
        }

        //TODO 17: Cerramos conexión y regresamos elementos
        cursor.close();
        return photos;

    }

}
