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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class WaiterActivity extends Activity implements View.OnClickListener {
    String userId;
    String waiterId;
    RatingBar rb;
    EditText reviewEdit;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);
        TextView n = (TextView) findViewById(R.id.waiter_name);
        ImageView iw = (ImageView) findViewById(R.id.imView);
        Button ok = (Button) findViewById(R.id.ok_btn);
        reviewEdit = (EditText) findViewById(R.id.review);
        rb = (RatingBar) findViewById(R.id.ratingBar);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        waiterId = intent.getStringExtra("waiterId");
        String waiterName = intent.getStringExtra("waiterName");
        token = intent.getStringExtra("token");
        n.setText(waiterName);
        rb.setRating((Float.parseFloat(intent.getStringExtra("waiterRating")) / 2));
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
        iw.setImageBitmap(bm);
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
        String rating = String.valueOf((int) (rb.getRating() * 2));
        new UploadingReviewTask().execute(String.valueOf(userId),
                String.valueOf(waiterId), String.valueOf(rating),
                reviewEdit.getText().toString(), token);
        Log.d("Rating", "" + rating);
        UserProfileActivity.countReview++;
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
