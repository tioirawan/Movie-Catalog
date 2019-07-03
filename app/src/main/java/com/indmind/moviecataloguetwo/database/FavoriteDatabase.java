package com.indmind.moviecataloguetwo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.models.TvShow;

@Database(entities = {Movie.class, TvShow.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    private static FavoriteDatabase instance;

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDatabase.class, "favorite_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract MovieDao movieDao();

    public abstract TvShowDao tvShowDao();
}
