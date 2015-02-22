package com.restomania.restomania;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

//first activity, sign in user, then goto profile
public class AccountAuthenticatorActivity extends Activity implements View.OnClickListener {

    TextView loginTW;
    TextView passwTW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn = (Button) findViewById(R.id.login_btn);
        loginTW = (TextView) findViewById(R.id.textLogin);
        passwTW = (TextView) findViewById(R.id.textPassword);
        //loginFromDB = "valera"; hash = "123";
        btn.setOnClickListener(this);


    }
/*    public void submit() {
        final String userName = ((TextView) findViewById(R.id.accountName)).getText().toString();
        final String userPass = ((TextView) findViewById(R.id.accountPassword)).getText().toString();
        new AsyncTask<Void, Void, Intent>() {
            @Override
            protected Intent doInBackground(Void... params) {
                String authtoken = sServerAuthenticate.userSignIn(userName, userPass, mAuthTokenType);
                final Intent res = new Intent();
                res.putExtra(AccountManager.KEY_ACCOUNT_NAME, userName);
                res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
                res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);
                res.putExtra(PARAM_USER_PASS, userPass);
                return res;
            }
            @Override
            protected void onPostExecute(Intent intent) {
                finishLogin(intent);
            }
        }.execute();
    }*/


    //stub
    public String hash(String strToHash) {
        return strToHash;
    }

    //TODO:check correctness input
    @Override
    public void onClick(View v) {
        try {
            String login, pass, token;
            login = loginTW.getText().toString();
            pass = passwTW.getText().toString();
            token = new SignInTask().execute(login, hash(pass)).get();
            User user = new GetUserTask().execute(token).get();
            //TODO put this in db, not in intent
            Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
            intent.putExtra("id", user.getId());
            intent.putExtra("name", user.getName());
            intent.putExtra("balance", user.getBalance());
            intent.putExtra("token", token);
            startActivity(intent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
