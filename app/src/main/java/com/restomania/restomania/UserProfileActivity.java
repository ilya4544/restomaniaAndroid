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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//user activity, has name, how many reviews he left, and balance
public class UserProfileActivity extends Activity implements View.OnClickListener {
    public static int countReview;
    private TextView nameFirst;
    private TextView nameLast;
    private TextView balance;
    TextView countReviewText;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn = (Button) findViewById(R.id.button);
        String name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        nameFirst = (TextView) findViewById(R.id.name1);
        nameLast = (TextView) findViewById(R.id.name2);
        countReviewText = (TextView) findViewById(R.id.count_review);
        countReviewText.setText("Отзывы:       " + countReview);
        nameFirst.setText(name.split(" ")[0]);
        nameLast.setText(name.split(" ")[1]);
        ImageView iw = (ImageView) findViewById(R.id.imageView);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.user);
        bm = getRoundedCornerBitmap(bm, 150);
        iw.setImageBitmap(bm);

        btn.setOnClickListener(this);
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
        intent.putExtra("token", getIntent().getStringExtra("token"));
        intent.putExtra("userId", id);
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
}
