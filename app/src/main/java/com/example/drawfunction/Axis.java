package com.example.drawfunction;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

//坐标系
public class Axis {
    private static  int nMinX = -10;
    private static int nMinY = -10;
    private static int nMaxX = 10;
    private static int nMaxY = 10;
    private double room;
    private Rect mRect; //物理范围
    public Axis(Rect rect,double room){
        nMinX = (int)(nMinX * room);
        nMaxX = (int)(nMaxX * room);
        nMinY = (int)(nMinY * room);
        nMaxY = (int)(nMaxY * room);
        this.room = room;
        mRect = rect;
    }

    //将逻辑坐标转换为物理坐标
    public int convertXLP2DP(double x) {
        return mRect.left + (int) (mRect.width() / (double) (nMaxX - nMinX) * (x - nMinX));
    }
    //将逻辑坐标转换为物理坐标
    public int convertYLP2DP(double y) {
        return mRect.bottom - (int) (mRect.height() / (double) (nMaxY - nMinY) * (y - nMinX));
    }
    //绘制坐标轴
    public void draw(Canvas canvas) {
        //画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        double nX=(double)(nMaxX-nMinX)/(20*room);
        double nY=(double)(nMaxY-nMinY)/(20*room);

        //绘制坐标轴
        canvas.drawLine(convertXLP2DP(nMinX), convertYLP2DP(0), convertXLP2DP(nMaxX), convertYLP2DP(0), paint);//x轴
        canvas.drawLine(convertXLP2DP(0), convertYLP2DP(nMaxY), convertXLP2DP(0), convertYLP2DP(nMinY), paint);//y轴

        //绘制坐标轴上的坐标（数字）
        paint.setStrokeWidth(1);
        paint.setTextSize(30);
        canvas.drawText("0", convertXLP2DP(nX), convertYLP2DP(-nY), paint);//原点
        canvas.drawText(nMinX +"", convertXLP2DP(nMinX+nX), convertYLP2DP(-nY), paint);//x最小
        canvas.drawText(nMaxX+"",convertXLP2DP(nMaxX-nX), convertYLP2DP(-nY),paint);//x最大
        canvas.drawText(nMinY+"",convertXLP2DP(-nX), convertYLP2DP(nMinY+nY),paint);//y最小
        canvas.drawText(nMaxY+"",convertXLP2DP(-nX), convertYLP2DP(nMaxY-nY),paint);//y最大
    }
}
