package com.indmind.moviecataloguetwo.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.indmind.moviecataloguetwo.database.FavoriteDatabase;
import com.indmind.moviecataloguetwo.database.MovieDao;

import java.util.Objects;

public class FavoriteMovieProvider extends ContentProvider {
    private static final String AUTHORITY = "com.indmind.moviecataloguetwo";
    private static final String BASE_PATH = "movie_table";

    private static final int MOVIE = 1;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, MOVIE);
    }

    private MovieDao movieDao;

    @Override
    public boolean onCreate() {
        movieDao = FavoriteDatabase.getInstance(getContext()).movieDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;

        if (uriMatcher.match(uri) == MOVIE) {
            cursor = movieDao.getAllMoviesCursor();
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
