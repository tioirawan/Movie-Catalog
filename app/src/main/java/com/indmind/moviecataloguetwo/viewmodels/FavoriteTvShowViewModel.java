package com.indmind.moviecataloguetwo.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.indmind.moviecataloguetwo.models.TvShow;
import com.indmind.moviecataloguetwo.repositories.FavoriteTvShowRepository;

import java.util.List;

public class FavoriteTvShowViewModel extends AndroidViewModel {
    private final FavoriteTvShowRepository repository;
    private final LiveData<List<TvShow>> allTvShows;

    public FavoriteTvShowViewModel(@NonNull Application application) {
        super(application);

        repository = new FavoriteTvShowRepository(application);
        allTvShows = repository.getAllTvShows();
    }

    public void insert(TvShow tvShow, FavoriteTvShowRepository.TvShowFactoryListener listener) {
        repository.insert(tvShow, listener);
    }

    public void delete(TvShow tvShow, FavoriteTvShowRepository.TvShowFactoryListener listener) {
        repository.delete(tvShow, listener);
    }

    public void getTvShowById(int id, FavoriteTvShowRepository.TvShowFactoryListener listener) {
        repository.getTvShowById(id, listener);
    }

    public LiveData<List<TvShow>> getAllTvShows() {
        return allTvShows;
    }
}
