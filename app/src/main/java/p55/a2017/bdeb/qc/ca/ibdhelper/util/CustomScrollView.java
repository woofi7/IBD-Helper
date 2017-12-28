package p55.a2017.bdeb.qc.ca.ibdhelper.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
    private boolean enableScrolling = true;

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isEnableScrolling() && super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isEnableScrolling() && super.onTouchEvent(ev);
    }

    public boolean isEnableScrolling() {
        return enableScrolling;
    }

    public void disableScrolling() {
        this.enableScrolling = false;
    }

    public void enableScrolling() {
        this.enableScrolling = true;
    }
}