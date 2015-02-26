package com.restomania.restomania;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//first activity, sign in user, then go to his/her profile
public class AccountAuthenticatorActivity extends Activity {


    SharedPreferences sPref;
    String mToken;
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button signInButton = (Button) findViewById(R.id.login_btn);
        mEmailView = (EditText) findViewById(R.id.textLogin);
        mPasswordView = (EditText) findViewById(R.id.textPassword);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        //loginFromDB = "pavel"; hash = "123";
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }


    //TODO stub
    public String hash(String strToHash) {
        return strToHash;
    }


/*
    void saveTokenAndUser() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("token", user.token);
        ed.putString("id", user.id);
        ed.putString("name", user.name);
        ed.putString("login", user.login);
        ed.putString("hash", user.hash);
        ed.putFloat("balance", user.balance);
        ed.commit();

    }*/
/*
    boolean loadTokenAndUser() {
        sPref = getPreferences(MODE_PRIVATE);
        user.token = sPref.getString("token", "");
        user.id = sPref.getString("id", "");
        user.name = sPref.getString("name", "");
        user.login = sPref.getString("login", "");
        user.hash = sPref.getString("hash", "");
        user.balance = sPref.getFloat("balance", 0.0f);
        return !user.token.equals("");

    }*/


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mLogin;
        private final String mPassword;

        UserLoginTask(String login, String password) {
            mLogin = login;
            mPassword = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //true if success
        @Override
        protected Boolean doInBackground(Void... strings) {
            try {
                long time = System.currentTimeMillis();
                List<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("login", mLogin));
                list.add(new BasicNameValuePair("hash", mPassword));
                String answer = makePostRequest("http://104.131.184.188:8080/restoserver/signIn", list);
                Gson gson = new Gson();
                mToken = gson.fromJson(answer, Token.class).token;
                Log.d("ACCOUNT", (System.currentTimeMillis() - time) + "");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error " + e, Toast.LENGTH_LONG).show();
                onCancelled();
            }
            //TODO what if not such user? need to create
            return !mToken.equals("");
        }

        public String makePostRequest(String url, List<NameValuePair> nameValuePairs) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost http = new HttpPost(url);

            StringBuilder total = null;
            try {
                http.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(http);
                String line = "";
                total = new StringBuilder();
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                while ((line = rd.readLine()) != null) {
                    total.append(line);

                }
                Log.d("Response", total.toString());
            } catch (Exception e) {
                Log.e("In Sending datatask", total.toString());
                e.printStackTrace();
            }
            return total.toString();

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            //TODO problem vith changing intent
            if (success) {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("token", mToken);
                startActivity(intent);
                finish();
            } else {
                //mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }


    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
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
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
