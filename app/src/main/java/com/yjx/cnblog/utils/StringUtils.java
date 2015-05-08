package com.yjx.cnblog.utils;/**
 * Created by yjx on 15/5/2.
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: YJX
 * Date: 2015-05-02
 * Time: 20:44
 */
public final class StringUtils {
    public static String readIn(InputStream in) {
        String content = "";
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        byte[] bytes = new byte[1024];
        try {
            while ((content = reader.readLine()) != null) {
                sb.append(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb
                .toString();
    }
}
