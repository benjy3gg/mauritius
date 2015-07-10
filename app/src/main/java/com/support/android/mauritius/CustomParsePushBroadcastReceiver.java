package com.support.android.mauritius;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by benjy3gg on 08.07.2015.
 */
public class CustomParsePushBroadcastReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
        Log.d("push", intent.getAction());
        try {
            JSONObject jD = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            String channel = intent.getExtras().getString("com.parse.Channel");
            if (channel != null) {
                JSONObject jC = new JSONObject();
            }
            //TODO: check the Channel string, depends on OS level?
            if (jD.getString("why") == "new") {
                context.sendBroadcast(new Intent("NEW_DRINK")
                        .putExtra("drinkId", jD.getString("drinkId"))
                        .putExtra("category", jD.getString("category"))
                        .putExtra("drinkname", jD.getString("name")));
            } else if (jD.getString("why") == "update") {
                context.sendBroadcast(new Intent("UPDATE_DRINK")
                        .putExtra("drinkId", jD.getString("drinkId"))
                        .putExtra("category", jD.getString("category"))
                        .putExtra("drinkname", jD.getString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //if(intent.getAction() == )
    }
}
