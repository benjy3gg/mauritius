package com.support.android.mauritius;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParseBroadcastReceiver;
import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by benjy3gg on 08.07.2015.
 */
public class CustomParseBroadcastReceiver extends ParseBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("broadcast", intent.getAction());
    }
}
