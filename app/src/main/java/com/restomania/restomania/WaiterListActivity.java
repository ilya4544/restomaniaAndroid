package com.restomania.restomania;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class WaiterListActivity extends ListActivity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        List<Waiter> waiters = new DownloadWaitersTask().doInBackground();
        ArrayAdapter<Waiter> adapter = new ArrayAdapter<Waiter>(this,
                R.layout.row_layout, R.id.label, waiters);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Waiter item = (Waiter) getListAdapter().getItem(position);
       // Toast.makeText(this, item.name + " selected", Toast.LENGTH_LONG).show();
        //TODO go to activity3 jsoup

        Intent intent = new Intent(this, WaiterActivity.class);
        intent.putExtra("name", item.name);
        intent.putExtra("rate", "" + item.rate);
        startActivity(intent);
    }
}
