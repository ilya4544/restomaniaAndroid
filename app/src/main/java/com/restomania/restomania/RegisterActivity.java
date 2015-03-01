package com.restomania.restomania;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Freemahn on 01.03.2015.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    EditText textName, textLogin, textPass, textRepeatPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textName = (EditText) findViewById(R.id.textName);
        textLogin = (EditText) findViewById(R.id.textLogin);
        textPass = (EditText) findViewById(R.id.textPassword);
        textRepeatPass = (EditText) findViewById(R.id.textRepeatPassword);
        Button register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        attemptRegister();

    }

    public void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        String name = textName.getText().toString();
        String login = textLogin.getText().toString();
        String pass = textPass.getText().toString();
        String repeatPass = textRepeatPass.getText().toString();
        String hash = "";
        if (!pass.equals(repeatPass)) {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG).show();
            return;
        }
        if (name.equals("") || login.equals("") || pass.equals("")) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show();
            return;
        }

        hash = PasswordHash.createHash(login, pass);

        //   Toast.makeText(this, "Ошибка при хешировании пароля", Toast.LENGTH_LONG).show();

        mAuthTask = new UserRegisterTask(login, hash, name);
        mAuthTask.execute((Void) null);
    }


    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mLogin;
        private final String mHash;
        private final String mName;

        UserRegisterTask(String login, String hash, String name) {
            mLogin = login;
            mHash = hash;
            mName = name;
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
                Log.d("parameters ", mLogin + ", " + mHash + "," + mName);
                list.add(new BasicNameValuePair("login", mLogin));
                list.add(new BasicNameValuePair("hash", mHash));
                list.add(new BasicNameValuePair("name", mName));
                String answer = makePostRequest("http://104.131.184.188:8080/restoserver/signUp", list);
                JSONObject ans = new JSONObject(answer);
                if (ans.has("error")) {
                    return false;
                }
            } catch (Exception e) {
                //Toast.makeText(getApplicationContext(), "Error " + e, Toast.LENGTH_LONG).show();
                //Log.e("")
                onCancelled();
            }
            return true;
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
                Log.d("RESPONCE", total.toString());
            } catch (Exception e) {
                Log.e("In PostRequest", total.toString());
                e.printStackTrace();
            }
            return total.toString();

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                Intent intent = new Intent(getApplicationContext(), AccountAuthenticatorActivity.class);
                intent.putExtra("login", mLogin);
                intent.putExtra("hash", mHash);
                startActivity(intent);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Что то пошло не так", Toast.LENGTH_LONG).show();


        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }

    private UserRegisterTask mAuthTask = null;
}
