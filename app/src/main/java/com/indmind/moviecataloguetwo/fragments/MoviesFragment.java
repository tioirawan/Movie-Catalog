package com.indmind.moviecataloguetwo.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.adapters.ListMovieAdapter;
import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.viewmodels.MovieViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    @BindView(R.id.rv_movie_container)
    RecyclerView movieContainer;

    @BindView(R.id.pb_movie)
    ProgressBar pbMovie;

    private ListMovieAdapter adapter;

    private final Observer<ArrayList<Movie>> observer = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setListMovie(movies);
                setProgressBarVisible();
            }
        }
    };

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ListMovieAdapter(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(getViewLifecycleOwner(), observer);

        int displayedPage = 1;
        movieViewModel.loadMovies(getContext(), displayedPage);

        movieContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        movieContainer.setAdapter(new ScaleInAnimationAdapter(adapter));
    }

    private void setProgressBarVisible() {
        pbMovie.setVisibility(View.GONE);
    }
}
