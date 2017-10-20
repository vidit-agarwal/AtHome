package com.devbrain.athome.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.devbrain.athome.R;
import com.devbrain.athome.rest.RestAPIURL;

public class AboutUsActivity extends AppCompatActivity {
//    Toolbar toolbar = null;
//    private ProgressDialog progDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Terms & Conditions");*/

//        progDailog = ProgressDialog.show(this, "Loading", "Please wait...", true);
//        progDailog.setCancelable(false);

        WebView wvTnc = (WebView) findViewById(R.id.wv_tnc);

//        wvTnc.getSettings().setJavaScriptEnabled(true);
//        wvTnc.getSettings().setLoadWithOverviewMode(true);
//        wvTnc.getSettings().setUseWideViewPort(true);

        wvTnc.loadUrl(RestAPIURL.ABOUT_US_URL);

//        wvTnc.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                progDailog.show();
//                view.loadUrl(url);
//
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, final String url) {
//                progDailog.dismiss();
//            }
//        });
//
//        wvTnc.clearView();
//        wvTnc.measure(100, 100);
//        wvTnc.getSettings().setUseWideViewPort(true);
//        wvTnc.getSettings().setLoadWithOverviewMode(true);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
