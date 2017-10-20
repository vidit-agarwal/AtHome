package com.devbrain.athome.modal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import com.devbrain.athome.rest.RestAPIURL;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Mukesh Jha on 7/16/2016.
 */
public class Utility {
    /**
     * This method return formatted(yyyy-MM-dd HH:mm:ss) current device time in .
     *
     * @author Mukesh Jha
     * @return String
     * @since 5/21/2016
     */
    private static String TAG = "Utility";

    public static String getDeviceCurrentTime() {
        SimpleDateFormat formatter = null;
        try {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.format(new Date());
        } catch (Exception e) {
            printExceptionLog(e.getMessage(), e);
        } finally {
            formatter = null;
        }

        return "";
    }


    public static void printExceptionLog(String mes, Exception e) {

        System.out.println(mes + "::::::::::::: ----------" + e);
        e.printStackTrace();
    }

    public static List<ApplicationInfo> getAllInstalledApplication(Context context) {
        final PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            printLog("Name :" + pm.getApplicationLabel(packageInfo));
            printLog("Installed package :" + packageInfo.packageName);
            printLog("Source dir : " + packageInfo.sourceDir);
            printLog("Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));

        }

        return packages;
    }


    public static String getAppropriateSize(long x) {
        String lastValue = "";
        long tot = x;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");

        double kby = 1024;
        double mby = kby * 1024;
        double gby = mby * 1024;
        double tby = gby * 1024;

        double kb = tot / 1024.0;
        double mb = tot / (kby * 1024);
        double gb = tot / (mby * 1024);
        double tb = tot / (gby * 1024);

        if (tb > 1) {
            lastValue = twoDecimalForm.format(tb).concat(" TB");
        } else if (gb > 1) {
            lastValue = twoDecimalForm.format(gb).concat(" GB");
        } else if (mb > 1) {
            lastValue = twoDecimalForm.format(mb).concat(" MB");
        } else if (kb > 1) {
            lastValue = twoDecimalForm.format(kb).concat(" KB");
        } else {
            lastValue = twoDecimalForm.format(tot).concat(" Byte");
        }
        return lastValue;
    }

    public static void showToast(Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void printLog(String message) {
        System.out.println("@@@@@@@: " + message + " " + getDeviceCurrentTime());
    }

    public static void printException(String message, Exception e) {
        System.out.println(message + "::::::::::::: ----------" + e);
        e.printStackTrace();
    }


    public static Date getForamattedDate(long milisecond) {
        Date modifiledDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ;
            modifiledDate = format.parse(formatter.format(new Date(milisecond)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return modifiledDate;
    }

    public static String getFormattedDateFromMilis(long milisecond) {
        SimpleDateFormat formatter = null;
        try {
            formatter = new SimpleDateFormat("dd MMM, yyyy");
            return formatter.format(new Date(milisecond));
        } catch (Exception e) {
//            App.getInstance().trackException(TAG + " String getFormattedDateFromMilis(long milisecond)", e);
        } finally {
            formatter = null;
        }

        return "";
    }

    public static boolean isInternetPresent(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static void rateThisApplication(Context context) {

        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        } finally {
            uri = null;
            goToMarket = null;
        }
    }

    public static void shareThisApplication(Context context) {
        Intent sharingIntent = null;
        StringBuilder sbMsg = null;

        try {
            sbMsg = new StringBuilder();
            sbMsg.append("Hi \n I've tried this Smart Phone cleaning app. You can also try this out and enjoy.\n");
//            sbMsg.append("You also can win gifts by downloading this app.\n");
            /*if(SharedPreferencesUtil.getInstance().hasKey("shareUrl"))
            {
                sbMsg.append(SharedPreferencesUtil.getInstance().getStringDataFromSharedPreference("shareUrl"));
            }
            else
            {
                sbMsg.append("https://play.google.com/store/apps/details?id="+this.getPackageName());
            }*/

//            sbMsg.append("https://play.google.com/store/apps/details?id="+this.getPackageName());
            sbMsg.append("http://qcleanerr.com/Qcleaner/prd/apknw/sharewithyourfriend.apk");

            sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, sbMsg.toString());
            context.startActivity(Intent.createChooser(sharingIntent, "Share Using"));
        } catch (Exception e) {
//            App.getInstance().trackException(TAG + " shareThisApplication()", e);
        } finally {
            sharingIntent = null;
            sbMsg = null;
        }
    }

    public static void update(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            String appVersionCode = String.valueOf(pm.getPackageInfo(context.getPackageName(), 0).versionCode);

            String updateUrl = RestAPIURL.UPDATE_URL + "?appid=1&version=" + appVersionCode;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
            context.startActivity(intent);

            pm = null;
            appVersionCode = null;
            updateUrl = null;
            intent = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }



    /*    ********************  Start Get Image and Set Image  **********************   */
    public static void setImageFromUrl(String url, final ImageView imageView) {

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... urls) {
                Bitmap map = null;
                for (String url : urls) {
                    map = downloadImage(url);
                }
                return map;
            }

            // Sets the Bitmap returned by doInBackground
            @Override
            protected void onPostExecute(Bitmap result) {
                if (result != null)
                    imageView.setImageBitmap(result);
            } // Creates Bitmap from InputStream and returns it

            private Bitmap downloadImage(String url) {
                Bitmap bitmap = null;
                InputStream stream = null;
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inSampleSize = 1;
                try {
                    stream = getHttpConnection(url);
                    bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                    if (stream != null)
                        stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return bitmap;
            } // Makes HttpURLConnection and returns InputStream

            private InputStream getHttpConnection(String urlString)
                    throws IOException {
                InputStream stream = null;
                URL url = new URL(urlString);
                URLConnection connection = url.openConnection();
                try {
                    HttpURLConnection httpConnection = (HttpURLConnection) connection;
                    httpConnection.setRequestMethod("GET");
                    httpConnection.connect();
                    if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        stream = httpConnection.getInputStream();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return stream;
            }
        }.execute(url);

    }

    public static double highToLow(double high){
        return high*1000;
    }

    public static double lowToHigh(int low){
        return low*0.001;
    }

//    public static String getMinUnit(String prodUnit) {
//        switch (prodUnit) {
//            case "kg":
//                return "g";
//            case "l":
//                return "ml";
//            case "p":
//                return "p";
//            default:
//                return "";
//        }
//    }



    public static boolean isUnitHigh(String unit) {
        switch (unit) {
            case "kg":
            case "l":
            case "p":
                return true;
            default:
                return false;
        }
    }

//    public static double getMinAmount(ProductInfo productInfo) {
//        switch (productInfo.getProdUnit()) {
//            case "p":
//                return productInfo.getProdMinOrder();
//            case "kg":
//            case "l":
//                return Utility.lowToHigh(productInfo.getProdMinOrder());
//            default:
//                return productInfo.getProdMinOrder();
//        }
//    }


}
