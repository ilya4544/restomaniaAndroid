package com.restomania.restomania;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


public class WaiterActivity extends Activity {
    private static final String USERID = "1122334455";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);
        TextView n = (TextView) findViewById(R.id.waiter_name);
        TextView r = (TextView) findViewById(R.id.waiter_rate);
        Button b = (Button) findViewById(R.id.ok_btn);
        final RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        n.setText(intent.getStringExtra("name"));
        r.setText(intent.getStringExtra("rating"));

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO send rate to server
                float rat = rb.getRating();
                String rating = String.valueOf((int) (rat * 2));
                new SendingDataTask().execute(new String[]{String.valueOf(USERID), String.valueOf(id), String.valueOf(rating)});
                Log.d("Rating", "" + rating);
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.waiter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
