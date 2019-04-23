package com.chatbot.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.chatbot.R;

public class WebViewActivity extends AppCompatActivity{

    private final String URL = "https://medium.com/@caueferreira/timber-enhancing-your-logging-experience-330e8af97341";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressBar pgBar = findViewById(R.id.pgBar);
       WebView wb=(WebView)findViewById(R.id.webview);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setBuiltInZoomControls(false);
        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
      //  wb.getSettings().setPluginsEnabled(true);
        wb.setWebViewClient(new WebViewClass(wb,pgBar));
        wb.loadUrl(URL);

        // Enablejavascript
        WebSettings ws = wb.getSettings();
        ws.setJavaScriptEnabled(true);
        // Add the interface to record javascript events


        wb.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void performClick() {
                Intent i = new Intent(WebViewActivity.this, SpeechToTextActivity.class);
                startActivity(i);
            }
        }, "valid");
    }

    class WebViewClass extends WebViewClient {
        final ProgressBar loadProgress;
        final WebView wv;

        WebViewClass(WebView webView, ProgressBar progressBar) {
            this.wv = webView;
            this.loadProgress = progressBar;
        }

        public void onPageFinished(WebView view, String url) {
            wv.setVisibility(View.VISIBLE);
            loadProgress.setVisibility(View.INVISIBLE);
            view.clearCache(true);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            wv.loadData("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<center>" + getString(R.string.erroopsproblem) + ".</center>", "text/html", "UTF-8");
        }
    }


}
