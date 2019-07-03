package com.indmind.moviecataloguetwo.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.repositories.FavoriteMovieRepository;

import java.util.List;

public class FavoriteMovieViewModel extends AndroidViewModel {
    private final FavoriteMovieRepository repository;
    private final LiveData<List<Movie>> allMovies;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);

        repository = new FavoriteMovieRepository(application);
        allMovies = repository.getAllMovies();
    }

    public void insert(Movie movie, FavoriteMovieRepository.MovieFactoryListener listener) {
        repository.insert(movie, listener);
    }

    public void delete(Movie movie, FavoriteMovieRepository.MovieFactoryListener listener) {
        repository.delete(movie, listener);
    }

    public void deleteAllMovies() {
        repository.deleteAllMovies();
    }

    public void getMovieById(int id, FavoriteMovieRepository.MovieFactoryListener listener) {
        repository.getMovieById(id, listener);
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }
}
