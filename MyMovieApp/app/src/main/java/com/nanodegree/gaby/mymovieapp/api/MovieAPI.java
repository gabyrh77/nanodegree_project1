package com.nanodegree.gaby.mymovieapp.api;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Gaby on 9/26/2015.
 */
public interface MovieAPI {

    static final String MOVIE_PATH = "discover/movie";

    @GET(MOVIE_PATH)
    Observable<DiscoverMovieResponse> listMoviesWithOrder(@Query("api_key") String api, @Query("sort_by") String sort);
}
