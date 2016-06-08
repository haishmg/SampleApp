package sample.codepath.com.sampleapp;


import com.facebook.FacebookSdk;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class FearlessIntroActivity extends AppCompatActivity {
    private static final String title = "Welcome to Fearless";
    private static final String first_subtitle = "Wherever you go, get there safely and fearlessly";
    private static final String first_description = "Select your destination and activate the sensors you want Fearless & your friends to monitor";

    private static final String second_title = "Add Friends";
    private static final String second_subtitle="Quickly select friends from your contacts and they will watch over you, as you travel.";
    private static final String second_description = "Your friends are not on fearless... don't worry, you can invite them by sending an SMS right from the App.";

    private static final String third_title = "Travel to your destination";
    private static final String third_subtitle = "Travel as your friends and fearless will watch over you..";
    private static final String third_description = "If any of the sensors are triggered either by you or Fearless... we will notify your friends immediately.";

    private static final String TAG = "FearlessIntroActivity";

    public static int APP_REQUEST_CODE = 99;

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, FearlessIntroActivity.class);
        return i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AccountKit.initialize(getApplicationContext());
        onLoginPhone(this.findViewById(android.R.id.content));

    }





    public void onLoginPhone(final View view) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);

        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // checking for resultCode == Activity.RESULT_CANCELED , because of UI kit bug, resultCode is sent as cancelled though the result was success
        if (requestCode == APP_REQUEST_CODE) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(
                    AccountKitLoginResult.RESULT_KEY);

            } else {
                // TODO: The following does not work if
                // "App Secret Proof for Server API calls" is on for the App. Not sure why.
                // I checked that AccountKit.getCurrentAccessToken() does not return null.
                // The sample code from Facebook indicates it should work as well:
                // https://github.com/fbsamples/account-kit-samples-for-android/blob/master/AccountKitSample/src/com/example/accountkitsample/TokenActivity.java
                //
                // I asked in http://stackoverflow.com/questions/36669139/android-accountkit-getcurrentaccount-returns-api-calls-from-the-server-require
                // to see if we have a better solution.
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(final Account account) {
                        // Get phone number
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        String phoneNumberString = phoneNumber.toString();
                        Log.i(TAG, "Phone number obtained from Facebook is " + phoneNumberString);
                    }

                    @Override
                    public void onError(final AccountKitError error) {

                    }
                });
            }
        }
    }


