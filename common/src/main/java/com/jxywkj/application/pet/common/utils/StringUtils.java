package com.jxywkj.application.pet.common.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName StringUtils
 * @Description String工具类
 * @Author LiuXiangLin
 * @Date 2019/7/22 16:48
 * @Version 1.0
 **/
public class StringUtils {
    /**
     * @return java.lang.String
     * @Author LiuXiangLin
     * @Description 获取UUID
     * @Date 15:52 2019/7/22
     * @Param []
     **/
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @return boolean
     * @Author LiuXiangLin
     * @Description 字符串是否为空（空串和null）
     * @Date 17:29 2019/7/22
     * @Param [str]
     **/
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * @return boolean
     * @Author LiuXiangLin
     * @Description 是否为空（包括null,'',以及空格）
     * @Date 17:41 2019/7/22
     * @Param [str]
     **/
    public static boolean isBlank(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }

        // 遍历字符串
        for (int i = 0; i < str.length(); ++i) {
            // 只要有一个不是空白符就返回false
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * @return boolean
     * @Author LiuXiangLin
     * @Description 判断某个字符串是不是在第一行第一次出现
     * @Date 16:52 2019/9/6
     * @Param [str, regexStr]
     **/
    public static boolean startWith(String str, String regexStr) {
        return str.indexOf(regexStr) == 0;
    }

    /**
     * <p>
     * 过滤表情符号
     * </p>
     *
     * @param source 需要过滤的文字
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 16:39 2019/11/18
     **/
    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("*");
                return source;
            }
            return source;
        }
        return source;
    }

    /**
     * <p>
     * 字符串是否不为空
     * </p>
     *
     * @param str 需要判断的字符串
     * @return boolean
     * @author LiuXiangLin
     * @date 16:25 2020/1/6
     **/
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
