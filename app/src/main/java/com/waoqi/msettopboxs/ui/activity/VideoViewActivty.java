package com.waoqi.msettopboxs.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.VideoView;

import com.iflytek.xiri.Feedback;
import com.iflytek.xiri.scene.ISceneListener;
import com.iflytek.xiri.scene.Scene;
import com.socks.library.KLog;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.presenter.VideoViewPresenter;
import com.waoqi.msettopboxs.util.ArtUtils;
import com.waoqi.msettopboxs.util.RawFileUtils;
import com.waoqi.msettopboxs.view.MyMediaController;
import com.waoqi.mvp.mvp.XActivity;

import me.jessyan.autosize.internal.CustomAdapt;

public class VideoViewActivty extends XActivity<VideoViewPresenter> implements CustomAdapt {


    private VideoView videoView;


    @Override
    public void initView() {
        videoView = (VideoView) findViewById(R.id.videoView);
    }


    public String localVideoPath;
    private Scene scenePlus;
//    private Feedback mFeedback;

    @Override
    public void initData(Bundle savedInstanceState) {
        localVideoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/x264.mp4";

        String path = getIntent().getStringExtra("video");
        String title = getIntent().getStringExtra("title");
        boolean local = getIntent().getBooleanExtra("local", false);

        if (local) {
            videoView.setVideoPath(localVideoPath);
        } else {
            videoView.setVideoPath(path);
        }

//        mFeedback = new Feedback(context);
        scenePlus = new Scene(VideoViewActivty.this);
        scenePlus.init(new ISceneListener() {
            @Override
            public String onQuery() {
                return RawFileUtils.readFileFromRaw(VideoViewActivty.this, R.raw.scene);
            }

            @Override
            public void onExecute(Intent intent) {
//                #Intent;action=com.iflytek.xiri2.scenes.EXECUTE;launchFlags=0x20;package=com.waoqi.msettopboxs;S._scene= com.waoqi.msettopboxs.ui.activity.TestActivity;i._token=38;S._command=landlords;S._rawtext=斗地主;S.package=com.waoqi.msettopboxs;S.pkgname=com.iflytek.xiri;S._objhash=1101723376;S._linkId=fee645c2-7a61-4413-94ed-c920e1341716;end
                KLog.e(" 语音 " + Uri.decode(intent.toURI()));
                String _command = intent.getStringExtra("_command");
                String _action = intent.getStringExtra("_action");

                if (!TextUtils.isEmpty(_command)) {
                    if (_command.equals("esc")) {//退出
                        ArtUtils.appExit();
                    } else if (_command.equals("resume")) {//恢复播放
                        KLog.e(" 恢复播放 ");
                        videoView.start();
                        requestAudioFocus();
                    } else if (_command.equals("mute")) {// 使用系统的 静音
                        KLog.e(" 静音 ");
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0, AudioManager.FLAG_SHOW_UI);
                    } else if (_command.equals("volume_up")) {// 使用系统的 音量大一点
                        KLog.e(" 音量大一点 ");
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                    } else if (_command.equals("volume_down")) {// 使用系统的 音量小一点
                        KLog.e(" 音量小一点 ");
                        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        currentVolume -= 2;
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume < 0 ? 0 : currentVolume, AudioManager.FLAG_SHOW_UI);
                    } else if (_command.equals("home")) {//主页
                        KLog.e(" 主页 ");
                        ArtUtils.startActivity(VideoViewActivty.this, MainActivity.class);
                    }
                }
                if (!TextUtils.isEmpty(_action)) {
                    if (_action.equals("PLAY")) {//播放
                        KLog.e(" 播放 ");
                        videoView.start();
                    } else if (_action.equals("PAUSE")) {//暂停
                        KLog.e(" 暂停 ");
                        videoView.pause();
                    } else if (_action.equals("RESUME")) {//继续播放
                        KLog.e(" 继续播放 ");
                        videoView.start();
                    } else if (_action.equals("RESTART")) {//重头播放
                        KLog.e(" 重头播放 ");
                        videoView.seekTo(0);
                        videoView.start();
                    } else if (_action.equals("SEEK")) {//跳到指定位置
                        int position = intent.getIntExtra("position", 0);
                        KLog.e(" 跳到指定位置 " + position);
                        videoView.seekTo(position * 1000);
                        videoView.start();
                    } else if (_action.equals("FORWARD")) {//快进指定时间
                        int offset = intent.getIntExtra("offset", 0);
                        KLog.e(" 快进指定时间 " + offset);
                        int currentPostion = videoView.getCurrentPosition();
                        videoView.seekTo(currentPostion + offset * 1000);
                        videoView.start();

                    } else if (_action.equals("BACKWARD")) {//后退指定时间
                        int offset = intent.getIntExtra("offset", 0);
                        KLog.e(" 后退指定时间 " + offset);
                        int currentPostion = videoView.getCurrentPosition();
                        videoView.seekTo(currentPostion - offset * 1000);
                        videoView.start();
                    } else if (_action.equals("EXIT")) {//退出
                        KLog.e(" 退出 ");
                        finish();
                    }
                    requestAudioFocus();
                }
//                 TODO mFeedback是什么东西
//                mFeedback.begin(intent);
            }
        });
//        mFeedback.feedback("我是反馈的文本", Feedback.SILENCE);
        videoView.setMediaController(new MyMediaController(this, title));
        videoView.start();
        requestAudioFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scenePlus != null) {
            scenePlus.release();
        }
        abandonAudioFocus();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_view;
    }

    @Override
    public VideoViewPresenter newP() {
        return new VideoViewPresenter();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }

    AudioManager mAudioManager;

    private void requestAudioFocus() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
    }

    private void abandonAudioFocus() {
        AudioManager mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
    }

    public static AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {//是否新建个class，代码更规矩，并且变量的位置也很尴尬
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    Log.d("wlx", "AUDIOFOCUS_LOSS [" + this.hashCode() + "]");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    Log.d("wlx", "AUDIOFOCUS_LOSS_TRANSIENT [" + this.hashCode() + "]");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
            }
        }
    };

}
