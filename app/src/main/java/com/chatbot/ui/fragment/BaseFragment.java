package com.chatbot.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatbot.R;
import com.chatbot.helper.Constants;
import com.chatbot.interfaces.IFragmentCallback;

public abstract class BaseFragment extends Fragment {

    protected IFragmentCallback mCallback;
  protected Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IFragmentCallback)
            mCallback = (IFragmentCallback) context;
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }


    abstract void initView(View view);


}
