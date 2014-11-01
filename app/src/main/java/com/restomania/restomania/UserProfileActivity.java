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


public class UserProfileActivity extends Activity {
    public static int count = 10;
    private TextView name;
    private TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn = (Button) findViewById(R.id.button);
        String name = getIntent().getStringExtra("name");
        final String id = getIntent().getStringExtra("id");
        final TextView n1 = (TextView) findViewById(R.id.name1);
        final TextView n2 = (TextView) findViewById(R.id.name2);
        TextView countReview = (TextView) findViewById(R.id.count_review);
        countReview.setText("Отзывы:       " + count);
        n1.setText(name.split(" ")[0]);
        n2.setText(name.split(" ")[1]);
        ImageView iw = (ImageView) findViewById(R.id.imageView);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.user);
        bm = getRoundedCornerBitmap(bm, 150);
        iw.setImageBitmap(bm);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), WaiterListActivity.class);
                intent.putExtra("user_id", id);
                //  intent.putExtra("rate", "" + b.getText());
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
}
