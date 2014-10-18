package com.restomania.restomania;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WaiterListActivity extends ListActivity {
    String userId;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //List<Waiter> waiters = new DownloadWaitersTask().doInBackground();
        userId = getIntent().getStringExtra("user_id");
        Waiter[] waiters = null;
        try {
            waiters = new DownloadWaitersTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("Waiters:", Arrays.toString(waiters));
        ArrayAdapter<Waiter> adapter = new ArrayAdapter<Waiter>(this,
                R.layout.row_layout, R.id.label, waiters );
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Waiter item = (Waiter) getListAdapter().getItem(position);
        // Toast.makeText(this, item.name + " selected", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, WaiterActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("name", item.name);
        intent.putExtra("id", String.valueOf(item.id));
        intent.putExtra("rating", "" + item.rating);
        startActivity(intent);
    }
}
