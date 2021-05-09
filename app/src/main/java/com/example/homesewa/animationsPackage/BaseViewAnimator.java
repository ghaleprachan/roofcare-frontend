package com.example.homesewa.animationsPackage;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.core.view.ViewCompat;


public abstract class BaseViewAnimator {

    public static final long DURATION = 1000;

    private AnimatorSet mAnimatorSet;

    private long mDuration = DURATION;
    private int mRepeatTimes = 0;
    private int mRepeatMode = ValueAnimator.RESTART;

    {
        mAnimatorSet = new AnimatorSet();
    }


    protected abstract void prepare(View target);

    public void setTarget(View target) {
        reset(target);
        prepare(target);
    }

    public void animate() {
        start();
    }

    public void restart() {
        mAnimatorSet = mAnimatorSet.clone();
        start();
    }

    /**
     * reset the view to default status
     *
     * @param target
     */
    public void reset(View target) {
        ViewCompat.setAlpha(target, 1);
        ViewCompat.setScaleX(target, 1);
        ViewCompat.setScaleY(target, 1);
        ViewCompat.setTranslationX(target, 0);
        ViewCompat.setTranslationY(target, 0);
        ViewCompat.setRotation(target, 0);
        ViewCompat.setRotationY(target, 0);
        ViewCompat.setRotationX(target, 0);
    }

    /**
     * start to animate
     */
    public void start() {
        for (Animator animator : mAnimatorSet.getChildAnimations()) {
            if (animator instanceof ValueAnimator) {
                ((ValueAnimator) animator).setRepeatCount(mRepeatTimes);
                ((ValueAnimator) animator).setRepeatMode(mRepeatMode);
            }
        }
        mAnimatorSet.setDuration(mDuration);
        mAnimatorSet.start();
    }

    public BaseViewAnimator setDuration(long duration) {
        mDuration = duration;
        return this;
    }

    public void setStartDelay(long delay) {
        getAnimatorAgent().setStartDelay(delay);
    }

    public long getStartDelay() {
        return mAnimatorSet.getStartDelay();
    }

    public void addAnimatorListener(Animator.AnimatorListener l) {
        mAnimatorSet.addListener(l);
    }

    public void cancel() {
        mAnimatorSet.cancel();
    }

    public boolean isRunning() {
        return mAnimatorSet.isRunning();
    }

    public boolean isStarted() {
        return mAnimatorSet.isStarted();
    }

    public void removeAnimatorListener(Animator.AnimatorListener l) {
        mAnimatorSet.removeListener(l);
    }

    public void removeAllListener() {
        mAnimatorSet.removeAllListeners();
    }

    public BaseViewAnimator setInterpolator(Interpolator interpolator) {
        mAnimatorSet.setInterpolator(interpolator);
        return this;
    }

    public long getDuration() {
        return mDuration;
    }

    public AnimatorSet getAnimatorAgent() {
        return mAnimatorSet;
    }

    public BaseViewAnimator setRepeatTimes(int repeatTimes) {
        mRepeatTimes = repeatTimes;
        return this;
    }

    public BaseViewAnimator setRepeatMode(int repeatMode) {
        mRepeatMode = repeatMode;
        return this;
    }
}
