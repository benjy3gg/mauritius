package com.support.android.mauritius;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseBroadcastReceiver;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;

import org.json.JSONArray;

/**
 * Created by benjy3gg on 07.07.2015.
 */
public class Application extends android.app.Application {
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "cJQ7e6I2efYQmUPdoZGzmSejA07UNrC7BdDKKLKc", "wJLcGmxxuJfc3LdZZVzMoXjJprUFAOTTryP7SsNl");
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("uniqueId", getWifiMacAddress(this));
        installation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.d("test", "installation " + e );
            }

        });

        ParsePush.subscribeInBackground("", new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        //ParseUser.enableAutomaticUser();
        //ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        //ParseACL.setDefaultACL(defaultACL, true);
    }

    private String getWifiMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.getConnectionInfo() != null && wifiManager.getConnectionInfo().getMacAddress() != null) {
            return wifiManager.getConnectionInfo().getMacAddress()  ;
        }

        return "12:23:34:45";
    }
}
