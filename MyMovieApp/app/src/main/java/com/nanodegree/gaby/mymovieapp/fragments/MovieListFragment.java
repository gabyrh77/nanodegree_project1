package com.nanodegree.gaby.mymovieapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.nanodegree.gaby.mymovieapp.R;
import com.nanodegree.gaby.mymovieapp.adapters.MovieAdapter;
import com.nanodegree.gaby.mymovieapp.api.DiscoverMovieResponse;
import com.nanodegree.gaby.mymovieapp.api.MovieResponse;
import com.nanodegree.gaby.mymovieapp.api.MovieService;
import com.nanodegree.gaby.mymovieapp.utils.MySharedPreferences;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A list fragment representing a list of Movies. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link MovieDetailFragment}.

 */
public class MovieListFragment extends Fragment {

    public static final int DIALOG_FRAGMENT = 1;
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final String SETTINGS_DIALOG_TAG = "SDF_TAG";
    private SettingsDialogFragment settingsDialog;
    private Subscription mSubscription;
    private GridView mGridView;
    private MovieAdapter mMovieAdapter;
    private OnMovieSelectedListener mMovieSelectedListener;

    /* Must be implemented by host activity */
    public interface OnMovieSelectedListener {
        public void onMovieSelected(MovieResponse movie);
    }

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.movies_gridView);
        mMovieAdapter = new MovieAdapter(getContext(), null);
        mGridView.setAdapter(mMovieAdapter);
        mMovieSelectedListener = (OnMovieSelectedListener)getActivity();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mMovieSelectedListener.onMovieSelected(mMovieAdapter.getItem(i));
            }
        });
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
        int prefOrder = mySharedPreferences.getPreferedMoviesOrder();
        fetchMovies(prefOrder);
    }

    private void fetchMovies(int prefOrder){
        Observable<DiscoverMovieResponse> responseObservable = MovieService.getMoviesByOrder(prefOrder);
        if(responseObservable!=null){
            if(mSubscription!=null && !mSubscription.isUnsubscribed()){
                mSubscription.unsubscribe();
            }
            mSubscription = responseObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<DiscoverMovieResponse>() {
                        @Override
                        public void onCompleted() {
                            if(!isUnsubscribed()){
                                unsubscribe();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(DiscoverMovieResponse resultMovieResponse) {
                            if(resultMovieResponse!=null){
                                mMovieAdapter.updateMovieList(resultMovieResponse.getResults());
                            }
                        }
                    });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mSubscription!=null && !mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }

    private void showSettingsDialog() {
        if(settingsDialog==null) {
            settingsDialog = new SettingsDialogFragment();
        }
        settingsDialog.setTargetFragment(this, DIALOG_FRAGMENT);
        settingsDialog.show(getFragmentManager().beginTransaction(), SETTINGS_DIALOG_TAG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_order) {
            showSettingsDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_list, menu);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        mGridView.setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            mGridView.setItemChecked(mActivatedPosition, false);
        } else {
            mGridView.setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
