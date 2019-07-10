package com.indmind.moviecataloguetwo.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.activities.MovieDetailActivity;
import com.indmind.moviecataloguetwo.services.StackWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteMovieWidget extends AppWidgetProvider {
    public static final String EXTRA_ID = "FavoriteMovieWidget.EXTRA_ID";
    private static final String DETAIL_ACTION = "FavoriteMovieWidget.DETAIL_ACTION";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {
        Intent intent = new Intent(context, StackWidgetService.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_movie_widget);

        views.setRemoteAdapter(R.id.stv_movie_widget_stack, intent);
        views.setEmptyView(R.id.stv_movie_widget_stack, R.id.tv_movie_widget_empty_view);

        Intent detailIntent = new Intent(context, FavoriteMovieWidget.class);

        detailIntent.setAction(FavoriteMovieWidget.DETAIL_ACTION);
        detailIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingDetailIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.stv_movie_widget_stack, pendingDetailIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction() != null) {
            if (intent.getAction().equals(DETAIL_ACTION)) {
                int movieId = intent.getIntExtra(EXTRA_ID, 69);

                Intent detailIntent = new Intent(context, MovieDetailActivity.class);

                detailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieId);

                context.startActivity(detailIntent);
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

