package com.indmind.moviecataloguetwo.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.indmind.moviecataloguetwo.apis.ApiClient;
import com.indmind.moviecataloguetwo.apis.ApiService;
import com.indmind.moviecataloguetwo.database.FavoriteDatabase;
import com.indmind.moviecataloguetwo.database.MovieDao;
import com.indmind.moviecataloguetwo.models.Genre;
import com.indmind.moviecataloguetwo.models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteMovieRepository {
    @SuppressWarnings("WeakerAccess")
    public static final int SOURCE_API = 1;
    public static final int SOURCE_DB = 2;
    private final MovieDao movieDao;
    private final ApiService mService = ApiClient.getService();
    private final LiveData<List<Movie>> allMovies;

    public FavoriteMovieRepository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        movieDao = database.movieDao();
        allMovies = movieDao.getAllMovies();
    }

    public void insert(Movie movie, FavoriteMovieRepositoryListener listener) {
        new InsertMovieTask(movieDao, listener).execute(movie);
    }

    public void delete(Movie movie, FavoriteMovieRepositoryListener listener) {
        new DeleteMovieTask(movieDao, listener).execute(movie);
    }

    public void deleteAllMovies() {
        new DeleteAllMoviesTask(movieDao);
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    public List<Movie> getAllMoviesAsList() {
        return movieDao.getAllMoviesAsList();
    }

    public void getMovieById(int id, FavoriteMovieRepositoryListener listener) {
        new GetMovieByIdTask(movieDao, new FavoriteMovieRepositoryListener() {
            @Override
            public void onMovieReceived(Movie movie, int source) {
                if (movie != null) {
                    listener.onMovieReceived(movie, SOURCE_DB);
                    return;
                }

                mService.getMovieById(id).enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                        if (response.body() != null) {
                            listener.onMovieReceived(response.body(), SOURCE_API);
                        } else {
                            listener.onMovieReceived(null, SOURCE_API);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                        listener.onMovieReceived(null, SOURCE_API);
                    }
                });
            }

            @Override
            public void onMovieInserted() {

            }

            @Override
            public void onMovieDeleted() {

            }
        }).execute(id);
    }

    public interface FavoriteMovieRepositoryListener {
        void onMovieReceived(Movie movie, int source);

        void onMovieInserted();

        void onMovieDeleted();
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {
        private final MovieDao movieDao;
        private final FavoriteMovieRepositoryListener listener;

        InsertMovieTask(MovieDao movieDao, FavoriteMovieRepositoryListener listener) {
            this.movieDao = movieDao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            // Movie from search by id doesn't have genre_ids
            if (movies[0].getGenre_ids().length > 0) {
                movieDao.insert(movies[0]);
            } else {
                Movie movie = movies[0];

                Genre[] genre = movie.getGenres();

                int[] genre_ids = new int[genre.length];

                for (int i = 0; i < genre.length; i++) {
                    genre_ids[i] = genre[i].getId();
                }

                movie.setGenre_ids(genre_ids);

                movieDao.insert(movie);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listener.onMovieInserted();
        }
    }

    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {
        private final MovieDao movieDao;
        private final FavoriteMovieRepositoryListener listener;

        DeleteMovieTask(MovieDao movieDao, FavoriteMovieRepositoryListener listener) {
            this.movieDao = movieDao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.delete(movies[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.onMovieDeleted();
        }
    }

    private static class DeleteAllMoviesTask extends AsyncTask<Void, Void, Void> {
        private final MovieDao movieDao;

        DeleteAllMoviesTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAllMovies();
            return null;
        }
    }

    private static class GetMovieByIdTask extends AsyncTask<Integer, Void, Void> {
        private final MovieDao movieDao;
        private final FavoriteMovieRepositoryListener listener;
        private Movie movie;

        GetMovieByIdTask(MovieDao movieDao, FavoriteMovieRepositoryListener listener) {
            this.movieDao = movieDao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Integer... ints) {
            movie = movieDao.getMovieById(ints[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listener.onMovieReceived(movie, SOURCE_DB);
        }
    }
}
