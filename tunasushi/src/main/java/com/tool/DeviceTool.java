package com.tool;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Tunasashimi
 * @date 2019-11-19 16:46
 * @Copyright 2019 TunaSashimi. All rights reserved.
 * @Description
 */
public class DeviceTool {
    //
    public static DisplayMetrics displayMetrics;
    public static TelephonyManager telephonyManager;

    //
    public static int displayWidth, displayHeight;
    public static float displayDensity, displayScaledDensity, displayXdpi, displayYdpi;

    //
    public static StringBuffer tunaStringBuffer;
    public static final int tunaStringBufferCapacity = 288;

    //
    public static void initDisplayMetrics(Context context) {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
            displayWidth = displayMetrics.widthPixels;
            displayHeight = displayMetrics.heightPixels;
            displayDensity = displayMetrics.density;
            displayScaledDensity = displayMetrics.scaledDensity;
            displayXdpi = displayMetrics.xdpi;
            displayYdpi = displayMetrics.ydpi;

            tunaStringBuffer = new StringBuffer(tunaStringBufferCapacity);
            tunaStringBuffer.append("设备厂商 : ");
            tunaStringBuffer.append(Build.BRAND);
            tunaStringBuffer.append(" , ");
            tunaStringBuffer.append("设备型号 : ");
            tunaStringBuffer.append(Build.MODEL);
            tunaStringBuffer.append(" , ");
            tunaStringBuffer.append("系统版本 : ");
            tunaStringBuffer.append(Build.VERSION.RELEASE);
            tunaStringBuffer.append(" , ");
            tunaStringBuffer.append("API等级 : ");
            tunaStringBuffer.append(Build.VERSION.SDK);
            tunaStringBuffer.append(" , ");
            tunaStringBuffer.append("系统语言 : ");
            tunaStringBuffer.append(Locale.getDefault().getLanguage());
            tunaStringBuffer.append(" , ");
            tunaStringBuffer.append("屏幕分辨率 : ");
            tunaStringBuffer.append(displayWidth);
            tunaStringBuffer.append(" * ");
            tunaStringBuffer.append(displayHeight);
            tunaStringBuffer.append(" ( ");
            tunaStringBuffer.append(displayWidth / displayDensity);
            tunaStringBuffer.append("dp * ");
            tunaStringBuffer.append(displayHeight / displayDensity);
            tunaStringBuffer.append("dp ) ");
            tunaStringBuffer.append(" , 屏幕密度 : ");
            tunaStringBuffer.append(displayDensity);
            tunaStringBuffer.append(" , 伸缩密度 : ");
            tunaStringBuffer.append(displayScaledDensity);
            tunaStringBuffer.append(" , X维 : ");
            tunaStringBuffer.append(displayXdpi);
            tunaStringBuffer.append(" 像素点 / 英寸 , Y维 : ");
            tunaStringBuffer.append(displayYdpi);
            tunaStringBuffer.append(" 像素点 / 英寸 。 ");
        }
    }

    public static DeviceInfo getDeviceInfo(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo();

        deviceInfo.deviceId = getDeviceId(context);
        deviceInfo.deviceMac = getDeviceMac(context);
        deviceInfo.deviceManufacturer = getDeviceManufacturer(context);
        deviceInfo.deviceModel = getDeviceModel();
        deviceInfo.deviceRelease = getDeviceRelease();
        deviceInfo.deviceSDK = getDeviceSDK();
        deviceInfo.deviceWidth = getDeviceWidth(context);
        deviceInfo.deviceHeight = getDeviceHeight(context);
        deviceInfo.deviceDensity = getDeviceDensity(context);
        deviceInfo.deviceScaledDensity = getDeviceScaledDensity(context);
        deviceInfo.deviceXdpi = getDeviceXdpi(context);
        deviceInfo.deviceYdpi = getDeviceYdpi(context);
        deviceInfo.IMSI = getIMSI(context);
        deviceInfo.operators = getOperators(deviceInfo.IMSI);
        deviceInfo.line1Number = getLine1Number(context);
        deviceInfo.networkOperatorName = getNetworkOperatorName(context);
        deviceInfo.networkOperator = getNetworkOperator(context);
        deviceInfo.networkCountryIso = getNetworkCountryIso(context);
        deviceInfo.simCountryIso = getSimCountryIso(context);
        deviceInfo.simOperator = getSimOperator(context);
        deviceInfo.simOperatorName = getSimOperatorName(context);
        deviceInfo.simSerialNumber = getSimSerialNumber(context);
        deviceInfo.subscriberId = getSubscriberId(context);
        deviceInfo.neighboringCellInfo = getNeighboringCellInfo(context);
        deviceInfo.phoneType = getPhoneType(context);
        deviceInfo.networkType = getNetworkType(context);

        return deviceInfo;
    }


    //getDeviceId , Requires android.permission.READ_PHONE_STATE.
    public static String getDeviceId(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
//        String deviceId = telephonyManager.getDeviceId();
        String deviceId = "";
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    //getDeviceMac , Requires android.permission.ACCESS_WIFI_STATE.
    public static String getDeviceMac(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifi.getConnectionInfo().getMacAddress();
    }

    //getDeviceManufacturer
    public static String getDeviceManufacturer(Context context) {
        return android.os.Build.MANUFACTURER;
    }

    //getDeviceModel
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    //getDeviceRelease
    public static String getDeviceRelease() {
        return Build.VERSION.RELEASE;
    }

    //getDeviceReSDK
    public static String getDeviceSDK() {
        return Build.VERSION.SDK;
    }

    //getDeviceWidth
    public static int getDeviceWidth(Context context) {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        return displayMetrics.widthPixels;
    }

    //getDeviceHeight
    public static int getDeviceHeight(Context context) {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        return displayMetrics.heightPixels;
    }

    //getDeviceDensity
    public static float getDeviceDensity(Context context) {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        return displayMetrics.density;
    }

    //getDeviceScaledDensity
    public static float getDeviceScaledDensity(Context context) {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        return displayMetrics.scaledDensity;
    }

    //getDeviceXdpi
    public static float getDeviceXdpi(Context context) {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        return displayMetrics.xdpi;
    }

    //getDeviceYdpi
    public static float getDeviceYdpi(Context context) {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        return displayMetrics.ydpi;
    }

    //getIMSI
    public static String getIMSI(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
//        return telephonyManager.getSubscriberId();
        return "";
    }

    //getOperators
    public static String getOperators(String string) {
        if (string != null) {
            if (string.startsWith("46000") || string.startsWith("46002")) {
                //Because the mobile network under the IMSI number 46,000 has been exhausted, so a virtual number 46002, 134/159 number section uses this number
                return "中国移动";
            } else if (string.startsWith("46001")) {
                return "中国联通";
            } else if (string.startsWith("46003")) {
                return "中国电信";
            }
        }
        return null;
    }

    //getLine1Number
    public static String getLine1Number(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
//        return telephonyManager.getLine1Number();
        return "";
    }

    //getNetworkOperatorName
    public static String getNetworkOperatorName(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return telephonyManager.getNetworkOperatorName();
    }

    //getNetworkOperator
    public static String getNetworkOperator(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return telephonyManager.getNetworkOperator();
    }

    //getNetworkCountryIso
    public static String getNetworkCountryIso(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return telephonyManager.getNetworkCountryIso();
    }

    //getSimCountryIso
    public static String getSimCountryIso(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return telephonyManager.getSimCountryIso();
    }

    //getSimOperator
    public static String getSimOperator(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return telephonyManager.getSimOperator();
    }

    //getSimOperatorName
    public static String getSimOperatorName(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return telephonyManager.getSimOperatorName();
    }

    //getSimSerialNumber
    public static String getSimSerialNumber(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
//        return telephonyManager.getSimSerialNumber();
        return "";
    }

    //getSubscriberId
    public static String getSubscriberId(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
//        return telephonyManager.getSubscriberId();
        return "";
    }

    //getNeighboringCellInfo
    public static List getNeighboringCellInfo(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
//        return telephonyManager.getNeighboringCellInfo();
        return new ArrayList();
    }

    //phoneType
    public static int getPhoneType(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return telephonyManager.getPhoneType();
    }

    //getNetworkType
    public static String getNetworkType(Context context) {
        String strNetworkType = "null";
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String subTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: // api < 8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // api < 9 :  replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD: // api < 11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP: // api < 13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE: // api < 11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        if (subTypeName.equalsIgnoreCase("TD-SCDMA") || subTypeName.equalsIgnoreCase("WCDMA") || subTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = subTypeName;
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }

    //getMemory
    public static String getMemoryInfo() {
        String str1 = "/proc/meminfo";
        String str2 = "";
        StringBuffer stringBuffer = new StringBuffer(28);
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                stringBuffer.append(str2 + "\n");
            }
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    //getRAM
    public static long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }

    public static long getTotalInternalStorgeSize() {
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSize();
        long totalBlocks = mStatFs.getBlockCount();
        return totalBlocks * blockSize;
    }

    public static long getAvailableInternalStorgeSize() {
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSize();
        long availableBlocks = mStatFs.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static boolean getExternalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static long getTotalExternalStorgeSize() {
        if (getExternalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSize = mStatFs.getBlockSize();
            long totalBlocks = mStatFs.getBlockCount();
            return totalBlocks * blockSize;
        }
        return 0;
    }

    public static long getAvailableExternalStorgeSize() {
        if (getExternalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSize = mStatFs.getBlockSize();
            long availableBlocks = mStatFs.getAvailableBlocks();
            return availableBlocks * blockSize;
        }
        return 0;
    }

    // Parameter need to float for example tunaStroke is float convert the value to dip or dp px values​​, to ensure constant size
    public static float pxToDp(Context context, float pxValue) {
        if (displayDensity == 0f) {
            initDisplayMetrics(context);
        }
        return pxValue / displayDensity;
    }

    // Parameter need to float for example tunaStroke is float convert the value to dip or dp px values​​, to ensure constant size
    public static float pxToSp(Context context, float pxValue) {
        if (displayDensity == 0f) {
            initDisplayMetrics(context);
        }
        return pxValue / displayScaledDensity;
    }

    // convert the value px sp values​​, to ensure constant size
    public static int spToPx(Context context, float spValue) {
        if (displayDensity == 0f) {
            initDisplayMetrics(context);
        }
        return (int) (spValue * displayScaledDensity + 0.5f);
    }

    // convert the value to px dp values​​, to ensure constant size
    public static int dpToPx(Context context, float dpValue) {
        if (displayDensity == 0f) {
            initDisplayMetrics(context);
        }
        return (int) (dpValue * displayDensity + 0.5f);
    }

    public static float convertToPX(float value, Activity activity) {
        return convertToPX(value, TypedValue.COMPLEX_UNIT_DIP, activity);
    }

    public static float convertToPX(float value, int unit, Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return applyDimension(unit, value, dm);
    }

    public static float convertToPX(float value, View view) {
        return convertToPX(value, TypedValue.COMPLEX_UNIT_DIP, view);
    }

    public static float convertToPX(float value, int unit, View view) {
        return applyDimension(unit, value, getViewDisplayMetrics(view));
    }

    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    //
    public static DisplayMetrics getViewDisplayMetrics(View view) {
        return getViewResources(view).getDisplayMetrics();
    }

    //
    public static Resources getViewResources(View view) {
        Resources resources;
        Context context = view.getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        return resources;
    }


    public static class DeviceInfo {
        public String deviceId;//863077025146139
        public String deviceMac;//f8:a4:5f:50:a6:a3
        public String deviceManufacturer;//Xiaomi
        public String deviceModel;//MI 2S
        public String deviceRelease;//4.1.1
        public String deviceSDK;//16
        public int deviceWidth;//720
        public int deviceHeight;//1280
        public float deviceDensity;//2.0
        public float deviceScaledDensity;//2.0
        public float deviceXdpi;//345.0566
        public float deviceYdpi;//342.23157
        public String IMSI;//460010783794229
        public String operators;//中国联通
        public String line1Number;//
        public String networkOperatorName;//China Unicom
        public String networkOperator;//46001
        public String networkCountryIso;//cn
        public String simCountryIso;//cn
        public String simOperator;//46001
        public String simOperatorName;//
        public String simSerialNumber;//89860114733102699519
        public String subscriberId;//460010783794229
        public List neighboringCellInfo;//[[116@-84]]
        public int phoneType;//1
        public String networkType;//WIFI

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb
                    .append("DeviceInfo[")//
                    .append("\ndeviceId[").append(deviceId + "]")//
                    .append("\ndeviceMac[").append(deviceMac + "]")//
                    .append("\ndeviceManufacturer[").append(deviceManufacturer + "]")//
                    .append("\ndeviceModel[").append(deviceModel + "]")//
                    .append("\ndeviceRelease[").append(deviceRelease + "]")//
                    .append("\ndeviceSDK[").append(deviceSDK + "]")//
                    .append("\ndeviceWidth[").append(deviceWidth + "]")//
                    .append("\ndeviceHeight[").append(deviceHeight + "]")//
                    .append("\ndeviceDensity[").append(deviceDensity + "]")//
                    .append("\ndeviceScaledDensity[").append(deviceScaledDensity + "]")//
                    .append("\ndeviceXdpi[").append(deviceXdpi + "]")//
                    .append("\ndeviceYdpi[").append(deviceYdpi + "]")//
                    .append("\nIMSI[").append(IMSI + "]")//
                    .append("\noperators[").append(operators + "]")//
                    .append("\nline1Number[").append(line1Number + "]")//
                    .append("\nnetworkOperatorName[").append(networkOperatorName + "]")//
                    .append("\nnetworkOperator[").append(networkOperator + "]")//
                    .append("\nnetworkCountryIso[").append(networkCountryIso + "]")//
                    .append("\nsimCountryIso[").append(simCountryIso + "]")//
                    .append("\nsimOperator[").append(simOperator + "]")//
                    .append("\nsimOperatorName[").append(simOperatorName + "]")//
                    .append("\nsimSerialNumber[").append(simSerialNumber + "]")//
                    .append("\nsubscriberId[").append(subscriberId + "]")//
                    .append("\nneighboringCellInfo[").append(neighboringCellInfo + "]")//
                    .append("\nphoneType[").append(phoneType + "]")//
                    .append("\nnetworkType[").append(networkType + "]")//
            ;
            return sb.toString();
        }
    }
}
