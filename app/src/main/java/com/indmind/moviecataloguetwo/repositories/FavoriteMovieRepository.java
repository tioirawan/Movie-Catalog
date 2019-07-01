package com.indmind.moviecataloguetwo.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.indmind.moviecataloguetwo.database.MovieDao;
import com.indmind.moviecataloguetwo.database.MovieDatabase;
import com.indmind.moviecataloguetwo.models.Movie;

import java.util.List;

public class FavoriteMovieRepository {
    private MovieDao movieDao;
    private LiveData<List<Movie>> allMovies;

    public FavoriteMovieRepository(Application application) {
        MovieDatabase database = MovieDatabase.getInstance(application);
        movieDao = database.movieDao();
        allMovies = movieDao.getAllMovies();
    }

    public void insert(Movie movie, OnMovieFactoryListener listener) {
        new InsertMovieTask(movieDao, listener).execute(movie);
    }

    public void update(Movie movie) {
        new UpdateMovieTask(movieDao).execute(movie);
    }

    public void delete(Movie movie, OnMovieFactoryListener listener) {
        new DeleteMovieTask(movieDao, listener).execute(movie);
    }

    public void deleteAllMovies() {
        new DeleteAllMoviesTask(movieDao);
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    public void getMovieById(int id, OnMovieFactoryListener listener) {
        new GetMovieByIdTask(movieDao, listener).execute(id);
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao movieDao;
        private OnMovieFactoryListener listener;

        public InsertMovieTask(MovieDao movieDao, OnMovieFactoryListener listener) {
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
        private MovieDao movieDao;

        public UpdateMovieTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.update(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao movieDao;
        private OnMovieFactoryListener listener;

        public DeleteMovieTask(MovieDao movieDao, OnMovieFactoryListener listener) {
            this.movieDao = movieDao;
            this.listener =  listener;
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
        private MovieDao movieDao;

        public DeleteAllMoviesTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAllMovies();
            return null;
        }
    }

    private static class GetMovieByIdTask extends AsyncTask<Integer, Void, Void> {
        private MovieDao movieDao;
        private Movie movie;
        private OnMovieFactoryListener listener;

        public GetMovieByIdTask(MovieDao movieDao, OnMovieFactoryListener listener) {
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

    public interface OnMovieFactoryListener {
        void onMovieReceived(Movie movie);
        void onMovieInserted();
        void onMovieDeleted();
    }
}
