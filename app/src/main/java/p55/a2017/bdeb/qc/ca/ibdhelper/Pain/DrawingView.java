package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Debug;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class DrawingView extends View {
    public static final int ARRAY_SIZE = 10;
    private static final int STROKE_WIDTH_PEN = 50;
    private static final int STROKE_WIDTH_ERASER = 100;
    private EventEmitter onDraw = new EventEmitter();

    private boolean[][] position = new boolean[ARRAY_SIZE][ARRAY_SIZE];

    private float mX;
    private float mY;
    private static final float TOUCH_TOLERANCE = 4;
    private Paint mPaintAdd;
    private Paint mPaintErase;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;

    private boolean drawState = false;
    private boolean eraseState = false;

    public DrawingView(Context context) {
        super(context);
        mPaintAdd = new Paint();
        mPaintAdd.setAntiAlias(true);
        mPaintAdd.setColor(0xFFFF0000);
        mPaintAdd.setStyle(Paint.Style.STROKE);
        mPaintAdd.setStrokeCap(Paint.Cap.ROUND);
        mPaintAdd.setStrokeWidth(STROKE_WIDTH_PEN);

        mPaintErase = new Paint();
        mPaintErase.setAntiAlias(true);
        mPaintErase.setStyle(Paint.Style.STROKE);
        mPaintErase.setStrokeCap(Paint.Cap.ROUND);
        mPaintErase.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaintErase.setStrokeWidth(STROKE_WIDTH_ERASER);

        mPath = new Path();
        mBitmapPaint = new Paint();
        mBitmapPaint.setColor(Color.argb(128, 255, 0, 0));
    }

    public void setOnDrawListener(Observer e) {
        onDraw.subscribe(e);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        if (getDrawState()) {
            canvas.drawPath(mPath, mPaintAdd);
        }
        else {
            canvas.drawPath(mPath, mPaintErase);
        }
    }

    private void touch_start(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

        updatePosArray((int) x,(int) y);
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }

        updatePosArray((int) x,(int) y);
    }
    private void touch_up() {
        mPath.lineTo(mX, mY);
        if (getDrawState()) {
            mCanvas.drawPath(mPath, mPaintAdd);
        }
        else {
            mCanvas.drawPath(mPath, mPaintErase);
        }
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!drawState && !eraseState) {
            return false;
        }

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                onDraw.next(false);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                onDraw.next(true);
                invalidate();
                break;
        }

        //debugArray();

        return true;
    }

    private void updatePosArray(int x, int y) {
        boolean insertValue;
        int diameter;
        if (getDrawState()) {
            insertValue = true;
            diameter = STROKE_WIDTH_PEN;
        }
        else if (getEraseState()){
            insertValue = false;
            diameter = STROKE_WIDTH_ERASER;
        }
        else {
            return;
        }

        int tabX, tabY;
        int radius = diameter / 2;
        for (int i = 0; i < radius; i++) {
            tabX = (x + i) * position.length / mCanvas.getWidth();
            tabY = (y + i) * position.length / mCanvas.getHeight();

            if (tabX >= position.length) {
                tabX = position.length - 1;
            }
            if (tabX < 0) {
                tabX = 0;
            }
            if (tabY >= position.length) {
                tabY = position.length - 1;
            }
            if (tabY < 0) {
                tabY = 0;
            }

            position[tabX][tabY] = insertValue;
        }
    }

    private void debugArray() {
        StringBuilder array = new StringBuilder();

        array.append("  ");
        for (int i = 0; i < position.length; i++) {
            array.append(i).append(" ");
        }
        array.append("\n");

        for (int i = 0; i < position.length; i++) {
            array.append(i).append(" ");
            for (int j = 0; j < position[i].length; j++) {
                if (position[j][i]) {
                    array.append("X ");
                }
                else {
                    array.append("0 ");
                }

            }
            array.append("\n");
        }

        Log.d("Array", array.toString());
    }

    public void changeDrawState() {
        drawState = !drawState;
    }

    public void changeEraseState() {
        eraseState = !eraseState;
    }

    public boolean getDrawState() {
        return drawState;
    }

    public boolean getEraseState() {
        return eraseState;
    }

    public void clearCanvas() {
        Toast.makeText(getContext(), "Clear", Toast.LENGTH_SHORT).show();
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
        position = new boolean[position.length][position.length];
    }
}