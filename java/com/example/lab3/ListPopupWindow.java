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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(w, 80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("onDraw" + result);
        super.onDraw(canvas);
        paint.setColor(Color.BLUE);
        paint.setTextSize(40f);
        canvas.drawText(result, 0, paint.getTextSize(), paint);
    }

}