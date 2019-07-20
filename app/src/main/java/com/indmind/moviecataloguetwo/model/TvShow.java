package com.indmind.moviecataloguetwo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShow implements Parcelable {
    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
    private String title, overview, runtime;
    private int poster, score;

    public TvShow() {
    }

    protected TvShow(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.runtime = in.readString();
        this.poster = in.readInt();
        this.score = in.readInt();
    }

    public static Creator<TvShow> getCREATOR() {
        return CREATOR;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.runtime);
        dest.writeInt(this.poster);
        dest.writeInt(this.score);
    }
}
