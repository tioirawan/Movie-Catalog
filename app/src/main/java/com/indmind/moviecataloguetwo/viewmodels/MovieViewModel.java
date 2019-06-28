package com.indmind.moviecataloguetwo.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.indmind.moviecataloguetwo.Api;
import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.models.MovieApiResponse;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Movie>> mListMovies = new MutableLiveData<>();

    public void loadMovies(final Context context, int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<MovieApiResponse> call = api.getMovies(page);

        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                MovieApiResponse result = response.body();

                mListMovies.postValue(Objects.requireNonNull(result).getResults());
            }

            @Override
            public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return mListMovies;
    }
}
