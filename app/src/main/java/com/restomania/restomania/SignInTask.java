package com.restomania.restomania;

/**
 * Created by Freemahn on 26.10.2014.
 */

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by Freemahn on 19.10.2014.
 */
public class SignInTask extends AsyncTask<String, Void, String> {

    String login, hash;

    @Override
    protected String doInBackground(String... strings) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("login", strings[0]));
        list.add(new BasicNameValuePair("hash", strings[1]));
        String answer = makePostRequest("http://104.131.184.188:8080/restoserver/signIn", list);
        Gson gson = new Gson();
        String b = gson.fromJson(answer, Token.class).token;
        return b;
    }

    public static String makePostRequest(String url, List<NameValuePair> nameValuePairs) {
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
}
