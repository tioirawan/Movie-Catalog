package com.indmind.moviecataloguetwo.apis;

import com.indmind.moviecataloguetwo.BuildConfig;
import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.models.MovieApiResponse;
import com.indmind.moviecataloguetwo.models.TvShowApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("movie/{id}?language=en-US&api_key=" + BuildConfig.TMDB_API_KEY)
    Call<Movie> getMovieById(@Path("id") int id);

    @GET("discover/movie?language=en-US&page=1&api_key=" + BuildConfig.TMDB_API_KEY)
    Call<MovieApiResponse> getMovieByReleaseRange(@Query("primary_release_date.lte") String releaseDateLte, @Query("primary_release_date.gte") String releaseDateGte, @Query("sort_by") String sortBy);
}
