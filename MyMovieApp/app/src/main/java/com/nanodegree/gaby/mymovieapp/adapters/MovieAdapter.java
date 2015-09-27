package com.nanodegree.gaby.mymovieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nanodegree.gaby.mymovieapp.R;
import com.nanodegree.gaby.mymovieapp.api.MovieResponse;
import com.nanodegree.gaby.mymovieapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaby on 9/26/2015.
 */
public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private List<MovieResponse> mList;

    public MovieAdapter(Context context, List<MovieResponse> list){
        mContext =context;
        if(list==null){
            mList = new ArrayList<>();
        }else {
            mList = list;
        }

    }
    @Override
    public int getCount() {
        if (mList==null) {
            return 0;
        }else{
            return mList.size();
        }
    }

    @Override
    public MovieResponse getItem(int i) {
        if (mList==null) {
            return null;
        }else{
            return mList.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        if (mList==null) {
            return 0;
        }else{
            return mList.get(i).getId();
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {

            LayoutInflater inflater =
                    LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_movie_poster, viewGroup, false);
            // well set up the ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_movie_poster);

            // store the holder with the view.
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        MovieResponse item = getItem(i);
        if(item!=null) {
            Utils.getPosterThumbnail(item.getPosterPath(), mContext, viewHolder.imageView);
        }
        return view;
    }

    public void updateMovieList(List<MovieResponse> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();

    }

    static class ViewHolder{
        ImageView imageView;
    }

}
