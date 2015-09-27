package com.nanodegree.gaby.mymovieapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nanodegree.gaby.mymovieapp.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gaby on 9/26/2015.
 */
public class Utils {
    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w342/";

    public static void getPosterThumbnail(String posterString, Context context, ImageView imageView){
        Picasso.with(context).load(POSTER_URL + posterString).placeholder(R.drawable.movie_placeholder).into(imageView);

    }

    public static String getFormattedDateString(String dateString){
        String resultDateString = "";
        if(dateString!=null && !dateString.isEmpty()) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                resultDateString = new SimpleDateFormat("MMMM yyyy").format(date);
                if (resultDateString!=null && !resultDateString.isEmpty()){
                    resultDateString = resultDateString.substring(0, 1).toUpperCase() + resultDateString.substring(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultDateString;
    }
}
