package com.restomania.restomania;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by Freemahn on 18.10.2014.
 */
public class DownloadingWaitersListTask extends AsyncTask<String, Void, Waiter[]> {
    String url = "http://104.131.184.188:8080/restoserver/";

    @Override
    protected Waiter[] doInBackground(String... arg) {
        try {

            String json = Jsoup.connect(url + "getWaiters").ignoreContentType(true).execute().body();
            Gson gson = new Gson();
            return gson.fromJson(json, Waiter[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
