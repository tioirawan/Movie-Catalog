package com.indmind.moviecataloguetwo.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Converters {

    @TypeConverter
    public static int[] jsonToArrayInt(String data) {
        if (data == null) {
            return new int[]{};
        }

        return new Gson().fromJson(data, new TypeToken<int[]>(){}.getType());
    }

   @TypeConverter
    public static String intArrayToJson(int[] val) {
       return new Gson().toJson(val);
   }
}
