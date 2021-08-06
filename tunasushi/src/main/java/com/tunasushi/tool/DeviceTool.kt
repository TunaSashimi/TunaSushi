package com.tunasushi.tool

import com.tunasushi.tool.ConvertTool.pxToDp
import com.tunasushi.tool.ConvertTool.dpToPx
import android.util.DisplayMetrics
import android.telephony.TelephonyManager
import android.os.Build
import android.text.TextUtils
import android.net.wifi.WifiManager
import android.net.ConnectivityManager
import android.app.ActivityManager
import android.os.Environment
import android.os.StatFs
import com.tunasushi.view.TView
import android.view.LayoutInflater
import com.tunasushi.R
import com.tunasushi.view.TPicker.PickerSelectListener
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.provider.Settings
import android.view.View
import android.widget.*
import com.tunasushi.view.TPickerDialog
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*

/**
 * @author TunaSashimi
 * @date 2019-11-19 16:46
 * @Copyright 2019 TunaSashimi. All rights reserved.
 * @Description
 */
object DeviceTool {
    //
    var displayMetrics: DisplayMetrics? = null
    var telephonyManager: TelephonyManager? = null

    //
    var displayWidth = 0
    var displayHeight = 0
    var displayDensity = 0F
    var displayScaledDensity = 0F
    var displayXdpi = 0F
    var displayYdpi = 0F

    //
    var stringBuffer: StringBuffer? = null
    val stringBufferCapacity = 288

    //
    @JvmStatic
    fun initDisplayMetrics() {
        if (displayMetrics == null) {
            displayMetrics = Resources.getSystem().displayMetrics
            displayWidth = displayMetrics!!.widthPixels
            displayHeight = displayMetrics!!.heightPixels
            displayDensity = displayMetrics!!.density
            displayScaledDensity = displayMetrics!!.scaledDensity
            displayXdpi = displayMetrics!!.xdpi
            displayYdpi = displayMetrics!!.ydpi
            stringBuffer = StringBuffer(stringBufferCapacity)
            stringBuffer!!.append("设备厂商 : ")
            stringBuffer!!.append(Build.BRAND)
            stringBuffer!!.append(" , ")
            stringBuffer!!.append("设备型号 : ")
            stringBuffer!!.append(Build.MODEL)
            stringBuffer!!.append(" , ")
            stringBuffer!!.append("系统版本 : ")
            stringBuffer!!.append(Build.VERSION.RELEASE)
            stringBuffer!!.append(" , ")
            stringBuffer!!.append("API等级 : ")
            stringBuffer!!.append(Build.VERSION.SDK)
            stringBuffer!!.append(" , ")
            stringBuffer!!.append("系统语言 : ")
            stringBuffer!!.append(Locale.getDefault().language)
            stringBuffer!!.append(" , ")
            stringBuffer!!.append("屏幕分辨率 : ")
            stringBuffer!!.append(displayWidth)
            stringBuffer!!.append(" * ")
            stringBuffer!!.append(displayHeight)
            stringBuffer!!.append(" ( ")
            stringBuffer!!.append(displayWidth / displayDensity)
            stringBuffer!!.append("dp * ")
            stringBuffer!!.append(displayHeight / displayDensity)
            stringBuffer!!.append("dp ) ")
            stringBuffer!!.append(" , 屏幕密度 : ")
            stringBuffer!!.append(displayDensity)
            stringBuffer!!.append(" , 伸缩密度 : ")
            stringBuffer!!.append(displayScaledDensity)
            stringBuffer!!.append(" , X维 : ")
            stringBuffer!!.append(displayXdpi)
            stringBuffer!!.append(" 像素点 / 英寸 , Y维 : ")
            stringBuffer!!.append(displayYdpi)
            stringBuffer!!.append(" 像素点 / 英寸 。 ")
        }
    }

    fun getDeviceInfo(context: Context): DeviceInfo {
        val deviceInfo = DeviceInfo()
        deviceInfo.deviceId = getDeviceId(context)
        deviceInfo.deviceMac = getDeviceMac(context)
        deviceInfo.deviceManufacturer = getDeviceManufacturer(context)
        deviceInfo.deviceModel = deviceModel
        deviceInfo.deviceRelease = deviceRelease
        deviceInfo.deviceSDK = deviceSDK
        deviceInfo.deviceWidth = getDeviceWidth(context)
        deviceInfo.deviceHeight = getDeviceHeight(context)
        deviceInfo.deviceDensity = getDeviceDensity(context)
        deviceInfo.deviceScaledDensity = getDeviceScaledDensity(context)
        deviceInfo.deviceXdpi = getDeviceXdpi(context)
        deviceInfo.deviceYdpi = getDeviceYdpi(context)
        deviceInfo.IMSI = getIMSI(context)
        deviceInfo.operators = getOperators(deviceInfo.IMSI)
        deviceInfo.line1Number = getLine1Number(context)
        deviceInfo.networkOperatorName = getNetworkOperatorName(context)
        deviceInfo.networkOperator = getNetworkOperator(context)
        deviceInfo.networkCountryIso = getNetworkCountryIso(context)
        deviceInfo.simCountryIso = getSimCountryIso(context)
        deviceInfo.simOperator = getSimOperator(context)
        deviceInfo.simOperatorName = getSimOperatorName(context)
        deviceInfo.simSerialNumber = getSimSerialNumber(context)
        deviceInfo.subscriberId = getSubscriberId(context)
        deviceInfo.neighboringCellInfo = getNeighboringCellInfo(context)
        deviceInfo.phoneType = getPhoneType(context)
        deviceInfo.networkType = getNetworkType(context)
        return deviceInfo
    }

    //getDeviceId , Requires android.permission.READ_PHONE_STATE.
    fun getDeviceId(context: Context): String? {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        //        String deviceId = telephonyManager.getDeviceId();
        var deviceId: String? = ""
        if (TextUtils.isEmpty(deviceId)) {
            deviceId =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }
        return deviceId
    }

    //getDeviceMac , Requires android.permission.ACCESS_WIFI_STATE.
    fun getDeviceMac(context: Context): String {
        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifi.connectionInfo.macAddress
    }

    //getDeviceManufacturer
    fun getDeviceManufacturer(context: Context?): String {
        return Build.MANUFACTURER
    }

    //getDeviceModel
    val deviceModel: String
        get() = Build.MODEL

    //getDeviceRelease
    val deviceRelease: String
        get() = Build.VERSION.RELEASE

    //getDeviceReSDK
    val deviceSDK: String
        get() = Build.VERSION.SDK

    //getDeviceWidth
    fun getDeviceWidth(context: Context): Int {
        if (displayMetrics == null) {
            displayMetrics = context.resources.displayMetrics
        }
        return displayMetrics!!.widthPixels
    }

    //getDeviceHeight
    fun getDeviceHeight(context: Context): Int {
        if (displayMetrics == null) {
            displayMetrics = context.resources.displayMetrics
        }
        return displayMetrics!!.heightPixels
    }

    //getDeviceDensity
    fun getDeviceDensity(context: Context): Float {
        if (displayMetrics == null) {
            displayMetrics = context.resources.displayMetrics
        }
        return displayMetrics!!.density
    }

    //getDeviceScaledDensity
    fun getDeviceScaledDensity(context: Context): Float {
        if (displayMetrics == null) {
            displayMetrics = context.resources.displayMetrics
        }
        return displayMetrics!!.scaledDensity
    }

    //getDeviceXdpi
    fun getDeviceXdpi(context: Context): Float {
        if (displayMetrics == null) {
            displayMetrics = context.resources.displayMetrics
        }
        return displayMetrics!!.xdpi
    }

    //getDeviceYdpi
    fun getDeviceYdpi(context: Context): Float {
        if (displayMetrics == null) {
            displayMetrics = context.resources.displayMetrics
        }
        return displayMetrics!!.ydpi
    }

    //getIMSI
    fun getIMSI(context: Context): String {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        //        return telephonyManager.getSubscriberId();
        return ""
    }

    //getOperators
    fun getOperators(string: String?): String? {
        if (string != null) {
            if (string.startsWith("46000") || string.startsWith("46002")) {
                //Because the mobile network under the IMSI number 46,000 has been exhausted, so a virtual number 46002, 134/159 number section uses this number
                return "中国移动"
            } else if (string.startsWith("46001")) {
                return "中国联通"
            } else if (string.startsWith("46003")) {
                return "中国电信"
            }
        }
        return null
    }

    //getLine1Number
    fun getLine1Number(context: Context): String {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        //        return telephonyManager.getLine1Number();
        return ""
    }

    //getNetworkOperatorName
    fun getNetworkOperatorName(context: Context): String {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        return telephonyManager!!.networkOperatorName
    }

    //getNetworkOperator
    fun getNetworkOperator(context: Context): String {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        return telephonyManager!!.networkOperator
    }

    //getNetworkCountryIso
    fun getNetworkCountryIso(context: Context): String {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        return telephonyManager!!.networkCountryIso
    }

    //getSimCountryIso
    fun getSimCountryIso(context: Context): String {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        return telephonyManager!!.simCountryIso
    }

    //getSimOperator
    fun getSimOperator(context: Context): String {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        return telephonyManager!!.simOperator
    }

    //getSimOperatorName
    fun getSimOperatorName(context: Context): String {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        return telephonyManager!!.simOperatorName
    }

    //getSimSerialNumber
    fun getSimSerialNumber(context: Context): String {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        //        return telephonyManager.getSimSerialNumber();
        return ""
    }

    //getSubscriberId
    fun getSubscriberId(context: Context): String {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        //        return telephonyManager.getSubscriberId();
        return ""
    }

    //getNeighboringCellInfo
    fun getNeighboringCellInfo(context: Context): List<*> {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        //        return telephonyManager.getNeighboringCellInfo();
        return ArrayList<Any?>()
    }

    //phoneType
    fun getPhoneType(context: Context): Int {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        return telephonyManager!!.phoneType
    }

    //getNetworkType
    fun getNetworkType(context: Context): String {
        var strNetworkType = "null"
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI"
            } else if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                val subTypeName = networkInfo.subtypeName
                // TD-SCDMA networkType is 17
                val networkType = networkInfo.subtype
                when (networkType) {
                    TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> strNetworkType =
                        "2G"
                    TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> strNetworkType =
                        "3G"
                    TelephonyManager.NETWORK_TYPE_LTE -> strNetworkType = "4G"
                    else -> if (subTypeName.equals(
                            "TD-SCDMA",
                            ignoreCase = true
                        ) || subTypeName.equals(
                            "WCDMA",
                            ignoreCase = true
                        ) || subTypeName.equals("CDMA2000", ignoreCase = true)
                    ) {
                        strNetworkType = "3G"
                    } else {
                        strNetworkType = subTypeName
                    }
                }
            }
        }
        return strNetworkType
    }

    //getMemory
    val memoryInfo: String
        get() {
            val str1 = "/proc/meminfo"
            var str2 = ""
            val stringBuffer = StringBuffer(28)
            try {
                val fr = FileReader(str1)
                val localBufferedReader = BufferedReader(fr, 8192)
                while ((localBufferedReader.readLine().also { str2 = it }) != null) {
                    stringBuffer.append(str2 + "\n")
                }
                localBufferedReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return stringBuffer.toString()
        }

    //getRAM
    fun getAvailMemory(context: Context): Long {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        return mi.availMem
    }

    val totalInternalStorgeSize: Long
        get() {
            val path = Environment.getDataDirectory()
            val mStatFs = StatFs(path.path)
            val blockSize = mStatFs.blockSize.toLong()
            val totalBlocks = mStatFs.blockCount.toLong()
            return totalBlocks * blockSize
        }
    val availableInternalStorgeSize: Long
        get() {
            val path = Environment.getDataDirectory()
            val mStatFs = StatFs(path.path)
            val blockSize = mStatFs.blockSize.toLong()
            val availableBlocks = mStatFs.availableBlocks.toLong()
            return availableBlocks * blockSize
        }
    val externalMemoryAvailable: Boolean
        get() = (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
    val totalExternalStorgeSize: Long
        get() {
            if (externalMemoryAvailable) {
                val path = Environment.getExternalStorageDirectory()
                val mStatFs = StatFs(path.path)
                val blockSize = mStatFs.blockSize.toLong()
                val totalBlocks = mStatFs.blockCount.toLong()
                return totalBlocks * blockSize
            }
            return 0
        }
    val availableExternalStorgeSize: Long
        get() {
            if (externalMemoryAvailable) {
                val path = Environment.getExternalStorageDirectory()
                val mStatFs = StatFs(path.path)
                val blockSize = mStatFs.blockSize.toLong()
                val availableBlocks = mStatFs.availableBlocks.toLong()
                return availableBlocks * blockSize
            }
            return 0
        }

    @JvmStatic
    fun showProperties(t: TView) {
        val propertiesView = LayoutInflater.from(t.context).inflate(R.layout.properties, null)
        val text_display = propertiesView.findViewById<TextView>(R.id.text_display)
        val edit_width = propertiesView.findViewById<EditText>(R.id.edit_width)
        val edit_height = propertiesView.findViewById<EditText>(R.id.edit_height)
        val edit_backgroundNormal =
            propertiesView.findViewById<EditText>(R.id.edit_backgroundNormal)
        val edit_backgroundPress = propertiesView.findViewById<EditText>(R.id.edit_backgroundPress)
        val edit_backgroundSelect =
            propertiesView.findViewById<EditText>(R.id.edit_backgroundSelect)
        val edit_textSize = propertiesView.findViewById<EditText>(R.id.edit_textSize)
        val edit_textColorNormal = propertiesView.findViewById<EditText>(R.id.edit_textColorNormal)
        val edit_strokeWidth = propertiesView.findViewById<EditText>(R.id.edit_strokeWidth)
        val edit_strokeColor = propertiesView.findViewById<EditText>(R.id.edit_strokeColor)
        val btn_width_pius = propertiesView.findViewById<Button>(R.id.btn_width_pius)
        val btn_width_minus = propertiesView.findViewById<Button>(R.id.btn_width_minus)
        val btn_height_pius = propertiesView.findViewById<Button>(R.id.btn_height_pius)
        val btn_height_minus = propertiesView.findViewById<Button>(R.id.btn_height_minus)
        val btn_textSize_pius = propertiesView.findViewById<Button>(R.id.btn_textSize_pius)
        val btn_textSize_minus = propertiesView.findViewById<Button>(R.id.btn_textSize_minus)
        val btn_strokeWidth_pius = propertiesView.findViewById<Button>(R.id.btn_strokeWidth_pius)
        val btn_strokeWidth_minus = propertiesView.findViewById<Button>(R.id.btn_strokeWidth_minus)
        val btn_backgroundNormal = propertiesView.findViewById<Button>(R.id.btn_backgroundNormal)
        val btn_backgroundPress = propertiesView.findViewById<Button>(R.id.btn_backgroundPress)
        val btn_backgroundSelect = propertiesView.findViewById<Button>(R.id.btn_backgroundSelect)
        val btn_textColorNormal = propertiesView.findViewById<Button>(R.id.btn_textColorNormal)
        val btn_strokeColor = propertiesView.findViewById<Button>(R.id.btn_strokeColor)
        val toogle_mark = propertiesView.findViewById<ToggleButton>(R.id.toogle_mark)
        val text_mark = propertiesView.findViewById<TextView>(R.id.text_mark)
        val toogle_thisHardwareAccelerated =
            propertiesView.findViewById<ToggleButton>(R.id.toogle_thisHardwareAccelerated)
        val text_thisHardwareAccelerated =
            propertiesView.findViewById<TextView>(R.id.text_thisHardwareAccelerated)
        val toogle_canvasHardwareAccelerated =
            propertiesView.findViewById<ToggleButton>(R.id.toogle_canvasHardwareAccelerated)
        val text_canvasHardwareAccelerated =
            propertiesView.findViewById<TextView>(R.id.text_canvasHardwareAccelerated)
        initDisplayMetrics()
        text_display.text = stringBuffer
        edit_width.setText(pxToDp(t.width.toFloat()).toString())
        edit_height.setText(pxToDp(t.height.toFloat()).toString())
        edit_backgroundNormal.setText(if (t.backgroundNormal != 0) Integer.toHexString(t.backgroundNormal) else "00000000")
        edit_backgroundPress.setText(if (t.backgroundPress != 0) Integer.toHexString(t.backgroundPress) else "00000000")
        edit_backgroundSelect.setText(if (t.backgroundSelect != 0) Integer.toHexString(t.backgroundSelect) else "00000000")
        edit_textSize.setText(pxToDp(t.textSize).toString())
        edit_textColorNormal.setText(if (t.textColorNormal != 0) Integer.toHexString(t.textColorNormal) else "00000000")
        edit_strokeWidth.setText(pxToDp(t.strokeColorNormal.toFloat()).toString())
        edit_strokeColor.setText(if (t.strokeColorNormal != 0) Integer.toHexString(t.strokeColorNormal) else "00000000")

        //
        edit_backgroundNormal.setTextColor(t.backgroundNormal)
        edit_backgroundPress.setTextColor(t.backgroundPress)
        edit_backgroundSelect.setTextColor(t.backgroundSelect)
        edit_strokeColor.setTextColor(t.strokeColorNormal)
        edit_textColorNormal.setTextColor(t.textColorNormal)
        btn_backgroundNormal.setBackgroundColor(t.backgroundNormal)
        btn_backgroundPress.setBackgroundColor(t.backgroundPress)
        btn_backgroundSelect.setBackgroundColor(t.backgroundSelect)
        btn_strokeColor.setBackgroundColor(t.strokeColorNormal)
        btn_textColorNormal.setBackgroundColor(t.textColorNormal)
        toogle_mark.isChecked = t.isTextMark
        text_mark.text = t.isTextMark.toString()
        toogle_thisHardwareAccelerated.isChecked = t.isHardwareAccelerated
        text_thisHardwareAccelerated.text = t.isHardwareAccelerated.toString()
        toogle_canvasHardwareAccelerated.isChecked = t.isHardwareAccelerated
        text_canvasHardwareAccelerated.text = t.isHardwareAccelerated.toString()

        //
        toogle_mark.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            text_mark.text = isChecked.toString()
        })

        //
        val onClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(view: View) {
                /**
                 * adt14 will lib where R in the file ID to the final tag removed All you want to set library package, which the switch ... case statement can only be changed if ... else for the job
                 */
                val viewId = view.id
                if (viewId == R.id.btn_width_pius) {
                    edit_width.setText((edit_width.text.toString().trim { it <= ' ' }
                        .toFloat() + 1).toString())
                } else if (viewId == R.id.btn_width_minus) {
                    edit_width.setText((edit_width.text.toString().trim { it <= ' ' }
                        .toFloat() - 1).toString())
                } else if (viewId == R.id.btn_height_pius) {
                    edit_height.setText((edit_height.text.toString().trim { it <= ' ' }
                        .toFloat() + 1).toString())
                } else if (viewId == R.id.btn_height_minus) {
                    edit_height.setText((edit_height.text.toString().trim { it <= ' ' }
                        .toFloat() - 1).toString())
                } else if (viewId == R.id.btn_textSize_pius) {
                    edit_textSize.setText((edit_textSize.text.toString().trim { it <= ' ' }
                        .toFloat() + 1).toString())
                } else if (viewId == R.id.btn_textSize_minus) {
                    edit_textSize.setText((edit_textSize.text.toString().trim { it <= ' ' }
                        .toFloat() - 1).toString())
                } else if (viewId == R.id.btn_strokeWidth_pius) {
                    edit_strokeWidth.setText((edit_strokeWidth.text.toString().trim { it <= ' ' }
                        .toFloat() + 1).toString())
                } else if (viewId == R.id.btn_strokeWidth_minus) {
                    edit_strokeWidth.setText((edit_strokeWidth.text.toString().trim { it <= ' ' }
                        .toFloat() - 1).toString())
                } else if (viewId == R.id.btn_backgroundNormal) {
                    TPickerDialog(t.context, t.backgroundNormal, object : PickerSelectListener {
                        override fun pickerSelect(color: Int) {
                            btn_backgroundNormal.setBackgroundColor(color)
                            edit_backgroundNormal.setTextColor(color)
                            edit_backgroundNormal.setText(Integer.toHexString(color))
                        }
                    }).show()
                } else if (viewId == R.id.btn_backgroundPress) {
                    TPickerDialog(t.context, t.backgroundPress, object : PickerSelectListener {
                        override fun pickerSelect(color: Int) {
                            btn_backgroundPress.setBackgroundColor(color)
                            edit_backgroundPress.setTextColor(color)
                            edit_backgroundPress.setText(Integer.toHexString(color))
                        }
                    }).show()
                } else if (viewId == R.id.btn_backgroundSelect) {
                    TPickerDialog(t.context, t.backgroundSelect, object : PickerSelectListener {
                        override fun pickerSelect(color: Int) {
                            btn_backgroundSelect.setBackgroundColor(color)
                            edit_backgroundSelect.setTextColor(color)
                            edit_backgroundSelect.setText(Integer.toHexString(color))
                        }
                    }).show()
                } else if (viewId == R.id.btn_textColorNormal) {
                    TPickerDialog(t.context, t.textColorNormal, object : PickerSelectListener {
                        override fun pickerSelect(color: Int) {
                            btn_textColorNormal.setBackgroundColor(color)
                            edit_textColorNormal.setTextColor(color)
                            edit_textColorNormal.setText(Integer.toHexString(color))
                        }
                    }).show()
                } else if (viewId == R.id.btn_strokeColor) {
                    TPickerDialog(t.context, t.strokeColorNormal, object : PickerSelectListener {
                        override fun pickerSelect(color: Int) {
                            btn_strokeColor.setBackgroundColor(color)
                            edit_strokeColor.setTextColor(color)
                            edit_strokeColor.setText(Integer.toHexString(color))
                        }
                    }).show()
                }
            }
        }
        btn_width_pius.setOnClickListener(onClickListener)
        btn_width_minus.setOnClickListener(onClickListener)
        btn_height_pius.setOnClickListener(onClickListener)
        btn_height_minus.setOnClickListener(onClickListener)
        btn_textSize_pius.setOnClickListener(onClickListener)
        btn_textSize_minus.setOnClickListener(onClickListener)
        btn_strokeWidth_pius.setOnClickListener(onClickListener)
        btn_strokeWidth_minus.setOnClickListener(onClickListener)
        btn_backgroundNormal.setOnClickListener(onClickListener)
        btn_backgroundPress.setOnClickListener(onClickListener)
        btn_backgroundSelect.setOnClickListener(onClickListener)
        btn_textColorNormal.setOnClickListener(onClickListener)
        btn_strokeColor.setOnClickListener(onClickListener)
        AlertDialog.Builder(t.context, android.R.style.Theme_Holo_Light)
            .setIconAttribute(android.R.attr.alertDialogIcon)
            .setView(propertiesView)
            .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, whichButton: Int) {
                    //
                    t.backgroundNormal = Color.parseColor(
                        "#" + edit_backgroundNormal.text.toString().trim { it <= ' ' })
                    t.backgroundPress = Color.parseColor(
                        "#" + edit_backgroundPress.text.toString().trim { it <= ' ' })
                    t.backgroundSelect = Color.parseColor(
                        "#" + edit_backgroundSelect.text.toString().trim { it <= ' ' })
                    t.strokeColorNormal =
                        Color.parseColor("#" + edit_strokeColor.text.toString().trim { it <= ' ' })
                    t.textColorNormal = Color.parseColor(
                        "#" + edit_textColorNormal.text.toString().trim { it <= ' ' })
                    t.textSize =
                        dpToPx(edit_textSize.text.toString().trim { it <= ' ' }.toFloat()).toFloat()
                    t.strokeWidthNormal = dpToPx(edit_strokeWidth.text.toString().trim { it <= ' ' }
                        .toFloat()).toFloat()
                    t.isTextMark = if ((text_mark.text.toString()
                            .trim { it <= ' ' } == "true")
                    ) true else false
                    t.setLayout(dpToPx(edit_width.text.toString().trim { it <= ' ' }
                        .toFloat()),
                        dpToPx(edit_height.text.toString().trim { it <= ' ' }.toFloat()))
                }
            }).setNegativeButton("Cancel", null).create().show()
    }

    class DeviceInfo() {
        var deviceId //863077025146139
                : String? = null
        var deviceMac //f8:a4:5f:50:a6:a3
                : String? = null
        var deviceManufacturer //Xiaomi
                : String? = null
        var deviceModel //MI 2S
                : String? = null
        var deviceRelease //4.1.1
                : String? = null
        var deviceSDK //16
                : String? = null
        var deviceWidth //720
                = 0
        var deviceHeight //1280
                = 0
        var deviceDensity //2.0
                = 0F
        var deviceScaledDensity //2.0
                = 0F
        var deviceXdpi //345.0566
                = 0F
        var deviceYdpi //342.23157
                = 0F
        var IMSI //460010783794229
                : String? = null
        var operators //中国联通
                : String? = null
        var line1Number //
                : String? = null
        var networkOperatorName //China Unicom
                : String? = null
        var networkOperator //46001
                : String? = null
        var networkCountryIso //cn
                : String? = null
        var simCountryIso //cn
                : String? = null
        var simOperator //46001
                : String? = null
        var simOperatorName //
                : String? = null
        var simSerialNumber //89860114733102699519
                : String? = null
        var subscriberId //460010783794229
                : String? = null
        var neighboringCellInfo //[[116@-84]]
                : List<*>? = null
        var phoneType //1
                = 0
        var networkType //WIFI
                : String? = null

        override fun toString(): String {
            val sb = StringBuffer()
            sb
                .append("DeviceInfo[") //
                .append("\ndeviceId[").append("$deviceId]") //
                .append("\ndeviceMac[").append("$deviceMac]") //
                .append("\ndeviceManufacturer[").append("$deviceManufacturer]") //
                .append("\ndeviceModel[").append("$deviceModel]") //
                .append("\ndeviceRelease[").append("$deviceRelease]") //
                .append("\ndeviceSDK[").append("$deviceSDK]") //
                .append("\ndeviceWidth[").append("$deviceWidth]") //
                .append("\ndeviceHeight[").append("$deviceHeight]") //
                .append("\ndeviceDensity[").append("$deviceDensity]") //
                .append("\ndeviceScaledDensity[").append("$deviceScaledDensity]") //
                .append("\ndeviceXdpi[").append("$deviceXdpi]") //
                .append("\ndeviceYdpi[").append("$deviceYdpi]") //
                .append("\nIMSI[").append("$IMSI]") //
                .append("\noperators[").append("$operators]") //
                .append("\nline1Number[").append("$line1Number]") //
                .append("\nnetworkOperatorName[").append("$networkOperatorName]") //
                .append("\nnetworkOperator[").append("$networkOperator]") //
                .append("\nnetworkCountryIso[").append("$networkCountryIso]") //
                .append("\nsimCountryIso[").append("$simCountryIso]") //
                .append("\nsimOperator[").append("$simOperator]") //
                .append("\nsimOperatorName[").append("$simOperatorName]") //
                .append("\nsimSerialNumber[").append("$simSerialNumber]") //
                .append("\nsubscriberId[").append("$subscriberId]") //
                .append("\nneighboringCellInfo[").append(neighboringCellInfo.toString() + "]") //
                .append("\nphoneType[").append("$phoneType]") //
                .append("\nnetworkType[").append("$networkType]") //
            return sb.toString()
        }
    }
}