package com.restomania.restomania;

import android.os.AsyncTask;

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
public class DownloadWaitersTask extends AsyncTask<String, Void, ArrayList<Waiter>> {
    //    @Override
//    protected Waiter[] doInBackground(String... strings) {
//        //TODO get waiters from server
//
//        Waiter[] waiters = new Waiter[3];
//        waiters[0] = new Waiter("Вася", 1);
//        waiters[1] = new Waiter("Джон", 4.9);
//        waiters[2] = new Waiter("Семен", 5);
//        return waiters;
//    }
    Elements id;
    public ArrayList<String> waiterList = new ArrayList<String>();

    @Override
    protected ArrayList<Waiter> doInBackground(String... arg) {
        ArrayList<Waiter> result = null;
        try {
            String json = Jsoup.connect("http://91.225.131.187:8080/restoserver/getWaiters").ignoreContentType(true).execute().body();
            Gson gson = new Gson();
            Waiter[] waiters = gson.fromJson(json, Waiter[].class);
            result = new ArrayList<Waiter>();
            result.addAll(Arrays.asList(waiters));
        } catch (IOException e) {
            e.printStackTrace();
        }

    return result;
    }
}
