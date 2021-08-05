package com.tunasushi.tool

import java.text.DecimalFormat

/**
 * @author TunaSashimi
 * @date 2019-11-19 15:33
 * @Copyright 2019 TunaSashimi. All rights reserved.
 * @Description
 */
object NumberTool {
    /*** 小数点后一位 */
    const val PATTERN_0_0 = "##0.0"

    /*** 小数点后二位 */
    const val PATTERN_0_00 = "##0.00"

    /*** 小数点后三位 */
    const val PATTERN_0_000 = "##0.000"

    /**
     * 格式化小数点 double型
     *
     * @param num
     * @param pattern
     * @return
     */
    fun formatDecimal(num: Double, pattern: String?): String {
        val format = DecimalFormat(pattern)
        return format.format(num)
    }

    /**
     * 格式化小数点 float 型
     *
     * @param num
     * @param pattern
     * @return
     */
    fun formatDecimal(num: Float, pattern: String?): String {
        val format = DecimalFormat(pattern)
        return format.format(num.toDouble())
    }

    /**
     * 保留小数点后二位
     * @param num
     * @return
     */
    fun formatDecimal2(num: Double): String {
        return formatDecimal(num, PATTERN_0_00)
    }

    /**
     * 保留小数点后二位
     * @param num
     * @return
     */
    fun formatDecimal2(num: Float): String {
        return formatDecimal(num, PATTERN_0_00)
    }
}