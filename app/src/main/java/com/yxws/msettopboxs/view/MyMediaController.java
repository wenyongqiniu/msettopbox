package com.yxws.msettopboxs.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yxws.msettopboxs.R;

import java.util.Formatter;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MyMediaController extends MediaController {

    private MediaPlayerControl mPlayer;
    private Context mContext;
    private View mRoot;

    private TextView title;
    private ProgressBar mProgress;
    private TextView mEndTime, mCurrentTime;

    private static final int sDefaultTimeout = 3000;


    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private ImageView mPauseButton;

    private String mTitle;
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

    public MyMediaController(Context context, String title) {
        super(context, false);
        this.mContext = context;
        this.mTitle = title;
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

        cancelProgressTimer();
        startProgressTimer();

        title = v.findViewById(R.id.title);
        mProgress = v.findViewById(R.id.mediacontroller_progress);
        if (mProgress != null) {
            if (mProgress instanceof SeekBar) {
                SeekBar seeker = (SeekBar) mProgress;
                seeker.requestFocus();

                seeker.setOnClickListener(mPauseListener);
                seeker.setOnSeekBarChangeListener(mSeekListener);

            }
            mProgress.setMax(1000);
        }

        mEndTime = v.findViewById(R.id.time);
        mCurrentTime = v.findViewById(R.id.time_current);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        title.setText(mTitle);
    }

    @Override
    public void setMediaPlayer(MediaPlayerControl player) {
        super.setMediaPlayer(player);
        mPlayer = player;
        updatePausePlay();
    }

    @Override
    public void show(int timeout) {
        super.show(timeout);
        updatePausePlay();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        final boolean uniqueDown = event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN;
        if (keyCode ==  KeyEvent.KEYCODE_HEADSETHOOK
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                || keyCode == KeyEvent.KEYCODE_SPACE) {
            if (uniqueDown) {
                doPauseResume();
                show(sDefaultTimeout);
                if (mPauseButton != null) {
                    mPauseButton.requestFocus();
                }
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
            if (uniqueDown && !mPlayer.isPlaying()) {
                mPlayer.start();
                updatePausePlay();
                show(sDefaultTimeout);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
                || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
            if (uniqueDown && mPlayer.isPlaying()) {
                mPlayer.pause();
                updatePausePlay();
                show(sDefaultTimeout);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                || keyCode == KeyEvent.KEYCODE_VOLUME_UP
                || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE
                || keyCode == KeyEvent.KEYCODE_CAMERA) {
            // don't show the controls for volume adjustment
            return super.dispatchKeyEvent(event);
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            if (uniqueDown) {
                hide();
            }
            return true;
        }

        show(sDefaultTimeout);
        return super.dispatchKeyEvent(event);
    }



    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStartTrackingTouch(SeekBar bar) {
            KLog.e("WLX", "onStartTrackingTouch");
        }

        @Override
        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser) {

                return;
            }
            KLog.e("WLX", "progress :  " + progress + "   fromuser  " + fromuser);

            long duration = mPlayer.getDuration();
            long newposition = (duration * progress) / 1000L;
            mPlayer.seekTo((int) newposition);
            if (mCurrentTime != null)
                mCurrentTime.setText(stringForTime((int) newposition));
        }

        @Override
        public void onStopTrackingTouch(SeekBar bar) {
            KLog.e("WLX", "onStopTrackingTouch");
            updatePausePlay();
        }
    };


    private final View.OnClickListener mPauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cancelProgressTimer();
            startProgressTimer();
            doPauseResume();
            setProgress();
        }
    };


    private void doPauseResume() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            show(0);
        } else {
            mPlayer.start();
            show(sDefaultTimeout);
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
            mPauseButton.setImageResource(R.drawable.jz_pause_normal);
            mPauseButton.setContentDescription(mPauseDescription);
        } else {
            mPauseButton.setImageResource(R.drawable.jz_play_normal);
            mPauseButton.setContentDescription(mPlayDescription);
        }
    }


    protected Timer UPDATE_PROGRESS_TIMER;
    protected ProgressTimerTask mProgressTimerTask;

    private void startProgressTimer() {
        cancelProgressTimer();
        UPDATE_PROGRESS_TIMER = new Timer();
        mProgressTimerTask = new ProgressTimerTask();
        UPDATE_PROGRESS_TIMER.schedule(mProgressTimerTask, 0, 300);
    }

    private void cancelProgressTimer() {
        if (UPDATE_PROGRESS_TIMER != null) {
            UPDATE_PROGRESS_TIMER.cancel();
        }
        if (mProgressTimerTask != null) {
            mProgressTimerTask.cancel();
        }
    }

    private class ProgressTimerTask extends TimerTask {
        @Override
        public void run() {
            post(() -> {
                setProgress();

            });

        }
    }

}