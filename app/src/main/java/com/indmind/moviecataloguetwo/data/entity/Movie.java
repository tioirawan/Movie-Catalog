package com.indmind.moviecataloguetwo.data.entity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.indmind.moviecataloguetwo.utils.Converters;

@SuppressWarnings({"unused"})
@Entity(tableName = "movie_table")
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
    @PrimaryKey
    private final int id;
    private final int vote_count;
    private final boolean video;
    private final float vote_average;
    private final Double popularity;
    private final String poster_path;
    private final String original_language;
    private final String original_title;
    private final String backdrop_path;
    private final boolean adult;
    private final String overview;
    private final String release_date;
    private String title;
    @TypeConverters(Converters.class)
    private int[] genre_ids;

    public Movie(int id, int vote_count, boolean video, float vote_average, String title, Double popularity, String poster_path, String original_language, String original_title, String backdrop_path, boolean adult, String overview, String release_date, int[] genre_ids) {
        this.id = id;
        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.vote_count = in.readInt();
        this.video = in.readByte() != 0;
        this.vote_average = in.readFloat();
        this.title = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.poster_path = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.backdrop_path = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.release_date = in.readString();
        this.genre_ids = in.createIntArray();
    }

    public int getId() {
        return id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public int[] getGenre_ids() {
        if (genre_ids != null) return genre_ids;
        return new int[]{};
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
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
        dest.writeString(this.backdrop_path);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeIntArray(this.genre_ids);
    }
}