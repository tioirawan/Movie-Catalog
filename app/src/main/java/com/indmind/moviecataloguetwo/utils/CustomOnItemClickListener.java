package com.indmind.moviecataloguetwo.utils;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {
    private final int position;
    private final OnItemClickCallback onItemClickCallback;

    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @SuppressWarnings("unused")
    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(position);
    }

    public interface OnItemClickCallback {
        void onItemClicked(int position);
    }
}
