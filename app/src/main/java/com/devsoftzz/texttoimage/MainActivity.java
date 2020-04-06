package com.devsoftzz.texttoimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage = findViewById(R.id.image);
        mImage.setImageBitmap(drawMultilineTextToBitmap(this,
                                R.drawable.whiteboard,
                                "Deven Vachhani, B.Tech ICT, Gandhinagar, Gujarat, IN. +919824978997",
                                96));

    }

    public Bitmap drawMultilineTextToBitmap(Context gContext, int gResId, String gText,float size) {

        // prepare canvas
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);

        // new antialiased Paint
        TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        // text color
        paint.setColor(Color.parseColor("#3F51B5"));
        // text size in pixels
        paint.setTextSize((int) (size * scale));
        //font-family
        Typeface bold = ResourcesCompat.getFont(gContext,R.font.robot_bolditalic);
        paint.setTypeface(bold);
        // text Shadow
        paint.setShadowLayer(2, size/10, size/10, Color.parseColor("#D6D6D6"));

        // set text width to canvas width minus padding
        int textWidth = canvas.getWidth() - (int) (100 * scale);

        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(gText,
                                                    paint,
                                                    textWidth,
                                                    Layout.Alignment.ALIGN_OPPOSITE,
                                                    1.0f,
                                                    0.0f,
                                                    false);

        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth)/2;
        float y = (bitmap.getHeight() - textHeight)/2;

        // draw text to the Canvas center
        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        // Adding TextBox to Background
        Bitmap Background = BitmapFactory.decodeResource(getResources(), R.drawable.backdrop).copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas1 = new Canvas(Background);

        float x1 = (Background.getWidth() - bitmap.getWidth())/2;
        float y1 = (Background.getHeight() - bitmap.getHeight())/2;

        canvas1.drawBitmap(bitmap, x1, y1, new Paint());

        return Background;
    }
}
