package com.indmind.moviecataloguetwo.ui.home.movielist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indmind.moviecataloguetwo.data.entity.Movie;
import com.indmind.moviecataloguetwo.data.repository.MoviesRepository;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class DiscoverMoviesViewModel extends ViewModel {
    private final MoviesRepository repository;
    private final MutableLiveData<ArrayList<Movie>> allMovies = new MutableLiveData<>();

    public DiscoverMoviesViewModel() {
        repository = new MoviesRepository();
    }

    void loadMovies(int page, FailureListener listener) {
        repository.getMovies(page, new MoviesRepository.DiscoverMoviesRepositoryListener() {
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

    void searchMovies(String query, FailureListener listener) {
        repository.searchMovies(query, new MoviesRepository.DiscoverMoviesRepositoryListener() {
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

    LiveData<ArrayList<Movie>> getAllMovies() {
        return allMovies;
    }

    public interface FailureListener {
        void onFailure(Throwable t);
    }
}
