package com.indmind.moviecataloguetwo.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.indmind.moviecataloguetwo.database.FavoriteDatabase;
import com.indmind.moviecataloguetwo.database.TvShowDao;
import com.indmind.moviecataloguetwo.models.TvShow;

import java.util.List;

public class FavoriteTvShowRepository {
    private final TvShowDao tvShowDao;
    private final LiveData<List<TvShow>> allTvShows;

    public FavoriteTvShowRepository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        tvShowDao = database.tvShowDao();
        allTvShows = tvShowDao.getAllTvShows();

        Log.d("FavoriteTvShow", "FavoriteTvShowRepository: " + new Gson().toJson(allTvShows));
    }

    public void insert(TvShow tvShow, TvShowFactoryListener listener) {
        new InsertTvShowTask(tvShowDao, listener).execute(tvShow);
    }

    public void delete(TvShow tvShow, TvShowFactoryListener listener) {
        new DeleteTvShowTask(tvShowDao, listener).execute(tvShow);
    }

    public LiveData<List<TvShow>> getAllTvShows() {
        return allTvShows;
    }

    public void getTvShowById(int id, TvShowFactoryListener listener) {
        new GetTvShowByIdTask(tvShowDao, listener).execute(id);
    }

    public interface TvShowFactoryListener {
        void onTvShowReceived(TvShow tvShow);

        void onTvShowInserted();

        void onTvShowDeleted();
    }

    private static class InsertTvShowTask extends AsyncTask<TvShow, Void, Void> {
        private final TvShowDao tvShowDao;
        private final TvShowFactoryListener listener;

        InsertTvShowTask(TvShowDao tvShowDao, TvShowFactoryListener listener) {
            this.tvShowDao = tvShowDao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(TvShow... tvShows) {
            tvShowDao.insert(tvShows[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listener.onTvShowInserted();
        }
    }

    private static class UpdateTvShowTask extends AsyncTask<TvShow, Void, Void> {
        private final TvShowDao tvShowDao;

        UpdateTvShowTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Void doInBackground(TvShow... tvShows) {
            tvShowDao.update(tvShows[0]);
            return null;
        }
    }

    private static class DeleteTvShowTask extends AsyncTask<TvShow, Void, Void> {
        private final TvShowDao tvShowDao;
        private final TvShowFactoryListener listener;

        DeleteTvShowTask(TvShowDao tvShowDao, TvShowFactoryListener listener) {
            this.tvShowDao = tvShowDao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(TvShow... tvShows) {
            tvShowDao.delete(tvShows[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.onTvShowDeleted();
        }
    }

    private static class DeleteAllTvShowsTask extends AsyncTask<Void, Void, Void> {
        private final TvShowDao tvShowDao;

        DeleteAllTvShowsTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tvShowDao.deleteAllTvShows();
            return null;
        }
    }

    private static class GetTvShowByIdTask extends AsyncTask<Integer, Void, Void> {
        private final TvShowDao tvShowDao;
        private TvShow tvShow;
        private final TvShowFactoryListener listener;

        GetTvShowByIdTask(TvShowDao tvShowDao, TvShowFactoryListener listener) {
            this.tvShowDao = tvShowDao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Integer... ints) {
            tvShow = tvShowDao.getTvShowById(ints[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listener.onTvShowReceived(tvShow);
        }
    }
}
