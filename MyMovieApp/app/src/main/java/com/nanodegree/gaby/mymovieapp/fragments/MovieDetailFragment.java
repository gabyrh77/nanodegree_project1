package com.nanodegree.gaby.mymovieapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.gaby.mymovieapp.R;
import com.nanodegree.gaby.mymovieapp.activities.MovieDetailActivity;
import com.nanodegree.gaby.mymovieapp.activities.MovieListActivity;
import com.nanodegree.gaby.mymovieapp.api.MovieResponse;
import com.nanodegree.gaby.mymovieapp.utils.Utils;

import java.text.SimpleDateFormat;


/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private MovieResponse mMovie;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
           mMovie = getArguments().getParcelable(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        if(savedInstanceState!=null){
            mMovie = savedInstanceState.getParcelable(ARG_ITEM_ID);
        }
        if (mMovie != null) {
            ((TextView) rootView.findViewById(R.id.movie_title)).setText(mMovie.getOriginalTitle());
            ((TextView) rootView.findViewById(R.id.movie_release_date)).setText(Utils.getFormattedDateString(mMovie.getReleaseDate()));
            ((TextView) rootView.findViewById(R.id.movie_synopsis)).setText(mMovie.getOverview());
            ((TextView) rootView.findViewById(R.id.movie_rating)).setText(String.format("%.1f/10", mMovie.getVoteAverage()));
            ((TextView) rootView.findViewById(R.id.movie_popularity)).setText(String.format("%.2f/100", mMovie.getPopularity()));
            ImageView image = (ImageView) rootView.findViewById(R.id.movie_poster);
            Utils.getPosterThumbnail(mMovie.getPosterPath(), getContext(), image);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_ITEM_ID, mMovie);
    }
}
