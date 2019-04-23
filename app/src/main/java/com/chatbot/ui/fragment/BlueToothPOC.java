package com.chatbot.ui.fragment;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chatbot.R;
import com.chatbot.dialog.AppDialog;
import com.chatbot.helper.BlueToothHealper;
import com.chatbot.helper.Tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class BlueToothPOC extends BaseFragment implements View.OnClickListener {
    BlueToothHealper helper;
    TextView tvBTDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        return view;
    }


    @Override
    void initView(View view) {
        helper = new BlueToothHealper();
        view.findViewById(R.id.btn_enable_bluetooth).setOnClickListener(this);
        view.findViewById(R.id.btn_search_bluetooth).setOnClickListener(this);
        view.findViewById(R.id.btn_searchable).setOnClickListener(this);
        view.findViewById(R.id.btn_scan_bt).setOnClickListener(this);
        tvBTDetail = view.findViewById(R.id.tv_bluetooth_prop);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_enable_bluetooth:
                if (helper.isDevicesHasBlueToothDevice(mContext)) {
                    updateText(getString(R.string.has_bluetooth));
                    boolean isEnable = helper.enableBlueTooth(mContext);
                    if (isEnable) updateText("Bluetooth is off. Turing on");
                    else updateText("Bluetooth is on");
                } else {
                    updateText(getString(R.string.has_not_bluttooth));
                }

                break;

            case R.id.btn_search_bluetooth:
                boolean isEnable = helper.enableBlueTooth(mContext);
                if (!isEnable) {
                    Set<BluetoothDevice> devices = helper.getListOfPairedDevices();

                    for (BluetoothDevice device : devices) {
                        updateText("Device found : " + device.getName() + "-" + device.getAddress());
                    }
                }
                break;
            case R.id.btn_searchable:
                helper.enableBTDiscoverable(mContext);
                break;
            case R.id.btn_scan_bt:
                test();
//                addItems();
//                 adapter = AppDialog.showDialog(mContext, mItemString);
//
//                  registerScanReceiverReceiver();
//                  helper.enableBTDiscoverable(mContext);
//                helper.searchNearByDevices();
                break;
        }
    }

    private static List<String> mItemString = new ArrayList<>();
    private static ArrayAdapter<String> adapter;
    private void addItems() {
        for (int i = 0; i < 2; i++) {
            mItemString.add("Item " + i);
        }
    }


    static List<com.chatbot.model.BluetoothDevice> mList = new ArrayList<>();


    private void updateText(String text) {
        if (tvBTDetail.getText().toString().equals(getString(R.string.txt_bt_placeholder))) {
            tvBTDetail.setText(text);
        } else
            tvBTDetail.setText(tvBTDetail.getText().toString() + "\n" + text);
    }

    private boolean checkBlueToothIsEnable() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isRegister)
        mContext.unregisterReceiver(mReceiver);
    }

    boolean isRegister = false;
    private void registerScanReceiverReceiver() {
        isRegister = true;
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mContext.registerReceiver(mReceiver, filter);
        updateText("Searching.. for devices nearby");
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            updateText("Found");
            //Finding devices
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                com.chatbot.model.BluetoothDevice deviceItem = new com.chatbot.model.BluetoothDevice();
                deviceItem.setName(device.getName());
                deviceItem.setMacAddress(device.getAddress());
                updateText(device.getName());
                mList.add(deviceItem);
                mItemString.add(device.getName());
                adapter.notifyDataSetChanged();
             }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                Log.v(" ","discovery Finished ");
                if(mList.size() != 0)
                {
                    Tools.showToast(mContext,"New Devices Found");
                   // deviceList.invalidateViews();
                    //sectionAdapter.notifyDataSetChanged();
                }
                else
                {
                    Tools.showToast(mContext,"No New Devices Found");
                }
            }
        }
    };


    BluetoothAdapter ba;
    BluetoothDevice bd;

    private void test()
    {
        ba=BluetoothAdapter.getDefaultAdapter();
        if(!ba.isEnabled())
        {
            Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(i);
            ba.startDiscovery();
        }
        else
        {
            Toast.makeText(mContext, "START DISCOVERY...", Toast.LENGTH_SHORT).show();
            ba.startDiscovery();
        }
        BroadcastReceiver br=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action=intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action))
                {
                    BluetoothDevice bd=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Toast.makeText(mContext,
                            bd.getName()+"||"+bd.getAddress()
                            , Toast.LENGTH_SHORT).show();
                }
            }
        }  ;
        IntentFilter ifr=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(br,ifr);

    }


}
