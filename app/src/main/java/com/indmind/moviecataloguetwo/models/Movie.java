package com.indmind.moviecataloguetwo.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private final int id;
    private final int vote_count;
    private final boolean video;
    private final float vote_average;
    private final String title;
    private final Double popularity;
    private final String poster_path;
    private final String original_language;
    private final String original_title;
    private final int[] genre_ids;
    private final String backdrop_path;
    private final boolean adult;
    private final String overview;
    private final String release_date;

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.vote_count = in.readInt();
        this.video = in.readByte() != 0;
        this.vote_average = in.readFloat();
        this.title = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.poster_path = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.genre_ids = in.createIntArray();
        this.backdrop_path = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.release_date = in.readString();
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.vote_count);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.vote_average);
        dest.writeString(this.title);
        dest.writeValue(this.popularity);
        dest.writeString(this.poster_path);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeIntArray(this.genre_ids);
        dest.writeString(this.backdrop_path);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
    }
}