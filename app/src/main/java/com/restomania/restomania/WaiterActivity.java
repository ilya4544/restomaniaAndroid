package com.restomania.restomania;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class WaiterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);
        TextView n = (TextView) findViewById(R.id.waiter_name);
        ImageView iw = (ImageView) findViewById(R.id.imView);
        Button b = (Button) findViewById(R.id.ok_btn);
        final RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
        Intent intent = getIntent();
        final String userId = intent.getStringExtra("user_id");
        final String id = intent.getStringExtra("id");
        n.setText(intent.getStringExtra("name"));
        rb.setRating((Float.parseFloat(intent.getStringExtra("rating")) / 2));
        Bitmap bm = null;
        if (id.equals("1"))
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.w1);
        if (id.equals("2"))
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.m1);
        if (id.equals("3"))
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.m2);
        if (id.equals("4"))
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.m3);
        bm = getRoundedCornerBitmap(bm, 150);
        iw.setImageBitmap(bm);
        final EditText editText = (EditText) findViewById(R.id.review);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float rat = rb.getRating();
                String rating = String.valueOf((int) (rat * 2));
                new SendingDataTask().execute(String.valueOf(userId), String.valueOf(id), String.valueOf(rating), editText.getText().toString());
                Log.d("Rating", "" + rating);
                StartActivity.count++;
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });

    }

    public Bitmap getRoundedCornerBitmap(final Bitmap source, final int radius) {
        final Bitmap output = Bitmap.createBitmap(source.getWidth(), source
                .getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        final RectF rect = new RectF(0.0f, 0.0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, radius, radius, paint);

        return output;
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
