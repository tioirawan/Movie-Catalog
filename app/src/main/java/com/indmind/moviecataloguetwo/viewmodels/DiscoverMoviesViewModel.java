package com.indmind.moviecataloguetwo.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.repositories.DiscoverMoviesRepository;

import java.util.ArrayList;

public class DiscoverMoviesViewModel extends AndroidViewModel {
    private final DiscoverMoviesRepository repository;
    private final MutableLiveData<ArrayList<Movie>> allMovies = new MutableLiveData<>();

    public DiscoverMoviesViewModel(@NonNull Application application) {
        super(application);

        repository = new DiscoverMoviesRepository(application);
    }

    public void loadMovies(int page) {
        repository.getMovies(page, allMovies::postValue);
    }

    public void searchMovies(String query) {
        repository.searchMovies(query, allMovies::postValue);
    }

    public LiveData<ArrayList<Movie>> getAllMovies() {
        return allMovies;
    }
}
