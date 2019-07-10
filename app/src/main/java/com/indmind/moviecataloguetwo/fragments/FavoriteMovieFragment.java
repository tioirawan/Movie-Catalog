package com.indmind.moviecataloguetwo.fragments;


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

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.adapters.ListMovieAdapter;
import com.indmind.moviecataloguetwo.viewmodels.FavoriteMovieViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    @BindView(R.id.rv_favorite_movie)
    RecyclerView rvFavoriteMovie;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavoriteMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavoriteMovie.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListMovieAdapter favoriteMovieAdapter = new ListMovieAdapter(getActivity());

        rvFavoriteMovie.setAdapter(favoriteMovieAdapter);

        FavoriteMovieViewModel movieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        movieViewModel.getAllMovies().observe(getViewLifecycleOwner(), favoriteMovieAdapter::setMovies);
    }
}
