package com.tunasushi.tool;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.tuna.R;
import com.tunasushi.tuna.ColorPickerDialog;
import com.tunasushi.tuna.TView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.tunasushi.tool.ConvertTool.dpToPx;
import static com.tunasushi.tool.ConvertTool.pxToDp;

/**
 * @author TunaSashimi
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
    public static StringBuffer stringBuffer;
    public static final int stringBufferCapacity = 288;

    //
    public static void initDisplayMetrics() {
        if (displayMetrics == null) {
            displayMetrics = Resources.getSystem().getDisplayMetrics();
            displayWidth = displayMetrics.widthPixels;
            displayHeight = displayMetrics.heightPixels;
            displayDensity = displayMetrics.density;
            displayScaledDensity = displayMetrics.scaledDensity;
            displayXdpi = displayMetrics.xdpi;
            displayYdpi = displayMetrics.ydpi;

            stringBuffer = new StringBuffer(stringBufferCapacity);
            stringBuffer.append("设备厂商 : ");
            stringBuffer.append(Build.BRAND);
            stringBuffer.append(" , ");
            stringBuffer.append("设备型号 : ");
            stringBuffer.append(Build.MODEL);
            stringBuffer.append(" , ");
            stringBuffer.append("系统版本 : ");
            stringBuffer.append(Build.VERSION.RELEASE);
            stringBuffer.append(" , ");
            stringBuffer.append("API等级 : ");
            stringBuffer.append(Build.VERSION.SDK);
            stringBuffer.append(" , ");
            stringBuffer.append("系统语言 : ");
            stringBuffer.append(Locale.getDefault().getLanguage());
            stringBuffer.append(" , ");
            stringBuffer.append("屏幕分辨率 : ");
            stringBuffer.append(displayWidth);
            stringBuffer.append(" * ");
            stringBuffer.append(displayHeight);
            stringBuffer.append(" ( ");
            stringBuffer.append(displayWidth / displayDensity);
            stringBuffer.append("dp * ");
            stringBuffer.append(displayHeight / displayDensity);
            stringBuffer.append("dp ) ");
            stringBuffer.append(" , 屏幕密度 : ");
            stringBuffer.append(displayDensity);
            stringBuffer.append(" , 伸缩密度 : ");
            stringBuffer.append(displayScaledDensity);
            stringBuffer.append(" , X维 : ");
            stringBuffer.append(displayXdpi);
            stringBuffer.append(" 像素点 / 英寸 , Y维 : ");
            stringBuffer.append(displayYdpi);
            stringBuffer.append(" 像素点 / 英寸 。 ");
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

    public static void showProperties(final TView t) {
        View propertiesView = LayoutInflater.from(t.getContext()).inflate(R.layout.properties, null);

        final TextView text_display = propertiesView.findViewById(R.id.text_display);

        final EditText edit_width = propertiesView.findViewById(R.id.edit_width);
        final EditText edit_height = propertiesView.findViewById(R.id.edit_height);

        final EditText edit_backgroundNormal = propertiesView.findViewById(R.id.edit_backgroundNormal);
        final EditText edit_backgroundPress = propertiesView.findViewById(R.id.edit_backgroundPress);
        final EditText edit_backgroundSelect = propertiesView.findViewById(R.id.edit_backgroundSelect);

        final EditText edit_textSize = propertiesView.findViewById(R.id.edit_textSize);
        final EditText edit_textColorNormal = propertiesView.findViewById(R.id.edit_textColorNormal);

        final EditText edit_strokeWidth = propertiesView.findViewById(R.id.edit_strokeWidth);
        final EditText edit_strokeColor = propertiesView.findViewById(R.id.edit_strokeColor);

        final Button btn_width_pius = propertiesView.findViewById(R.id.btn_width_pius);
        final Button btn_width_minus = propertiesView.findViewById(R.id.btn_width_minus);
        final Button btn_height_pius = propertiesView.findViewById(R.id.btn_height_pius);
        final Button btn_height_minus = propertiesView.findViewById(R.id.btn_height_minus);

        final Button btn_textSize_pius = propertiesView.findViewById(R.id.btn_textSize_pius);
        final Button btn_textSize_minus = propertiesView.findViewById(R.id.btn_textSize_minus);

        final Button btn_strokeWidth_pius = propertiesView.findViewById(R.id.btn_strokeWidth_pius);
        final Button btn_strokeWidth_minus = propertiesView.findViewById(R.id.btn_strokeWidth_minus);

        final Button btn_backgroundNormal = propertiesView.findViewById(R.id.btn_backgroundNormal);
        final Button btn_backgroundPress = propertiesView.findViewById(R.id.btn_backgroundPress);
        final Button btn_backgroundSelect = propertiesView.findViewById(R.id.btn_backgroundSelect);

        final Button btn_textColorNormal = propertiesView.findViewById(R.id.btn_textColorNormal);

        final Button btn_strokeColor = propertiesView.findViewById(R.id.btn_strokeColor);

        final ToggleButton toogle_mark = propertiesView.findViewById(R.id.toogle_mark);
        final TextView text_mark = propertiesView.findViewById(R.id.text_mark);

        final ToggleButton toogle_thisHardwareAccelerated = propertiesView.findViewById(R.id.toogle_thisHardwareAccelerated);
        final TextView text_thisHardwareAccelerated = propertiesView.findViewById(R.id.text_thisHardwareAccelerated);

        final ToggleButton toogle_canvasHardwareAccelerated = propertiesView.findViewById(R.id.toogle_canvasHardwareAccelerated);
        final TextView text_canvasHardwareAccelerated = propertiesView.findViewById(R.id.text_canvasHardwareAccelerated);


        DeviceTool.initDisplayMetrics();
        text_display.setText(DeviceTool.stringBuffer);

        edit_width.setText(String.valueOf(pxToDp(t.width)));
        edit_height.setText(String.valueOf(pxToDp(t.height)));

        edit_backgroundNormal.setText(t.getBackgroundNormal() != 0 ? Integer.toHexString(t.getBackgroundNormal()) : "00000000");
        edit_backgroundPress.setText(t.getBackgroundPress() != 0 ? Integer.toHexString(t.getBackgroundPress()) : "00000000");
        edit_backgroundSelect.setText(t.getBackgroundSelect() != 0 ? Integer.toHexString(t.getBackgroundSelect()) : "00000000");

        edit_textSize.setText(String.valueOf(pxToDp(t.getTextSize())));
        edit_textColorNormal.setText(t.getTextColorNormal() != 0 ? Integer.toHexString(t.getTextColorNormal()) : "00000000");

        edit_strokeWidth.setText(String.valueOf(pxToDp(t.getStrokeColorNormal())));
        edit_strokeColor.setText(t.getStrokeColorNormal() != 0 ? Integer.toHexString(t.getStrokeColorNormal()) : "00000000");

        //
        edit_backgroundNormal.setTextColor(t.getBackgroundNormal());
        edit_backgroundPress.setTextColor(t.getBackgroundPress());
        edit_backgroundSelect.setTextColor(t.getBackgroundSelect());
        edit_strokeColor.setTextColor(t.getStrokeColorNormal());
        edit_textColorNormal.setTextColor(t.getTextColorNormal());

        btn_backgroundNormal.setBackgroundColor(t.getBackgroundNormal());
        btn_backgroundPress.setBackgroundColor(t.getBackgroundPress());
        btn_backgroundSelect.setBackgroundColor(t.getBackgroundSelect());
        btn_strokeColor.setBackgroundColor(t.getStrokeColorNormal());
        btn_textColorNormal.setBackgroundColor(t.getTextColorNormal());

        toogle_mark.setChecked(t.isTextMark());
        text_mark.setText(String.valueOf(t.isTextMark()));

        toogle_thisHardwareAccelerated.setChecked(t.isHardwareAccelerated());
        text_thisHardwareAccelerated.setText(String.valueOf(t.isHardwareAccelerated()));

        toogle_canvasHardwareAccelerated.setChecked(t.isHardwareAccelerated());
        text_canvasHardwareAccelerated.setText(String.valueOf(t.isHardwareAccelerated()));

        //
        toogle_mark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                text_mark.setText(String.valueOf(isChecked));
            }
        });

        //
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * adt14 will lib where R in the file ID to the final tag removed All you want to set library package, which the switch ... case statement can only be changed if ... else for the job
                 */
                int viewId = view.getId();
                if (viewId == R.id.btn_width_pius) {
                    edit_width.setText(String.valueOf(Float.parseFloat(edit_width.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_width_minus) {
                    edit_width.setText(String.valueOf(Float.parseFloat(edit_width.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_height_pius) {
                    edit_height.setText(String.valueOf(Float.parseFloat(edit_height.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_height_minus) {
                    edit_height.setText(String.valueOf(Float.parseFloat(edit_height.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_textSize_pius) {
                    edit_textSize.setText(String.valueOf(Float.parseFloat(edit_textSize.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_textSize_minus) {
                    edit_textSize.setText(String.valueOf(Float.parseFloat(edit_textSize.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_strokeWidth_pius) {
                    edit_strokeWidth.setText(String.valueOf(Float.parseFloat(edit_strokeWidth.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_strokeWidth_minus) {
                    edit_strokeWidth.setText(String.valueOf(Float.parseFloat(edit_strokeWidth.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_backgroundNormal) {
                    new ColorPickerDialog(t.getContext(), t.getBackgroundNormal(), new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_backgroundNormal.setBackgroundColor(color);
                            edit_backgroundNormal.setTextColor(color);
                            edit_backgroundNormal.setText(Integer.toHexString(color));
                        }
                    }).show();
                } else if (viewId == R.id.btn_backgroundPress) {
                    new ColorPickerDialog(t.getContext(), t.getBackgroundPress(), new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_backgroundPress.setBackgroundColor(color);
                            edit_backgroundPress.setTextColor(color);
                            edit_backgroundPress.setText(Integer.toHexString(color));
                        }
                    }).show();
                } else if (viewId == R.id.btn_backgroundSelect) {
                    new ColorPickerDialog(t.getContext(), t.getBackgroundSelect(), new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_backgroundSelect.setBackgroundColor(color);
                            edit_backgroundSelect.setTextColor(color);
                            edit_backgroundSelect.setText(Integer.toHexString(color));
                        }
                    }).show();
                } else if (viewId == R.id.btn_textColorNormal) {
                    new ColorPickerDialog(t.getContext(), t.getTextColorNormal(), new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_textColorNormal.setBackgroundColor(color);
                            edit_textColorNormal.setTextColor(color);
                            edit_textColorNormal.setText(Integer.toHexString(color));
                        }
                    }).show();
                } else if (viewId == R.id.btn_strokeColor) {
                    new ColorPickerDialog(t.getContext(), t.getStrokeColorNormal(), new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_strokeColor.setBackgroundColor(color);
                            edit_strokeColor.setTextColor(color);
                            edit_strokeColor.setText(Integer.toHexString(color));
                        }
                    }).show();
                }
            }
        };

        btn_width_pius.setOnClickListener(onClickListener);
        btn_width_minus.setOnClickListener(onClickListener);
        btn_height_pius.setOnClickListener(onClickListener);
        btn_height_minus.setOnClickListener(onClickListener);
        btn_textSize_pius.setOnClickListener(onClickListener);
        btn_textSize_minus.setOnClickListener(onClickListener);
        btn_strokeWidth_pius.setOnClickListener(onClickListener);
        btn_strokeWidth_minus.setOnClickListener(onClickListener);

        btn_backgroundNormal.setOnClickListener(onClickListener);
        btn_backgroundPress.setOnClickListener(onClickListener);
        btn_backgroundSelect.setOnClickListener(onClickListener);

        btn_textColorNormal.setOnClickListener(onClickListener);
        btn_strokeColor.setOnClickListener(onClickListener);

        new AlertDialog.Builder(t.getContext(), android.R.style.Theme_Holo_Light)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setView(propertiesView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //
                        t.setBackgroundNormal(Color.parseColor("#" + edit_backgroundNormal.getText().toString().trim()));
                        t.setBackgroundPress(Color.parseColor("#" + edit_backgroundPress.getText().toString().trim()));
                        t.setBackgroundSelect(Color.parseColor("#" + edit_backgroundSelect.getText().toString().trim()));
                        t.setStrokeColorNormal(Color.parseColor("#" + edit_strokeColor.getText().toString().trim()));
                        t.setTextColorNormal(Color.parseColor("#" + edit_textColorNormal.getText().toString().trim()));

                        t.setTextSize(dpToPx(Float.parseFloat(edit_textSize.getText().toString().trim())));

                        t.setStrokeWidthNormal(dpToPx(Float.parseFloat(edit_strokeWidth.getText().toString().trim())));

                        t.setTextMark(text_mark.getText().toString().trim().equals("true") ? true : false);

                        t.setLayout(dpToPx(Float.parseFloat(edit_width.getText().toString().trim())), dpToPx(Float.parseFloat(edit_height.getText().toString().trim())));
                    }
                }).setNegativeButton("Cancel", null).create().show();
    }
}
