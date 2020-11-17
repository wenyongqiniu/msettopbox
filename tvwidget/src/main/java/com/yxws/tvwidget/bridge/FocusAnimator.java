package com.yxws.tvwidget.bridge;

import android.animation.TimeAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class FocusAnimator implements TimeAnimator.TimeListener {

    private float mFocusLevel = 0f;
    private final float mScaleDiff;
    private final View mView;
    private final int mDuration;
    private float mFocusLevelStart;
    private float mFocusLevelDelta;
    private final TimeAnimator mAnimator = new TimeAnimator();
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    void animateFocus(boolean select, boolean immediate) {
        endAnimation();
        final float end = select ? 1 : 0;
        if (immediate) {
            setFocusLevel(end);
        } else if (mFocusLevel != end) {
            mFocusLevelStart = mFocusLevel;
            mFocusLevelDelta = end - mFocusLevelStart;
            mAnimator.start();
        }
    }

    FocusAnimator(View view, float scale, int duration) {
        mView = view;
        mDuration = duration;
        mScaleDiff = scale - 1f;
        mAnimator.setTimeListener(this);
    }

    void setFocusLevel(float level) {
        mFocusLevel = level;
        float scale = 1f + mScaleDiff * level;
        mView.setScaleX(scale);
        mView.setScaleY(scale);
    }
    float getFocusLevel() {
        return mFocusLevel;
    }


    void endAnimation() {
        mAnimator.end();
    }

    @Override
    public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
        float fraction;
        if (totalTime >= mDuration) {
            fraction = 1;
            mAnimator.end();
        } else {
            fraction = (float) (totalTime / (double) mDuration);
        }
        if (mInterpolator != null) {
            fraction = mInterpolator.getInterpolation(fraction);
        }
        setFocusLevel(mFocusLevelStart + fraction * mFocusLevelDelta);
    }
}