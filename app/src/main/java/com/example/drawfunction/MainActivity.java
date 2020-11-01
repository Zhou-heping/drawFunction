package com.example.drawfunction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private double nLenStart = 0;
    private double nLenEnd = 0;
    private CustomView customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText txtFunction= findViewById(R.id.txtFunction);
        customView=(CustomView)findViewById(R.id.plotview);
        final Button buttonPlot=(Button)findViewById(R.id.buttonPlot);
        buttonPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customView!=null){
                    String strFunction=txtFunction.getText().toString();
                    customView.setStrFunction(strFunction);
                    customView.invalidate();
                }
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int nCnt = event.getPointerCount();
        int n = event.getAction();
        if ((event.getAction() & MotionEvent.ACTION_MASK) ==
                MotionEvent.ACTION_POINTER_DOWN && 2 == nCnt){//有两个手指缩放
            for (int i = 0; i < nCnt; i++) {
                float x = event.getX(i);
                float y = event.getY(i);
                Point pt = new Point((int) x, (int) y);
            }
            int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
            int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));
            nLenStart = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
        }
        else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP && 2 == nCnt) {
            for (int i = 0; i < nCnt; i++) {
                float x = event.getX(i);
                float y = event.getY(i);
                Point pt = new Point((int) x, (int) y);
            }
            int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
            int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));
            nLenEnd = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
            if (nLenEnd > nLenStart){//通过两个手指开始距离和结束距离，来判断放大缩小
                //放大
               double zoom1 =nLenStart/nLenEnd;
               System.out.println("放大倍数"+zoom1);
               customView.zoom = zoom1  ;
               customView.invalidate();
                return super.onTouchEvent(event);

            } else {
                //缩小
                double zoom1 = nLenStart/nLenEnd;
                System.out.println("缩小倍数"+zoom1);
                customView.zoom = zoom1 ;
                customView.invalidate();
                return super.onTouchEvent(event);
            }
        }
        return super.onTouchEvent(event);
    }
    //选项菜单的创建和其选项的动作
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_help:
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}