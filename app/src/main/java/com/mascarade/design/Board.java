package com.mascarade.design;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by melanie on 3/30/16.
 */
public class Board extends View {

    private static final String BOARD = "BOARD";

    public Board(Context context) {
        super(context);
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Board(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        float x1 = (float) (0.25 * getWidth());
        float y1 = (float) (0.25 * getHeight());
        float x2 = (float) (0.75 * getWidth());
        float y2 = (float) (0.75 * getHeight());
        Log.d(BOARD, "width : " + getWidth() + " height : " + getHeight());
        Log.d(BOARD, "x1 : " + x1 + " y1 : " + y1);
        Log.d(BOARD, "x2 : " + x2 + " y2 : " + y2);
        RectF oval1 = new RectF(x1, y1, x2, y2);
        canvas.drawOval(oval1, paint);
/*
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        RectF oval2 = new RectF(50, 50, 150, 150);
        canvas.drawOval(oval2, paint);

        paint.setColor(Color.BLUE);
        RectF oval3 = new RectF(250, 50, 350, 400);
        canvas.drawOval(oval3, paint);
        */
    }

}