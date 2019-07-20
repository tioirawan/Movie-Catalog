package com.indmind.moviecataloguetwo.data.repository;

import androidx.annotation.NonNull;

import com.indmind.moviecataloguetwo.data.entity.TvShow;
import com.indmind.moviecataloguetwo.data.entity.TvShowApiResponse;
import com.indmind.moviecataloguetwo.utils.EspressoIdlingResource;
import com.indmind.moviecataloguetwo.utils.apis.ApiClient;
import com.indmind.moviecataloguetwo.utils.apis.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsRepository {
    private final ApiService mService = ApiClient.getService();

    public void getShows(int page, DiscoverTvShowsListener listener) {
        EspressoIdlingResource.increment();

        mService.getTvShows(page).enqueue(new Callback<TvShowApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowApiResponse> call, @NonNull Response<TvShowApiResponse> response) {
                if (response.body() != null) {
                    listener.onShowsReceived(response.body().getResults());
                }

                EspressoIdlingResource.decrement();
            }

            @Override
            public void onFailure(@NonNull Call<TvShowApiResponse> call, @NonNull Throwable t) {
                listener.onFailure(t);

                EspressoIdlingResource.decrement();
            }
        });
    }

    public void searchShows(String query, DiscoverTvShowsListener listener) {
        EspressoIdlingResource.increment();

        mService.searchShows(query).enqueue(new Callback<TvShowApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowApiResponse> call, @NonNull Response<TvShowApiResponse> response) {
                if (response.body() != null) {
                    listener.onShowsReceived(response.body().getResults());
                }

                EspressoIdlingResource.decrement();
            }

            @Override
            public void onFailure(@NonNull Call<TvShowApiResponse> call, @NonNull Throwable t) {
                listener.onFailure(t);

                EspressoIdlingResource.decrement();
            }
        });
    }

    public interface DiscoverTvShowsListener {
        void onShowsReceived(ArrayList<TvShow> shows);

        void onFailure(Throwable t);
    }
}

