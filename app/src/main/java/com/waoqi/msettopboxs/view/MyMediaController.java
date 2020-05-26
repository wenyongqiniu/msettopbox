//package com.waoqi.msettopboxs.view;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.MediaController;
//
//public class MyMediaController extends MediaController {
//    private   Context mContext;
//
//    public MyMediaController(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mContext = context;
//    }
//
//    public MyMediaController(Context context, boolean useFastForward) {
//        super(context, useFastForward);
//        mContext = context;
//    }
//
//    public MyMediaController(Context context) {
//        super(context);
//        mContext = context;
//    }
//
//
//    @Override
//    public void setAnchorView(View view) {
//        super.setAnchorView(view);
//        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//        );
//        removeAllViews();
//        View v = makeControllerView();
//        addView(v, frameParams);
//    }
//
//    private View makeControllerView() {
//        LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mRoot = inflate.inflate(com.android.internal.R.layout.media_controller, null);
//
//        initControllerView(mRoot);
//        return null;
//    }
//}
