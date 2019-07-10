package com.indmind.moviecataloguetwo.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.indmind.moviecataloguetwo.models.TvShow;
import com.indmind.moviecataloguetwo.repositories.DiscoverTvShowsRepository;

import java.util.ArrayList;

public class DiscoverTvShowsViewModel extends AndroidViewModel {
    private final DiscoverTvShowsRepository repository;
    private final MutableLiveData<ArrayList<TvShow>> allShows = new MutableLiveData<>();

    public DiscoverTvShowsViewModel(@NonNull Application application) {
        super(application);

        repository = new DiscoverTvShowsRepository(application);
    }

    public void loadShows(int page) {
        repository.getShows(page, allShows::postValue);
    }

    public void searchShows(String query) {
        repository.searchShows(query, allShows::postValue);
    }

    public LiveData<ArrayList<TvShow>> getAllShows() {
        return allShows;
    }
}
