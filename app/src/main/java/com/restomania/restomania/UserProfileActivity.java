package com.restomania.restomania;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;

//user activity, has name, how many reviews he left, and balance
public class UserProfileActivity extends Activity implements View.OnClickListener {

    public static int countReview;
    private TextView nameFirst;
    private TextView nameLast;
    private TextView balance;
    TextView countReviewText;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Button rateWaiterButton = (Button) findViewById(R.id.button);
        ImageView iw = (ImageView) findViewById(R.id.imageView);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.m1);
        nameFirst = (TextView) findViewById(R.id.name1);
        nameLast = (TextView) findViewById(R.id.name2);
        GetUserInfoTask getUserInfoTask = new GetUserInfoTask(getIntent().getStringExtra("token"));
        getUserInfoTask.execute((Void) null);
        countReviewText = (TextView) findViewById(R.id.count_review);
        countReviewText.setText("Отзывы:       " + countReview);


        bm = getRoundedCornerBitmap(bm, 150);
        iw.setImageBitmap(bm);

        rateWaiterButton.setOnClickListener(this);
    }

    //dont touch this
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
    public void onClick(View v) {
        Intent intent = new Intent(this, WaiterListActivity.class);
        intent.putExtra("token", user.token);
        intent.putExtra("userId", user.id);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        countReviewText.setText("Отзывы:       " + countReview);
        //String name = data.getStringExtra("name");
        //tvName.setText("Your name is " + name);
    }

    public class GetUserInfoTask extends AsyncTask<Void, Void, Void> {

        final String url = "http://104.131.184.188:8080/restoserver/";
        final String mToken;

        GetUserInfoTask(String token) {
            mToken = token;
        }

        @Override
        protected Void doInBackground(Void... strings) {
            long time = System.currentTimeMillis();
            String json = null;
            try {
                json = Jsoup.connect(url + "getUserProfile?token=" + mToken).ignoreContentType(true).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            Log.d("USER", json + "");
            user = gson.fromJson(json, User.class);
            Log.d("GETUSER", (System.currentTimeMillis() - time) + "");
            return null;
        }

        @Override
        protected void onPostExecute(final Void success) {
            nameFirst.setText(user.name.split(" ")[0]);
            nameLast.setText(user.name.split(" ")[1]);
        }
    }
}