package com.cn.library;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by nurmemet on 2016/3/5.
 */
public class MorhpingAnimation  {


    private  final int MORPHINT_DURATION=2000;
    private int height;
    private StrockeGradientDrawable mBackground;


    private float fromCorner;
    private float toCorner;

    private int fromColor;
    private int toColor;

    private int fromWidth;
    private int toWidth;





    private int toStrokeCololr;
    private int  fromStrokeColor;

    private Animator.AnimatorListener animatorListener;

    public void setAnimtorListener(Animator.AnimatorListener animtorListener){
        this.animatorListener=animtorListener;
    }


    public MorhpingAnimation(int height,StrockeGradientDrawable mBackground){
        this.height=height;
        this.mBackground=mBackground;
    }





    public void morph(){
       ValueAnimator widthAnimator=ValueAnimator.ofFloat(fromWidth,toWidth);
        widthAnimator.setDuration(MORPHINT_DURATION);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value=(Float) animation.getAnimatedValue();
                float left=0;
                float right=0;
                if(fromWidth>toWidth){
                    left=(fromWidth-value)/2;
                    right=fromWidth-left;
                }
                else{
                    left=(toWidth-value)/2;
                    right=toWidth-left;
                }

                mBackground.getDrawable().setBounds((int)left,0,(int)right,height);
            }
        });

        ObjectAnimator colorAnimator=ObjectAnimator.ofInt(mBackground.getDrawable(),"color",fromColor,toColor);
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(MORPHINT_DURATION);

        ObjectAnimator cornerAnimation=ObjectAnimator.ofFloat(mBackground.getDrawable(),"cornerRadius",fromCorner,toCorner);
        cornerAnimation.setDuration(MORPHINT_DURATION);

        ObjectAnimator strokeColorAnimator=ObjectAnimator.ofInt(mBackground,"strokeColor",fromStrokeColor,toStrokeCololr);
        strokeColorAnimator.setEvaluator(new ArgbEvaluator());
        strokeColorAnimator.setDuration(MORPHINT_DURATION);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(MORPHINT_DURATION);
        animatorSet.playTogether(widthAnimator,colorAnimator,strokeColorAnimator);
        if(animatorListener!=null){
            animatorSet.addListener(animatorListener);
        }
        animatorSet.start();



    }




    public int getToStrokeColor() {
        return toStrokeColor;
    }

    public void setToStrokeColor(int toStrokeColor) {
        this.toStrokeColor = toStrokeColor;
    }

    private int toStrokeColor;

    public int getFromColor() {
        return fromColor;
    }

    public void setFromColor(int fromColor) {
        this.fromColor = fromColor;
    }

    public float getFromCorner() {
        return fromCorner;
    }

    public void setFromCorner(float fromCorner) {
        this.fromCorner = fromCorner;
    }

    public int getFromStrokeColor() {
        return fromStrokeColor;
    }

    public void setFromStrokeColor(int fromStrokeColor) {
        this.fromStrokeColor = fromStrokeColor;
    }



    public int getFromWidth() {
        return fromWidth;
    }

    public void setFromWidth(int fromWidth) {
        this.fromWidth = fromWidth;
    }

    public int getToColor() {
        return toColor;
    }

    public void setToColor(int toColor) {
        this.toColor = toColor;
    }

    public float getToCorner() {
        return toCorner;
    }

    public void setToCorner(float toCorner) {
        this.toCorner = toCorner;
    }

    public int getToStrokeCololr() {
        return toStrokeCololr;
    }

    public void setToStrokeCololr(int toStrokeCololr) {
        this.toStrokeCololr = toStrokeCololr;
    }

    public int getToWidth() {
        return toWidth;
    }

    public void setToWidth(int toWidth) {
        this.toWidth = toWidth;
    }


}
