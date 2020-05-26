//package com.waoqi.msettopboxs.presenter;
//
//import com.waoqi.msettopboxs.ui.activity.SplashActivity;
//import com.waoqi.mvp.mvp.XPresent;
//
//import java.util.concurrent.TimeUnit;
//
//import io.reactivex.Observable;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//
//public class SplashPresenter extends XPresent<SplashActivity> {
//
//
//    private static final long MAX_TIME = 3;
//
//    public void startMainActivity() {
////        Observable.interval(0, 1, TimeUnit.SECONDS)
////                .take(MAX_TIME + 1)
////                .subscribeOn(Schedulers.io())//倒计时放在io线程中
////                .doOnSubscribe(new Consumer<Disposable>() {
////                    @Override
////                    public void accept(Disposable disposable) throws Exception {
////                        addDispose(disposable);
////                    }
////                })
////                .observeOn(AndroidSchedulers.mainThread())//显示放在主线程。
////                .subscribe(new Observer<Long>() {
////                    @Override
////                    public void onSubscribe(Disposable d) {
////
////                    }
////
////                    @Override
////                    public void onNext(Long aLong) {
////
////                    }
////
////                    @Override
////                    public void onError(Throwable e) {
////
////                    }
////
////                    @Override
////                    public void onComplete() {
////                        getV().startMainActivity();
////                    }
////                });
////        getV().startMainActivity();
//
//    }
//}
