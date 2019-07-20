package com.indmind.moviecataloguetwo.utils.apis;

import com.indmind.moviecataloguetwo.BuildConfig;
import com.indmind.moviecataloguetwo.data.entity.MovieApiResponse;
import com.indmind.moviecataloguetwo.data.entity.TvShowApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("discover/movie?language=en-US&api_key=" + BuildConfig.TMDB_API_KEY)
    Call<MovieApiResponse> getMovies(@Query("page") int page);

    @GET("discover/tv?language=en-US&api_key=" + BuildConfig.TMDB_API_KEY)
    Call<TvShowApiResponse> getTvShows(@Query("page") int page);

    @GET("search/movie?language=en-US&api_key=" + BuildConfig.TMDB_API_KEY)
    Call<MovieApiResponse> searchMovies(@Query("query") String query);

    @GET("search/tv?language=en-US&api_key=" + BuildConfig.TMDB_API_KEY)
    Call<TvShowApiResponse> searchShows(@Query("query") String query);
}
