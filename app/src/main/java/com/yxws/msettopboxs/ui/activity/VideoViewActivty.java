package com.yxws.msettopboxs.ui.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.DevInfoManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.VideoView;

import com.chinamobile.SWDevInfoManager;
import com.iflytek.xiri.Feedback;
import com.iflytek.xiri.scene.ISceneListener;
import com.iflytek.xiri.scene.Scene;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.socks.library.KLog;
import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.VideoDetailBean;
import com.yxws.msettopboxs.presenter.VideoViewPresenter;
import com.yxws.msettopboxs.util.ArtUtils;
import com.yxws.msettopboxs.util.DevInfoUtil;
import com.yxws.msettopboxs.util.OnResultCall;
import com.yxws.msettopboxs.util.RawFileUtils;
import com.yxws.msettopboxs.view.MyMediaController;
import com.yxws.mvp.mvp.XActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import me.jessyan.autosize.internal.CustomAdapt;

import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_HOME_KEY;
import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_KEY;

public class VideoViewActivty extends XActivity<VideoViewPresenter> implements CustomAdapt, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, ISceneListener, MediaPlayer.OnErrorListener {


    private VideoView videoView;


    @Override
    public void initView() {
        videoView = (VideoView) findViewById(R.id.videoView);
    }


    private Scene scenePlus;
    private Feedback mFeedback;

    private DevInfoManager devInfoManager;
    LoadingPopupView loadingPopup;
    private long contentTotalTime, startWatchTime, endWatchTime, playTime;
    private int videoId;
    private VideoDetailBean mVideoDetailBean;
    private DevInfoManager systemService;
    @SuppressLint("WrongConstant")
    @Override
    public void initData(Bundle savedInstanceState) {
        videoId = getIntent().getIntExtra("id", 0);
        getP().getVideoDetail(videoId);

        devInfoManager = (DevInfoManager) getSystemService(DevInfoManager.DATA_SERVER);

        videoView.setOnPreparedListener(this);
        videoView.setOnCompletionListener(this);
        videoView.setOnErrorListener(this);

        loadingPopup = (LoadingPopupView) new XPopup.Builder(this)
                .asLoading("加载中")
                .bindLayout(R.layout.xpopup_center_impl_loading)
                .show();

        mFeedback = new Feedback(context);
        scenePlus = new Scene(VideoViewActivty.this);

        startWatchTime = System.currentTimeMillis();
        endWatchTime = System.currentTimeMillis();
        playTime = endWatchTime - startWatchTime;

//        TODO 开始保存历史任务
        startTimer();
        initReceiver();

        systemService = SWDevInfoManager.getDevInfoManager(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        scenePlus.init(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (scenePlus != null) {
            scenePlus.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelTimerTask();
        abandonAudioFocus();

        if (mHomeRecaiver != null) {
            unregisterReceiver(mHomeRecaiver);
        }
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


    protected SaveHistotyTimerTask mPrePratationTimerTask;
    protected Timer UPDATE_PROGRESS_TIMER;


    private void startTimer() {
        UPDATE_PROGRESS_TIMER = new Timer();
        mPrePratationTimerTask = new SaveHistotyTimerTask(this);
        UPDATE_PROGRESS_TIMER.schedule(mPrePratationTimerTask, 0, 10000);
    }

    private void cancelTimerTask() {
        if (UPDATE_PROGRESS_TIMER != null) {
            UPDATE_PROGRESS_TIMER.cancel();
        }
        if (mPrePratationTimerTask != null) {
            mPrePratationTimerTask.cancel();
        }
    }

    private String videoPathUrl;

    /**
     * 设置播放详情
     *
     * @param videoPathUrl     视频播放地址
     * @param title
     * @param mVideoDetailBean
     */
    public void setVideoDetail(String videoPathUrl, String title, VideoDetailBean mVideoDetailBean) {
        this.mVideoDetailBean = mVideoDetailBean;
        this.videoPathUrl = videoPathUrl;
        videoView.setVideoPath(videoPathUrl);
        videoView.setMediaController(new MyMediaController(this, title));
        videoView.start();
    }

    @Override
    public String onQuery() {
        return RawFileUtils.readFileFromRaw(VideoViewActivty.this, R.raw.scene);
    }

    @Override
    public void onExecute(Intent intent) {
        //TODO mFeedback是什么东西
        mFeedback.begin(intent);
        mFeedback.feedback("我是反馈的文本", Feedback.SILENCE);
//                #Intent;action=com.iflytek.xiri2.scenes.EXECUTE;launchFlags=0x20;package=com.yxws.msettopboxs;S._scene= com.yxws.msettopboxs.ui.activity.TestActivity;i._token=38;S._command=landlords;S._rawtext=斗地主;S.package=com.yxws.msettopboxs;S.pkgname=com.iflytek.xiri;S._objhash=1101723376;S._linkId=fee645c2-7a61-4413-94ed-c920e1341716;end
        KLog.e(" 语音 " + Uri.decode(intent.toURI()));
        String _command = intent.getStringExtra("_command");
        String _action = intent.getStringExtra("_action");

        if (!TextUtils.isEmpty(_command)) {
            if (_command.equals("esc")) {//退出
                ArtUtils.appExit();
            } else if (_command.equals("resume")) {//恢复播放
                KLog.e("wlx", " 恢复播放 ");
                videoView.start();
                requestAudioFocus();
            } else if (_command.equals("mute")) {// 使用系统的 静音
                KLog.e("wlx", " 静音 ");
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
            } else if (_command.equals("volume_up")) {// 使用系统的 音量大一点
                KLog.e("wlx", " 音量大一点 ");
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            } else if (_command.equals("volume_down")) {// 使用系统的 音量小一点
                KLog.e("wlx", " 音量小一点 ");
                int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                currentVolume -= 2;
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume < 0 ? 0 : currentVolume, AudioManager.FLAG_SHOW_UI);
            } else if (_command.equals("home")) {//主页
                KLog.e("wlx", " 主页 ");
                ArtUtils.startActivity(VideoViewActivty.this, MainActivity.class);
            }
        }
        if (!TextUtils.isEmpty(_action)) {
            if (_action.equals("PLAY")) {//播放
                KLog.e("wlx", " 播放 ");
                videoView.start();
            } else if (_action.equals("PAUSE")) {//暂停
                KLog.e("wlx", " 暂停 ");
                videoView.pause();
            } else if (_action.equals("RESUME")) {//继续播放
                KLog.e("wlx", " 继续播放 ");
                videoView.start();
            } else if (_action.equals("RESTART")) {//重头播放
                KLog.e("wlx", " 重头播放 ");
                videoView.seekTo(0);
                videoView.start();
            } else if (_action.equals("SEEK")) {//跳到指定位置
                int position = intent.getIntExtra("position", 0);
                KLog.e("wlx", " 跳到指定位置 " + position);
                videoView.seekTo(position * 1000);
                videoView.start();
            } else if (_action.equals("FORWARD")) {//快进指定时间
                int offset = intent.getIntExtra("offset", 0);
                KLog.e("wlx", " 快进指定时间 " + offset);
                int currentPostion = videoView.getCurrentPosition();
                videoView.seekTo(currentPostion + offset * 1000);
                videoView.start();

            } else if (_action.equals("BACKWARD")) {//后退指定时间
                int offset = intent.getIntExtra("offset", 0);
                KLog.e("wlx", " 后退指定时间 " + offset);
                int currentPostion = videoView.getCurrentPosition();
                videoView.seekTo(currentPostion - offset * 1000);
                videoView.start();
            } else if (_action.equals("EXIT")) {//退出
                KLog.e("wlx", " 退出 ");
                finish();
            }

        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        KLog.e("wlx","播放失败 重新播放");
        videoView.setVideoPath(videoPathUrl.replace(devInfoManager.getValue(DevInfoManager.CDN_ADDRESS),devInfoManager.getValue(DevInfoManager.CDN_ADDRESS_BACK)));
        videoView.start();
        return true;
    }

    public class SaveHistotyTimerTask extends TimerTask {
        private WeakReference<VideoViewActivty> mWeakReference;

        public SaveHistotyTimerTask(VideoViewActivty videoViewActivty) {
            mWeakReference = new WeakReference<>(videoViewActivty);
        }

        @Override
        public void run() {
            if (mWeakReference.get() == null) {
                return;
            }
            mWeakReference.get().videoView.post(() -> {
                endWatchTime = System.currentTimeMillis();
                playTime = endWatchTime - startWatchTime;
                if (mVideoDetailBean != null && devInfoManager != null) {
                    DevInfoUtil.getToken(mWeakReference.get(), new OnResultCall() {
                        @Override
                        public void onResult(String token) {
                            mWeakReference.get().getP().saveHistoty(mWeakReference.get().mVideoDetailBean.getTvName(), mWeakReference.get().
                                            mVideoDetailBean.getCpAlbumId(), mWeakReference.get().mVideoDetailBean.getCpTvId(),
                                    mWeakReference.get().contentTotalTime,
                                    mWeakReference.get().startWatchTime,
                                    mWeakReference.get().endWatchTime,
                                    playTime,
                                    "watching"
                                    , token,
                                    mWeakReference.get().mVideoDetailBean.getTvPicHead());
                        }
                    });

                }
            });
        }
    }


    //播放结束
    @Override
    public void onCompletion(MediaPlayer mp) {
        endWatchTime = (int) System.currentTimeMillis();
        playTime = endWatchTime - startWatchTime;
        if (mp != null && mVideoDetailBean != null && devInfoManager != null) {
            contentTotalTime = mp.getDuration() / 1000;
            if (mVideoDetailBean != null && devInfoManager != null) {
                DevInfoUtil.getToken(this, new OnResultCall() {
                    @Override
                    public void onResult(String token) {
                        getP().saveHistoty(mVideoDetailBean.getTvName(), mVideoDetailBean.getCpAlbumId(), mVideoDetailBean.getCpTvId(),
                                contentTotalTime, startWatchTime, endWatchTime, playTime,
                                "end"
                                , token, mVideoDetailBean.getTvPicHead());
                    }
                });

            }
        }
    }

    //视频准备完成
    @Override
    public void onPrepared(MediaPlayer mp) {
        endWatchTime = System.currentTimeMillis();
        playTime = endWatchTime - startWatchTime;

        loadingPopup.dismiss();

        if (mp != null && mVideoDetailBean != null && devInfoManager != null) {
            contentTotalTime = mp.getDuration() / 1000;
            DevInfoUtil.getToken(this, new OnResultCall() {
                @Override
                public void onResult(String token) {
                    getP().saveHistoty(mVideoDetailBean.getTvName(), mVideoDetailBean.getCpAlbumId(), mVideoDetailBean.getCpTvId(),
                            contentTotalTime, startWatchTime, endWatchTime, playTime,
                            "begin"
                            , token, mVideoDetailBean.getTvPicHead());
                }
            });
        }
    }


    private HomeRecaiver mHomeRecaiver;

    private void initReceiver() {
        mHomeRecaiver = new HomeRecaiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeRecaiver, filter);
    }

    class HomeRecaiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }

    }


}
