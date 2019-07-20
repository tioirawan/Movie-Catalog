package com.indmind.moviecataloguetwo.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.indmind.moviecataloguetwo.data.repository.MoviesRepository;
import com.indmind.moviecataloguetwo.data.repository.TvShowsRepository;
import com.indmind.moviecataloguetwo.ui.home.movielist.MoviesViewModel;
import com.indmind.moviecataloguetwo.ui.home.tvshowlist.TvShowsViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    public static ViewModelFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory();
                }
            }
        }

        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MoviesViewModel.class)) {
            //noinspection unchecked
            return (T) new MoviesViewModel(new MoviesRepository());
        } else if (modelClass.isAssignableFrom(TvShowsViewModel.class)) {
            //noinspection unchecked
            return (T) new TvShowsViewModel(new TvShowsRepository());
        }

        throw new IllegalArgumentException("Unknown ViewModel Class: " + modelClass.getName());
    }
}
