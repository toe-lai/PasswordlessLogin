package com.udacity.toelie.passwordlesslogin.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.udacity.toelie.passwordlesslogin.R;
import com.udacity.toelie.passwordlesslogin.ui.login.LoginActivity;
import com.udacity.toelie.passwordlesslogin.util.FontHelper;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by toelie on 10/16/17.
 */

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.info_label)
    TextView infoLabel;
    @BindView(R.id.info)
    TextView info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        FontHelper.setCustomTypeface(findViewById(R.id.view_root));

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                //Get Account Kit ID
                String accountKitId = account.getId();
                id.setText(accountKitId);

                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (phoneNumber != null) {
                    String formattedPhoneNumber = formatPhoneNumber(phoneNumber.getPhoneNumber());
                    info.setText(formattedPhoneNumber);
                    infoLabel.setText(R.string.phone_label);
                } else {
                    String email = account.getEmail();
                    info.setText(email);
                    infoLabel.setText(R.string.email_label);
                }
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                // display error
                String errorMessage = accountKitError.getErrorType().getMessage();
                Toast.makeText(AccountActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onLogout(View view) {
        AccountKit.logOut();
        launchLoginActivity();
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private String formatPhoneNumber(String phoneNumber) {
        // helper method to format the phone number for display
        try {
            PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pn = pnu.parse(phoneNumber, Locale.getDefault().getCountry());
            phoneNumber = pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        }
        catch (NumberParseException e) {
            e.printStackTrace();
        }
        return phoneNumber;
    }

}
