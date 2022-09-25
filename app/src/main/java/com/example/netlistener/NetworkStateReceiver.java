package com.example.netlistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkStateWatcher.getDefault(context).post(context);
    }
}