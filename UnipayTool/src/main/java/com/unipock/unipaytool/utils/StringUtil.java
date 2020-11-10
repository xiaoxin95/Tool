package com.unipock.unipaytool.utils;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class StringUtil {

    private final static Pattern URL = Pattern.compile("[a-zA-z]+://[^\\s]*");// 网址
    // 时间(小时:分钟, 24小时制)
    private final static Pattern TIME = Pattern
            .compile("((1|0?)[0-9]|2[0-3]):([0-5][0-9])");
    // 登录密码校验
    private static final Pattern loginPassword = Pattern
            .compile("((\\d+[a-zA-Z]+)|([a-zA-Z]+\\d+))+");

    /**
     * 判断是不是URL地址
     *
     * @param url
     * @return
     */
    public static boolean isURL(String url) {
        if (url == null || url.trim().length() == 0) {
            return false;
        }
        return URL.matcher(url).matches();
    }

    /**
     * 判断字符串是否为空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 校验登录密码
     *
     * @param pwd
     * @return
     */
    public static boolean isLoginPassword(String pwd) {
        if (pwd == null || pwd.trim().length() == 0) {
            return false;
        }
        return loginPassword.matcher(pwd).matches();
    }


    /**
     * 日期转换为自定义格式输出
     */
    public static String dateToString(Date date, String formatType) {
        if (date == null) {
            return null;
        }
        if (formatType == null || "".equals(formatType)) {
            return null;
        }
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatType);
            dateStr = sdf.format(date);
            return dateStr;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 本月月初
     */
    public static String thisMonth() {
        //本月
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //当前月第一天
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * 当天
     *
     * @return
     */
    public static String today() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 当天
     *
     * @return
     */
    public static String todayEx() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    public static Calendar stringToCalendar(String time) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(time);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * 将元字符串转换为分
     *
     * @param amountStr
     * @return
     */
    public static long parseAmountStr2Long(String amountStr) {
        if (amountStr == null || "".equals(amountStr)) {
            return 0L;
        }
        double amount = parseDouble(amountStr);
        Double db = amount * 100;
        DecimalFormat df = new DecimalFormat("#");
        String s = df.format(db);
        return Long.parseLong(s);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss
     *
     * @param ts yyyy-MM-dd HH:mm:ss
     * @return HH:mm
     */
    public static String getStringToTimeFormat(String ts) {
        if (ts == null || "".equals(ts)) {
            return null;
        }
        String dateStr = "";
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateValue = simpleDateFormat.parse(ts, position);
        SimpleDateFormat formatters = new SimpleDateFormat("HH:mm");
        try {
            dateStr = formatters.format(dateValue);
            return dateStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 后面留两位小数点
     *
     * @param money
     * @return
     */
    public static String amountFormat(String money) {
        double withdrawalAmount = parseDouble(money);
        DecimalFormat df = new DecimalFormat("0.00");
        String format = df.format((withdrawalAmount));
        return format;
    }

    /**
     * 金额格式转换，千分位展示
     *
     * @param money 金额
     * @return
     */
    public static String getMoneyType(String money) {
        if (money.equals(".")) {
            return money;
        }
        if (money.equals("0")) {
            return money;
        }
        BigDecimal a = new BigDecimal(money);
//         DecimalFormat df=new DecimalFormat(",###,##0"); //没有小数
        DecimalFormat df = new DecimalFormat(",###,##0.00"); //保留一位小数
        String numString = df.format(a);
        return numString;
    }


    /**
     * 金额格式转换，千分位展示
     *
     * @param money 金额
     * @return
     */
    public static String getMoneyTypeNoDecimal(String money) {
        if (money.equals(".")) {
            return money;
        }
        if (money.equals("0")) {
            return money;
        }
        BigDecimal a = new BigDecimal(money);
        DecimalFormat df = new DecimalFormat(",###,##0"); //没有小数
//        DecimalFormat df = new DecimalFormat(",###,##0.00"); //保留一位小数
        String numString = df.format(a);
        return numString;
    }




    /**
     * 字符转map
     *
     * @param mapJson
     * @return
     */
    public static Map<String, Object> jsonToMap(String mapJson) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map2 = gson.fromJson(mapJson, type);
        return map2;
    }


    public static String stringDateFormat(String time, String dataFormat, String dataFormat2) {
        SimpleDateFormat simpleDateFormat;
        ParsePosition position = new ParsePosition(0);
        simpleDateFormat = new SimpleDateFormat(dataFormat);
        Date parse = simpleDateFormat.parse(time, position);
        simpleDateFormat = new SimpleDateFormat(dataFormat2);
        return simpleDateFormat.format(parse);
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 转换为元（两位小数点），并且转化为千分位
     *
     * @param amount
     * @return
     */
    public static String amountConvert(Long amount) {
        if (amount == null) {
            amount = 0L;
        }
        double amountValue = new BigDecimal(amount).divide(new BigDecimal("100")).doubleValue();
        return getMoneyType(String.valueOf(amountValue));
    }


    public static String dealDateFormat(String oldDate) {
        if (oldDate == null) {
            return "";
        }
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df2.format(date1);
    }


    /**
     * 返回当天的时间段
     *
     * @param date
     */
    public static String dayPeriod(String date) {

        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat mmddFormat = new SimpleDateFormat("MM-dd");
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String str = null;
        Date parse = null;
        try {
            parse = dateFormat.parse(date);
            str = df.format(parse);
            int a = Integer.parseInt(str);
            if (a >= 0 && a <= 6) {
                return mmddFormat.format(parse) + " 上午";
            }
            if (a > 6 && a <= 12) {
                return mmddFormat.format(parse) + " 上午";
            }
            if (a == 13) {
                return mmddFormat.format(parse) + " 下午";
            }
            if (a > 13 && a <= 18) {
                return mmddFormat.format(parse) + " 下午";
            }
            if (a > 18 && a <= 24) {
                return mmddFormat.format(parse) + " 下午";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间格式为 MMddHHmm
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String mmddhhmm(String date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat mmddhhmmTemp = new SimpleDateFormat("MM/dd HH:mm");
        String res = "";
        Date parse;
        try {
            parse = dateFormat.parse(date);
            res = mmddhhmmTemp.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * 时间格式为 MMddHHmm
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String yyyyMMdd(String date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat mmddhhmmTemp = new SimpleDateFormat("yyyy-MM-dd");
        String res = "";
        Date parse;
        try {
            parse = dateFormat.parse(date);
            res = mmddhhmmTemp.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 金额相加
     * 钱
     * @param userAmount
     * @param orderAmount
     * @return
     */
    public static long amountAdd(long userAmount, long orderAmount) {
        BigDecimal userBigDecimal = new BigDecimal(userAmount);
        BigDecimal OrderBigDecimal = new BigDecimal(orderAmount);
        return userBigDecimal.add(OrderBigDecimal).longValue();
    }

    /**
     * 时间戳转时间
     *
     * @param seconds
     * @return
     */
    public static String timestampToTime(String seconds) {

        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(new Date(Long.valueOf(seconds)));
        } catch (Exception ex) {
            return seconds;
        }
    }
    /**
     * 时间戳转时间
     *
     * @param seconds
     * @return
     */
    public static String timestampToTime(String seconds,String format) {

        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(new Date(Long.valueOf(seconds)));
        } catch (Exception ex) {
            return seconds;
        }
    }

    /**
     * 相差多少分钟
     *
     * @param addedDate
     * @return
     */
    public static long isTimeout(String addedDate) {
        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());
        long NTime = 0;
        long OTime = 0;
        try {
            NTime = sdf.parse(currentTime).getTime();
            //从对象中拿到时间
            OTime = sdf.parse(addedDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = (NTime - OTime) / 1000;
        return diff;
    }


}
