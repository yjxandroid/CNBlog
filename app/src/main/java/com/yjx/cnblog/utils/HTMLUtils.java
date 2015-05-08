package com.yjx.cnblog.utils;/**
 * Created by yjx on 15/5/2.
 */

import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: YJX
 * Date: 2015-05-02
 * Time: 20:07
 * html工具
 */
public final class HTMLUtils {

    public static String changeHtmlImgSize(String html, int widht, int height) {
        Document document = Jsoup.parse(html);
        Elements pngs = document.select("img");
        for (Element element : pngs) {
            element.attr("widht", "400px");
            element.attr("height", "500px");
        }
        return document.outerHtml();

    }

    public static String replaceFont(String paramString) {
        String returnString = paramString;
        // 过滤style标签
        Pattern patternStyle = Pattern.compile(
                "<\\s*style\\s*[^>]*?\\s*>\\s*[\\s\\S]*?\\s*<\\s*/\\s*style\\s*?>",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = patternStyle.matcher(returnString);
        while (matcher.find()) {
            String group = matcher.group();
            if (group == null) {
                continue;
            }
            // System.out.println(group);
            returnString = returnString.replace(group, "$$");
        }
        return returnString;
    }

    /**
     * 替换img中src的内容
     *
     * @param htmlcontent
     * @return
     */
    public static String replaceImgTag(String htmlcontent) {
        Pattern pattern = Pattern.compile("<img(.+?)src=\"(.+?)\"(.+?)>");
        Matcher matcher = pattern.matcher(htmlcontent);
        while (true) {
            if (!(matcher.find()))
                return htmlcontent;
            Logger.d(matcher.toString());
            htmlcontent = matcher.replaceAll("【<a href=\"#\" onClick=\"window.cnblog.showImg('$2')\">点击查看图片</a>】");
        }
    }
    public static String FormatImgTag(String paramString)
    {
        Pattern patternImgSrc = Pattern.compile("<img(.+?)src=\"(.+?)\"(.+?)>");

        Matcher localMatcher = patternImgSrc.matcher(paramString);
        while (true)
        {
            if (!(localMatcher.find()))
                return paramString;
            paramString = localMatcher.replaceAll("<img src=\"$2\" onClick=\"window.cnblog.showImg('$2')\" >");
        }
    }
}
