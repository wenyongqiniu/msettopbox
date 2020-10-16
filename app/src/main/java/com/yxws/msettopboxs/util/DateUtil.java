package com.yxws.msettopboxs.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * author : Zzy
 * date   : 2020/3/27
 */
public class DateUtil {
    /**
     * 默认日期格式
     */
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DISPLAY_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DISPLAY_FORMAT_MMDDHHMM = "MM-dd HH:mm";

    /**
     * @param pubtime 样例：2011-06-20T17:23:11Z
     * @return 样例：05-10 17:11
     */
    public static String getFormatTime(String pubtime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = null;
        try {
            date = df.parse(pubtime.replace("Z", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (null == date) ? null : (new SimpleDateFormat("MM-dd HH:mm"))
                .format(date);
    }

    /**
     * @param pubtime 样例：2014-08-08 18:30:40
     * @return 样例：05-10 17:11
     */
    public static String getFormatTime2(String pubtime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = df.parse(pubtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (null == date) ? null : (new SimpleDateFormat(DISPLAY_FORMAT))
                .format(date);
    }

    /**
     * @param pubtime 样例：2011-06-20T17:23:11Z
     * @param format
     * @return
     */
    public static String getFormatTime(String pubtime, String format) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = null;
        try {
            date = df.parse(pubtime.replace("Z", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (null == date) ? null : (new SimpleDateFormat(format))
                .format(date);
    }

    /**
     * @param pubtime 样例：2011-06-20 17:23:11
     * @param format
     * @return
     */
    public static String getFormatTime3(String pubtime, String format) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = df.parse(pubtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (null == date) ? null : (new SimpleDateFormat(format))
                .format(date);
    }



    /**
     * 获取两个时间戳之间差值,并且返回小时
     */
    public static int subTimeStamp2Hour(Timestamp one, Timestamp two) {
        int minute = subTimeStamp2Minute(one, two);
        return minute / 60;
    }

    /**
     * 获取两个时间戳之间差值,并且返回分钟
     */
    public static int subTimeStamp2Minute(Timestamp one, Timestamp two) {
        return (int) (two.getTime() - one.getTime()) / (1000 * 60);
    }

    /**
     * 获取两个时间戳之间差值,并且返回秒
     */
    public static int subTimeStamp2Second(Timestamp one, Timestamp two) {
        int minute = subTimeStamp2Minute(one, two);
        return minute * 60;
    }

    /**
     * Date转Timestamp
     *
     * @param pubtime
     * @return
     */
    public static Timestamp string2Timestamp(String pubtime) {
        Timestamp timestamp = null;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = null;
        try {
            date = df.parse(pubtime.replace("Z", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (null != date) {
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df1.format(date);
            timestamp = Timestamp.valueOf(time);
        }

        return timestamp;
    }

    /**
     * 获取用于显示的时间
     *
     * @param pubtime
     * @return
     */
    public static String getTime(String pubtime) {
        String displayTime = getFormatTime(pubtime);
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Timestamp pubTimestamp = DateUtil.string2Timestamp(pubtime);

        if (null != pubTimestamp) {
            int second = subTimeStamp2Second(pubTimestamp, current);
            if (second < 60 && second > 0) {
                displayTime = second + "秒前";
            } else {
                int minute = subTimeStamp2Minute(pubTimestamp, current);
                if (minute < 60 && minute > 0) {
                    displayTime = minute + "分钟前";
                } else {
                    int hour = subTimeStamp2Hour(pubTimestamp, current);

                    if (hour < 24 && hour > 0) {
                        displayTime = hour + "小时前";
                    }
                }
            }
        }

        return displayTime;
    }



    /**
     * 两个格林时间差
     *
     * @param date1 当前时间
     * @param date2 过去时间
     * @return
     */
    public static String compressData(long date1, long date2) {
        String strDate = "";
        if (date1 == 0 || date2 == 0) {
            // strDate = String.valueOf(0)+" 秒钟前";
            strDate = "正在";
        } else {
            long cmDate = date1 - date2;
            long mDate = cmDate / 1000; // 将毫秒转为 秒
            if (mDate >= 60) {
                long minDate = mDate / 60;// 将秒转化为分钟
                if (minDate >= 60) {
                    long hourDate = minDate / 60;// 将分钟转化为小时
                    if (hourDate > 24) {
                        long dayDate = hourDate / 24;
                        if (dayDate == 0) {
                            dayDate = 1;
                        }
                        strDate = String.valueOf(dayDate) + " 天前";
                    } else {
                        if (hourDate == 0) {
                            hourDate = 1;
                        }
                        strDate = String.valueOf(hourDate) + " 小时前";
                    }
                } else {
                    if (minDate == 0) {
                        minDate = 1;
                    }
                    strDate = String.valueOf(minDate) + " 分钟前";
                }
            } else {
                if (mDate == 0) {
                    mDate = 1;
                }
                strDate = String.valueOf(mDate) + " 秒钟前";
            }
        }
        if (strDate.startsWith("-")) {
            return "刚刚";
        }
        return strDate;
    }

    /**
     * 显示时间为 几秒前，几分钟前，几小时前，几天前，几月前，几年前的实现
     *
     * @param date
     * @return
     */
    private static final long ONE_MINUTE = 60000L;

    private static final long ONE_HOUR = 3600000L;

    private static final long ONE_DAY = 86400000L;

    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";

    private static final String ONE_MINUTE_AGO = "分钟前";

    private static final String ONE_HOUR_AGO = "小时前";

    private static final String ONE_DAY_AGO = "天前";

    private static final String ONE_MONTH_AGO = "月前";

    private static final String ONE_YEAR_AGO = "年前";

    public static String format(Date date) {
        String strDate;

        long delta = new Date().getTime() - date.getTime();

        if (delta < 1L * ONE_MINUTE) {

            long seconds = toSeconds(delta);

            strDate = (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;

        } else if (delta < 45L * ONE_MINUTE) {

            long minutes = toMinutes(delta);

            strDate = (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;

        } else if (delta < 24L * ONE_HOUR) {

            long hours = toHours(delta);

            strDate = (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;

        } else if (delta < 48L * ONE_HOUR) {

            strDate = "昨天";

        } else if (delta < 30L * ONE_DAY) {

            long days = toDays(delta);

            strDate = (days <= 0 ? 1 : days) + ONE_DAY_AGO;

        } else if (delta < 12L * 4L * ONE_WEEK) {

            long months = toMonths(delta);

            strDate = (months <= 0 ? 1 : months) + ONE_MONTH_AGO;

        } else {

            long years = toYears(delta);

            strDate = (years <= 0 ? 1 : years) + ONE_YEAR_AGO;

        }
        if (strDate.startsWith("-")) {
            return "刚刚";
        }
        return strDate;
    }

    private static long toSeconds(long date) {

        return date / 1000L;

    }

    private static long toMinutes(long date) {

        return toSeconds(date) / 60L;

    }

    private static long toHours(long date) {

        return toMinutes(date) / 60L;

    }

    private static long toDays(long date) {

        return toHours(date) / 24L;

    }

    private static long toMonths(long date) {

        return toDays(date) / 30L;

    }

    private static long toYears(long date) {

        return toMonths(date) / 365L;

    }


    public static String transLongToString(long time, String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        // 前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        Date dt = new Date(time);
        String sDateTime = sdf.format(dt); // 得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }

    public static String getDisplayTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_FORMAT);
        // 前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        Date dt = new Date(time);
        String sDateTime = sdf.format(dt); // 得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }

    /**
     * 计算年月日加减
     *
     * @param startDate
     * @param dateFormat
     * @param dateLength
     * @param dateUnit
     * @return
     * @throws ParseException
     */
    public static String dateArithmetic(String startDate, String dateFormat, int dateLength, int dateUnit) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date dt = sdf.parse(startDate);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(dateUnit, dateLength);
        Date dt1 = rightNow.getTime();
        return sdf.format(dt1);
    }

    public static String getVideoDisplayDuration(long second) {
        long h = second / 3600;
        long m = (second % 3600) / 60;
        long s = (second % 3600) % 60;
        StringBuffer sb = new StringBuffer();
        if (h > 0) {
            if (h < 10) {
                sb.append(0);
            }
            sb.append(h);
            sb.append(":");
        }
        if (m < 10) {
            sb.append(0);
        }
        sb.append(m);
        sb.append(":");
        if (s < 10) {
            sb.append(0);
        }
        sb.append(s);
        return sb.toString();
    }

    //比较时间大小
    public static boolean compare(String time1, String time2) {
        try {
            //如果想比较日期则写成"yyyy-MM-dd"就可以了   
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //将字符串形式的时间转化为Date类型的时间   
            Date a = sdf.parse(time1);
            Date b = sdf.parse(time2);

            // Date类的一个方法，如果a早于b返回true，否则返回false   
            if (a.before(b) || a.equals(b)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        }

        return false;
    }


    public static String getFormatHMS(long time) {
        time = time / 1000;
        int s = (int) (time % 60);
        int m = (int) (time / 60);
        int h = (int) (time / 3600);
        return String.format("%02d:%02d:%02d", h, m, s);
    }


}
