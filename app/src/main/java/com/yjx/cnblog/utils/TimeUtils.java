package com.yjx.cnblog.utils;/**
 * Created by yjx on 15/5/2.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: YJX
 * Date: 2015-05-02
 * Time: 15:39
 */
public final class TimeUtils {


    public static String parseTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+ss:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(time);
            return sdf1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

}
