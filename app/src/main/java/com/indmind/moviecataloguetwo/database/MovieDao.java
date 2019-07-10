package com.indmind.moviecataloguetwo.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.indmind.moviecataloguetwo.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    void insert(Movie movie);

    @Update
    void update(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("DELETE FROM movie_table")
    void deleteAllMovies();

    @Query("SELECT * FROM movie_table")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movie_table")
    List<Movie> getAllMoviesAsList();

    @Query("SELECT * FROM movie_table WHERE id=:id")
    Movie getMovieById(int id);

    @Query("SELECT * FROM movie_table")
    Cursor getAllMoviesCursor();

    @Query("SELECT * FROM movie_table WHERE id=:id")
    Cursor getMovieByIdCursor(String id);
}
