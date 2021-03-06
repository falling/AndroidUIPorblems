package com.github.lzyzsd.androiduiproblems;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by bruce on 2/27/16.
 */
public class BusyOnDrawView extends View {

    private Paint mPaint = new Paint();
    private ExecutorService pool = Executors.newCachedThreadPool();

    public BusyOnDrawView(Context context) {
        this(context,null);
    }

    public BusyOnDrawView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BusyOnDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BusyOnDrawView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pool.execute(getCommand(canvas));

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        int radius = Math.min(getWidth(), getHeight()) / 2;
        canvas.drawCircle(getWidth()/2, getHeight()/2, radius, mPaint);
    }

    @NonNull
    private Runnable getCommand(final Canvas canvas) {
        return new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("canvas = [" + canvas + "]" + i);
                }
            }
        };
    }
}
