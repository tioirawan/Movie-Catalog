package com.indmind.moviecataloguetwo;

import com.indmind.moviecataloguetwo.models.MovieApiResponse;
import com.indmind.moviecataloguetwo.models.TvShowApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://api.themoviedb.org/3/discover/";
    String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w92";
    String POSTER_BASE_URL_185 = "https://image.tmdb.org/t/p/w185";

    @GET("movie?language=en-US&api_key=" + BuildConfig.TMDB_API_KEY)
    Call<MovieApiResponse> getMovies(@Query("page") int page);

    @GET("tv?language=en-US&api_key=" + BuildConfig.TMDB_API_KEY)
    Call<TvShowApiResponse> getTvShows(@Query("page") int page);
}
