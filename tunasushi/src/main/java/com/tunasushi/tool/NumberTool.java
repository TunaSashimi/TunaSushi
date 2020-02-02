package com.tunasushi.tool;

import java.text.DecimalFormat;

/**
 * @author Tunasashimi
 * @date 2019-11-19 15:33
 * @Copyright 2019 Sashimi. All rights reserved.
 * @Description
 */
public class NumberTool {

    /*** 小数点后一位*/
    public static final String PATTERN_0_0 = "##0.0";
    /*** 小数点后二位*/
    public static final String PATTERN_0_00 = "##0.00";
    /*** 小数点后三位*/
    public static final String PATTERN_0_000 = "##0.000";

    /**
     * 格式化小数点 double型
     *
     * @param num
     * @param pattern
     * @return
     */
    public static String formatDecimal(double num, String pattern) {
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(num);
    }

    /**
     * 格式化小数点 float 型
     *
     * @param num
     * @param pattern
     * @return
     */
    public static String formatDecimal(float num, String pattern) {
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(num);
    }

    /**
     * 保留小数点后二位
     * @param num
     * @return
     */
    public static String formatDecimal2(double num) {
        return formatDecimal(num, PATTERN_0_00);
    }
    /**
     * 保留小数点后二位
     * @param num
     * @return
     */
    public static String formatDecimal2(float num) {
        return formatDecimal(num, PATTERN_0_00);
    }

}
