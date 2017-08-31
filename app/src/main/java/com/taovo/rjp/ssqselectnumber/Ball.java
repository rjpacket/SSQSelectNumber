package com.taovo.rjp.ssqselectnumber;

import android.graphics.Color;

/**
 * @author Gimpo create on 2017/8/29 15:38
 * @email : jimbo922@163.com
 */

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getMissValue() {
        return missValue;
    }

    public void setMissValue(String missValue) {
        this.missValue = missValue;
    }

    public int getMissValueColor() {
        return missValueColor;
    }

    public void setMissValueColor(int missValueColor) {
        this.missValueColor = missValueColor;
    }

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

    public void setTxtLocation(float x, float y) {
        this.x = x;
        this.y = y;
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

    public int getmLeft() {
        return mLeft;
    }

    public void setmLeft(int mLeft) {
        this.mLeft = mLeft;
    }

    public int getmTop() {
        return mTop;
    }

    public void setmTop(int mTop) {
        this.mTop = mTop;
    }

    public int getmRight() {
        return mRight;
    }

    public void setmRight(int mRight) {
        this.mRight = mRight;
    }

    public int getmBottom() {
        return mBottom;
    }

    public void setmBottom(int mBottom) {
        this.mBottom = mBottom;
    }
}
