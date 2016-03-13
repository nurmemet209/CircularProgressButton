package com.cn.library;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

/**
 * Created by nurmemet on 2016/3/1.
 */
public class CircularProgressButton extends Button {
    private String mIdleText;
    private String mErrorText;
    private String mCompleteText;

    private CircularAnimateDrawable circularAnimatedDrawable;


    private ColorStateList mIdleColorState;
    private ColorStateList mProgressColorState;
    private ColorStateList mSuccessColorState;
    private ColorStateList mErrorColorState;

    private StateListDrawable mIdleDrawableSate;
    private StateListDrawable mSuccessDrawableState;
    private StateListDrawable mErrorDrawableState;

    private float mCornerRadius;
    private float mStrokeWidth;

    private StrockeGradientDrawable mBackgroundDrawable;


    private int mProgressBackgroundColor;
    private int mIndicatorBackgroundColor;
    private int mIndicatorColor;

    private boolean isMorphing;
    private boolean mNeed2SwicthBg=false;

    public enum State{
        IDLE,PROGRESS,COMPLETE,ERROR
    }

    private State state;

    public CircularProgressButton(Context context) {
        super(context);
    }

    public CircularProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircularProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.CircularProgressButton,defStyleAttr,0);

        mCompleteText=a.getString(R.styleable.CircularProgressButton_complete_text);
        mErrorText=a.getString(R.styleable.CircularProgressButton_error_text);
        mIdleText=a.getString(R.styleable.CircularProgressButton_idle_text);

        mCornerRadius=a.getDimension(R.styleable.CircularProgressButton_corner_radius,5F);

        mIdleColorState= ContextCompat.getColorStateList(this.getContext(),R.color.idle_state_selector);
        mSuccessColorState = ContextCompat.getColorStateList(this.getContext(),R.color.complete_state_selector);
        mErrorColorState=ContextCompat.getColorStateList(this.getContext(),R.color.error_state_selector);

        mProgressBackgroundColor=a.getColor(R.styleable.CircularProgressButton_progress_background_color,Color.GREEN);
        mIndicatorBackgroundColor=a.getColor(R.styleable.CircularProgressButton_idicator_background_color,Color.BLUE);

        mStrokeWidth=a.getDimension(R.styleable.CircularProgressButton_strocke_width,0);

        mIndicatorColor=a.getColor(R.styleable.CircularProgressButton_indiator_color,Color.YELLOW);
        //初始化mIdleDrawableSate
        initIdleStateDrawable();
        initErrorStateDrawable();
        initSuccessStateDrawable();
        state=State.IDLE;
        setBackground(mIdleDrawableSate);
        this.setText(mIdleText);
        this.setGravity(Gravity.CENTER);
        a.recycle();
    }






    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(state==State.PROGRESS&&!isMorphing){
            if(circularAnimatedDrawable==null){
                circularAnimatedDrawable=new CircularAnimateDrawable(mIndicatorColor,mStrokeWidth);
                int left=(this.getWidth()-getHeight())/2;
                circularAnimatedDrawable.setBounds(mBackgroundDrawable.getDrawable().getBounds());
                circularAnimatedDrawable.setCallback(this);

            }
            if (!circularAnimatedDrawable.isRunning()){
                circularAnimatedDrawable.start();
            }
            circularAnimatedDrawable.draw(canvas);
        }
    }

    public void set2Progress(){

        MorhpingAnimation morhping2Progress=new MorhpingAnimation(this.getHeight(), mBackgroundDrawable);

        morhping2Progress.setFromColor(getNormalStateColor(mIdleColorState));
        morhping2Progress.setToColor(mProgressBackgroundColor);

        morhping2Progress.setFromStrokeColor(getNormalStateColor(mIdleColorState));
        morhping2Progress.setToStrokeCololr(mIndicatorBackgroundColor);

        morhping2Progress.setFromCorner(mCornerRadius);
        morhping2Progress.setToCorner(this.getHeight()/2);

        morhping2Progress.setFromWidth(this.getWidth());
        morhping2Progress.setToWidth(this.getHeight());

        morhping2Progress.setAnimtorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isMorphing=false;
                state=State.PROGRESS;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        isMorphing=true;
        morhping2Progress.morph();
        this.setClickable(false);

    }

    public State getState(){
        return state;
    }

    public  void set2Idle(){
        state=State.IDLE;
        mBackgroundDrawable.setStrokeColor(getNormalStateColor(mIdleColorState));
        mBackgroundDrawable.getDrawable().setColor(getNormalStateColor(mIdleColorState));
        initIdleStateDrawable();
        setText(mIdleText);
        setBackground(mIdleDrawableSate);
    }

    public void set2Success(){
        circularAnimatedDrawable.stop();

        MorhpingAnimation morhping2Success=new MorhpingAnimation(this.getHeight(), mBackgroundDrawable);

        morhping2Success.setFromColor(mProgressBackgroundColor);
        morhping2Success.setToColor(getNormalStateColor(mSuccessColorState));

        morhping2Success.setFromStrokeColor(mIndicatorBackgroundColor);
        morhping2Success.setToStrokeCololr(getNormalStateColor(mSuccessColorState));

        morhping2Success.setFromCorner(mCornerRadius);
        morhping2Success.setToCorner(0);

        morhping2Success.setFromWidth(this.getHeight());
        morhping2Success.setToWidth(this.getWidth());

        morhping2Success.setAnimtorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isMorphing=false;
                state=State.COMPLETE;
                setText(mCompleteText);
                setClickable(true);
                mNeed2SwicthBg=true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        isMorphing=true;
        morhping2Success.morph();
       // this.setClickable(false);

    }

    public void set2Error(){
        circularAnimatedDrawable.stop();

        MorhpingAnimation morhping2Success=new MorhpingAnimation(this.getHeight(), mBackgroundDrawable);

        morhping2Success.setFromColor(mProgressBackgroundColor);
        morhping2Success.setToColor(getNormalStateColor(mErrorColorState));

        morhping2Success.setFromStrokeColor(mIndicatorBackgroundColor);
        morhping2Success.setToStrokeCololr(getNormalStateColor(mErrorColorState));

        morhping2Success.setFromCorner(mCornerRadius);
        morhping2Success.setToCorner(0);

        morhping2Success.setFromWidth(this.getHeight());
        morhping2Success.setToWidth(this.getWidth());

        morhping2Success.setAnimtorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isMorphing=false;
                state=State.ERROR;
                setText(mErrorText);
                setClickable(true);
                mNeed2SwicthBg=true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        isMorphing=true;
        morhping2Success.morph();
    }



    private void initIdleStateDrawable(){
        int normalColor=getNormalStateColor(mIdleColorState);
        int pressedColor=getPressedStateColor(mIdleColorState);
        StrockeGradientDrawable normalDrawable=createDrawable(normalColor);
        StrockeGradientDrawable pressedDrawable=createDrawable(pressedColor);
        mBackgroundDrawable =normalDrawable;
        mIdleDrawableSate=new StateListDrawable();
        mIdleDrawableSate.addState(PRESSED_ENABLED_STATE_SET,pressedDrawable.getDrawable());
        mIdleDrawableSate.addState(ENABLED_STATE_SET,normalDrawable.getDrawable());




    }
    private void initErrorStateDrawable(){
        int normalColor=getNormalStateColor(mErrorColorState);
        int pressedColor=getPressedStateColor(mErrorColorState);
        StrockeGradientDrawable normalDrawable=createDrawable(normalColor);
        StrockeGradientDrawable pressedDrawable=createDrawable(pressedColor);
        mErrorDrawableState=new StateListDrawable();
        mErrorDrawableState.addState(PRESSED_ENABLED_STATE_SET,pressedDrawable.getDrawable());
        mErrorDrawableState.addState(ENABLED_STATE_SET,normalDrawable.getDrawable());
    }

    private void initSuccessStateDrawable(){
        int normalColor=getNormalStateColor(mSuccessColorState);
        int pressedColor=getPressedStateColor(mSuccessColorState);
        StrockeGradientDrawable normalDrawable=createDrawable(normalColor);
        StrockeGradientDrawable pressedDrawable=createDrawable(pressedColor);
        mSuccessDrawableState=new StateListDrawable();
        mSuccessDrawableState.addState(PRESSED_ENABLED_STATE_SET,pressedDrawable.getDrawable());
        mSuccessDrawableState.addState(ENABLED_STATE_SET,normalDrawable.getDrawable());
    }


    private int getNormalStateColor(ColorStateList stateList){

        return stateList.getColorForState(new int[]{android.R.attr.state_enabled},0);
    }
    private int getPressedStateColor(ColorStateList stateList){
        return stateList.getColorForState(new int[]{android.R.attr.state_pressed},0);
    }


    private StrockeGradientDrawable createDrawable(int color){
        GradientDrawable drawable=(GradientDrawable) ContextCompat.getDrawable(this.getContext(),R.drawable.bg).mutate();
        drawable.setColor(color);
        drawable.setCornerRadius(mCornerRadius);
        StrockeGradientDrawable strokeGradientDrawable = new StrockeGradientDrawable(drawable);
        strokeGradientDrawable.setmStrokeWidth((int)mStrokeWidth);
        strokeGradientDrawable.setStrokeColor(color);

        return strokeGradientDrawable;
    }


    @Override
    protected void drawableStateChanged() {
        if(mNeed2SwicthBg){
            if(state==State.IDLE){
                setBackground(mIdleDrawableSate);
               // mBackgroundDrawable=createDrawable(getNormalStateColor(mIdleColorState));
            }
            else if(state==State.COMPLETE){
                setBackground(mSuccessDrawableState);
                //mBackgroundDrawable=createDrawable(getNormalStateColor(mSuccessColorState));
            }
            else if(state==State.ERROR){
                setBackground(mErrorDrawableState);
                //mBackgroundDrawable=createDrawable(getNormalStateColor(mErrorColorState));
            }
            mNeed2SwicthBg=false;
        }
        super.drawableStateChanged();
    }


    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == circularAnimatedDrawable || super.verifyDrawable(who);
    }
}
