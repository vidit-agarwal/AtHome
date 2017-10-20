package com.devbrain.athome.modal;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.devbrain.athome.rest.NetworkTransaction;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mukesh Jha on 7/16/2016.
 */
public class MGrocery extends Application {
    private static MGrocery app;
//    ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private Activity activity;
    private List<ApplicationInfo> lstApplicationInfos = Collections.EMPTY_LIST;

    @Override
    public void onCreate() {
        super.onCreate();

//        Initialize volley
        NetworkTransaction.getInstance(this);
        app = new MGrocery();
        app.setContext(this);
//        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MGrocery getInstance() {
        return app;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<ApplicationInfo> getLstApplicationInfos() {
        return lstApplicationInfos;
    }

    public void setLstApplicationInfos(List<ApplicationInfo> lstApplicationInfos) {
        this.lstApplicationInfos = lstApplicationInfos;
    }

    public static void clearApplicationData() {
        File cache = getInstance().getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {

                    deleteDir(new File(appDir, s));

                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s + " DELETED");
                }
            }
        }
    }

//    public ImageLoader getImageLoader() {
//        return imageLoader;
//    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
