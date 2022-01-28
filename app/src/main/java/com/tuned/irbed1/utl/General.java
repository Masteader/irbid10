package com.tuned.irbed1.utl;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.CursorLoader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ADMIN on 8/21/2016.
 */
public class General {

    public static  void addToSharedPreference(Context c, String key, String value) {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor e=sp.edit();
        e.putString(key,value);
        e.commit();
    }

    public static void addToLongPreference(Context c, String key, long value) {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor e=sp.edit();
        e.putLong(key,value);
        e.commit();
    }


    public static void addToSharedPreference(Context c, String key, boolean value) {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor e=sp.edit();
        e.putBoolean(key, value);
        e.commit();
    }

    public static void addToSharedPreference(Context c, String key, int value) {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor e=sp.edit();
        e.putInt(key, value);
        e.commit();
    }

    public static String getPreferenceValue(Context c, String key, String defaultValue) {
        String result;
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(c);
        result= sp.getString(key, defaultValue);
        return result;
    }


    public static int getIntegerPreferenceValue(Context c, String key, int defaultValue) {
        int result=0;
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(c);
        result= sp.getInt(key, defaultValue);
        return result;
    }


    public static long getLongPreferenceValue(Context c, String key, long defaultValue) {
        long result=0;
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(c);
        result= sp.getLong(key, defaultValue);
        return result;
    }

    public static boolean getBooleanPreferenceValue(Context c, String key, boolean defaultValue) {
        boolean result;
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(c);
        result= sp.getBoolean(key,defaultValue);
        return result;
    }


    public static void goToActivity(Context c, Class NewActivity) {
        Intent i=new Intent(c,NewActivity);
        c.startActivity(i);
    }

    public static void setActionBarWithoutTitle(AppCompatActivity appCompatActivity, Toolbar toolbar,boolean displayHome){
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(displayHome);
    }

    public static void setActionBarWithTitle(AppCompatActivity appCompatActivity, Toolbar toolbar,String title,boolean displayHome){
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        appCompatActivity.setTitle(title);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(displayHome);
    }
    public static void openUrl(Context context,String url){
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)));
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void share(Context context,String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public static String getVideoId(String videoUrl) {
        String expression = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        if (videoUrl == null || videoUrl.trim().length() <= 0){
            return "null";
        }
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(videoUrl);
        try {
            if (matcher.find())
                return matcher.group();
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        return "null";
    }

    public static String getRealPathFromURI(Context context,Uri contentUri) {
        try {
            String[] proj = {
                    MediaStore.Images.Media.DATA
            };
            CursorLoader loader = new CursorLoader(context.getApplicationContext(), contentUri, proj, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(column_index);
            cursor.close();
            return result;
        }catch (NullPointerException e){
            return contentUri.getPath();
        }
    }

}
