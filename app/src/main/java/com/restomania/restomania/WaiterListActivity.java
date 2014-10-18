package com.restomania.restomania;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class WaiterListActivity extends ListActivity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Waiter[] waiters = new DownloadWaitersTask().doInBackground();
        ArrayAdapter<Waiter> adapter = new ArrayAdapter<Waiter>(this,
                R.layout.row_layout, R.id.label, waiters);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Waiter item = (Waiter) getListAdapter().getItem(position);
        Toast.makeText(this, item.name + " selected", Toast.LENGTH_LONG).show();
    }
}
