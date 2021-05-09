package com.example.homesewa.animationsPackage;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;

public class YoYo {

    private static final long DURATION = BaseViewAnimator.DURATION;
    private static final long NO_DELAY = 0;
    private static final float CENTER_PIVOT = Float.MAX_VALUE;

    private final BaseViewAnimator animator;
    private final long duration;
    private final long delay;
    private final int repeatTimes;
    private final int repeatMode;
    private Interpolator interpolator;
    private final float pivotX, pivotY;
    private final List<Animator.AnimatorListener> callbacks;
    private final View target;

    private YoYo(AnimationComposer animationComposer) {
        animator = animationComposer.animator;
        duration = animationComposer.duration;
        delay = animationComposer.delay;
        repeatTimes = animationComposer.repeatTimes;
        repeatMode = animationComposer.repeatMode;
        pivotX = animationComposer.pivotX;
        pivotY = animationComposer.pivotY;
        callbacks = animationComposer.callbacks;
        target = animationComposer.target;
    }

    public static AnimationComposer with(Techniques techniques) {
        return new AnimationComposer(techniques);
    }


    public static final class AnimationComposer {

        private final List<Animator.AnimatorListener> callbacks = new ArrayList<>();

        private final BaseViewAnimator animator;
        private final long duration = DURATION;

        private final long delay = NO_DELAY;
        private final boolean repeat = false;
        private final int repeatTimes = 0;
        private final int repeatMode = ValueAnimator.RESTART;
        private final float pivotX = YoYo.CENTER_PIVOT, pivotY = YoYo.CENTER_PIVOT;
        private View target;

        private AnimationComposer(Techniques techniques) {
            this.animator = techniques.getAnimator();
        }


        public YoYoString playOn(View target) {
            this.target = target;
            return new YoYoString(new YoYo(this).play(), this.target);
        }

    }

    /**
     * YoYo string, you can use this string to control your YoYo.
     */
    private static final class YoYoString {

        private final BaseViewAnimator animator;
        private final View target;

        private YoYoString(BaseViewAnimator animator, View target) {
            this.target = target;
            this.animator = animator;
        }
    }

    private BaseViewAnimator play() {
        animator.setTarget(target);

        if (pivotX == YoYo.CENTER_PIVOT) {
            ViewCompat.setPivotX(target, target.getMeasuredWidth() / 2.0f);
        } else {
            target.setPivotX(pivotX);
        }
        if (pivotY == YoYo.CENTER_PIVOT) {
            ViewCompat.setPivotY(target, target.getMeasuredHeight() / 2.0f);
        } else {
            target.setPivotY(pivotY);
        }

        animator.setDuration(duration)
                .setRepeatTimes(repeatTimes)
                .setRepeatMode(repeatMode)
                .setInterpolator(interpolator)
                .setStartDelay(delay);

        if (callbacks.size() > 0) {
            for (Animator.AnimatorListener callback : callbacks) {
                animator.addAnimatorListener(callback);
            }
        }
        animator.animate();
        return animator;
    }

}
