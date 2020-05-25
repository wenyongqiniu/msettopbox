package com.waoqi.msettopboxs.view;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.socks.library.KLog;
import com.waoqi.msettopboxs.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

public class JzvdTvStd extends JzvdStd implements SeekBar.OnSeekBarChangeListener {
    private ImageView start_bottom;

    public JzvdTvStd(Context context) {
        super(context);
    }

    public JzvdTvStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_tv_std;
    }

    @Override
    public void init(Context context) {
        super.init(context);
        start_bottom = findViewById(R.id.start_bottom);
        start_bottom.setOnClickListener(this);
        progressBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void updateStartImage() {
        super.updateStartImage();
        if (state == STATE_PLAYING) {
            startButton.setVisibility(VISIBLE);
            startButton.setImageResource(R.drawable.ag_btn_movie_suspend);
            start_bottom.setImageResource(R.drawable.ag_btn_movie_stop_bottom);

            replayTextView.setVisibility(GONE);
        } else if (state == STATE_PREPARING) {
            backButton.setVisibility(VISIBLE);
        } else if (state == STATE_ERROR) {
            startButton.setVisibility(INVISIBLE);
            replayTextView.setVisibility(GONE);

        } else if (state == STATE_AUTO_COMPLETE) {
            //视频播放完成状态
            startButton.setVisibility(View.GONE);
            replayTextView.setVisibility(VISIBLE);

        } else {
            startButton.setImageResource(R.drawable.ag_btn_movie_play);
            start_bottom.setImageResource(R.drawable.ag_btn_movie_play_bottom);
            replayTextView.setVisibility(GONE);

        }
    }

    /**
     * 按下逻辑
     *
     * @param keyCode
     */
    public void onKeyDown(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT://向左
                changeUiToPauseShow();

                startProgressTimer();
                //当前时间
                long quickRetreatCurrentPositionWhenPlaying = getCurrentPositionWhenPlaying();
                //快退（15S）
                long quickRetreatProgress = quickRetreatCurrentPositionWhenPlaying - 15 * 1000;
                if (quickRetreatProgress > 0) {
                    mediaInterface.seekTo(quickRetreatProgress);
                } else {
                    mediaInterface.seekTo(0);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT://向右
                KLog.e("wlx", "向右快进");
                changeUiToPauseShow();

                startProgressTimer();

                //总时间长度
                long duration = getDuration();
                //当前时间
                long currentPositionWhenPlaying = getCurrentPositionWhenPlaying();
                //快进（15S）
                long fastForwardProgress = currentPositionWhenPlaying + 15 * 1000;
                if (duration > fastForwardProgress) {
                    mediaInterface.seekTo(fastForwardProgress);
                } else {
                    mediaInterface.seekTo(duration);
                }
                break;
        }
    }

    /**
     * 抬起逻辑
     *
     * @param keyCode
     */
    public void onKeyUp(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER://中
                if (state == STATE_NORMAL) {
                    if (!jzDataSource.getCurrentUrl().toString().startsWith("file") && !
                            jzDataSource.getCurrentUrl().toString().startsWith("/") &&
                            !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {//这个可以放到std中
                        showWifiDialog();
                        return;
                    }
                    startVideo();
                } else if (state == STATE_PLAYING) {
                    Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
                    mediaInterface.pause();
                    onStatePause();
                } else if (state == STATE_PAUSE) {
                    mediaInterface.start();
                    onStatePlaying();
                } else if (state == STATE_AUTO_COMPLETE) {
                    startVideo();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT://向左
            case KeyEvent.KEYCODE_DPAD_RIGHT://向右
                startDismissControlViewTimer();
                break;
        }

    }


    @Override
    public void setScreenNormal() {
        backButton.setVisibility(View.GONE);
        tinyBackImageView.setVisibility(View.INVISIBLE);
        changeStartButtonSize((int) getResources().getDimension(cn.jzvd.R.dimen.jz_start_button_w_h_normal));
        batteryTimeLayout.setVisibility(View.GONE);
        clarity.setVisibility(View.GONE);
    }


    @Override
    public void setScreenFullscreen() {
        //进入全屏之后要保证原来的播放状态和ui状态不变，改变个别的ui
        backButton.setVisibility(View.VISIBLE);
        tinyBackImageView.setVisibility(View.INVISIBLE);
        batteryTimeLayout.setVisibility(View.VISIBLE);
        if (jzDataSource.urlsMap.size() == 1) {
            clarity.setVisibility(GONE);
        } else {
            clarity.setText(jzDataSource.getCurrentKey().toString());
            clarity.setVisibility(View.VISIBLE);
        }
        changeStartButtonSize((int) getResources().getDimension(cn.jzvd.R.dimen.jz_start_button_w_h_fullscreen));
        setSystemTimeAndBattery();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        KLog.e("WLX", "onProgressChanged" + seekBar.getProgress() + "    fromUser:" + fromUser);


        if (state != STATE_PLAYING &&
                state != STATE_PAUSE) return;
        long time = seekBar.getProgress() * getDuration() / 100;
        seekToManulPosition = seekBar.getProgress();
        mediaInterface.seekTo(time);

    }

}
