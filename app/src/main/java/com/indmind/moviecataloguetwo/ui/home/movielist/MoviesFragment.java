package com.indmind.moviecataloguetwo.ui.home.movielist;


import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.data.entity.Movie;
import com.indmind.moviecataloguetwo.utils.SimpleIdlingResource;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements SearchView.OnQueryTextListener {
    private final int page_number = 2;
    @BindView(R.id.rv_movie_container)
    RecyclerView movieContainer;
    @BindView(R.id.pb_movie)
    ProgressBar pbMovie;
    @BindView(R.id.sv_movie)
    SearchView svMovie;
    @BindView(R.id.tv_movie_not_found)
    TextView tvNotFound;

    private ListMovieAdapter mAdapter;
    private Handler mHandler;

    private final DiscoverMoviesViewModel.FailureListener failureListener = t -> Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    private DiscoverMoviesViewModel mMovieViewModel;

    private SimpleIdlingResource mIdlingResource;

    private final Observer<ArrayList<Movie>> moviesChangeObserver = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null) {
                mAdapter.setMovies(movies);
                setProgressBarVisibility(false);

                mIdlingResource.setIdleState(true);

                if (movies.size() <= 0) {
                    tvNotFound.setVisibility(View.VISIBLE);
                } else {
                    tvNotFound.setVisibility(View.GONE);
                }
            }
        }
    };

    public MoviesFragment() {
        // Required empty public constructor
    }

    @VisibleForTesting
    @NonNull
    IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }

        return mIdlingResource;
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

        getIdlingResource();
        mIdlingResource.setIdleState(false);

        mAdapter = new ListMovieAdapter(getActivity());
        mHandler = new Handler();

        movieContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        movieContainer.setAdapter(new ScaleInAnimationAdapter(mAdapter));

        svMovie.setOnQueryTextListener(this);

        mMovieViewModel = ViewModelProviders.of(this).get(DiscoverMoviesViewModel.class);
        mMovieViewModel.getAllMovies().observe(getViewLifecycleOwner(), moviesChangeObserver);

        if (mAdapter.getItemCount() <= 0 && (tvNotFound.getVisibility() == View.GONE)) {
            mMovieViewModel.loadMovies(page_number, failureListener);
        }
    }

    private void setProgressBarVisibility(Boolean state) {
        if (state) pbMovie.setVisibility(View.VISIBLE);
        else pbMovie.setVisibility(View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.trim().isEmpty()) return false;

        setProgressBarVisibility(true);
        mAdapter.clearMovies();
        tvNotFound.setVisibility(View.GONE);

        mMovieViewModel.searchMovies(query, failureListener);

        movieContainer.smoothScrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mHandler.removeCallbacksAndMessages(null);

        mHandler.postDelayed(() -> {
            if (TextUtils.isEmpty(newText)) {
                mMovieViewModel.loadMovies(page_number, failureListener);
            } else {
                mMovieViewModel.searchMovies(newText, failureListener);
            }
        }, 300);

        return true;
    }
}
