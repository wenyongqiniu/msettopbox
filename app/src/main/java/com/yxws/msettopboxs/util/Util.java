package com.yxws.msettopboxs.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Util {

    public static float getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static float getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics;
    }

}
