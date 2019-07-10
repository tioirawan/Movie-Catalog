package com.indmind.moviecataloguetwo.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.indmind.moviecataloguetwo.widgets.StackRemoteViewsFactory;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
