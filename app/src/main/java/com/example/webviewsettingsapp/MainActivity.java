package com.example.webviewsettingsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    //save url
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String text = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String regUrlString = sharedPreferences.getString("url", "false");
        URI uri = URI.create(regUrlString);
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(uri.toString());
        webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            final Uri uri = request.getUrl();
            String regUrl = sharedPreferences.getString("url", "false");
            String url2 = "https://";
            if(uri.toString().startsWith(url2)){
                view.loadUrl(uri.toString());
                saveData(uri.toString());
                return true;
            }else {
                view.loadUrl(uri.toString());
                return true;
            }
        }
    }

    public void saveData(String url){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(text, url);
        editor.apply();
        //webView.loadUrl("https://www.google.com/");
    }
}
