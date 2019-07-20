package com.indmind.moviecataloguetwo.data.repository;

import androidx.annotation.NonNull;

import com.indmind.moviecataloguetwo.data.entity.Movie;
import com.indmind.moviecataloguetwo.data.entity.MovieApiResponse;
import com.indmind.moviecataloguetwo.utils.EspressoIdlingResource;
import com.indmind.moviecataloguetwo.utils.apis.ApiClient;
import com.indmind.moviecataloguetwo.utils.apis.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {
    private final ApiService mService = ApiClient.getService();

    public void getMovies(int page, DiscoverMoviesRepositoryListener listener) {
        EspressoIdlingResource.increment();

        mService.getMovies(page).enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                if (response.body() != null) {
                    listener.onMoviesReceived(response.body().getResults());
                }

                EspressoIdlingResource.decrement();
            }

            @Override
            public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {
                listener.onFailure(t);
                EspressoIdlingResource.decrement();
            }
        });
    }

    public void searchMovies(String query, DiscoverMoviesRepositoryListener listener) {
        EspressoIdlingResource.increment();

        mService.searchMovies(query).enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                if (response.body() != null) {
                    listener.onMoviesReceived(response.body().getResults());
                }

                EspressoIdlingResource.decrement();
            }

            @Override
            public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {
                listener.onFailure(t);
                EspressoIdlingResource.decrement();
            }
        });
    }

    public interface DiscoverMoviesRepositoryListener {
        void onMoviesReceived(ArrayList<Movie> movies);

        void onFailure(Throwable t);
    }
}

