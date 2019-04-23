package com.chatbot.helper;

import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.chatbot.R;

public class MyWebClient extends WebChromeClient {
    final ProgressBar loadProgress;
    private WebView wv;

    public MyWebClient(WebView webView, ProgressBar progressBar) {
        this.wv = webView;
        this.loadProgress = progressBar;
        loadProgress.setVisibility(View.GONE);
    }


    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

        Log.e("Console log",consoleMessage.message());
        return super.onConsoleMessage(consoleMessage);
    }



    public void onPageFinished(WebView view, String url) {
        wv = view;
        wv.setVisibility(View.VISIBLE);
        loadProgress.setVisibility(View.INVISIBLE);
        //view.clearCache(true);


       // updateItem("");


    }


    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        wv.loadData("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<center>" + wv.getContext().getString(R.string.erroopsproblem) + ".</center>", "text/html", "UTF-8");
    }


    boolean isReload = false;
    String whatToUpdate = "Password";

    public void updateItem(String whatToUpdate) {
//
    String js = "javascript:callFromApp();";
//         wv.loadUrl("javascript:testEcho('Hello World!')");

//        if (!isReload) {
//            //  wv.reload();
//            if (!TextUtils.isEmpty(whatToUpdate)) {
//                this.whatToUpdate = whatToUpdate;
//                isReload = true;
//                String webUrl = wv.getUrl();
//                wv.destroy();
//                wv.loadUrl(webUrl);
//
//                //wv.loadUrl( "javascript:window.location.reload( true )" );
//                return;
//            }
//        } else isReload = false;
//        final String password = "password";
////            final String answer = "5";
////
////            final String js = "javascript:" +
////                    "var x = document.getElementById('messageField').value = '" + password + "';" ;
////                    +
////                    "document.getElementById('send').click()";
//////
////            wv.loadUrl(js,null);
//        String js = "javascript: document.getElementById('messageField').value='" + this.whatToUpdate + "';";
//
//        wv.loadUrl(js,null);
//        if (Build.VERSION.SDK_INT >= 19) {
//            wv.evaluateJavascript(js, s -> {
//                Log.e("", "Working");
//
//            });
//        } else {
//            wv.loadUrl(js);
//        }
//        wv.loadUrl("javascript:(function() { androidInvoke(); })()");
//
//        //   wv.loadUrl("javascript:(function() { document.getElementById('messageField').value = '" + password + "';document.getElementById('ok').click(); })()");


    }

    public void updateItem(String item, WebView wv) {
        String js = "javascript:document.getElementById('messageField').value = 'shailesh';" +
                "document.getElementById('ok').click()";
        //   wv.loadUrl(js);

        if (Build.VERSION.SDK_INT >= 19) {
            wv.evaluateJavascript(js, s -> {
                Log.e("", "Working");
            });
        } else {
            wv.loadUrl(js);
        }
    }
}