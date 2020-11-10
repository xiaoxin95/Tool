package com.unipock.unipaytool.utils;


import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额处理
 */
public class AmountUtils {

    /**
     * 金额相加
     * 钱
     * @param one
     * @param tow
     * @return
     */
    public static long amountAdd(long one, long tow) {
        BigDecimal oneBigDecimal = new BigDecimal(one);
        BigDecimal twoBigDecimal = new BigDecimal(tow);
        return oneBigDecimal.add(twoBigDecimal).longValue();
    }

    /**
     * 千分位转不带 , 字符串
     *
     * @param thousandsPrice
     * @return
     */
    public static String thousandsConvertDou(String thousandsPrice) {
        return thousandsPrice.replaceAll(",", "");
    }


    public static String amountFormat(long amount){
        double amountValue = new BigDecimal(amount).divide(new BigDecimal("100"),BigDecimal.ROUND_HALF_UP).doubleValue();
        return getMoneyType(String.valueOf(amountValue));
    }


    /**
     * 金额格式转换，千分位展示
     *
     * @param money 金额
     * @return
     */
    private static String getMoneyType(String money) {
        if (money.equals(".")) {
            return money;
        }
        if (money.equals("0")) {
            return money;
        }
        BigDecimal a = new BigDecimal(money);
        DecimalFormat df = new DecimalFormat(",###,##0.00"); //保留一位小数
        String numString = df.format(a);
        return numString;
    }

}
