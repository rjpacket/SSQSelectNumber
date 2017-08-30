package com.taovo.rjp.ssqselectnumber;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gimpo create on 2017/8/25 19:28
 * @email : jimbo922@163.com
 */

public class SelectBallsView extends View {
    /**
     * 每列球个数 默认7个
     */
    private int numCount;
    /**
     * 开始数字
     */
    private int startNum;
    /**
     * 结束数字
     */
    private int endNum;
    /**
     * 球颜色
     */
    private int ballColor;
    /**
     * 是否为数字补全0
     */
    private boolean hasZero;
    /**
     * 是否显示遗漏
     */
    private boolean showMissValue = true;
    /**
     * 小球画笔
     */
    private Paint ballPaint;
    /**
     * 文字画笔
     */
    private Paint txtPaint;
    /**
     * 每列球个数 默认7个
     */
    private Paint msPaint;

    private List<Ball> balls = new ArrayList<>();
    private int width;
    private int height;
    /**
     * 球之间的空格
     */
    private int space;
    private int padding;
    private int ballWidth;
    private int ballHeight;
    private int missWidth;
    private int missHeight;
    private Bitmap bitmapSelected;
    private Bitmap bitmapUnselected;
    private int txtSelectedColor;
    private int txtUnselectedColor;
    private Context mContext;
    private Paint circlePaint;
    private int strokeWidth;
    private float txtMidValue;
    private float missMidValue;

    public SelectBallsView(Context context) {
        super(context);
        initView(context, null);
    }

    public SelectBallsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SelectBallsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        mContext = context;
        Resources resources = mContext.getResources();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SelectBallsView);
            numCount = a.getInteger(R.styleable.SelectBallsView_numCount, 7);
            startNum = a.getInteger(R.styleable.SelectBallsView_start, 0);
            endNum = a.getInteger(R.styleable.SelectBallsView_end, 33);
            ballColor = a.getColor(R.styleable.SelectBallsView_ballColor, Color.parseColor("#eb1c42"));
            txtSelectedColor = a.getColor(R.styleable.SelectBallsView_txtSelectedColor, Color.parseColor("#ffffff"));
            txtUnselectedColor = a.getColor(R.styleable.SelectBallsView_txtUnselectedColor, Color.parseColor("#eb1c42"));
            hasZero = a.getBoolean(R.styleable.SelectBallsView_hasZero, true);
            bitmapSelected = BitmapFactory.decodeResource(resources, a.getResourceId(R.styleable.SelectBallsView_drawableSelected, 0));
            bitmapUnselected = BitmapFactory.decodeResource(resources, a.getResourceId(R.styleable.SelectBallsView_drawableUnselected, 0));
        }

        ballPaint = new Paint();
        ballPaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.parseColor("#d4d4d4"));
        strokeWidth = dp2px(mContext, 1);
        circlePaint.setStrokeWidth(strokeWidth);

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setTextSize(sp2px(mContext, 14));
        txtPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        txtMidValue = (fontMetrics.top + fontMetrics.bottom) / 2;

        msPaint = new Paint();
        msPaint.setAntiAlias(true);
        msPaint.setTextSize(sp2px(mContext, 14));
        msPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics1 = msPaint.getFontMetrics();
        missMidValue = (fontMetrics1.top + fontMetrics1.bottom) / 2;

        padding = space = dp2px(context, 10);

        initBalls();
    }

    /**
     * 初始化球
     */
    private void initBalls() {
        balls.clear();
        for (int i = startNum; i <= endNum; i++) {
            Ball ball = new Ball();
            ball.setNumber(addZero(String.valueOf(i)));
            ball.setSelected(false);
            balls.add(ball);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        missWidth = ballWidth = ballHeight = (width - (numCount - 1) * space - padding * 2) / numCount;
        missHeight = missWidth / 2;
        computeLocation(ballWidth, ballHeight);

        int rows = balls.size() / numCount + ((balls.size() % numCount > 0) ? 1 : 0);
        if (showMissValue) {
            height = rows * (ballHeight + missHeight) + (rows - 1) * space + padding * 2;
        } else {
            height = rows * ballHeight + (rows - 1) * space + padding * 2;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * 计算球位置  文字位置
     *
     * @param ballWidth
     * @param ballHeight
     */
    private void computeLocation(int ballWidth, int ballHeight) {
        int size = balls.size();
        for (int i = 0; i < size; i++) {
            Ball cb = balls.get(i);
            if (showMissValue) {
                cb.setRect((i % numCount) * ballWidth + i % numCount * space + padding, (i / numCount) * ballHeight + i / numCount * (space + missHeight) + padding,
                        (i % numCount + 1) * ballWidth + i % numCount * space + padding, (i / numCount + 1) * ballHeight + i / numCount * (space + missHeight) + padding);
                cb.setMissRect((i % numCount) * ballWidth + i % numCount * space + padding, (i / numCount) * ballHeight + i / numCount * (space + missHeight) + padding + ballHeight,
                        (i % numCount + 1) * ballWidth + i % numCount * space + padding, (i / numCount + 1) * ballHeight + i / numCount * (space + missHeight) + padding + missHeight);
            } else {
                cb.setRect((i % numCount) * ballWidth + i % numCount * space + padding, (i / numCount) * ballHeight + i / numCount * space + padding,
                        (i % numCount + 1) * ballWidth + i % numCount * space + padding, (i / numCount + 1) * ballHeight + i / numCount * space + padding);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = balls.size();
        for (int i = 0; i < size; i++) {
            Ball cb = balls.get(i);
            RectF rectF = new RectF(cb.getLeft(), cb.getTop(), cb.getRight(), cb.getBottom());
            if (bitmapSelected != null || bitmapUnselected != null) {
                canvas.drawBitmap(cb.isSelected() ? bitmapSelected : bitmapUnselected, null, new Rect(cb.getLeft(), cb.getTop(), cb.getRight(), cb.getBottom()), ballPaint);
            } else {
                canvas.drawArc(new RectF(cb.getLeft() - strokeWidth, cb.getTop() - strokeWidth, cb.getRight() + strokeWidth, cb.getBottom() + strokeWidth), 0, 360, false, circlePaint);
                ballPaint.setColor(cb.isSelected() ? ballColor : Color.WHITE);
                canvas.drawOval(rectF, ballPaint);
            }
            txtPaint.setColor(cb.isSelected() ? txtSelectedColor : txtUnselectedColor);
            canvas.drawText(cb.getNumber(), rectF.centerX(), rectF.centerY() - txtMidValue, txtPaint);
            if (showMissValue) {
                msPaint.setColor(cb.getMissValueColor());
                RectF missRectF = new RectF(cb.getmLeft(), cb.getmTop(), cb.getmRight(), cb.getmBottom());
                canvas.drawText(cb.getMissValue(), missRectF.centerX(), missRectF.centerY() - missMidValue, msPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                int size = balls.size();
                for (int i = 0; i < size; i++) {
                    Ball tempBall = balls.get(i);
                    if (pointAtBall(tempBall, x, y)) {
                        tempBall.setSelected(!tempBall.isSelected());
                        requestLayout();
                        invalidate();
                        break;
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 这个点落在球上
     *
     * @param tempBall
     * @param x
     * @param y
     * @return
     */
    private boolean pointAtBall(Ball tempBall, float x, float y) {
        Rect rect = new Rect(tempBall.getLeft(), tempBall.getTop(), tempBall.getRight(), tempBall.getBottom());
        return rect.contains((int) x, (int) y);
    }

    /**
     * 需要补0的在前面直接补0
     *
     * @param num
     * @return
     */
    private String addZero(String num) {
        if (hasZero && num.length() == 1) {
            return "0" + num;
        }
        return num;
    }

    /**
     * 设置是否需要补0
     *
     * @param hasZero
     */
    public void setHasZero(boolean hasZero) {
        this.hasZero = hasZero;
        invalidate();
    }

    public void setShowMissValue(boolean showMissValue){
        this.showMissValue = showMissValue;
        requestLayout();
        invalidate();
    }

    public boolean getShowMissValue(){
        return showMissValue;
    }

    /**
     * 将sp值转换为px值
     */
    public int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
