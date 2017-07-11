package mfh.com.bezierdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

/**
 * Created by Blessed-tec1 on 2017/7/7.
 */

public class ExpertView extends AdvanceView {

    private Random random = new Random();

    public ExpertView(Context context) {
        this(context, null);
    }

    public ExpertView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpertView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG", getMeasuredWidth() + "");
                pointFStart = new PointF();
                pointFLeft = new PointF();
                pointFRight = new PointF();
                pointFEnd = new PointF();

                pointFStart.x = getMeasuredWidth() / 2 - bitmap.getWidth() / 2;
                pointFStart.y = getMeasuredHeight() - bitmap.getHeight();

                pointFEnd.y = 0;
                pointFEnd.x = random.nextFloat() * getMeasuredWidth();

                pointFLeft.x = random.nextFloat() * getMeasuredWidth();
                pointFRight.x = getMeasuredWidth() - pointFLeft.x;
                pointFRight.y = random.nextFloat() * getMeasuredHeight() / 2 + getMeasuredHeight() / 2;
                pointFLeft.y = random.nextFloat() * getMeasuredHeight() / 2;
                Log.i("TAG", "出发了");
                addHeart();
                break;
        }
        return true;
    }
}
