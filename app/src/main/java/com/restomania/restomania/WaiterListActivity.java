package com.restomania.restomania;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class WaiterListActivity extends ListActivity {
    String userId;
    String token;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        userId = getIntent().getStringExtra("userId");
        token = getIntent().getStringExtra("token");
        Waiter[] waiters = null;
        try {
            waiters = new DownloadingWaitersListTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("Waiters:", Arrays.toString(waiters));
        //TODO make good adapter
        ArrayAdapter<Waiter> adapter = new ArrayAdapter<Waiter>(this,
                R.layout.row_layout, R.id.name_waiter, waiters);
        setListAdapter(adapter);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Toast.makeText(getApplicationContext(), view + " selected", Toast.LENGTH_LONG).show();
        Waiter waiter = (Waiter) getListAdapter().getItem(position);
        Intent intent = new Intent(this, WaiterActivity.class);
        //TODO how can replace this ugly code?
        intent.putExtra("userId", userId);
        intent.putExtra("waiterName", waiter.name);
        intent.putExtra("waiterId", String.valueOf(waiter.id));
        intent.putExtra("waiterRating", "" + waiter.rating);
        intent.putExtra("token", "" + token);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        setResult(RESULT_OK, getIntent());
        finish();
        //if(resultCode)
    }

}
