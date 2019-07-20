package com.indmind.moviecataloguetwo.ui.home.tvshowlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indmind.moviecataloguetwo.data.entity.TvShow;
import com.indmind.moviecataloguetwo.data.repository.TvShowsRepository;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class TvShowsViewModel extends ViewModel {
    private final TvShowsRepository repository;
    private final MutableLiveData<ArrayList<TvShow>> allShows = new MutableLiveData<>();

    public TvShowsViewModel(TvShowsRepository repository) {
        this.repository = repository;
    }

    void loadShows(FailureListener listener) {
        repository.getShows(1, new TvShowsRepository.DiscoverTvShowsListener() {
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

    void searchShows(String query, FailureListener listener) {
        repository.searchShows(query, new TvShowsRepository.DiscoverTvShowsListener() {
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

    LiveData<ArrayList<TvShow>> getAllShows() {
        return allShows;
    }

    public interface FailureListener {
        void onFailure(Throwable t);
    }
}
