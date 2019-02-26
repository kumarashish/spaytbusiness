package com.spaytbusiness;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;

import org.json.JSONException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import interfaces.WebApiResponseCallback;
import models.BusinessProfile;
import models.UserProfile;
import utils.Utils;
import utils.Validation;

public class Login  extends Activity implements View.OnClickListener, WebApiResponseCallback {
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.signUp)
    TextView signUp;
    @BindView(R.id.paypal)
    ImageView paypal;
    @BindView(R.id.forgetpassword)
    TextView forgotPassword;
    @BindView(R.id.email_id)
    EditText emailId;
    @BindView(R.id.password)
    EditText password;
    AppController controller;
    @BindView(R.id.view)
    LinearLayout view;
    @BindView(R.id.progress_bar)
    ProgressBar progressbar;
    int REQUEST_CODE_PROFILE_SHARING=1;
    Validation validation;
    public int apicall;
    int login=1,signUpwithPaypal=2;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX     )
            .clientId(Common.paypalClientId)
            .merchantName("Spayt GmbH")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.spayt.de"))
            .merchantUserAgreementUri(Uri.parse("https://www.spayt.de"));;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        ButterKnife.bind(this);
        paypal.setOnClickListener(this);
        submit.setOnClickListener(this);
        signUp.setOnClickListener(this);
        controller=(AppController)getApplicationContext();
        validation=new Validation(Login.this);
        forgotPassword.setOnClickListener(this);
        Intent intent=new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);



    }
    public void onProfileSharingPressed() {
        Intent intent = new Intent(Login.this, PayPalProfileSharingActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PayPalProfileSharingActivity.EXTRA_REQUESTED_SCOPES, getOauthScopes());

        startActivityForResult(intent, REQUEST_CODE_PROFILE_SHARING);
    }

    private PayPalOAuthScopes getOauthScopes() {
        /* create the set of required scopes
         * Note: see https://developer.paypal.com/docs/integration/direct/identity/attributes/ for mapping between the
         * attributes you select for this app in the PayPal developer portal and the scopes required here.
         */
        Set<String> scopes = new HashSet<String>(
                Arrays.asList(PayPalOAuthScopes.PAYPAL_SCOPE_EMAIL, PayPalOAuthScopes.PAYPAL_SCOPE_ADDRESS,PayPalOAuthScopes.PAYPAL_SCOPE_PROFILE,PayPalOAuthScopes.PAYPAL_SCOPE_PHONE) );
        return new PayPalOAuthScopes(scopes);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.paypal:
                onProfileSharingPressed();
                break;

            case R.id.submit:
                if((validation.isEmailIdValid(emailId))&&(validation.isNotNull(password)))
                {
                    if(Utils.isNetworkAvailable(Login.this))
                    {apicall=login;
                        progressbar.setVisibility(View.VISIBLE);
                        view.setVisibility(View.GONE);
                        controller.getWebApiCall().login(Common.login,emailId.getText().toString().trim(),password.getText().toString().trim(),"",this);
                    }
                }


                break;
            case R.id.signUp:
                startActivity(new Intent(Login.this,Registration.class));
                break;
            case R.id.forgetpassword:
                startActivity(new Intent(Login.this,ForgetPassword.class));

                break;
        }

    }

    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Utils.getStatus(value))
                {
                    controller.setProfile(new UserProfile(value));
                    controller.setBusinessProfile(new BusinessProfile(value));
                    controller.getManager().setUserLoggedIn(true);
                    controller.getManager().setUserToken(value);
                    utils.Utils.showToast(Login.this, "Logged in sucessfully.");
                    startActivity(new Intent(Login.this, Dashboard.class));
                    finish();
                }else{
                    Toast.makeText(Login.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Login.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
                     Bundle bundle=  data.getExtras();
                if (auth != null) {
                    try {
                       Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));


                        String authorization_code = auth.getAuthorizationCode();

                        progressbar.setVisibility(View.VISIBLE);

                        view.setVisibility(View.GONE);
                        apicall=signUpwithPaypal;
                        controller.getWebApiCall().getData(Common.login_withPaypal,authorization_code,this);
                        Log.i("ProfileSharingExample", authorization_code);
                       // sendAuthorizationToServer(auth);
                       // displayResultText("Profile Sharing code received from PayPal");

                    } catch (JSONException e) {
                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("ProfileSharingExample", "The user canceled.");
            }
        }
    }
}
