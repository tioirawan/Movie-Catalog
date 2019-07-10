package com.indmind.moviecataloguetwo.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.indmind.moviecataloguetwo.apis.ApiClient;
import com.indmind.moviecataloguetwo.apis.ApiService;
import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.models.MovieApiResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverMoviesRepository {
    private final ApiService mService = ApiClient.getService();
    private final Context mContext;

    public DiscoverMoviesRepository(Context context) {
        this.mContext = context;
    }

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
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface DiscoverMoviesRepositoryListener {
        void onMoviesReceived(ArrayList<Movie> movies);
    }
}

