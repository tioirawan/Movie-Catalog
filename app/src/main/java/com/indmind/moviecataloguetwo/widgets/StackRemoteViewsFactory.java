package com.indmind.moviecataloguetwo.widgets;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.apis.ApiClient;
import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.repositories.FavoriteMovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context mContext;
    private final FavoriteMovieRepository repository;
    private List<Movie> mListMovie = new ArrayList<>();

    public StackRemoteViewsFactory(Context context) {
        mContext = context;
        this.repository = new FavoriteMovieRepository((Application) context);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mListMovie = repository.getAllMoviesAsList();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mListMovie.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Movie movie = mListMovie.get(position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_stack_movie_widget);
        rv.setTextViewText(R.id.tv_movie_widget_banner, movie.getTitle());

        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(ApiClient.POSTER_BASE_URL_185 + movie.getPoster_path())
                    .submit(185, 278)
                    .get();

            rv.setImageViewBitmap(R.id.iv_item_movie_poster, bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();

        bundle.putInt(FavoriteMovieWidget.EXTRA_ID, movie.getId());

        Intent fillIntent = new Intent();

        fillIntent.putExtras(bundle);

        rv.setOnClickFillInIntent(R.id.iv_item_movie_poster, fillIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @SuppressWarnings("SameReturnValue")
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
