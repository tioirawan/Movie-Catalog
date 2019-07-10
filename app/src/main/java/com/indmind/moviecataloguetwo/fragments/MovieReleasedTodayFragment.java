package com.indmind.moviecataloguetwo.fragments;


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
import com.indmind.moviecataloguetwo.repositories.DiscoverMoviesRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieReleasedTodayFragment extends Fragment {
    @BindView(R.id.rv_movie_release_today_container)
    RecyclerView container;
    @BindView(R.id.pb_loading)
    ProgressBar loading;


    public MovieReleasedTodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_released_today, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListMovieAdapter adapter = new ListMovieAdapter(getContext());

        container.setLayoutManager(new LinearLayoutManager(getContext()));
        container.setHasFixedSize(true);

        container.setAdapter(adapter);

        new DiscoverMoviesRepository(getContext()).getReleaseNow(movies -> {
            loading.setVisibility(View.GONE);
            adapter.setMovies(movies);
        }, "popularity.desc");
    }
}
