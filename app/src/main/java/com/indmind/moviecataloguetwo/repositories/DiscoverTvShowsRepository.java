package com.indmind.moviecataloguetwo.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.indmind.moviecataloguetwo.apis.ApiClient;
import com.indmind.moviecataloguetwo.apis.ApiService;
import com.indmind.moviecataloguetwo.models.TvShow;
import com.indmind.moviecataloguetwo.models.TvShowApiResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverTvShowsRepository {
    private final ApiService mService = ApiClient.getService();
    private final Context mContext;

    public DiscoverTvShowsRepository(Context context) {
        this.mContext = context;
    }

    public void getShows(int page, DiscoverTvShowsListener listener) {
        mService.getTvShows(page).enqueue(new Callback<TvShowApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowApiResponse> call, @NonNull Response<TvShowApiResponse> response) {
                if (response.body() != null) {
                    listener.onShowsReceived(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void searchShows(String query, DiscoverTvShowsListener listener) {
        mService.searchShows(query).enqueue(new Callback<TvShowApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowApiResponse> call, @NonNull Response<TvShowApiResponse> response) {
                if (response.body() != null) {
                    listener.onShowsReceived(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface DiscoverTvShowsListener {
        void onShowsReceived(ArrayList<TvShow> movies);
    }
}

