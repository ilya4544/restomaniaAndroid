package com.restomania.restomania;

import android.os.AsyncTask;

/**
 * Created by Freemahn on 18.10.2014.
 */
public class DownloadWaitersTask extends AsyncTask<String, Void, Waiter[]> {
    @Override
    protected Waiter[] doInBackground(String... strings) {
        //TODO get waiters from server

        Waiter[] waiters = new Waiter[3];
        waiters[0] = new Waiter("Вася", 1);
        waiters[1] = new Waiter("Джон", 4.9);
        waiters[2] = new Waiter("Семен", 5);
        return waiters;
    }
}
