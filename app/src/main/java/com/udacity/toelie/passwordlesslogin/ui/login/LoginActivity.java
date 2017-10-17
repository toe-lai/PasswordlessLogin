package com.udacity.toelie.passwordlesslogin.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.udacity.toelie.passwordlesslogin.R;
import com.udacity.toelie.passwordlesslogin.ui.account.AccountActivity;
import com.udacity.toelie.passwordlesslogin.util.FontHelper;

/**
 * Created by toelie on 10/16/17.
 */

public class LoginActivity extends AppCompatActivity {

    private static final int APP_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FontHelper.setCustomTypeface(findViewById(R.id.view_root));

        // Check an for existing access token
        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        if (accessToken != null) {
            // if previously logged in, proceed to the account activity
            launchAccountActivity();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // confirm that this request code matches the account kit request
        if (requestCode == APP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null) {
                // display login error
                String errorMessage = loginResult.getError().getErrorType().getMessage();
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                // on successful login, proceed to the account activity
                launchAccountActivity();
            }

        }
    }

    private void onLogin(final LoginType loginType) {
        // create intent for the Account Kit activity
        Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        loginType,
                        AccountKitActivity.ResponseType.TOKEN
                );

        final AccountKitConfiguration configuration = configurationBuilder.build();

        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configuration);
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    public void onPhoneLogin(View view) {
        onLogin(LoginType.PHONE);
    }

    public void onEmailLogin(View view) {
        onLogin(LoginType.EMAIL);
    }

    private void launchAccountActivity() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
        finish();
    }
}
