package com.indmind.moviecataloguetwo.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.indmind.moviecataloguetwo.database.FavoriteDatabase;
import com.indmind.moviecataloguetwo.database.MovieDao;
import com.indmind.moviecataloguetwo.models.Movie;

import java.util.List;

public class FavoriteMovieRepository {
    private final MovieDao movieDao;
    private final LiveData<List<Movie>> allMovies;

    public FavoriteMovieRepository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        movieDao = database.movieDao();
        allMovies = movieDao.getAllMovies();
    }

    public void insert(Movie movie, MovieFactoryListener listener) {
        new InsertMovieTask(movieDao, listener).execute(movie);
    }

    public void update(Movie movie) {
        new UpdateMovieTask(movieDao).execute(movie);
    }

    public void delete(Movie movie, MovieFactoryListener listener) {
        new DeleteMovieTask(movieDao, listener).execute(movie);
    }

    public void deleteAllMovies() {
        new DeleteAllMoviesTask(movieDao);
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    public void getMovieById(int id, MovieFactoryListener listener) {
        new GetMovieByIdTask(movieDao, listener).execute(id);
    }

    public interface MovieFactoryListener {
        void onMovieReceived(Movie movie);

        void onMovieInserted();

        void onMovieDeleted();
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {
        private final MovieDao movieDao;
        private final MovieFactoryListener listener;

        InsertMovieTask(MovieDao movieDao, MovieFactoryListener listener) {
            this.movieDao = movieDao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.insert(movies[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listener.onMovieInserted();
        }
    }

    private static class UpdateMovieTask extends AsyncTask<Movie, Void, Void> {
        private final MovieDao movieDao;

        UpdateMovieTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.update(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {
        private final MovieDao movieDao;
        private final MovieFactoryListener listener;

        DeleteMovieTask(MovieDao movieDao, MovieFactoryListener listener) {
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
        private Movie movie;
        private final MovieFactoryListener listener;

        GetMovieByIdTask(MovieDao movieDao, MovieFactoryListener listener) {
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

            listener.onMovieReceived(movie);
        }
    }
}
