package com.restomania.restomania;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;


public class WaiterActivity extends Activity implements View.OnClickListener {
    private String userId;
    private RatingBar waiterRatingbar;
    private EditText waiterReview;
    private String mToken;
    private Waiter waiter;
    private TextView waiterName;
    private ImageView waiterImg;
    private static String TAG = "WaiterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToken = getIntent().getStringExtra("token");
        setContentView(R.layout.activity_waiter);
        waiterName = (TextView) findViewById(R.id.text_waiter_name);
        waiterImg = (ImageView) findViewById(R.id.img_waiter);
        waiterReview = (EditText) findViewById(R.id.text_waiter_review);
        waiterRatingbar = (RatingBar) findViewById(R.id.rating_bar_waiter);
        new GetWaiterInfoTask(getIntent().getStringExtra("waiter_id"), mToken).execute();
        userId = getIntent().getStringExtra("user_id");
        Button ok = (Button) findViewById(R.id.btn_send_review);
        ok.setOnClickListener(this);

    }

    //TODO ah, dat ugly code again
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
        String rating = String.valueOf((int) (waiterRatingbar.getRating() * 2));
        String rev = waiterReview.getText().toString();
        //TODO good name of variable,fix rating
        String rat = String.valueOf((int) waiter.rating * 2);
        Log.e(TAG, "args:" + mToken + " " + waiter.id + " " + rev + " " + rat);
        new UploadingReviewTask().execute(mToken, String.valueOf(waiter.id), rev, rat);
        //Log.d("Rating", "" + rating);
        UserProfileActivity.countReview++;
        finish();
    }

    //TODO how unite this to GetUserInfo?
    public class GetWaiterInfoTask extends AsyncTask<Void, Void, Void> {

        final String url = "http://104.131.184.188:8080/restoserver/getWaiter?";
        final String mWaiterId;
        final String mToken;


        GetWaiterInfoTask(String waiterId, String token) {
            mWaiterId = waiterId;
            mToken = token;
        }

        @Override
        protected Void doInBackground(Void... strings) {
            String json = null;
            Log.e("WAITER_ACTIVITY", mWaiterId + " " + mToken + "!!!");
            try {
                json = Jsoup.connect(url + "waiterId=" + mWaiterId + "&mToken=" + mToken).
                        ignoreContentType(true).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("WAITER_ACTIVITY", json);
            Gson gson = new Gson();
            waiter = gson.fromJson(json, Waiter.class);
            Log.d("WAITER_ACTIVITY", waiter + "");
            return null;
        }

        @Override
        protected void onPostExecute(final Void success) {
            waiterName.setText(waiter.name);
            waiterRatingbar.setRating(waiter.rating / 2);
            Bitmap bm = null;
            //TODO get pictures from internet, save to localdb???
            if (userId.equals("1"))
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.w1);
            if (userId.equals("2"))
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.m1);
            if (userId.equals("3"))
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.m2);
            if (userId.equals("4"))
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.m3);
            bm = getRoundedCornerBitmap(bm, 150);
            waiterImg.setImageBitmap(bm);
        }
    }
}
