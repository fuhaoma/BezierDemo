package mfh.com.bezierdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 该控件一定要加背景颜色，否则不显示
 * Created by Bessed-tec1 on 2017/7/7.
 */

public class BasicBezierView extends LinearLayout {
    private Paint mPaint;
    private Path mPath;
    private float startX, startY, endX, endY, touchX, touchY;
    private boolean isFill = true;

    public BasicBezierView(Context context) {
        this(context, null);
    }

    public BasicBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.WHITE);
        init();
    }

    private void init() {
        //初始化画笔，路径
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        startX = getMeasuredWidth() / 5;
        startY = endY = getMeasuredHeight() / 2;
        endX = getMeasuredWidth() * 4 / 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画直线
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.MAGENTA);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(20);
        canvas.drawText("这是一阶贝塞尔曲线,就一条直线,没什么好说的", startX, startY / 4, mPaint);
        mPaint.setColor(Color.RED);
        if (isFill) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        mPath.moveTo(startX, startY / 3);
        mPath.lineTo(endX, endY / 3);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
        //画贝塞尔曲线初始点和操作点
        canvas.drawCircle(startX, startY, 10, mPaint);
        canvas.drawCircle(endX, endY, 10, mPaint);
        canvas.drawCircle(touchX, touchY, 10, mPaint);
        //画贝塞尔曲线
        mPath.moveTo(startX, startY);
        mPath.quadTo(touchX, touchY, endX, endY);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        postInvalidate();
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final Button button = new Button(getContext());
        button.setText("不填充");
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isFill = !isFill;
                if (isFill) {
                    button.setText("不填充");
                } else {
                    button.setText("填充");
                }
            }
        });
        addView(button);
    }
}
