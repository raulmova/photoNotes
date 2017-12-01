package com.example.raul.photonotes;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import DB.Modelo.Photo;

/**
 * Created by cocoy on 19/10/2017.
 */

public class Metadatos {


                public ArrayList<Photo> getAllShownImagesPath(Activity activity,String dateClass, String horaInit, String horafin) {
        //TODO: DATES
        String stringDate = "2017:08:01 00:00:00";
        DateFormat format = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        Date dateInitMeta, dateAugust;
        int dayOfTheWeek=0,dayOfTheWeek2=0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name, column_index_date;
        ArrayList<Photo> listOfAllImages = new ArrayList<Photo>();
        String absolutePathOfImage = null;

        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");


        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        //TODO: INITIAL DATE August 1,2017

        if(dateClass.contains("Lunes") && dateClass.contains("Jueves")){
            dayOfTheWeek = 1;
            dayOfTheWeek2 = 4;
        } else  if(dateClass.contains("Jueves")){
            dayOfTheWeek = 4;
        }
        if(dateClass.contains("Martes") && dateClass.contains("Viernes")){
            dayOfTheWeek = 2;
            dayOfTheWeek2 = 5;
        }
        if(dateClass.contains("Miercoles")){
            dayOfTheWeek = 3;
        }


        while (cursor.moveToNext()) {
            String folder = cursor.getString(column_index_folder_name);
            if(folder.contains("CAMERA") || folder.contains("amera") || folder.contains("100MEDIA")) {
                absolutePathOfImage = cursor.getString(column_index_data);
                //Log.d("folder name: ", cursor.getString(column_index_folder_name));
                String datemeta = getExifDate(absolutePathOfImage);
                if(datemeta != null) {
                    try {
                        int day = 0;
                        //TODO PARSEAE LAS FECHAS
                        //fecha foto
                        //Log.d("datemeta: ",datemeta);
                        dateInitMeta = format.parse(datemeta);
                        //day = Integer.parseInt(dayOfWeek);
                        //fecha a comparar - 2017:08:01
                        dateAugust = format.parse(stringDate);

                        if(dateInitMeta.after(dateAugust)) {
                            //TODO: SACAR DIA DE LA SEMANA DE LA FOTO
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(dateInitMeta);

                            day = cal.get(Calendar.DAY_OF_WEEK) - 1;
                            int hourphoto = cal.get(Calendar.HOUR_OF_DAY);
                            int minutephoto = cal.get(Calendar.MINUTE);

                            //TODO: PARSEAR LAS HORAS
                            Date TimeInit = parser.parse(horaInit);
                            Date TimeEnd = parser.parse(horafin);
                            Date TimePhoto = parser.parse(hourphoto + ":" + minutephoto);
                            //Log.d("Dias: ", dayOfTheWeek + " " + dayOfTheWeek2 + " " + day);
                            //Log.d("time: ", TimePhoto.toString() + " " + TimeInit.toString() + " " + TimeEnd.toString());

                            if (dayOfTheWeek == day) {
                                if (TimePhoto.after(TimeInit) && TimePhoto.before(TimeEnd)) {
                                    //  Log.d("Fecha: ", absolutePathOfImage + " " + day  );
                                    listOfAllImages.add(new Photo(absolutePathOfImage, datemeta));
                                }
                            }
                            if (dayOfTheWeek2 != 0 && dayOfTheWeek2 == day) {
                                if (TimePhoto.after(TimeInit) && TimePhoto.before(TimeEnd)) {
                                    //Log.d("Fecha: ", absolutePathOfImage + " " + day  );
                                    listOfAllImages.add(new Photo(absolutePathOfImage, datemeta));
                                }
                            }

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        ArrayList<Photo> temp = new ArrayList<>();

        for(int i = listOfAllImages.size(); i>0 ;i--) {

            temp.add(listOfAllImages.get(i-1));
        }
        return temp;
    }
        
    public ArrayList<String> getAllShownImagesPath(Activity activity) {
        //TODO: DATES
        String stringDate = "2017:08:01 00:00:00";
        DateFormat format = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        Date dateInit, dateFirst;

        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name, column_index_date;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        //uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        File file = new File("/storage/emulated/0/DCIM/");
        Log.d("check if folder exists:",file.exists() + "");
        //uri = Uri.fromFile(file);
        //uri = MediaStore.Images.Media.getContentUri("internal");
        Log.d("uri: ", uri.toString());

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        //column_index_date = cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED);
        //TODO: INITIAL DATE August 1,2017

        while (cursor.moveToNext()) {
            String folder = cursor.getString(column_index_folder_name);
            if(folder.contains("CAMERA") || folder.contains("camera") || folder.contains("100MEDIA")) {
                absolutePathOfImage = cursor.getString(column_index_data);
                //Log.d("folder name: ", cursor.getString(column_index_folder_name));
                String datemeta = getExifDate(absolutePathOfImage)+"";
                //Log.i("dated: ",datemeta);

                try {
                    //fecha foto
                    dateInit = format.parse(datemeta);
                    //fecha a comparar - 2017:08:01
                    dateFirst = format.parse(stringDate);
                    if(dateInit.after(dateFirst))
                        listOfAllImages.add(absolutePathOfImage);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                /*Log.d("date: ", cursor.getColumnIndex(
                        MediaStore.Images.Media.DATE_TAKEN) + "of photo" + cursor.getColumnIndex(
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME));*/
                //getExifDate(absolutePathOfImage);

            }
        }
        //listOfAllImages.add("/storage/emulated/0/DCIM/100MEDIA/IMAG0882.jpg");
        //Log.d("list of images: ", listOfAllImages.size()+"");
        ArrayList<String> temp = new ArrayList<>();

        for(int i = listOfAllImages.size(); i>0 ;i--) {
            temp.add(listOfAllImages.get(i-1));
        }
        return temp;
    }


    private String getRealPathFromURI(Uri contentURI, Activity activity) {
        String result;
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;

    }


    public String getExifDate(String filePath) {
        ExifInterface intf = null;
        String dateString ="";
        try {
            intf = new ExifInterface(filePath);
            if (intf != null) {
                dateString = intf.getAttribute(ExifInterface.TAG_DATETIME);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(dateString == "") {
                dateString = "2017:01:01 12:00:00";
            }
        }

        /*if (intf == null) {
            Date lastModDate = new Date(file.lastModified());
            Log.d("Dated : ", lastModDate.toString());//Dispaly lastModDate. You can do/use it your own way
        }*/

        return dateString;
    }
}
