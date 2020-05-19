package com.waoqi.mvp.mvp;

import android.os.Bundle;
import android.view.View;

/**
 * Created by wanglei on 2016/12/29.
 */

public interface IView<P> {




    void initData(Bundle savedInstanceState);

    int getOptionsMenuId();

    int getLayoutId();


    P newP();
}
