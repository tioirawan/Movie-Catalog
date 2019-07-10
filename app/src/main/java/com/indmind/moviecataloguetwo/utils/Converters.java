package com.indmind.moviecataloguetwo.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indmind.moviecataloguetwo.models.Genre;

public class Converters {
    @TypeConverter
    public static int[] jsonToArrayInt(String data) {
        if (data == null) {
            return new int[]{};
        }

        return new Gson().fromJson(data, new TypeToken<int[]>() {
        }.getType());
    }

    @TypeConverter
    public static String intArrayToJson(int[] val) {
        return new Gson().toJson(val);
    }

    @TypeConverter
    public static String[] jsonToArrayString(String data) {
        if (data == null) {
            return new String[]{};
        }

        return new Gson().fromJson(data, new TypeToken<String[]>() {
        }.getType());
    }

    @TypeConverter
    public static String stringArrayToJson(String[] val) {
        return new Gson().toJson(val);
    }

    @TypeConverter
    public static Genre[] jsonToArrayGenres(String data) {
        if (data == null) {
            return new Genre[]{};
        }

        return new Gson().fromJson(data, new TypeToken<Genre[]>() {
        }.getType());
    }

    @TypeConverter
    public static String genreArrayToJson(Genre[] val) {
        return new Gson().toJson(val);
    }
}
