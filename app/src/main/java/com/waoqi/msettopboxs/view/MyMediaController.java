package com.waoqi.msettopboxs.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.waoqi.msettopboxs.R;

import java.util.Formatter;
import java.util.Locale;

public class MyMediaController extends MediaController {

    private MediaPlayerControl mPlayer;
    private Context mContext;
    private View mRoot;

    private ProgressBar mProgress;
    private TextView mEndTime, mCurrentTime;

    private static final int sDefaultTimeout = 3000;


    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private ImageButton mPauseButton;

    private CharSequence mPlayDescription;
    private CharSequence mPauseDescription;

    public MyMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

    }

    public MyMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
        this.mContext = context;

    }

    public MyMediaController(Context context) {
        super(context, true);
        this.mContext = context;
    }


    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);


        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        removeAllViews();
        View v = makeControllerView();
        addView(v, frameParams);
    }


    protected View makeControllerView() {
        LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflate.inflate(R.layout.my_media_controller, null);

        initControllerView(mRoot);

        return mRoot;
    }


    private void initControllerView(View v) {
        Resources res = mContext.getResources();
        mPlayDescription = res.getText(R.string.lockscreen_transport_play_description);
        mPauseDescription = res.getText(R.string.lockscreen_transport_pause_description);
        mPauseButton = v.findViewById(R.id.pause);
        if (mPauseButton != null) {
            mPauseButton.requestFocus();
            mPauseButton.setOnClickListener(mPauseListener);
        }

        mProgress = v.findViewById(R.id.mediacontroller_progress);
        if (mProgress != null) {
            if (mProgress instanceof SeekBar) {
                SeekBar seeker = (SeekBar) mProgress;
                mProgress.setOnClickListener(mPauseListener);
                seeker.setOnSeekBarChangeListener(mSeekListener);

            }
            mProgress.setMax(1000);
        }

        mEndTime = v.findViewById(R.id.time);
        mCurrentTime = v.findViewById(R.id.time_current);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

    }

    @Override
    public void setMediaPlayer(MediaPlayerControl player) {
        super.setMediaPlayer(player);
        mPlayer = player;
        updatePausePlay();
    }


    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStartTrackingTouch(SeekBar bar) {
            Log.e("WLX", "onStartTrackingTouch");
        }

        @Override
        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser) {

                return;
            }
            Log.e("WLX", "progress :  " + progress + "   fromuser  " + fromuser);

            long duration = mPlayer.getDuration();
            long newposition = (duration * progress) / 1000L;
            mPlayer.seekTo((int) newposition);
            if (mCurrentTime != null)
                mCurrentTime.setText(stringForTime((int) newposition));
        }

        @Override
        public void onStopTrackingTouch(SeekBar bar) {
            Log.e("WLX", "onStopTrackingTouch");
        }
    };


    private final View.OnClickListener mPauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doPauseResume();
            setProgress();
            show(sDefaultTimeout);
        }
    };


    private void doPauseResume() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updatePausePlay();
    }


    private int setProgress() {
        if (mPlayer == null) {
            return 0;
        }
        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        if (mEndTime != null)
            mEndTime.setText(stringForTime(duration));
        if (mCurrentTime != null)
            mCurrentTime.setText(stringForTime(position));

        return position;
    }

    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private void updatePausePlay() {
        if (mRoot == null || mPauseButton == null)
            return;

        if (mPlayer.isPlaying()) {
            mPauseButton.setImageResource(R.drawable.ic_media_pause);
            mPauseButton.setContentDescription(mPauseDescription);
        } else {
            mPauseButton.setImageResource(R.drawable.ic_media_play);
            mPauseButton.setContentDescription(mPlayDescription);
        }
    }


}