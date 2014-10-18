package com.restomania.restomania;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.jsoup.nodes.Document;
import org.jsoup.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Freemahn on 18.10.2014.
 */
public class DownloadWaitersTask extends AsyncTask<String, Void, Waiter[]> {

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
        String json = Jsoup.connect("http://91.225.131.187:8080/restoserver/getWaiters").ignoreContentType(true).execute().body();
        Gson gson = new Gson();
        Waiter[] result  = gson.fromJson(json, Waiter[].class);
        Log.d("Waiters:", Arrays.toString(result));
        return result;

    }
}
