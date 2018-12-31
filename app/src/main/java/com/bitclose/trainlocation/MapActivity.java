package com.bitclose.trainlocation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MapActivity extends AppCompatActivity {

    DemoView demoView;
    Paint paint = new Paint();
    Rect rect = new Rect();
    Map<String ,List<List<Float>>> pts;
    public static final String TAG = "LM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RailwayLines instance = RailwayLines.getInstance(this);
        demoView = new DemoView(this);
        try {
            pts = instance.getLines();
        } catch (JSONException e) {
            Log.e("LM", e.getMessage());
        }
        setContentView(demoView);
    }

    private class DemoView extends View {
        public DemoView(Context context){
            super(context);
        }

        @Override protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setStyle(Paint.Style.FILL);

            canvas.save();
            // canvas.translate(200, 200);

            // make the entire canvas white
            canvas.drawColor(Color.WHITE);

            // draw some text using STROKE style
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);

            for (String s : pts.keySet()) {
                List<List<Float>> line = pts.get(s);
                for(int i=0, length=line.size(); i < length -1; i++) {
                    float[] p = {line.get(i).get(0), line.get(i).get(1), line.get(i + 1).get(0), line.get(i + 1).get(1)};
                    Log.e(TAG, Arrays.toString(p));
                    canvas.drawLines(p, paint);
                }
            }

            // canvas.translate(0, 200);

            // draw some text using FILL style

            paint.setColor(Color.MAGENTA);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setTextSize(30);
            canvas.drawText("Style.FILL", 0, 0, paint);
        }
    }

}
