package com.cn.library;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by nurmemet on 2016/3/6.
 */
public class StrockeGradientDrawable {

    private int mStrokeWidth;
    private int strokeColor;
    private GradientDrawable mDrawable;

    public StrockeGradientDrawable(GradientDrawable drawable){
        this.mDrawable=drawable;
    }
    public int getmStrokeWidth() {
        return mStrokeWidth;
    }
    public void setmStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }
    public GradientDrawable getDrawable(){
        return mDrawable;
    }
    public int getStrokeColor() {
        return strokeColor;
    }
    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        mDrawable.setStroke(mStrokeWidth,strokeColor);
    }
}
