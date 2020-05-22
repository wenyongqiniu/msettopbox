package com.waoqi.msettopboxs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import com.waoqi.msettopboxs.R;

import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

public class JzvdTvStd extends JzvdStd {
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

    /**
     * 按下逻辑
     * @param keyCode
     */
    public void onKeyDown(int keyCode){
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT://向下
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT://向右
                break;
            case KeyEvent.KEYCODE_DPAD_UP://向上
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN://向下
                break;

        }
    }

    /**
     * 抬起逻辑
     * @param keyCode
     */
    public void onKeyUp(int keyCode){
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
            case KeyEvent.KEYCODE_DPAD_LEFT://向下
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT://向右
                break;
            case KeyEvent.KEYCODE_DPAD_UP://向上
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN://向下
                break;

        }
    }



}
