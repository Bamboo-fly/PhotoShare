package com.example.photoshare.ui.Talk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.photoshare.R;

public class TalkActivity extends AppCompatActivity {

    private static final String TAG = "TalkActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk2);

        //获得控件
        WebView webView = (WebView) findViewById(R.id.wv_webview);
        //访问网页
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://m.chat.sunning.fit/#/");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
    }
}