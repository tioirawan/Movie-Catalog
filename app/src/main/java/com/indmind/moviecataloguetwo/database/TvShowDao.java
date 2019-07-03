package com.indmind.moviecataloguetwo.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.indmind.moviecataloguetwo.models.TvShow;

import java.util.List;

@Dao
public interface TvShowDao {
    @Insert
    void insert(TvShow show);

    @Update
    void update(TvShow show);

    @Delete
    void delete(TvShow show);

    @Query("DELETE FROM tv_show_table")
    void deleteAllTvShows();

    @Query("SELECT * FROM tv_show_table")
    LiveData<List<TvShow>> getAllTvShows();

    @Query("SELECT * FROM tv_show_table WHERE id=:id")
    TvShow getTvShowById(int id);
}
