package com.example.jh.graphicseditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static int EMPTY = 0, GRECTANGLE = 1, GCIRCLE = 2, GLINE = 3;
    static int GShape = EMPTY;
    static int GColor = Color.BLACK;
    static List<MyShape> myShapeList = new ArrayList<MyShape>();
    static MyShape myShape;


    private static class MyShape {
        int shapeType;
        int startX, startY, stopX, stopY;
        int color;


        public MyShape(int shape, int color) {
            shapeType = shape;
            this.color = color;
        }

        public void display(Canvas canvas) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(color);

            switch (shapeType) {
                case GRECTANGLE:
                    canvas.drawRect(startX, startY, stopX, stopY, paint);
                    break;
                case GCIRCLE:
                    int radius = (int) Math.sqrt(Math.pow(stopX - startX, 2) + Math.pow(stopY - startX, 2));
                    canvas.drawCircle(startX, startY, radius, paint);
                    break;
                case GLINE:
                    canvas.drawLine(startX, startY, stopX, stopY, paint);
                    break;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicView(this));
        setTitle("GraphicsEditor");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        SubMenu drawMenu = menu.addSubMenu("Change Shape");
        SubMenu editMenu = menu.addSubMenu("Edit");
        SubMenu colorMenu = menu.addSubMenu("Change Color");
        drawMenu.add(0, 1, 0, "Line");
        drawMenu.add(0, 2, 0, "Circle");
        drawMenu.add(0, 3, 0, "Rectangle");
        editMenu.add(0, 4, 0, "Clear");
        colorMenu.add(1, 5, 0, "Black");
        colorMenu.add(1, 6, 0, "Red");
        colorMenu.add(1, 7, 0, "Green");
        colorMenu.add(1, 8, 0, "Blue");
        colorMenu.add(1, 9, 0, "Cyan");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:
                GShape = GLINE;
                return true;
            case 2:
                GShape = GCIRCLE;
                return true;
            case 3:
                GShape = GRECTANGLE;
                return true;
            case 4:
                myShapeList.removeAll(myShapeList);
                recreate();
                return true;
            case 5:
                GColor = Color.BLACK;
                return true;
            case 6:
                GColor = Color.RED;
                return true;
            case 7:
                GColor = Color.GREEN;
                return true;
            case 8:
                GColor = Color.BLUE;
                return true;
            case 9:
                GColor = Color.CYAN;
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class GraphicView extends View {


        public GraphicView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    myShape = new MyShape(GShape, GColor);
                    myShape.startX = (int) event.getX();
                    myShape.startY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    myShape.stopX = (int) event.getX();
                    myShape.stopY = (int) event.getY();
                    myShapeList.add(myShape);
                    this.invalidate();
                    break;
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (MyShape myshapes : myShapeList) {
                myshapes.display(canvas);
            }
        }
    }
}