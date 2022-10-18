package com.example.photoshare.ui.Talk;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.photoshare.R;

public class TalkFragment extends Fragment {

    private static final String TAG = "TalkFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View view=this.getLayoutInflater().inflate((R.layout.fragment_talk),null);
//        //获得控件
//        WebView webView = (WebView) view.findViewById(R.id.wv_webview);
//        webView.loadUrl("http://baidu.com");
//        //访问网页
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view,String url){
//                view.loadUrl(url);
//                return true;
//            }
//        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.getAllowContentAccess();
//        webView.setVisibility(View.VISIBLE);
//        //webView.loadUrl("https://www.baidu.com/");
//        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
//        Log.d(TAG, "onCreateView: ss");
//
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                Log.d(TAG, "shouldOverrideUrlLoading: ");
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    view.loadUrl(request.getUrl().toString());
//                } else {
//                    view.loadUrl(request.toString());
//                }
//                return true;
//            }
//        });

//        webView.loadUrl("https://www.baidu.com/");
//        webView.setWebViewClient(new WebViewClient());

        return inflater.inflate(R.layout.fragment_talk, container, false);
    }


    @Override
         public void onViewCreated(View view, Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);
        View view2=this.getLayoutInflater().inflate((R.layout.fragment_talk),null);
        //获得控件
        WebView webview = (WebView) view2.findViewById(R.id.wv_webview);
                 WebSettings settings = webview.getSettings();
                 settings.setJavaScriptEnabled(true);


                 //支持缩放
                 settings.setUseWideViewPort(true);//设定支持viewport
                 settings.setLoadWithOverviewMode(true);
                 settings.setBuiltInZoomControls(true);
                 settings.setSupportZoom(true);//设定支持缩放

                 //打开的网址
                 webview.loadUrl("http://www.527fgame.com/news.html");
             }
}