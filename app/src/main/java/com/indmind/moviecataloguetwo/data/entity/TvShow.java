package com.indmind.moviecataloguetwo.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.indmind.moviecataloguetwo.utils.Converters;

@SuppressWarnings("unused")
@Entity(tableName = "tv_show_table")
public class TvShow implements Parcelable {
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
    @PrimaryKey
    private final int id;
    private final String original_name;
    @TypeConverters(Converters.class)
    private final int[] genre_ids;
    private final String name;
    private final double popularity;
    @TypeConverters(Converters.class)
    private final String[] origin_country;
    private final int vote_count;
    private final String first_air_date;
    private final String backdrop_path;
    private final String original_language;
    private final double vote_average;
    private final String overview;
    private final String poster_path;

    public TvShow(int id, String original_name, int[] genre_ids, String name, double popularity, String[] origin_country, int vote_count, String first_air_date, String backdrop_path, String original_language, double vote_average, String overview, String poster_path) {
        this.id = id;
        this.original_name = original_name;
        this.genre_ids = genre_ids;
        this.name = name;
        this.popularity = popularity;
        this.origin_country = origin_country;
        this.vote_count = vote_count;
        this.first_air_date = first_air_date;
        this.backdrop_path = backdrop_path;
        this.original_language = original_language;
        this.vote_average = vote_average;
        this.overview = overview;
        this.poster_path = poster_path;
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

    @SuppressWarnings("SameReturnValue")
    public static Creator<TvShow> getCREATOR() {
        return CREATOR;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public double getPopularity() {
        return popularity;
    }

    public String[] getOrigin_country() {
        return origin_country;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

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
}
