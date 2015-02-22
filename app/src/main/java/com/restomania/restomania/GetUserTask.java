package com.restomania.restomania;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by Freemahn on 19.10.2014.
 */
public class GetUserTask extends AsyncTask<String, Void, User> {
    String url = "http://104.131.184.188:8080/restoserver/";

    @Override
    protected User doInBackground(String... strings) {
        try {
            return downloadUserData(strings[0]);//token
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    User downloadUserData(String token) throws IOException {
        String json = Jsoup.connect(url + "getUserProfile?token=" + token).ignoreContentType(true).execute().body();
        Gson gson = new Gson();
        Log.d("USER", json);
        User result = gson.fromJson(json, User.class);
        //Log.d("Waiters:", Arrays.toString(result));
        return result;

    }
}
