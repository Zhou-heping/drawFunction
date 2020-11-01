package com.example.drawfunction;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {
    private String strFunction;
    public double zoom = 1;//缩放倍数，默认为1
    public CustomView(Context context) {
        super(context);
    }
    public CustomView(Context context,AttributeSet attrs) {
        super(context,attrs);
    }
    public CustomView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
    }
    public void setStrFunction(String strFunction) {
        this.strFunction = strFunction;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (strFunction == null || strFunction.length() == 0)
            return;
        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.right = getWidth();
        rect.bottom = getHeight();

        //绘制坐标
        Axis axis = new Axis(rect,zoom);
        axis.draw(canvas);
        //绘制函数图形
        Plot plot = new Plot(axis, strFunction, "x");
        plot.draw(canvas);
    }
}
