package com.indmind.moviecataloguetwo.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.indmind.moviecataloguetwo.Api;
import com.indmind.moviecataloguetwo.models.TvShow;
import com.indmind.moviecataloguetwo.models.TvShowApiResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<TvShow>> mListTvShows = new MutableLiveData<>();

    public void loadTvShows(final Context context, int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<TvShowApiResponse> call = api.getTvShows(page);

        call.enqueue(new Callback<TvShowApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowApiResponse> call, @NonNull Response<TvShowApiResponse> response) {
                TvShowApiResponse result = response.body();

                mListTvShows.postValue(result.getResults());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getTvShows() {
        return mListTvShows;
    }
}
