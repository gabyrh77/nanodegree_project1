package com.nanodegree.gaby.mymovieapp.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gaby on 9/26/2015.
 */
public class MovieResponse implements Parcelable{
    private Long id;
    private String original_title;
    private String overview;
    private String release_date;
    private String poster_path;
    private Float vote_average;
    private Float popularity;

    public Long getId() {
        return id;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public Float getVoteAverage() {
        return vote_average;
    }

    public Float getPopularity() {
        return popularity;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.getId());
        out.writeString(this.getOriginalTitle());
        out.writeString(this.getOverview());
        out.writeString(this.getReleaseDate());
        out.writeString(this.getPosterPath());
        out.writeFloat(this.getVoteAverage());
        out.writeFloat(this.getPopularity());
    }

    public static final Parcelable.Creator<MovieResponse> CREATOR
            = new Parcelable.Creator<MovieResponse>() {
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    private MovieResponse(Parcel in) {
        this.id = in.readLong();
        this.original_title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.poster_path = in.readString();
        this.vote_average = in.readFloat();
        this.popularity = in.readFloat();
    }
}
