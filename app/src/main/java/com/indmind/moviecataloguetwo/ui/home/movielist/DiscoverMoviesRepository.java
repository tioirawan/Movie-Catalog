package com.indmind.moviecataloguetwo.ui.home.movielist;

import androidx.annotation.NonNull;

import com.indmind.moviecataloguetwo.data.Movie;
import com.indmind.moviecataloguetwo.data.MovieApiResponse;
import com.indmind.moviecataloguetwo.utils.apis.ApiClient;
import com.indmind.moviecataloguetwo.utils.apis.ApiService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverMoviesRepository {
    private final ApiService mService = ApiClient.getService();

    public void getMovies(int page, DiscoverMoviesRepositoryListener listener) {
        mService.getMovies(page).enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                if (response.body() != null) {
                    listener.onMoviesReceived(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void searchMovies(String query, DiscoverMoviesRepositoryListener listener) {
        mService.searchMovies(query).enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                if (response.body() != null) {
                    listener.onMoviesReceived(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void getReleaseNow(DiscoverMoviesRepositoryListener listener, String sortBy) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                Calendar.getInstance().getTime()
        );

        mService.getMovieByReleaseRange(currentDate, currentDate, sortBy).enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                if (response.body() != null) {

                    ArrayList<Movie> results = response.body().getResults();
                    ArrayList<Movie> releaseNow = new ArrayList<>();

                    for (Movie movie : results) {
                        // check if movie release date is today
                        if (movie.getRelease_date().equals(currentDate)) {
                            releaseNow.add(movie);
                        }
                    }

                    listener.onMoviesReceived(releaseNow);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface DiscoverMoviesRepositoryListener {
        void onMoviesReceived(ArrayList<Movie> movies);

        void onFailure(Throwable t);
    }
}

