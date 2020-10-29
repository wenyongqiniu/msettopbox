package com.yxws.msettopboxs.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DevInfoManager;
import android.os.Looper;

import com.chinamobile.SWDevInfoManager;
import com.chinamobile.authclient.AuthClient;
import com.chinamobile.authclient.Constants;
import com.socks.library.KLog;
import com.yxws.msettopboxs.config.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.DevInfoManager.EPG_ADDRESS;

public class DevInfoUtil {

    @SuppressLint("WrongConstant")
    public static void getValue(Activity activity) {
        DevInfoManager devInfoManager = SWDevInfoManager.getDevInfoManager(activity);
        final StringBuffer stringBuffer = new StringBuffer();
        if (devInfoManager != null) {
            try {
                stringBuffer.append("用户手机号 :").append(DevInfoManager.PHONE + "   ").append(devInfoManager.getValue(DevInfoManager.PHONE)).append("\n");
                stringBuffer.append("用户手机号服务密码 :").append(DevInfoManager.SPASSWORD + "   ").append(devInfoManager.getValue(DevInfoManager.SPASSWORD)).append("\n");
                stringBuffer.append("用户业务账号 :").append(DevInfoManager.ACCOUNT + "   ").append(devInfoManager.getValue(DevInfoManager.ACCOUNT)).append("\n");
                stringBuffer.append("用户业务账号密码 :").append(DevInfoManager.APASSWORD + "   ").append(devInfoManager.getValue(DevInfoManager.APASSWORD)).append("\n");
                stringBuffer.append("设备MAC地址 :").append(DevInfoManager.STB_MAC + "   ").append(devInfoManager.getValue(DevInfoManager.STB_MAC)).append("\n");
                stringBuffer.append("设备唯一序列号 :").append(DevInfoManager.STB_SN + "   ").append(devInfoManager.getValue(DevInfoManager.STB_SN)).append("\n");
                stringBuffer.append("开放平台主地址 :").append(DevInfoManager.PLAT_URL + "   ").append(devInfoManager.getValue(DevInfoManager.PLAT_URL)).append("\n");
                stringBuffer.append("开放平台备地址 :").append(DevInfoManager.PLAT_URLBACK + "   ").append(devInfoManager.getValue(DevInfoManager.PLAT_URLBACK)).append("\n");
                stringBuffer.append("终端管理主地址 :").append(DevInfoManager.CMSURL + "   ").append(devInfoManager.getValue(DevInfoManager.CMSURL)).append("\n");
                stringBuffer.append("终端管理备地址 :").append(DevInfoManager.CMSURLBACK + "   ").append(devInfoManager.getValue(DevInfoManager.CMSURLBACK)).append("\n");
                stringBuffer.append("家开平台地址 :").append(EPG_ADDRESS + "   ").append(devInfoManager.getValue(EPG_ADDRESS)).append("\n");
                stringBuffer.append("华为平台EPG地址 :").append(DevInfoManager.CDN_ADDRESS_BACK + "   ").append(devInfoManager.getValue(DevInfoManager.CDN_ADDRESS_BACK)).append("\n");
                stringBuffer.append("华为平台CDN地址 :").append(DevInfoManager.TV_ID + "   ").append(devInfoManager.getValue(DevInfoManager.TV_ID)).append("\n");
                stringBuffer.append("华为平台备份CDN地址 :").append(DevInfoManager.MANUFACTURER + "   ").append(devInfoManager.getValue(DevInfoManager.MANUFACTURER)).append("\n");
                stringBuffer.append("华为平台广电序列号 :").append(DevInfoManager.MODEL_NAME + "   ").append(devInfoManager.getValue(DevInfoManager.MODEL_NAME)).append("\n");
                stringBuffer.append("CPE设备型号名称 :").append(DevInfoManager.FIRMWARE_VERSION + "   ").append(devInfoManager.getValue(DevInfoManager.FIRMWARE_VERSION)).append("\n");
                stringBuffer.append("CDN类型 :").append(DevInfoManager.CDN_TYPE + "   ").append(devInfoManager.getValue(DevInfoManager.CDN_TYPE)).append("\n");
                stringBuffer.append("中兴开放平台主地址 :").append(DevInfoManager.PLAT_URL_ZTE + "   ").append(devInfoManager.getValue(DevInfoManager.PLAT_URL_ZTE)).append("\n");
                stringBuffer.append("中兴开放平台备地址 :").append(DevInfoManager.CDN_DISPATCH_URL + "   ").append(devInfoManager.getValue(DevInfoManager.CDN_DISPATCH_URL)).append("\n");
                stringBuffer.append("多CDN调度员系统主地址 :").append(DevInfoManager.CDN_DISPATCH_URL + "   ").append(devInfoManager.getValue(DevInfoManager.CDN_DISPATCH_URL)).append("\n");
                stringBuffer.append("多CDN调度员系统备地址 :").append(DevInfoManager.CDN_DISPATCH_URL_BACK + "   ").append(devInfoManager.getValue(DevInfoManager.CDN_DISPATCH_URL_BACK)).append("\n");

                KLog.e("wlx", stringBuffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void getToken(Activity activity, final OnResultCall onResultCall) {
        AuthClient mAuthClient = AuthClient.getIntance(activity);
        mAuthClient.getToken(new AuthClient.CallBack() {

            @Override
            public void onResult(JSONObject jsonObject) {
                KLog.e("wlx", "onResult: " + jsonObject.toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        try {
                            final String token = jsonObject.getString(Constants.VALUNE_KEY_TOKEN);
                            onResultCall.onResult(token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onResultCall.onResult(null);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            onResultCall.onResult(null);
                        }
                        Looper.loop();
                    }
                }).start();

            }
        });
    }
}
