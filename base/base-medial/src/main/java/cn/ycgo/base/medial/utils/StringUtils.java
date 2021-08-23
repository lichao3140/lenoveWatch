package cn.ycgo.base.medial.utils;


import android.text.TextUtils;
import android.util.Base64;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StringUtils
 * @Description: java类作用描述
 * @Author: Brian
 * @CreateDate: 2019/3/2 12:29
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains(".")) return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String formatBigNum(long num) {
        long[] values = {1000, 10000};
        String[] units = {"k", "w"};
        String unit = "";
        double dNum = 0;
        if (num < values[0]) {
            return num + "";
        } else {
            if (num < values[1]) {
                unit = units[0];
                BigDecimal b1 = new BigDecimal(Double.toString(num));
                BigDecimal b2 = new BigDecimal(Double.toString(values[0]));
                dNum = b1.divide(b2, 1, BigDecimal.ROUND_HALF_DOWN).doubleValue();
            } else {
                unit = units[1];
                BigDecimal b1 = new BigDecimal(Double.toString(num));
                BigDecimal b2 = new BigDecimal(Double.toString(values[1]));
                dNum = b1.divide(b2, 1, BigDecimal.ROUND_HALF_DOWN).doubleValue();
            }
            return dNum + unit;
        }
    }


    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断邮箱是否合法
     */
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    /**
     * 判断是否是中文
     *
     * @param c
     * @return
     */
    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    //直接base64解密 如果无法解密，则捕获异常，说明不是加密的
    public static boolean isBase64(String str) {
        try {
            Base64.decode(str, Base64.NO_WRAP);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 隐藏手机中间号码
     * @param phone
     */
    public static String hintPhoneNum(String phone){
        if(!TextUtils.isEmpty(phone) && phone.length() > 6 ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        return "";
    }

}
