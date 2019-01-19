package com.nikhil.locationalarm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.nikhil.locationalarm.R;
import com.nikhil.locationalarm.utils.AppCache;
import com.nikhil.locationalarm.model.LoginRequestModel;
import com.nikhil.locationalarm.model.LoginResponseModel;
import com.nikhil.locationalarm.model.NetworkModel;
import com.nikhil.locationalarm.network.NetworkRequest;
import com.nikhil.locationalarm.utils.Constants;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = findViewById(R.id.username);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in
     */
    private void attemptLogin() {

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            requestForLogin(username, password);
        }
    }

    private void requestForLogin(String username, String password) {
        NetworkRequest request = new NetworkRequest(Request.Method.POST,
                Constants.BASE_URL + Constants.ENDPOINT_LOGIN,
                this::onLoginResponse,
                this::onLoginError,
                new LoginResponseModel(),
                null,
                new LoginRequestModel(username, password).toString());

        Volley.newRequestQueue(this).add(request);
    }

    private void onLoginResponse(NetworkModel response) {
        if (response instanceof LoginResponseModel) {
            showProgress(false);
            LoginResponseModel loginResponseModel = (LoginResponseModel) response;
            AppCache.getCache().setUserInfo(loginResponseModel.getResult());
            startActivity(new Intent(LoginActivity.this, AlarmListActivity.class));
            Toast.makeText(LoginActivity.this, loginResponseModel.getStatus().getCode()
                    + " : " + loginResponseModel.getResult().getName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onLoginError(VolleyError error) {
        showProgress(false);
        Toast.makeText(LoginActivity.this, "Login error: " + error.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        // The ViewPropertyAnimator APIs are not available, so simply show
        // and hide the relevant UI components.
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);

    }
}

