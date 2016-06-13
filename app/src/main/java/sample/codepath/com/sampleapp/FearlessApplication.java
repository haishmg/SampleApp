package sample.codepath.com.sampleapp;

import com.facebook.accountkit.AccountKit;
import com.parse.Parse;
import com.parse.ParseInstallation;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class FearlessApplication extends Application {
    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        AccountKit.initialize(getApplicationContext());


        Parse.initialize(
                new Parse.Configuration.Builder(this)
                        .applicationId(getString(R.string.parse_app_id))
                        .clientKey(getString(R.string.parse_client_key))
                        .server(getString(R.string.parse_server))
                        .enableLocalDataStore()
                        .build());
        ParseInstallation.getCurrentInstallation().saveInBackground();

        CONTEXT = getApplicationContext();

        
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
