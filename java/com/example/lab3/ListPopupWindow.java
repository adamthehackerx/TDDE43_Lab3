package com.example.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ListPopupWindow extends View {
    Context context;
    String result;
    Paint paint = new Paint();

    public ListPopupWindow(Context context, String result) {
        super(context);
        this.context = context;
        this.result = result;
    }

    public ListPopupWindow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLUE);
        paint.setTextSize(40f);

        canvas.drawText(result, 0, paint.getTextSize(), paint);
    }

    @Override
    protected void onMeasure(int width, int height){
        setMeasuredDimension(width, height);
    }

    public ListPopupWindow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ListPopupWindow(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}