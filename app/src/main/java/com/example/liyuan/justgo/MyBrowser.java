package com.example.liyuan.justgo;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zhaoheng on 2018-03-26.
 */

public class MyBrowser extends WebViewClient{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
