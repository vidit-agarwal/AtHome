package com.devbrain.athome.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.devbrain.athome.R;
import com.devbrain.athome.modal.Device;
import com.devbrain.athome.modal.MGrocery;
import com.devbrain.athome.modal.Utility;
import com.devbrain.athome.rest.NetworkTransaction;
import com.devbrain.athome.rest.NetworkTransactionListener;
import com.devbrain.athome.rest.RestAPIURL;
import com.devbrain.athome.rest.TransactionType;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    String TAG = "SplashActivity";
    public static String UserID = "3";
    public static String VendorID = "2";
    public static String ROLE_JSON = "role";
    /**
     * Id to identify a contacts permission request.
     */
    public static final int REQUEST_CONTACTS = 1;
//    public AlertDialog usageStatsDialog;

    public static String[] PERMISSIONS_CONTACT = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.GET_ACCOUNTS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);

        MGrocery.getInstance().setActivity(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    askForPermission();
                } else {

                    initLoadData();
                }
            }
        }, 2000);

    }


    /*################################## Server Transaction ##########################################*/

    private void initLoadData() {

        JSONObject jObjDeviceInfo = Device.getInstance(this).toJSON();
//        JSONObject jObjDeviceIdentity = Device.getInstance(this).getDeviceIdentityJSON();
        try {
            jObjDeviceInfo.put(ROLE_JSON, UserID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Push device information to server
        NetworkTransaction.getInstance(this).ProcessRequest(TransactionType.POST, RestAPIURL.INIT_URL, jObjDeviceInfo, new NetworkTransactionListener<String>() {
            @Override
            public void onSuccess(String object, Object obj) {
                Utility.printLog("onSuccess: " + object);
                startActivity(new Intent(SplashActivity.this, HomeMainActivity.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onFail(String object, Object obj) {

                Utility.printLog("onFail: " + object);
                showNetworkConnection("Connection Error",object);
            }

            @Override
            public void onExist(String object, Object obj) {
                Utility.printLog("onExist: " + object);
            }

            @Override
            public void onNetworkError(String object, Object obj) {
                Utility.printLog("onNetworkError: " + object);
                showNetworkConnection("Network Error",object);
            }
        });

    }

    /*#################################### AST - DST Permission handling ###########################################*/

    /**
     * Called when the 'show camera' button is clicked.
     * Callback is defined in resource layout definition.
     */
    public void askForPermission() {

        // Verify that all required contact permissions have been granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {


            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        } else {
            initLoadData();
        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            if (requestCode == REQUEST_CONTACTS) {
                // for each permission check if the user grantet/denied them
                // you may want to group the rationale in a single dialog,
                // this is just an example
                int count = 0;
                for (int i = 0, len = permissions.length; i < len; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        count++;
                    }
                }
                if (count == permissions.length) {
                    initLoadData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNetworkConnection(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
