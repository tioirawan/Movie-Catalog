package com.indmind.moviecataloguetwo.ui.home.tvshowlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indmind.moviecataloguetwo.data.TvShow;

import java.util.ArrayList;

public class DiscoverTvShowsViewModel extends ViewModel {
    private final DiscoverTvShowsRepository repository;
    private final MutableLiveData<ArrayList<TvShow>> allShows = new MutableLiveData<>();

    public DiscoverTvShowsViewModel() {
        this.repository = new DiscoverTvShowsRepository();
    }

    public void loadShows(int page, FailureListener listener) {
        repository.getShows(page, new DiscoverTvShowsRepository.DiscoverTvShowsListener() {
            @Override
            public void onShowsReceived(ArrayList<TvShow> shows) {
                allShows.postValue(shows);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void searchShows(String query, FailureListener listener) {
        repository.searchShows(query, new DiscoverTvShowsRepository.DiscoverTvShowsListener() {
            @Override
            public void onShowsReceived(ArrayList<TvShow> shows) {
                allShows.postValue(shows);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getAllShows() {
        return allShows;
    }

    public interface FailureListener {
        void onFailure(Throwable t);
    }
}
