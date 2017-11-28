package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class DrawingView extends View {
    public static final int ARRAY_SIZE = 8;

    private EventEmitter onDraw = new EventEmitter();

    private boolean[][] position = new boolean[ARRAY_SIZE][ARRAY_SIZE];

    private Paint mPaint;
    private Canvas mCanvas;

    private boolean drawState = false;
    private boolean eraseState = false;

    public DrawingView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setOnDrawListener(Observer e) {
        onDraw.subscribe(e);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Bitmap mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getDrawState() || getEraseState()) {
            int cellHeigh = mCanvas.getHeight() / ARRAY_SIZE;
            int cellWidth = mCanvas.getWidth() / ARRAY_SIZE;
            for (int i = 0; i < position.length; i++) {
                for (int j = 0; j < position[i].length; j++) {
                    if (position[i][j]) {
                        Rect rect = new Rect(i* cellWidth,
                                j * cellHeigh + cellHeigh,
                                i* cellWidth + cellWidth,
                                j * cellHeigh);
                        canvas.drawRect(rect, mPaint);
                    }
                }
            }
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
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
                updatePosArray((int) x, (int) y);
                break;
            case MotionEvent.ACTION_MOVE:
                updatePosArray((int) x, (int) y);
                onDraw.next(false);
                break;
            case MotionEvent.ACTION_UP:
                onDraw.next(true);
                performClick();
                break;
        }
        invalidate();
        return true;
    }

    private void updatePosArray(int x, int y) {
        boolean insertValue;
        if (getDrawState()) {
            insertValue = true;
        }
        else if (getEraseState()){
            insertValue = false;
        }
        else {
            return;
        }

        int tabX, tabY;
        int cellHeigh = mCanvas.getHeight() / ARRAY_SIZE;
        int cellWidth = mCanvas.getWidth() / ARRAY_SIZE;
        tabX = x / cellWidth;
        tabY = y / cellHeigh;

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
        invalidate();
        position = new boolean[position.length][position.length];
    }
}