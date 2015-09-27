package com.nanodegree.gaby.mymovieapp.api;

import com.nanodegree.gaby.mymovieapp.utils.MySharedPreferences;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by Gaby on 9/26/2015.
 */
public class MovieService {
    private static final String API_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "";
    public static final String POPULARITY_QUERY_PARAM = "popularity.desc";
    public static final String RATING_QUERY_PARAM = "vote_average.desc";


    public static Observable<DiscoverMovieResponse> getMoviesByOrder(int order){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        MovieAPI api = retrofit.create(MovieAPI.class);

        String orderQuery;
        if(order == MySharedPreferences.ORDER_BY_POPULARITY){
            orderQuery = POPULARITY_QUERY_PARAM;
        }else{
            orderQuery = RATING_QUERY_PARAM;
        }

        return api.listMoviesWithOrder(API_KEY, orderQuery);
    }
}
