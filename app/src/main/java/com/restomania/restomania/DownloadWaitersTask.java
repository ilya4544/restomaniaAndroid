package com.restomania.restomania;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by Freemahn on 18.10.2014.
 */
public class DownloadWaitersTask extends AsyncTask<String, Void, Waiter[]> {
    String url = "http://104.131.184.188:8080/restoserver/";

    @Override
    protected Waiter[] doInBackground(String... arg) {
        try {
            Waiter[] result = getResult();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    Waiter[] getResult() throws IOException {
        String json = Jsoup.connect(url + "getWaiters").ignoreContentType(true).execute().body();
        Gson gson = new Gson();
        Waiter[] result = gson.fromJson(json, Waiter[].class);

        return result;

    }
}
