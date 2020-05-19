//package com.waoqi.mvp.mvp;
//
//import android.os.Bundle;
//import android.view.View;
//
//import com.tbruyelle.rxpermissions2.RxPermissions;
//import com.waoqi.mvp.XDroidConf;
//
//
///**
// * Created by wanglei on 2017/1/26.
// */
//
//public abstract class XLazyFragment<P extends IPresent>
//        extends LazyFragment implements IView<P> {
//
//    private VDelegate vDelegate;
//    private P p;
//
//    private RxPermissions rxPermissions;
//
//
//    @Override
//    protected void onCreateViewLazy(Bundle savedInstanceState) {
//        super.onCreateViewLazy(savedInstanceState);
//
//        getP();
//
//        if (getLayoutId() > 0) {
//            setContentView(getLayoutId());
//
//        }
//
//
//        initData(savedInstanceState);
//    }
//
//    public abstract void initView(View rootView);
//
//
//    public VDelegate getvDelegate() {
//        if (vDelegate == null) {
//            vDelegate = VDelegateBase.create(context);
//        }
//        return vDelegate;
//    }
//
//    protected P getP() {
//        if (p == null) {
//            p = newP();
//        }
//        if (p != null) {
//            if (!p.hasV()) {
//                p.attachV(this);
//            }
//        }
//        return p;
//    }
//
//    @Override
//    protected void onDestoryLazy() {
//        super.onDestoryLazy();
//
//        if (getP() != null) {
//            getP().detachV();
//        }
//        getvDelegate().destory();
//
//        p = null;
//        vDelegate = null;
//    }
//
//
//    protected RxPermissions getRxPermissions() {
//        rxPermissions = new RxPermissions(getActivity());
//        rxPermissions.setLogging(XDroidConf.DEV);
//        return rxPermissions;
//    }
//
//
//    @Override
//    public int getOptionsMenuId() {
//        return 0;
//    }
//
//
//
//
//
//}
