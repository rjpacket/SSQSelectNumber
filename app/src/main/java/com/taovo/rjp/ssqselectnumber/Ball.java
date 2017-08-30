package com.taovo.rjp.ssqselectnumber;

import android.graphics.Color;

/**
 * @author Gimpo create on 2017/8/29 15:38
 * @email : jimbo922@163.com
 */

public class Ball {
    private String number;
    private boolean isSelected;
    private int left;
    private int top;
    private int right;
    private int bottom;

    private float x;
    private float y;

    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;
    private String missValue = "8";
    private int missValueColor = Color.RED;

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

    public void setMissRect(int left, int top, int right, int bottom) {
        setmLeft(left);
        setmTop(top);
        setmRight(right);
        setmBottom(bottom);
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
