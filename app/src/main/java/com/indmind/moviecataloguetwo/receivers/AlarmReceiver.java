package com.indmind.moviecataloguetwo.receivers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.activities.MainActivity;
import com.indmind.moviecataloguetwo.activities.MovieDetailActivity;
import com.indmind.moviecataloguetwo.models.Movie;
import com.indmind.moviecataloguetwo.repositories.DiscoverMoviesRepository;
import com.squareup.phrase.Phrase;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    public static final int DAILY_ALARM_ID = 101;
    public static final int RELEASE_ALARM_ID = 111;

    private static final String EXTRA_ALARM_ID = "AlarmReceiver.alarmId";

    @Override
    public void onReceive(Context context, Intent intent) {
        int alarmId = intent.getIntExtra(EXTRA_ALARM_ID, DAILY_ALARM_ID);

        if (alarmId == RELEASE_ALARM_ID)
            new DiscoverMoviesRepository(context).getReleaseNow(movies -> {
                if (movies == null || movies.size() <= 0) {
                    showAlarmNotification(context, context.getString(R.string.sad_news), context.getString(R.string.no_movie_playing), alarmId, 0);
                    return;
                }

                for (int i = 0; i < movies.size(); i++) {
                    Movie movie = movies.get(i);

                    String message = Phrase.from(
                            context.getString(R.string.release_reminder_message)
                    )
                            .put("movie", movie.getTitle())
                            .format().toString();

                    showAlarmNotification(context, context.getString(R.string.release_reminder), message, alarmId + 1 + i, movie.getId());
                }

            }, "popularity.asc");
        else {
            showAlarmNotification(context,
                    context.getString(R.string.daily_reminder),
                    context.getString(R.string.daily_reminder_message),
                    alarmId, 0
            );
        }
    }

    public void setRepeatingAlarm(Context context, int alarmId, int hour) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra(EXTRA_ALARM_ID, alarmId);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void showAlarmNotification(Context context, String title, String message, int notificationId, int movieId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Reminder";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setSound(alarmSound)
                .setAutoCancel(true);

        PendingIntent pendingIntent = getPendingIntent(context, notificationId, movieId);

        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            Log.d("AlarmReceiver", "showAlarmNotification: " + notificationId);
            notificationManagerCompat.notify(notificationId, notification);
        }

    }

    private PendingIntent getPendingIntent(Context context, int notificationId, int movieId) {
        Intent intent;

        if (notificationId == DAILY_ALARM_ID) {
            intent = new Intent(context.getApplicationContext(), MainActivity.class);
        } else if (notificationId >= RELEASE_ALARM_ID) {
            intent = new Intent(context.getApplicationContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieId);
        } else {
            return null;
        }

        return PendingIntent.getActivity(context.getApplicationContext(), notificationId == DAILY_ALARM_ID ? notificationId : movieId, intent, 0);
    }
}
