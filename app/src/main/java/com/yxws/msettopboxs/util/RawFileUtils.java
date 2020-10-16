package com.yxws.msettopboxs.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * raw文件夹下的文件处理工具类
 */
public class RawFileUtils {
    private RawFileUtils() {

    }

    /**
     * 读取raw文件夹下的文件
     *
     * @param resourceId raw文件夹下的文件资源ID
     * @return 文件内容
     */
    public static String readFileFromRaw(Context context, int resourceId) {
        if (null == context || resourceId < 0) {
            return null;
        }

        String result = null;
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            result = readTextFromSDcard(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * @param is
     * @return
     * @throws Exception
     */
    private static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}