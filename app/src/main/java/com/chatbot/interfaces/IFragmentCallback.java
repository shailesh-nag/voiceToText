package com.chatbot.interfaces;

import android.os.Bundle;

/**
 * Interface for fragment callback and update
 */
public interface IFragmentCallback {
    void onLoadFragment(FRAGMENT_TYPE type, Bundle extras);

    void onFragmentResult(String result, Object object);

    enum FRAGMENT_TYPE {INDEX, WEBVIEW, BLUETOOTH , CHAT_BOT_SAMPLE_MIC;
    }
}
