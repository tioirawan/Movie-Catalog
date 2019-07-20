package com.indmind.moviecataloguetwo.fragment;


import android.content.res.TypedArray;
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
import com.indmind.moviecataloguetwo.adapter.ListMovieAdapter;
import com.indmind.moviecataloguetwo.model.Movie;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    @BindView(R.id.rv_movie_container)
    RecyclerView movieContainer;

    @BindArray(R.array.movie_title)
    String[] movieTitle;
    @BindArray(R.array.movie_overview)
    String[] movieOverview;
    @BindArray(R.array.movie_runtime)
    String[] movieRuntime;

    @BindArray(R.array.movie_score)
    int[] movieScore;
    @BindArray(R.array.movie_poster)
    TypedArray moviePoster;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieContainer.setHasFixedSize(true);
        movieContainer.setLayoutManager(new LinearLayoutManager(getContext()));

        ListMovieAdapter adapter = new ListMovieAdapter(getContext(), prepareMovieData());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);

        scaleAdapter.setFirstOnly(false);

        movieContainer.setAdapter(scaleAdapter);
    }

    private ArrayList<Movie> prepareMovieData() {
        ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i < movieTitle.length; i++) {
            Movie movie = new Movie();

            movie.setTitle(movieTitle[i]);
            movie.setOverview(movieOverview[i]);
            movie.setScore(movieScore[i]);
            movie.setRuntime(movieRuntime[i]);

            movie.setPoster(moviePoster.getResourceId(i, -1));

            movies.add(movie);
        }

        moviePoster.recycle();

        return movies;
    }
}
