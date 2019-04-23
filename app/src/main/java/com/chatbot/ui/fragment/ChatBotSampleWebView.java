package com.chatbot.ui.fragment;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chatbot.R;
import com.chatbot.helper.MyWebClient;
import com.chatbot.helper.Tools;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ChatBotSampleWebView extends BaseFragment {

    private static final int SPEECH_REQUEST_CODE = 100;
    private final String URL = "file:///android_asset/messengerbox.html";
    private ProgressBar pgBar;
    private MyWebClient mClient;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview,container,false);
        return view;
    }

    private WebView webview;

    @Override
    void initView(View view) {
       webview = view.findViewById(R.id.webview);

       webview.setWebViewClient(new CustomWebViewClient());
         pgBar = view.findViewById(R.id.pgBar);
        webview.loadUrl(URL);

        // Enablejavascript
         webview.getSettings().setJavaScriptEnabled(true);


       webview.addJavascriptInterface(new Object(){
           @JavascriptInterface
           public void callVoiceRecorderAndroid()
           {
//               Tools.showToast(mContext,"Works");
               displaySpeechRecognizer();
           }
       }, "interface");



    }


    /**
     * CustomWebViewClient is used to add a custom hook to the url loading.
     */
    private class CustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pgBar.setVisibility(View.GONE);
        }
    }


    @JavascriptInterface
    public void isItClicked()
    {
        Tools.showToast(mContext, "Java interface CLicked");
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }catch (ActivityNotFoundException ex)
        {
            Tools.showToast(mContext,"Ok google Not found in this device");
        }
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

          //  Tools.showToast(mContext,"Text is :"+ spokenText);
       Message msg= new Message();
       msg.what  = 100;
       Bundle mBundle = new Bundle();
       mBundle.putString("msg",spokenText);
       msg.setData(mBundle);
       handler.sendMessage(msg);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void CallJS(String message)
    {
     //   Tools.showToast(mContext,"Works");
        //String item = "callWithData";
        webview.loadUrl("javascript:callWithData('"+message+"');");
        //  webview.loadUrl("javascript:callFromApp();");

    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 100){
                String message = msg.getData().getString("msg");
                CallJS(message);
            }else
            CallJS("Is this working, Testing Testing....");
        }
    };
}
