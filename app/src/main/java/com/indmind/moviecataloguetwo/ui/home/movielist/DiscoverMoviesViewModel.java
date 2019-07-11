package com.indmind.moviecataloguetwo.ui.home.movielist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indmind.moviecataloguetwo.data.Movie;

import java.util.ArrayList;

public class DiscoverMoviesViewModel extends ViewModel {
    private final DiscoverMoviesRepository repository;
    private final MutableLiveData<ArrayList<Movie>> allMovies = new MutableLiveData<>();

    DiscoverMoviesViewModel() {
        repository = new DiscoverMoviesRepository();
    }

    public void loadMovies(int page, FailureListener listener) {
        repository.getMovies(page, new DiscoverMoviesRepository.DiscoverMoviesRepositoryListener() {
            @Override
            public void onMoviesReceived(ArrayList<Movie> movies) {
                allMovies.postValue(movies);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void searchMovies(String query, FailureListener listener) {
        repository.searchMovies(query, new DiscoverMoviesRepository.DiscoverMoviesRepositoryListener() {
            @Override
            public void onMoviesReceived(ArrayList<Movie> movies) {
                allMovies.postValue(movies);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public LiveData<ArrayList<Movie>> getAllMovies() {
        return allMovies;
    }

    public interface FailureListener {
        void onFailure(Throwable t);
    }
}
