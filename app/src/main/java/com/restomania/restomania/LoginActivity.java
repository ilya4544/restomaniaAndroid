package com.restomania.restomania;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends Activity {
    private static String token;
    private static String loginFromDB;
    private static String hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn = (Button) findViewById(R.id.login_btn);
        final User user;
        //TODO get this from db
        loginFromDB = "valera";
        hash = "123";
        //TODO What if it not exist?
        try {
            //hash = PasswordHash.createHash(hash);
            //Log.d("h", hash);
            token = new SignInTask().execute(loginFromDB, hash).get();
            user = new GetUserTask().execute(token).get();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                    intent.putExtra("id", user.id);
                    intent.putExtra("name", user.name);
                    intent.putExtra("balance", user.balance);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

  /*  public static String makePost(String url, List<NameValuePair> nameValuePairs) {
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
            Log.d("Responce", total.toString());
        } catch (Exception e) {
            Log.e("In Sending datatask", total.toString());
            e.printStackTrace();
        }
        return total.toString();

    }*/
}
