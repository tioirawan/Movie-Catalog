package com.indmind.moviecataloguetwo.utils;

import android.content.Context;
import android.util.SparseArray;

import com.indmind.moviecataloguetwo.R;

public class GenreMapper {
    private static final SparseArray<String> GENRES = new SparseArray<>();

    public static SparseArray<String> getGenres(Context context) {
        GENRES.put(28, context.getString(R.string.genre_action));
        GENRES.put(12, context.getString(R.string.genre_adventure));
        GENRES.put(16, context.getString(R.string.genre_animation));
        GENRES.put(35, context.getString(R.string.genre_comedy));
        GENRES.put(80, context.getString(R.string.genre_crime));
        GENRES.put(99, context.getString(R.string.genre_documentary));
        GENRES.put(18, context.getString(R.string.genre_drama));
        GENRES.put(10751, context.getString(R.string.genre_family));
        GENRES.put(14, context.getString(R.string.genre_fantasy));
        GENRES.put(36, context.getString(R.string.genre_history));
        GENRES.put(27, context.getString(R.string.genre_horror));
        GENRES.put(10402, context.getString(R.string.genre_music));
        GENRES.put(9648, context.getString(R.string.genre_mystery));
        GENRES.put(10749, context.getString(R.string.genre_romance));
        GENRES.put(878, context.getString(R.string.genre_science_fiction));
        GENRES.put(10770, context.getString(R.string.genre_tv_movie));
        GENRES.put(53, context.getString(R.string.genre_thriller));
        GENRES.put(10752, context.getString(R.string.genre_war));
        GENRES.put(37, context.getString(R.string.genre_western));

        GENRES.put(10759, context.getString(R.string.genre_action_drama));
        GENRES.put(10762, context.getString(R.string.genre_kids));
        GENRES.put(10763, context.getString(R.string.genre_news));
        GENRES.put(10764, context.getString(R.string.genre_reality));
        GENRES.put(10765, context.getString(R.string.genre_sci_fi_fantasy));
        GENRES.put(10766, context.getString(R.string.genre_soap));
        GENRES.put(10767, context.getString(R.string.genre_talk));
        GENRES.put(10768, context.getString(R.string.genre_war_politics));

        return GENRES;
    }
}
