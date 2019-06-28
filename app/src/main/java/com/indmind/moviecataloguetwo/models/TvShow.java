package com.indmind.moviecataloguetwo.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShow implements Parcelable {
    private final String original_name;
    private final int[] genre_ids;
    private final String name;
    private final double popularity;
    private final String[] origin_country;
    private final int vote_count;
    private final String first_air_date;
    private final String backdrop_path;
    private final String original_language;
    private final int id;
    private final double vote_average;
    private final String overview;
    private final String poster_path;

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public String getName() {
        return name;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.original_name);
        dest.writeIntArray(this.genre_ids);
        dest.writeString(this.name);
        dest.writeDouble(this.popularity);
        dest.writeStringArray(this.origin_country);
        dest.writeInt(this.vote_count);
        dest.writeString(this.first_air_date);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.original_language);
        dest.writeInt(this.id);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
    }

    private TvShow(Parcel in) {
        this.original_name = in.readString();
        this.genre_ids = in.createIntArray();
        this.name = in.readString();
        this.popularity = in.readDouble();
        this.origin_country = in.createStringArray();
        this.vote_count = in.readInt();
        this.first_air_date = in.readString();
        this.backdrop_path = in.readString();
        this.original_language = in.readString();
        this.id = in.readInt();
        this.vote_average = in.readDouble();
        this.overview = in.readString();
        this.poster_path = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
