package com.indmind.moviecataloguetwo.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.indmind.moviecataloguetwo.R;
import com.indmind.moviecataloguetwo.receivers.AlarmReceiver;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String DAILY_REMINDER = "dailyReminder";
    private static final String RELEASE_REMINDER = "releaseReminder";

    @BindView(R.id.sw_daily_reminder)
    Switch swDailyReminder;
    @BindView(R.id.sw_release_reminder)
    Switch swReleaseReminder;
    @BindView(R.id.ll_change_language)
    LinearLayout llOpenLanguageSetting;

    private SharedPreferences sharedPreferences;
    private AlarmReceiver alarmReceiver;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        alarmReceiver = new AlarmReceiver();

        loadPreferences();

        swDailyReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alarmReceiver.setRepeatingAlarm(getActivity(), AlarmReceiver.DAILY_ALARM_ID, 7);
            } else {
                alarmReceiver.cancelAlarm(getActivity(), AlarmReceiver.DAILY_ALARM_ID);
            }

            savePreferences();
        });

        swReleaseReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alarmReceiver.setRepeatingAlarm(getActivity(), AlarmReceiver.RELEASE_ALARM_ID, 8);
            } else {
                alarmReceiver.cancelAlarm(getActivity(), AlarmReceiver.RELEASE_ALARM_ID);
            }

            savePreferences();
        });

        llOpenLanguageSetting.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);

            startActivity(intent);
        });
    }

    private void loadPreferences() {
        swDailyReminder.setChecked(sharedPreferences.getBoolean(DAILY_REMINDER, false));
        swReleaseReminder.setChecked(sharedPreferences.getBoolean(RELEASE_REMINDER, false));
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(DAILY_REMINDER, swDailyReminder.isChecked());
        editor.putBoolean(RELEASE_REMINDER, swReleaseReminder.isChecked());

        editor.apply();
    }
}
