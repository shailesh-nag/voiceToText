package com.chatbot.ui.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chatbot.BR;
import com.chatbot.R;
import com.chatbot.databinding.FragmentIndexBinding;
import com.chatbot.helper.Constants;
import com.chatbot.interfaces.IFragmentCallback;

import static com.chatbot.BR.onclick;
import static com.chatbot.BR.name;

public class IndexFragment extends Fragment implements View.OnClickListener {

    private IFragmentCallback mCallback;
    private FragmentIndexBinding mBinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.fragment_index, container, false);
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_index, container, false);
        mBinder.setVariable(onclick, this);
        mBinder.setVariable(BR.name,getString(R.string.title_connected_to,"Admin"));
        return mBinder.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentCallback)
            mCallback = (IFragmentCallback) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }


    private void initView(View view) {
       // ((TextView) view.findViewById(R.id.tv_heading)).setText(getString(R.string.title_connected_to, "Admin"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chatbot:
                if (mCallback != null) mCallback.onFragmentResult(Constants.CHAT_BOT, null);
                break;
            case R.id.btn_speech_recognition:
                if (mCallback != null)
                    mCallback.onFragmentResult(Constants.SPEECH_RECOGNITION_INBUILT, null);
                break;
            case R.id.btn_chatbot_sample:
                if (mCallback != null) mCallback.onFragmentResult(Constants.CHAT_BOT_SAMPLE, null);
                break;
            case R.id.btn_bluetoothPOC:
                if (mCallback != null) mCallback.onFragmentResult(Constants.BLUETOOTH_POC, null);
                break;
            case R.id.btn_chatbot_with_mic_sample:
                if (mCallback != null)
                    mCallback.onFragmentResult(Constants.CHAT_BOT_SAMPLE_MIC, null);
                break;
            default:
                break;
        }
    }
}
