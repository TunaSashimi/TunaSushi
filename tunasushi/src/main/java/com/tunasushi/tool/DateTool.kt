package com.tunasushi.tool

import com.tunasushi.tool.DateTool
import java.lang.Exception
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author TunaSashimi
 * @date 2019-11-19 15:25
 * @Copyright 2019 TunaSashimi. All rights reserved.
 * @Description
 */
object DateTool {
    /**
     * 时间日期格式化到年月日时分秒.
     */
    const val dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss"

    /**
     * 时间日期格式化到年月日.
     */
    const val dateFormatYMD = "yyyy-MM-dd"

    /**
     * 时间日期格式化到年月.
     */
    const val dateFormatYM = "yyyy-MM"

    /**
     * 时间日期格式化到年月日时分.
     */
    const val dateFormatYMDHM = "yyyy-MM-dd HH:mm"

    /**
     * 时间日期格式化到月日.
     */
    const val dateFormatMD = "MM/dd"

    /**
     * 时分秒.
     */
    const val dateFormatHMS = "HH:mm:ss"

    /**
     * 时分.
     */
    const val dateFormatHM = "HH:mm"

    /**
     * 上午.
     */
    const val AM = "AM"

    /**
     * 下午.
     */
    const val PM = "PM"
    private var dif: Long = 0

    /**
     * 日期转成字符串
     *
     * @param strFormat 格式定义 如：yyyy-MM-dd HH:mm:ss
     * @param date      日期字符串
     * @return String
     */
    fun dateToString(strFormat: String?, date: Date?): String {
        val dateFormat = SimpleDateFormat(strFormat)
        return dateFormat.format(date)
    }// 设置日期格式

    /**
     * 获取当前年.
     *
     * @return String
     */
    val currYear: String
        get() {
            val df = SimpleDateFormat("yyyy") // 设置日期格式
            return df.format(Date())
        }

    /**
     * 获取当月.
     *
     * @return String
     */
    val currMonth: Int
        get() {
            val c = Calendar.getInstance()
            return c[Calendar.MONTH] + 1
        }

    /**
     * 获取当日.
     *
     * @return String
     */
    val currDay: Int
        get() {
            val c = Calendar.getInstance()
            return c[Calendar.DAY_OF_MONTH]
        }

    /**
     * 获取当前时.
     *
     * @return String
     */
    val currHour: Int
        get() {
            val c = Calendar.getInstance()
            return c[Calendar.HOUR]
        }

    /**
     * 获取当前分.
     *
     * @return String
     */
    val currMin: Int
        get() {
            val c = Calendar.getInstance()
            return c[Calendar.MINUTE]
        }

    /**
     * 获得当前时间的毫秒数
     *
     * @return
     */
    val cuurentTime: Long
        get() = System.currentTimeMillis()

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param milliseconds1 the milliseconds1
     * @param milliseconds2 the milliseconds2
     * @return int 所差的天数
     */
    fun getOffectDay(milliseconds1: Long, milliseconds2: Long): Int {
        val calendar1 = Calendar.getInstance()
        calendar1.timeInMillis = milliseconds1
        val calendar2 = Calendar.getInstance()
        calendar2.timeInMillis = milliseconds2
        // 先判断是否同年
        val y1 = calendar1[Calendar.YEAR]
        val y2 = calendar2[Calendar.YEAR]
        val d1 = calendar1[Calendar.DAY_OF_YEAR]
        val d2 = calendar2[Calendar.DAY_OF_YEAR]
        var maxDays = 0
        var day = 0
        if (y1 - y2 > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR)
            day = d1 - d2 + maxDays
        } else if (y1 - y2 < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR)
            day = d1 - d2 - maxDays
        } else {
            day = d1 - d2
        }
        return day
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    fun getOffectHour(date1: Long, date2: Long): Int {
        val calendar1 = Calendar.getInstance()
        calendar1.timeInMillis = date1
        val calendar2 = Calendar.getInstance()
        calendar2.timeInMillis = date2
        val h1 = calendar1[Calendar.HOUR_OF_DAY]
        val h2 = calendar2[Calendar.HOUR_OF_DAY]
        var h = 0
        val day = getOffectDay(date1, date2)
        h = h1 - h2 + day * 24
        return h
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    fun getOffectMinutes(date1: Long, date2: Long): Int {
        val calendar1 = Calendar.getInstance()
        calendar1.timeInMillis = date1
        val calendar2 = Calendar.getInstance()
        calendar2.timeInMillis = date2
        val m1 = calendar1[Calendar.MINUTE]
        val m2 = calendar2[Calendar.MINUTE]
        val h = getOffectHour(date1, date2)
        var m = 0
        m = m1 - m2 + h * 60
        return m
    }

    /**
     * 取指定日期为星期几.
     *
     * @param strDate  指定日期
     * @param inFormat 指定日期格式
     * @return String 星期几
     */
    fun getWeekNumber(strDate: String?, inFormat: String?): String {
        var week = "星期日"
        val calendar: Calendar = GregorianCalendar()
        val df: DateFormat = SimpleDateFormat(inFormat)
        try {
            calendar.time = df.parse(strDate)
        } catch (e: Exception) {
            return "错误"
        }
        val intTemp = calendar[Calendar.DAY_OF_WEEK] - 1
        when (intTemp) {
            0 -> week = "星期日"
            1 -> week = "星期一"
            2 -> week = "星期二"
            3 -> week = "星期三"
            4 -> week = "星期四"
            5 -> week = "星期五"
            6 -> week = "星期六"
        }
        return week
    }

    /**
     * 根据给定的日期判断是否为上下午.
     *
     * @param strDate the str date
     * @param format  the format
     * @return the time quantum
     */
    fun getTimeQuantum(strDate: String?, format: String?): String {
        val mDate = getDateByFormat(strDate, format)
        val hour = mDate!!.hours
        return if (hour >= 12) "PM" else "AM"
    }

    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return Date Date类型日期时间
     */
    fun getDateByFormat(strDate: String?, format: String?): Date? {
        val mSimpleDateFormat = SimpleDateFormat(format)
        var date: Date? = null
        try {
            date = mSimpleDateFormat.parse(strDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun formatDate(millisecond: Long): String? {
        val startTime = Date(millisecond)
        val nowDate = Calendar.getInstance().time
        if (startTime == null || nowDate == null) {
            return null
        }
        var timeLong = nowDate.time - millisecond
        return if (timeLong <= 20 * 1000) {
            "刚刚"
        } else if (timeLong < 60 * 1000)
            (timeLong / 1000).toString() + "秒前"
        else if (timeLong < 60 * 60 * 1000) {
            timeLong = timeLong / 1000 / 60
            timeLong.toString() + "分钟前"
        } else if (timeLong < 60 * 60 * 24 * 1000) {
            timeLong = timeLong / 60 / 60 / 1000
            timeLong.toString() + "小时前"
        } else if (timeLong < 60 * 60 * 24 * 1000 * 7) {
            timeLong = timeLong / 1000 / 60 / 60 / 24
            timeLong.toString() + "天前"
        } else if (timeLong < 60 * 60 * 24 * 1000 * 7 * 4) {
            timeLong = timeLong / 1000 / 60 / 60 / 24 / 7
            timeLong.toString() + "周前"
        } else {
            val sdf = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )
            sdf.format(startTime)
        }
    }

    /**
     * 根据传入的年份和月份,判断当前月有多少天.
     *
     * @param year
     * @param month
     * @return
     */
    fun getDaysOfMonth(year: Int, month: Int): Int {
        when (month) {
            1, 3, 5, 7, 8, 10, 12 -> return 31
            2 -> return if (isLeap(year)) {
                29
            } else {
                28
            }
            4, 6, 9, 11 -> return 30
        }
        return -1
    }

    /**
     * 判断是否为闰年.
     *
     * @param year
     * @return
     */
    fun isLeap(year: Int): Boolean {
        return if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            true
        } else false
    }

    /**
     * 根据传入的年份和月份，获取当前月份的日历分布.
     *
     * @param year
     * @param month
     * @return
     */
    fun getDayOfMonthFormat(year: Int, month: Int): Array<IntArray> {
        val calendar = Calendar.getInstance()
        calendar[year, month - 1] = 1 //设置时间为每月的第一天
        //设置日历格式数组,6行7列
        val days = Array(6) { IntArray(7) }
        //设置该月的第一天是周几
        val daysOfFirstWeek = calendar[Calendar.DAY_OF_WEEK]
        //设置本月有多少天
        val daysOfMonth = getDaysOfMonth(year, month)
        //设置上个月有多少天
        val daysOfLastMonth = getLastDaysOfMonth(year, month)
        var dayNum = 1
        val nextDayNum = 1
        //将日期格式填充数组
        for (i in days.indices) {
            for (j in 0 until days[i].size) {
                if (i == 0 && j < daysOfFirstWeek - 1) {
                    //					days[i][j] = daysOfLastMonth - daysOfFirstWeek + 2 + j;
                    days[i][j] = -1
                } else if (dayNum <= daysOfMonth) {
                    days[i][j] = dayNum++
                } else {
                    //					days[i][j] = nextDayNum++;
                    days[i][j] = -1
                }
            }
        }
        return days
    }

    /**
     * 根据传入的年份和月份，判断上一个月有多少天.
     *
     * @param year
     * @param month
     * @return
     */
    fun getLastDaysOfMonth(year: Int, month: Int): Int {
        val lastDaysOfMonth: Int
        lastDaysOfMonth = if (month == 1) {
            getDaysOfMonth(year - 1, 12)
        } else {
            getDaysOfMonth(year, month - 1)
        }
        return lastDaysOfMonth
    }

    fun getDif(): Long {
        return dif
    }

    fun setDif(server_time_millis: Long) {
        dif = server_time_millis * 1000 - System.currentTimeMillis()
    }

    fun currentTimeMillis(): Long {
        return System.currentTimeMillis() + dif
    }

    fun getTimeBySecond(second: Int): String {
        val hour = second / 3600
        val min = second % 3600 / 60
        val sec = second % 3600 % 60
        return if (hour > 0) {
            "$hour:" + String.format(
                "%02d",
                min
            ) + ":" + String.format("%02d", sec)
        } else {
            String.format("%02d", min) + ":" + String.format("%02d", sec)
        }
    }

    fun getSecondByTime(time: String): Int {
        val split = time.split(":").toTypedArray()
        var resultTime = 0
        if (split.size == 2) {
            val min = split[0].toInt()
            val second = split[1].toInt()
            resultTime = min * 60 + second
        } else if (split.size == 3) {
            val hour = split[0].toInt()
            val min = split[1].toInt()
            val second = split[2].toInt()
            resultTime = hour * 3600 + min * 60 + second
        }
        return resultTime
    }
}