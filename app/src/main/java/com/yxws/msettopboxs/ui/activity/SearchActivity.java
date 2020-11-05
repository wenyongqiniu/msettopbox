package com.yxws.msettopboxs.ui.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.socks.library.KLog;
import com.yxws.msettopboxs.R;
import com.yxws.msettopboxs.bean.VideoBean;
import com.yxws.msettopboxs.presenter.SearchPresenter;
import com.yxws.msettopboxs.ui.adpter.TypeVideoGridViewAdpter;
import com.yxws.msettopboxs.util.ArtUtils;
import com.yxws.msettopboxs.view.t9.NineKeybordButton;
import com.yxws.msettopboxs.view.t9.OnNineKeyListener;
import com.yxws.mvp.mvp.XActivity;
import com.yxws.tvwidget.bridge.EffectNoDrawBridge;
import com.yxws.tvwidget.bridge.OpenEffectBridge;
import com.yxws.tvwidget.keyboard.SkbContainer;
import com.yxws.tvwidget.keyboard.SoftKey;
import com.yxws.tvwidget.keyboard.SoftKeyBoardListener;
import com.yxws.tvwidget.utils.OPENLOG;
import com.yxws.tvwidget.view.GridViewTV;
import com.yxws.tvwidget.view.MainUpView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_HOME_KEY;
import static com.yxws.msettopboxs.config.Constant.SYSTEM_DIALOG_REASON_KEY;

/**
 * author : Zzy
 * date   : 2020/5/29
 */
public class SearchActivity extends XActivity<SearchPresenter> implements View.OnClickListener {
    private static final String TAG = TypeVideoActivity.class.getName();
    private MainUpView mainUpView2;
    private GridViewTV gridviewtv;
    private OpenEffectBridge mOpenEffectBridge;
    private View mOldGridView;
    private int point = 0; //gridview 位置
    private List<VideoBean> mVideoBeans = new ArrayList<>();
    private TypeVideoGridViewAdpter mVideoGridViewAdpter;
    private EditText lemon95MovieMsg_id;
    private Button btn_all, btn_t9;
    private LinearLayout linet9;


    SkbContainer skbContainer;

    @Override
    public void initView() {
        mainUpView2 = (MainUpView) findViewById(R.id.mainUpView2);
        gridviewtv = (GridViewTV) findViewById(R.id.gridviewtv);
        lemon95MovieMsg_id = (EditText) findViewById(R.id.lemon95_movie_msg_id);
        btn_all = (Button) findViewById(R.id.btn_all);
        btn_t9 = (Button) findViewById(R.id.btn_t9);
        linet9 = (LinearLayout) findViewById(R.id.linet9);
        btn_all.setOnClickListener(this);
        btn_t9.setOnClickListener(this);

        skbContainer = (SkbContainer) findViewById(R.id.skbContainer);
        skbContainer.setSkbLayout(R.xml.skb_all_key);
        skbContainer.setFocusable(true);
        skbContainer.setFocusableInTouchMode(true);

        setSelectAllT9(true);
        initGridView();
        initNineT9();

        // 设置属性(默认是不移动的选中边框)
        setSkbContainerOther();
        skbContainer.setOnSoftKeyBoardListener(new SoftKeyBoardListener() {
            @Override
            public void onCommitText(SoftKey softKey) {
                int keyCode = softKey.getKeyCode();
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    String text = lemon95MovieMsg_id.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        Toast.makeText(getApplicationContext(), "文本已空", Toast.LENGTH_LONG).show();
                    } else {
                        lemon95MovieMsg_id.setText(text.substring(0, text.length() - 1));
                    }
                } else if (keyCode == KeyEvent.KEYCODE_CLEAR) {
                    lemon95MovieMsg_id.setText("");
                } else if (keyCode == 250) {
                    setSkbContainerOther();
                    skbContainer.setSkbLayout(R.xml.sbd_number);
                } else if (keyCode == 1234) {
                    setSkbContainerOther();
                    skbContainer.setSkbLayout(R.xml.skb_all_key);
                } else {
                    lemon95MovieMsg_id.setText(lemon95MovieMsg_id.getText() + softKey.getKeyLabel());
                }

            }

            @Override
            public void onDelete(SoftKey key) {
                String text = lemon95MovieMsg_id.getText().toString();
                lemon95MovieMsg_id.setText(text.substring(0, text.length() - 1));
            }

            @Override
            public void onBack(SoftKey key) {
                finish();
            }
        });
        skbContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                OPENLOG.D("hasFocus:" + hasFocus);
                if (hasFocus) {
                    if (mOldSoftKey != null)
                        skbContainer.setKeySelected(mOldSoftKey);
                    else
                        skbContainer.setDefualtSelectKey(0, 0);
                } else {
                    mOldSoftKey = skbContainer.getSelectKey();
                    skbContainer.setKeySelected(null);
                }
            }
        });
        initReceiver();

        RxTextView.textChanges(lemon95MovieMsg_id)
                //监听输入完1秒之后发送事件
                .debounce(1500, TimeUnit.MILLISECONDS)
                //跳过输入框EditText 初始化的的时候产生的事件。
                .skip(1)
                //把观察者切换到UI线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        KLog.e("wlx", "charSequence.toString()  " + charSequence.toString());
                        mVideoBeans.clear();
                        mVideoGridViewAdpter.notifyDataSetChanged();
                        if (!TextUtils.isEmpty(charSequence)) {
                            getP().getVideo(charSequence.toString());
                        }
                    }
                });
    }

    private NineKeybordButton[] mNineKeybordButton = new NineKeybordButton[12];

    private void initNineT9() {
        initNineKeybordButton(0, R.id.button00, "1", "");
        initNineKeybordButton(1, R.id.button01, "2", "ABC");
        initNineKeybordButton(2, R.id.button02, "3", "DEF");
        initNineKeybordButton(3, R.id.button10, "4", "GHI");
        initNineKeybordButton(4, R.id.button11, "5", "JKL");
        initNineKeybordButton(5, R.id.button12, "6", "MNO");
        initNineKeybordButton(6, R.id.button20, "7", "PQRS");
        initNineKeybordButton(7, R.id.button21, "8", "TUV");
        initNineKeybordButton(8, R.id.button22, "9", "WXYZ");
        initNineKeybordButton(9, R.id.button30, "清空", "");
        initNineKeybordButton(10, R.id.button31, "0", "");
        initNineKeybordButton(11, R.id.button32, "删除", "");
    }

    private void initNineKeybordButton(int buttonId, int sourceId, String text1, String text2) {
        mNineKeybordButton[buttonId] = (NineKeybordButton) findViewById(sourceId);
        mNineKeybordButton[buttonId].setTextToTextView1(text1);
        mNineKeybordButton[buttonId].setOnNineKeyListener(new OnNineKeyListener() {
            @Override
            public void onKey(String key) {
                if (key.equals("删除")) {
                    String text = lemon95MovieMsg_id.getText().toString();
                    lemon95MovieMsg_id.setText(text.substring(0, text.length() - 1));
                } else if (key.equals("清空")) {
                    lemon95MovieMsg_id.setText("");
                } else {
                    lemon95MovieMsg_id.setText(lemon95MovieMsg_id.getText() + key);
                }
            }

        });
        if (text2 != "" && text2 != null) {
            mNineKeybordButton[buttonId].setTextToTextView2(text2);
        }
    }


    SoftKey mOldSoftKey;


    /**
     * 切换布局测试.
     * 因为布局不相同，所以属性不一样，
     * 需要重新设置(不用参考我的,只是DEMO)
     */
    private void setSkbContainerOther() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(false);
        skbContainer.setSoftKeySelectPadding(0);
        skbContainer.setSelectSofkKeyFront(false);
    }


    //按下
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getAction()== KeyEvent.KEYCODE_BACK) {
//            return false;
//        }
        KLog.e("   onKeyDown    " + event.getAction() + "  " + event.getKeyCode());
        if (skbContainer.onSoftKeyDown(keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }

    //抬起
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        KLog.e("   onKeyUp    " + event.getAction() + "  " + event.getKeyCode());
        if (skbContainer.onSoftKeyUp(keyCode, event))
            return true;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public SearchPresenter newP() {
        return new SearchPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all:
                setSelectAllT9(true);
                setSkbContainerOther();
                skbContainer.setSkbLayout(R.xml.skb_all_key);

                skbContainer.setVisibility(View.VISIBLE);
                linet9.setVisibility(View.GONE);
                break;
            case R.id.btn_t9:
                setSelectAllT9(false);
                skbContainer.setVisibility(View.GONE);
                linet9.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setSelectAllT9(boolean is) {
        btn_all.setSelected(is);
        btn_t9.setSelected(!is);
    }

    private void initGridView() {
        mOpenEffectBridge = (OpenEffectBridge) mainUpView2.getEffectBridge();
        EffectNoDrawBridge effectNoDrawBridge = new EffectNoDrawBridge();
        effectNoDrawBridge.setTranDurAnimTime(1);
        mOpenEffectBridge.setDrawUpRectEnabled(false);
        mainUpView2.setEffectBridge(effectNoDrawBridge); // 4.3以下版本边框移动.
        mainUpView2.setUpRectResource(R.drawable.bg_video_cover); // 设置移动边框的图片.

        gridviewtv.setIsSearch(true);
        mOpenEffectBridge.setVisibleWidget(true); // 隐藏
        mVideoGridViewAdpter = new TypeVideoGridViewAdpter(this, R.layout.item_type_video, mVideoBeans);
        gridviewtv.setAdapter(mVideoGridViewAdpter);
        gridviewtv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridviewtv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 这里注意要加判断是否为NULL.
                 * 因为在重新加载数据以后会出问题.
                 */
                mOpenEffectBridge.setVisibleWidget(false);
                if (view != null) {
                    if (point == 0) {
                        if (mOldGridView != view) {
                            view.bringToFront();
                            KLog.i(TAG, "放大" + position);
                            mainUpView2.setFocusView(view, mOldGridView, 1.1f);
                        }
                    } else {
                        view.bringToFront();
                        mainUpView2.setFocusView(view, mOldGridView, 1.1f);
                    }
                }
                point = position;
                mOldGridView = view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        gridviewtv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                KLog.i(TAG, "gridView" + hasFocus);
                if (hasFocus) {
                    if (mOldGridView == null) {
                        mOldGridView = gridviewtv.getChildAt(0);
                        gridviewtv.setFocusable(true);
                        gridviewtv.setFocusableInTouchMode(true);
                        gridviewtv.setSelection(0);
                    }
                    mainUpView2.setUpRectResource(R.drawable.bg_video_cover); // 设置移动边框的图片.
                    mOpenEffectBridge.setVisibleWidget(false);
                    mOldGridView.bringToFront();
                    mainUpView2.setFocusView(mOldGridView, 1.1f);
                } else {
                    mOpenEffectBridge.setVisibleWidget(true); // 隐藏
                    mainUpView2.setUpRectResource(R.drawable.test_rectangle); // 设置移动边框的图片.
                    mainUpView2.setUnFocusView(mOldGridView);
                }
            }
        });
        gridviewtv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoBean videoBean = mVideoBeans.get(position);
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra("id", videoBean.getId());
                intent.putExtra("classificationId", videoBean.getCpAlbumId());
                ArtUtils.startActivity(context, intent);
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHomeRecaiver != null) {
            unregisterReceiver(mHomeRecaiver);
        }
    }

    public void setVideoGridData(List<VideoBean> videoBeanData) {
        mVideoBeans.clear();
        mVideoBeans.addAll(videoBeanData);
        mVideoGridViewAdpter.notifyDataSetChanged();
    }


}
