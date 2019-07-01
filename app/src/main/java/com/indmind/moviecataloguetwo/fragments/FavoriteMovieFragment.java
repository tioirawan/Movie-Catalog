package com.indmind.moviecataloguetwo.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.adapters.FavoriteMovieAdapter;
import com.indmind.moviecataloguetwo.viewmodels.FavoriteMovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    private static final String TAG = "FavoriteMovieFragment";
    private FavoriteMovieViewModel movieViewModel;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFavoriteMovie = view.findViewById(R.id.rv_favorite_movie);

        rvFavoriteMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavoriteMovie.setHasFixedSize(true);

        FavoriteMovieAdapter favoriteMovieAdapter = new FavoriteMovieAdapter(getContext());

        rvFavoriteMovie.setAdapter(favoriteMovieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        movieViewModel.getAllMovies().observe(getViewLifecycleOwner(), movies -> {
            Toast.makeText(getActivity(), "Data changeed", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onViewCreated: " + new Gson().toJson(movies));
            favoriteMovieAdapter.setMovies(movies);
        });
    }
}
