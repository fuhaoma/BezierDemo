package mfh.com.bezierdemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import mfh.com.bezierdemo.R;

/**
 * Created by Blessed-tec1 on 2017/7/7.
 */

public class AdvanceView extends RelativeLayout {
    private int[] colors = {Color.TRANSPARENT, Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.BLACK, Color.LTGRAY, Color.GREEN, Color.RED};
    private Path mPath;
    private Paint mPaint;
    private Random random;
    private boolean isInit;//判断是否初始化坐标
    protected PointF pointFStart, pointFEnd, pointFLeft, pointFRight;
    protected Bitmap bitmap;

    public AdvanceView(Context context) {
        this(context, null);
    }

    public AdvanceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvanceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.WHITE);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPath = new Path();
        //四个点的坐标
        pointFStart = new PointF();
        pointFEnd = new PointF();
        pointFLeft = new PointF();
        pointFRight = new PointF();
        //随机生成心的颜色
        random = new Random();
        //获得图片的bitmap
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPoint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(pointFStart.x, pointFStart.y, 10, mPaint);
        canvas.drawCircle(pointFEnd.x, pointFEnd.y, 10, mPaint);
        canvas.drawCircle(pointFLeft.x, pointFLeft.y, 10, mPaint);
        canvas.drawCircle(pointFRight.x, pointFRight.y, 10, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#00bbbb"));
        mPath.moveTo(pointFStart.x, pointFStart.y);
        mPath.cubicTo(pointFRight.x, pointFRight.y, pointFLeft.x, pointFLeft.y, pointFEnd.x, pointFEnd.y);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
    }

    protected void initPoint() {
        //底部
        pointFStart.x = getMeasuredWidth() / 2;
        pointFStart.y = getMeasuredHeight() - 10;
        //左
        pointFLeft.x = 10;
        pointFLeft.y = getMeasuredHeight() / 4;
        //右
        pointFRight.x = getMeasuredWidth() - 10;
        pointFRight.y = getMeasuredHeight() * 3 / 4;
        //上
        pointFEnd.x = getMeasuredWidth() / 2;
        pointFEnd.y = 10;
        isInit = true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Button button = new Button(getContext());
        button.setText("添加一个心");
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addHeart();
            }
        });
        addView(button);
    }

    public void addHeart() {
        ImageView imageView = new ImageView(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_HORIZONTAL);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        imageView.setImageBitmap(drawHeart(colors[random.nextInt(colors.length)]));
        addView(imageView, layoutParams);
        moveHeart(imageView);
    }

    private Bitmap drawHeart(int color) {
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        Bitmap bitmap = Bitmap.createBitmap(this.bitmap.getWidth(), this.bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(this.bitmap, 0, 0, mPaint);
        canvas.drawColor(color, PorterDuff.Mode.SRC_ATOP);
        canvas.setBitmap(null);
        return bitmap;
    }

    private void moveHeart(final ImageView imageView) {
        if (!isInit) initPoint();
        PointF pointFStart = this.pointFStart;
        PointF pointFEnd = this.pointFEnd;
        PointF pointFLeft = this.pointFLeft;
        PointF pointFRight = this.pointFRight;
        ValueAnimator animator = ValueAnimator.ofObject(new MyTypeEvaluator(pointFRight, pointFLeft), pointFStart, pointFEnd);
        //移动监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF value = (PointF) animation.getAnimatedValue();
                imageView.setX(value.x);
                imageView.setY(value.y);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                AdvanceView.this.removeView(imageView);
            }
        });
        //透明度监听
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.9f, 0.6f, 0.4f, 0.2f, 0);
        alpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                AdvanceView.this.removeView(imageView);
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.play(animator).with(alpha);
        set.start();
    }

    /**
     * 绘制一个增值期
     */
    class MyTypeEvaluator implements TypeEvaluator<PointF> {
        private PointF start, end;

        public MyTypeEvaluator(PointF start, PointF end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF result = new PointF();
            float left = 1 - fraction;
            result.x = (float) (startValue.x * Math.pow(left, 2) +
                    3 * start.x * Math.pow(left, 2) * fraction +
                    3 * end.x * Math.pow(fraction, 2) * left +
                    endValue.x * Math.pow(fraction, 3));
            result.y = (float) (startValue.y * Math.pow(left, 3) +
                    3 * start.y * Math.pow(left, 2) * fraction +
                    3 * end.y * Math.pow(fraction, 2) * left +
                    endValue.y * Math.pow(fraction, 3));
            return result;
        }
    }
}
