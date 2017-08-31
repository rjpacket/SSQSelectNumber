### 一、先看效果

![ssqSelectNumber.gif](http://upload-images.jianshu.io/upload_images/5994029-4b5fff52805d8f7a.gif?imageMogr2/auto-orient/strip)

### 二、准备工作
典型的自定义view，把这个view当作一个对象来看，先撸撸这个view的属性，上面的动态图直观的可以看到以下几点：

1. **上下左右的padding**
2. **球与球之间的space**
3. **每行球的个数**（球列数需要吗？答：不需要，总球数除以行数就是列数了。）
4. **是否显示遗漏**（遗漏文字也是有颜色的，这个不是view的属性，可以单独设置在球上面）
5. **球选中的颜色，未选中的颜色** （这个可以是小球的属性，但是由于一个选号区所有的球都是一样的，可以看成view的属性了）
6. **球选中的背景，未选中的背景** （理由同上）

既然是选号，每一个球也可以看成对象，球的属性要考虑好，涉及后面的位置计算：

1. **球的号码**
2. **球选中的颜色，未选中的颜色**
3. **球选中的背景，未选中的背景**
4. **遗漏值，遗漏值的颜色**
5. **球位置（left, top, right, bottom）** （这里记录球所在的矩形区域的上下左右位置，为什么？留给大家思考）
6. **遗漏位置** （同上）

当然一开始肯定考虑的不是这么全面，还有限制最多选多少球，已选的号码后面选球区不能重复等，就靠大家一步步完善了。

### 三、小球建模
```java
public class Ball {
    private String number;                      // 号码
    private boolean isSelected;                 // 是否选中
    private int left;                           // 球左坐标
    private int top;                            // 球上坐标
    private int right;                          // 球右坐标
    private int bottom;                         // 球下坐标

    private float x;                            // 球圆心x
    private float y;                            // 球圆心y

    private int mLeft;                          // 遗漏左坐标
    private int mTop;                           // 遗漏上坐标
    private int mRight;                         // 遗漏右坐标
    private int mBottom;                        // 遗漏下坐标
    private String missValue = "8";             // 遗漏值
    private int missValueColor = Color.RED;     // 遗漏文字颜色
}
```
上面有一个圆心，但是如果利用圆心绘制文字的话，文字不能居中，如果要让它居中，还需要再绘制的时候计算，效率上就不是很高，所以舍弃了。同时要给球暴漏两个很重要的方法：
```java
/**
     * 设置球的位置
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setRect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

/**
     * 设置遗漏的位置
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setMissRect(int left, int top, int right, int bottom) {
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
    }
```
这两个方法就是绘制很顺滑的关键，后面再细说。

### 四、绘制选号区
依据步骤二我们先在属性文件里列好自定义的属性：
```xml
<declare-styleable name="SelectBallsView">
        <attr name="numCount" format="integer"/>
        <attr name="start" format="integer"/>
        <attr name="end" format="integer"/>
        <attr name="hasZero" format="boolean"/>
        <attr name="ballColor" format="color"/>
        <attr name="txtSelectedColor" format="color"/>
        <attr name="txtUnselectedColor" format="color"/>
        <attr name="drawableSelected" format="reference|integer"/>
        <attr name="drawableUnselected" format="reference|integer"/>
    </declare-styleable>
```
初始化的时候拿到这些属性，在绘制之前，我们需要计算好每一个球的位置，那什么时候可以计算呢？答案就是onMeasure！！！一旦布局测量好宽度，我们每一个球的位置都是可以计算的：
```
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
                cb.setRect((i % numCount) * ballWidth + i % numCount * space + padding,
                        (i / numCount) * ballHeight + i / numCount * (space + missHeight) + padding,
                        (i % numCount + 1) * ballWidth + i % numCount * space + padding,
                        (i / numCount + 1) * ballHeight + i / numCount * (space + missHeight) + padding);
                cb.setMissRect((i % numCount) * ballWidth + i % numCount * space + padding,
                        (i / numCount) * ballHeight + i / numCount * (space + missHeight) + padding + ballHeight,
                        (i % numCount + 1) * ballWidth + i % numCount * space + padding,
                        (i / numCount + 1) * ballHeight + i / numCount * (space + missHeight) + padding + missHeight);
            } else {
                cb.setRect((i % numCount) * ballWidth + i % numCount * space + padding,
                        (i / numCount) * ballHeight + i / numCount * space + padding,
                        (i % numCount + 1) * ballWidth + i % numCount * space + padding,
                        (i / numCount + 1) * ballHeight + i / numCount * space + padding);
            }
        }
    }
```
这里就是计算并保存球的上下左右坐标信息，一来避免onDraw的时候计算，二来方便绘制文字的位置。
有了小球的信息，接下来就是简单的绘制了：
```
@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = balls.size();
        for (int i = 0; i < size; i++) {
            Ball cb = balls.get(i);
            RectF rectF = new RectF(cb.getLeft(), cb.getTop(), cb.getRight(), cb.getBottom());
            if (bitmapSelected != null || bitmapUnselected != null) {
                //绘制图片
                canvas.drawBitmap(cb.isSelected() ? bitmapSelected : bitmapUnselected, null,
                        new Rect(cb.getLeft(), cb.getTop(), cb.getRight(), cb.getBottom()), ballPaint);
            } else {
                //绘制小球边框和小球
                canvas.drawArc(new RectF(cb.getLeft() - strokeWidth, cb.getTop() - strokeWidth,
                        cb.getRight() + strokeWidth, cb.getBottom() + strokeWidth), 0, 360, false, circlePaint);
                ballPaint.setColor(cb.isSelected() ? ballColor : Color.WHITE);
                canvas.drawOval(rectF, ballPaint);
            }
            //绘制文字
            txtPaint.setColor(cb.isSelected() ? txtSelectedColor : txtUnselectedColor);
            canvas.drawText(cb.getNumber(), rectF.centerX(), rectF.centerY() - txtMidValue, txtPaint);
            //绘制遗漏
            if (showMissValue) {
                msPaint.setColor(cb.getMissValueColor());
                RectF missRectF = new RectF(cb.getmLeft(), cb.getmTop(), cb.getmRight(), cb.getmBottom());
                canvas.drawText(cb.getMissValue(), missRectF.centerX(), missRectF.centerY() - missMidValue, msPaint);
            }
        }
    }
```
onDraw里面就是遍历所有的球，首先看有没有设置选号区的小球背景，没有就绘制球，有的话直接绘制bitmap，着重看下绘制文字：
```
RectF rectF = new RectF(cb.getLeft(), cb.getTop(), cb.getRight(), cb.getBottom());
//绘制文字
txtPaint.setColor(cb.isSelected() ? txtSelectedColor : txtUnselectedColor);
canvas.drawText(cb.getNumber(), rectF.centerX(), rectF.centerY() - txtMidValue, txtPaint);
```
先定义一个绘制的RectF对象，就是当前小球的区域，文字的位置是rectF.centerX()和rectF.centerY() - txtMidValue，这个txtMidValue是怎么计算的呢？
```
        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setTextSize(sp2px(mContext, 14));
        txtPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        txtMidValue = (fontMetrics.top + fontMetrics.bottom) / 2;
```
原理的话，参考百度词条，“canvas绘制文字如何居中”。在此不罗嗦了。绘制完之后，需要响应点击事件，最好的办法是重写onTouchEvent事件：
```
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
```
手指抬起的时候，判断一下点是不是落在当前小球身体里，是的话就跳出循环，调用一requestLayout()和invalidate()，刷新一下布局。

### 五、示例
新建布局：
```
<ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        >
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            >
            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="显示/隐藏遗漏"
                android:onClick="showMiss"
                />

            <com.taovo.rjp.ssqselectnumber.SelectBallsView
                android:id="@+id/view_1"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:numCount="10"
                app:start="0"
                app:end="9"
                app:hasZero="false"
                app:ballColor="#ee00ff"
                app:txtSelectedColor="#ffffff"
                app:txtUnselectedColor="#ee00ff"
                />

            <com.taovo.rjp.ssqselectnumber.SelectBallsView
                android:id="@+id/view_2"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:numCount="9"
                app:start="1"
                app:end="33"
                app:hasZero="true"
                app:ballColor="#ff0066"
                app:txtSelectedColor="#ffffff"
                app:txtUnselectedColor="#ff0066"
                />

            <com.taovo.rjp.ssqselectnumber.SelectBallsView
                android:id="@+id/view_3"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:numCount="8"
                app:start="1"
                app:end="16"
                app:hasZero="true"
                app:ballColor="#0088ff"
                app:txtSelectedColor="#ffffff"
                app:txtUnselectedColor="#0088ff"
                />

            <com.taovo.rjp.ssqselectnumber.SelectBallsView
                android:id="@+id/view_4"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:numCount="7"
                app:start="1"
                app:end="6"
                app:hasZero="false"
                app:ballColor="#00ff26"
                app:txtSelectedColor="#ffffff"
                app:txtUnselectedColor="#00ff26"
                />

            <com.taovo.rjp.ssqselectnumber.SelectBallsView
                android:id="@+id/view_5"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:numCount="6"
                app:start="1"
                app:end="6"
                app:hasZero="false"
                app:ballColor="#00ff26"
                app:txtSelectedColor="#ffffff"
                app:txtUnselectedColor="#00ff26"
                app:drawableSelected="@mipmap/basketball"
                app:drawableUnselected="@mipmap/football"
                />

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="确定"
                android:onClick="confirm"
                />
        </LinearLayout>
    </ScrollView>
```
mainActivity两个响应方法：
```
public void showMiss(View view){
        view1.setShowMissValue(!view1.getShowMissValue());
        view2.setShowMissValue(!view2.getShowMissValue());
        view3.setShowMissValue(!view3.getShowMissValue());
        view4.setShowMissValue(!view4.getShowMissValue());
        view5.setShowMissValue(!view5.getShowMissValue());
    }

    public void confirm(View view){
        String str1 = view1.getSelectBallsString();
        String str2 = view2.getSelectBallsString();
        String str3 = view3.getSelectBallsString();
        String str4 = view4.getSelectBallsString();
        String str5 = view5.getSelectBallsString();
        Toast.makeText(this, "选中的号码是:\n" + str1 + "\n" + str2 + "\n" + str3 + "\n" + str4 + "\n" + str5, Toast.LENGTH_SHORT).show();
    }
```
完事。效果就是一开始的示例效果了。有兴趣可以下载源码下来跑一跑，附上链接
 [GayHub](https://github.com/rjpacket/SSQSelectNumber)