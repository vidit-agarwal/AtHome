package com.devbrain.athome.modal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import org.json.JSONObject;

/**
 * Created by Mukesh Jha on 12/29/2015.
 */
public class Device {
    public static String TAG = Device.class.getSimpleName();

    private static Device device = null;
    private static DeviceLocation deviceLocation = null;
    private static Context context = null;

    private String id;
    private String serial;
    private final String type = "Android";
    private String manufacturer;
    private String model;
    private String IMEI;
    private String IMSI;
    private String newtworkOperator;
    private String OSVersion;
    private String displayDiagonalInInch;
    private String displayWidthInInch;
    private String displayHeightInInch;
    private String displayWidthInPixel;
    private String displayHeightInPixel;
    private String versionCode;
    private String versionName;
    private String GCMToken;
    private final static String roleId = "2";
    private final static String appId = "1";
    private String screenType;
    private final static String vendor = "2";
    private String androidId;
    private String AST_DST_IDENTIFIER = "devbrain-mc-01";

    private Device() {
        TelephonyManager telephonyManager = null;
        try {
            this.id = Build.ID;
            this.serial = Build.SERIAL;
            this.manufacturer = Build.MANUFACTURER;
            this.model = Build.MODEL;

//          Getting phone IMEI number;
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            this.IMEI = telephonyManager.getDeviceId();

//          Getting phone IMSI/SIM Number
            this.IMSI = telephonyManager.getSimSerialNumber();

            this.newtworkOperator = telephonyManager.getNetworkOperatorName();
            this.OSVersion = telephonyManager.getDeviceSoftwareVersion();

            this.androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

//            Getting device screen type/density
            this.screenType = this.getDeviceScreenDesity(context);

//            Setting screen size(in pixel and inch)
            this.setDeviceScreenSize();

//            set this application info
            this.setAppInfo();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            telephonyManager = null;
        }
    }

    public static Device getInstance(Context ctx) {
        context = ctx;

        if (device == null) {
            device = new Device();
        }

        return device;
    }

    public static Device getInstance(Context ctx, String gcmToken) {
        context = ctx;

        if (device == null) {
            device = new Device();
        }

        device.GCMToken = gcmToken;

        return device;
    }

    public DeviceLocation getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(DeviceLocation deviceLocation) {
        Device.deviceLocation = deviceLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public String getType() {
        return type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getIMEI() {
        return IMEI;
    }

    public String getIMSI() {
        return IMSI;
    }

    public String getNewtworkOperator() {
        return newtworkOperator;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public String getDisplayDiagonalInInch() {
        return displayDiagonalInInch;
    }

    public String getDisplayWidthInInch() {
        return displayWidthInInch;
    }

    public String getDisplayHeightInInch() {
        return displayHeightInInch;
    }

    public String getDisplayWidthInPixel() {
        return displayWidthInPixel;
    }

    public String getDisplayHeightInPixel() {
        return displayHeightInPixel;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getGCMToken() {
        return GCMToken;
    }

    public void setGCMToken(String GCMToken) {
        this.GCMToken = GCMToken;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getAppId() {
        return appId;
    }

    public String getScreenType() {
        return screenType;
    }

    public String getVendor() {
        return vendor;
    }

    public String getAndroidId() {
        return androidId;
    }

    public String getAST_DST_IDENTIFIER() {
        return AST_DST_IDENTIFIER;
    }

//    ****************************************Helper Methods*********************************************************

    public void setDeviceScrennSizeInPixel() {
        DisplayMetrics displaymetrics = null;

        try {
            displaymetrics = new DisplayMetrics();
            MGrocery.getInstance().getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            this.displayHeightInPixel = String.valueOf(displaymetrics.heightPixels);
            this.displayWidthInPixel = String.valueOf(displaymetrics.widthPixels);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            displaymetrics = null;
        }
    }

    public void setDeviceScreenSize() {
        DisplayMetrics displaymetrics = null;

        try {
            displaymetrics = new DisplayMetrics();
            MGrocery.getInstance().getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            int widthPixels = displaymetrics.widthPixels;
            int heightPixels = displaymetrics.heightPixels;

            float scaleFactor = displaymetrics.density;

            float widthDp = widthPixels / scaleFactor;
            float heightDp = heightPixels / scaleFactor;

            float widthDpi = displaymetrics.xdpi;
            float heightDpi = displaymetrics.ydpi;

            float widthInches = widthPixels / widthDpi;
            float heightInches = heightPixels / heightDpi;

            double diagonalInches = Math.sqrt((widthInches * widthInches) + (heightInches * heightInches));

//            Set device screen height/width/diagonal length in inches
            this.displayHeightInInch = String.valueOf(Math.round(heightInches * 100.0) / 100.0);
            this.displayWidthInInch = String.valueOf(Math.round(widthInches * 100.0) / 100.0);

            this.displayDiagonalInInch = String.valueOf(Math.round(diagonalInches * 100.0) / 100.0);

//            Set screen height/width in pixel
            this.displayWidthInPixel = String.valueOf(widthPixels);
            this.displayHeightInPixel = String.valueOf(heightPixels);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            displaymetrics = null;
        }
    }

    public JSONObject getDeviceSerialJSON() {
        JSONObject deviceSerialJObj = new JSONObject();
        try {
            deviceSerialJObj = new JSONObject();
            deviceSerialJObj.put("PhoneSerial", this.serial);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deviceSerialJObj;
    }

    public void setAppInfo() {
        PackageManager pm = null;
        PackageInfo packageInfo = null;

        try {
            pm = MGrocery.getInstance().getActivity().getPackageManager();
//            packageInfo = pm.getPackageInfo("com.devbrain.jeetogift", 0).versionCode;
            this.versionCode = String.valueOf(pm.getPackageInfo(context.getPackageName(), 0).versionCode);
            this.versionName = String.valueOf(pm.getPackageInfo(context.getPackageName(), 0).versionName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pm = null;
            packageInfo = null;
        }
    }

    public static String getDeviceScreenDesity(Context context) {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        String screenType = null;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                screenType = "ldpi";
                break;

            case DisplayMetrics.DENSITY_MEDIUM:
                screenType = "mdpi";
                break;

            case DisplayMetrics.DENSITY_HIGH:
                screenType = "hdpi";
                break;

            case DisplayMetrics.DENSITY_XHIGH:
                screenType = "xhdpi";
                break;

            case DisplayMetrics.DENSITY_XXHIGH:
                screenType = "xxhdpi";
                break;

            case DisplayMetrics.DENSITY_XXXHIGH:
                screenType = "xxxhdpi";
                break;
        }

        return screenType;
    }

    public String toJSONString() {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();
            jsonObject.put("phoneSerial", serial);
            jsonObject.put("deviceType", type);
            jsonObject.put("deviceManufacture", manufacturer);
            jsonObject.put("deviceModelNumber", model);
            jsonObject.put("imeiNumber", IMEI);
            jsonObject.put("imsiNumber", IMSI);
            jsonObject.put("networkOperator", newtworkOperator);
            jsonObject.put("osVersion", OSVersion);
            jsonObject.put("displayDiagonalInInche", displayDiagonalInInch);
            jsonObject.put("displayWidthInInches", displayWidthInInch);
            jsonObject.put("displayHeightInInches", displayHeightInInch);
            jsonObject.put("displayWidthInPixel", displayWidthInPixel);
            jsonObject.put("displayHeightInPixel", displayHeightInPixel);
            jsonObject.put("versionCode", versionCode);
            jsonObject.put("versionName", versionName);
            jsonObject.put("notificationToken", GCMToken);
            jsonObject.put("roleID", roleId);
            jsonObject.put("appid", appId);
            jsonObject.put("screenType", screenType);
            jsonObject.put("country", "India");
            jsonObject.put("vendor", vendor);
            jsonObject.put("androidid", androidId);
            jsonObject.put("ast_dst", AST_DST_IDENTIFIER);

            if (device.getDeviceLocation() != null) {
                jsonObject.put("longitude", device.getDeviceLocation().getLongitude());
                jsonObject.put("latitude", device.getDeviceLocation().getLatitude());
                /*jsonObject.put("Altitude", device.getDeviceLocation().getAltitude());
                jsonObject.put("Accuracy", device.getDeviceLocation().getAccuracy());
                jsonObject.put("Provider", device.getDeviceLocation().getProvider());*/

                jsonObject.put("locality", device.getDeviceLocation().getLocality());
                jsonObject.put("city", device.getDeviceLocation().getCity());
                jsonObject.put("state", device.getDeviceLocation().getState());
                jsonObject.put("country", device.getDeviceLocation().getCountry());
                jsonObject.put("zip", device.getDeviceLocation().getPostalCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();
            jsonObject.put("phoneSerial", serial);
            jsonObject.put("deviceType", type);
            jsonObject.put("deviceManufacture", manufacturer);
            jsonObject.put("deviceModelNumber", model);
            jsonObject.put("imeiNumber", IMEI);
            jsonObject.put("imsiNumber", IMSI);
            jsonObject.put("networkOperator", newtworkOperator);
            jsonObject.put("osVersion", OSVersion);
//            jsonObject.put("displayDiagonalInInche", displayDiagonalInInch);
//            jsonObject.put("displayWidthInInches", displayWidthInInch);
//            jsonObject.put("displayHeightInInches", displayHeightInInch);
//            jsonObject.put("displayWidthInPixel", displayWidthInPixel);
//            jsonObject.put("displayHeightInPixel", displayHeightInPixel);
            jsonObject.put("versionCode", versionCode);
            jsonObject.put("versionName", versionName);
            jsonObject.put("notificationToken", GCMToken);
//            jsonObject.put("roleID", roleId);
//            jsonObject.put("appid", appId);
            jsonObject.put("screenType", screenType);
            jsonObject.put("country", "India");
            jsonObject.put("vendor", vendor);
            jsonObject.put("androidid", androidId);
//            jsonObject.put("ast_dst", AST_DST_IDENTIFIER);

            if (device.getDeviceLocation() != null) {
                jsonObject.put("longitude", device.getDeviceLocation().getLongitude());
                jsonObject.put("latitude", device.getDeviceLocation().getLatitude());
                /*jsonObject.put("Altitude", device.getDeviceLocation().getAltitude());
                jsonObject.put("Accuracy", device.getDeviceLocation().getAccuracy());
                jsonObject.put("Provider", device.getDeviceLocation().getProvider());*/

//                jsonObject.put("locality", device.getDeviceLocation().getLocality());
//                jsonObject.put("city", device.getDeviceLocation().getCity());
//                jsonObject.put("state", device.getDeviceLocation().getState());
//                jsonObject.put("country", device.getDeviceLocation().getCountry());
//                jsonObject.put("zip", device.getDeviceLocation().getPostalCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getDeviceIdentityJSON() {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();
            jsonObject.put("phoneSerial", serial);
            jsonObject.put("imeiNumber", IMEI);
            jsonObject.put("androidid", androidId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getDeviceRegistrationJSON() {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();
            jsonObject.put("phoneSerial", serial);
            jsonObject.put("imeiNumber", IMEI);
            jsonObject.put("androidid", androidId);
            jsonObject.put("notificationToken", GCMToken);
            jsonObject.put("networkOperator", newtworkOperator);
            if (device.getDeviceLocation() != null) {
                jsonObject.put("longitude", device.getDeviceLocation().getLongitude());
                jsonObject.put("latitude", device.getDeviceLocation().getLatitude());
            }
            } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


}
