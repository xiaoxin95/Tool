package com.unipock.unipaytool.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import com.unipock.unipaytool.utils.StringUtil.isEmpty
import java.util.*


/**
 * 2017/8/22
 */

object DeviceUtils {

    /**
     * 获取设备名称
     *
     * @return
     */
    fun deviceName(): String {
        return Build.BRAND
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    fun deviceModel(): String {
        return Build.MODEL
    }

//    /**
//     * 获取设备编号
//     * @param context 上下文
//     * @return+
//     */
//    fun deviceNo(context: Context): String {
//        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        @SuppressLint("MissingPermission") val deviceId = tm.deviceId
//        return deviceId + "1"
//    }

    /**
     * 获取App版本号
     * Code
     *
     * @param context
     * @return
     */
    fun getAppVersionCode(context: Context): Int {

        var versionCode = 0;

        // 获取packagemanager的实例
        val packageManager = context.packageManager
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        var packInfo: PackageInfo? = null
        try {
            packInfo = packageManager.getPackageInfo(context.packageName, 0)  //PackageManager.GET_ACTIVITIES
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packInfo!!.longVersionCode.toInt()
        } else {
            packInfo!!.versionCode;
        }
        return versionCode;
    }

//    /**
//     * 获取App版本号
//     * Name
//     *
//     * @param context
//     * @return
//     */
//    fun getAppVersionName(context: Context): String {
//        val packageManager = context.packageManager
//        val packageInfo: PackageInfo
//        var versionName = ""
//        try {
//            packageInfo = packageManager.getPackageInfo(context.packageName, 0)
//            versionName = packageInfo.versionName
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//        return versionName
//    }

//    /**
//     * 获取App包名
//     * @return
//     */
//    fun getAppPackageName(context: Context): String {
//        val packageManager = context.packageManager
//        val packageInfo: PackageInfo
//        var packageName = ""
//        try {
//            packageInfo = packageManager.getPackageInfo(context.packageName, 0)
//            packageName = packageInfo.packageName
//            return packageName
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//
//        return packageName
//    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    fun getDeviceBrand(): String {
        return android.os.Build.BRAND
    }

    /*
* deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
*
* 渠道标志为：
* 1，andriod（a）
*
* 识别符来源标志：
* 1， wifi mac地址（wifi）；
* 2， IMEI（imei）；
* 3， 序列号（sn）；
* 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
*
* @param context
* @return
*/
    @SuppressLint("MissingPermission")
    fun getDeviceId(context: Context): String? {
        val deviceId = StringBuilder()
        // 渠道标志
        deviceId.append("a")
        try {
            //wifi mac地址
            val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = wifi.connectionInfo
            val wifiMac = info.macAddress
            if (!isEmpty(wifiMac)) {
                deviceId.append("wifi")
                deviceId.append(wifiMac)
                LogUtils.e("getDeviceId : ", deviceId.toString())
                return deviceId.toString()
            }
            //IMEI（imei）
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val imei = tm.deviceId
            if (!isEmpty(imei)) {
                deviceId.append("imei")
                deviceId.append(imei)
                LogUtils.e("getDeviceId : ", deviceId.toString())
                return deviceId.toString()
            }
            //序列号（sn）
            val sn = tm.simSerialNumber
            if (!isEmpty(sn)) {
                deviceId.append("sn")
                deviceId.append(sn)
                LogUtils.e("getDeviceId : ", deviceId.toString())
                return deviceId.toString()
            }
            //如果上面都没有， 则生成一个id：随机码
            val uuid = getUUID(context)
            if (!isEmpty(uuid)) {
                deviceId.append("id")
                deviceId.append(uuid)
                LogUtils.e("getDeviceId : ", deviceId.toString())
                return deviceId.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            deviceId.append("id").append(getUUID(context))
        }
        LogUtils.e("getDeviceId : ", deviceId.toString())
        return deviceId.toString()
    }

    /**
     * 得到全局唯一UUID
     */
    fun getUUID(context: Context?): String {
        var uuid =  SPUtils.getString(context,"uuid","");
        if (isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString()
            SPUtils.saveString(context,"uuid",uuid);
        }
        return uuid
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    fun isOPen(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return if (gps || network) {
            true
        } else false
    }

}
