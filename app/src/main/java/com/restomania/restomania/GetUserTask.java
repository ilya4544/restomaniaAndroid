package com.restomania.restomania;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Freemahn on 19.10.2014.
 */
public class GetUserTask extends AsyncTask<String, Void, User> {
    @Override
    protected User doInBackground(String... strings) {
        try {
            return getResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    User getResult() throws IOException {
        String json = Jsoup.connect("http://91.225.131.187:8080/restoserver/getUserProfile?id=1").ignoreContentType(true).execute().body();
        Gson gson = new Gson();
        User result = gson.fromJson(json, User.class);
        //Log.d("Waiters:", Arrays.toString(result));
        return result;

    }
}
