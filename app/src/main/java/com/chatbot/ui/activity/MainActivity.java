package com.chatbot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.chatbot.R;
import com.chatbot.interfaces.IFragmentCallback;
import com.chatbot.ui.fragment.BlueToothPOC;
import com.chatbot.ui.fragment.ChatBotSampleWebView;
import com.chatbot.ui.fragment.ChatbotWithMic;
import com.chatbot.ui.fragment.IndexFragment;

import static com.chatbot.helper.Constants.BLUETOOTH_POC;
import static com.chatbot.helper.Constants.CHAT_BOT;
import static com.chatbot.helper.Constants.CHAT_BOT_SAMPLE;
import static com.chatbot.helper.Constants.CHAT_BOT_SAMPLE_MIC;
import static com.chatbot.helper.Constants.SPEECH_RECOGNITION_INBUILT;

public class MainActivity extends AppCompatActivity implements IFragmentCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadFragment(R.id.fl_container, new IndexFragment(), true);


    }


    @Override
    public void onLoadFragment(FRAGMENT_TYPE type, Bundle extras) {
        Fragment mFragment = null;
        if (type == FRAGMENT_TYPE.INDEX) {
            mFragment = new IndexFragment();
        } else if (type == FRAGMENT_TYPE.WEBVIEW) {
            mFragment = new ChatBotSampleWebView();
        } else if (type == FRAGMENT_TYPE.BLUETOOTH)
            mFragment = new BlueToothPOC();
        else if (type == FRAGMENT_TYPE.CHAT_BOT_SAMPLE_MIC)
            mFragment = new ChatbotWithMic();
        if (mFragment != null) {
            if (extras != null)
                mFragment.setArguments(extras);
            loadFragment(R.id.fl_container, mFragment, false);
        }
    }

    @Override
    public void onFragmentResult(String result, Object object) {
        if (CHAT_BOT.equals(result)) {
            startActivity(new Intent(MainActivity.this, WebViewActivity.class));
        } else if (SPEECH_RECOGNITION_INBUILT.equals(result)) {
            startActivity(new Intent(MainActivity.this, SpeechToTextActivity.class));

        } else if (CHAT_BOT_SAMPLE.equals(result)) {
            onLoadFragment(FRAGMENT_TYPE.WEBVIEW, null);
        } else if (BLUETOOTH_POC.equals(result)) {
            onLoadFragment(FRAGMENT_TYPE.BLUETOOTH, null);
        } else if (CHAT_BOT_SAMPLE_MIC.equals(result)) {
            onLoadFragment(FRAGMENT_TYPE.CHAT_BOT_SAMPLE_MIC, null);

        }
    }


    /**
     * Change the fragment on the container
     *
     * @param container this container will load the fragment
     * @param fragment  fragment will be loaded on cointainer
     * @param isAdd     if wish to add the fragment or false if replace
     */
    void loadFragment(int container, Fragment fragment, boolean isAdd) {
        if (fragment == null) return;
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        if (isAdd)
            tr.add(container, fragment);
        else {
            tr.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            tr.replace(container, fragment, fragment.toString()).addToBackStack(fragment.toString());
        }
        tr.commit();
    }


}
