package com.restomania.restomania;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
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

/**
 * sign in user, then go to his/her profile
 */
public class LoginActivity extends Activity {

    private final String TAG = "LoginActivity";
    private SharedPreferences sPref;
    private String mToken;
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mLoginView;
    private EditText mPasswordView;
    private ActionProcessButton mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSignInButton = (ActionProcessButton) findViewById(R.id.btn_login);
        mSignInButton.setMode(ActionProcessButton.Mode.ENDLESS);
        mLoginView = (EditText) findViewById(R.id.text_login);
        mPasswordView = (EditText) findViewById(R.id.text_password);
        TextView tw = (TextView) findViewById(R.id.text_to_register);
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        // if (loadToken()) toUserProfile();

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin(null, null);
            }
        });

    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mLogin;
        private final String mHash;

        UserLoginTask(String login, String password) {
            mLogin = login;
            mHash = password;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //true if success
        @Override
        protected Boolean doInBackground(Void... strings) {
            try {
                List<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("login", mLogin));
                list.add(new BasicNameValuePair("hash", mHash));
                String answer = makePostRequest("http://104.131.184.188:8080/restoserver/signIn", list);
                Gson gson = new Gson();
                mToken = gson.fromJson(answer, Token.class).token;
            } catch (Exception e) {
                // Toast.makeText(getApplicationContext(), "Error " + e, Toast.LENGTH_LONG).show();
                Log.e(TAG, e.toString());
                onCancelled();
            }
            Log.d(TAG, "mToken " + mToken);
            return !(mToken == null || mToken.equals(""));
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
                Log.d(TAG, "RESPONSE " + total.toString());
            } catch (Exception e) {
                Log.e(TAG, total.toString());
                e.printStackTrace();
            }
            return total.toString();

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            Log.d(TAG, "success " + success);
            if (success) {
                mSignInButton.setProgress(100);
                saveToken();
                toUserProfile();
                //finish();
            } else {
                mSignInButton.setProgress(-1);
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    void toUserProfile() {
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        intent.putExtra("token", mToken);
        startActivity(intent);
    }


    public void attemptLogin(String login, String hash) {
        String password;
        if (mAuthTask != null) {
            return;
        }
        if (login != null && hash != null) {
            mAuthTask = new UserLoginTask(login, hash);
            mAuthTask.execute((Void) null);
        }

        // Reset errors.
        mLoginView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        login = mLoginView.getText().toString().trim();
        password = mPasswordView.getText().toString().trim();
        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }


        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(getString(R.string.error_field_required));
            focusView = mLoginView;
            cancel = true;
        } else if (!isEmailValid(login)) {
            mLoginView.setError(getString(R.string.error_invalid_email));
            focusView = mLoginView;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {

            hash = PasswordHash.createHash(login, password);

            Log.d(TAG, "Going to userlogintask, args:" + login + " " + password + " " + hash);
            mSignInButton.setProgress(1);

            mAuthTask = new UserLoginTask(login, hash);
            //Log.d("TIME", "4=" + (System.currentTimeMillis() - time));
            mAuthTask.execute((Void) null);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        String login = data.getStringExtra("login");
        String hash = data.getStringExtra("hash");
        mAuthTask = new UserLoginTask(login, hash);
        mAuthTask.execute((Void) null);
    }

    void saveToken() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("token", mToken);
        ed.commit();

    }

    boolean loadToken() {
        sPref = getPreferences(MODE_PRIVATE);
        mToken = sPref.getString("token", "");
        return !mToken.equals("");
    }

    String getToken() {
        return getPreferences(MODE_PRIVATE).getString("token", "");
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }


}
