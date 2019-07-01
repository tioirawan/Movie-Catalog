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

    @GET("movie?api_key=f2ef948b07787aa3f36a84f19766056d&language=en-US")
    Call<MovieApiResponse> getMovies(@Query("page") int page);

    @GET("tv?api_key=f2ef948b07787aa3f36a84f19766056d&language=en-US")
    Call<TvShowApiResponse> getTvShows(@Query("page") int page);
}
