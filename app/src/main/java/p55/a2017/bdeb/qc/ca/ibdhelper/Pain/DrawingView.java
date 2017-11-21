package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Observer;

import p55.a2017.bdeb.qc.ca.ibdhelper.util.EventEmitter;

public class DrawingView extends View {
    private EventEmitter onDraw = new EventEmitter();

    private boolean[][] position = new boolean[64][64];

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
        mPaintAdd.setStrokeWidth(50);

        mPaintErase = new Paint();
        mPaintErase.setAntiAlias(true);
        mPaintErase.setStyle(Paint.Style.STROKE);
        mPaintErase.setStrokeCap(Paint.Cap.ROUND);
        mPaintErase.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaintErase.setStrokeWidth(100);

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

    private void updatePosArray(int x, int y) {
        int tabX = x * position.length / mCanvas.getWidth();
        int tabY = y * position.length / mCanvas.getHeight();

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

        if (getDrawState()) {
            position[tabX][tabY] = true;
        }
        if (getEraseState()){
            position[tabX][tabY] = false;
        }

        Pair<String, String> posXY = new Pair<>(String.valueOf(tabX), String.valueOf(tabY));
        onDraw.next(posXY);
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

        onDraw.next();
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
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
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