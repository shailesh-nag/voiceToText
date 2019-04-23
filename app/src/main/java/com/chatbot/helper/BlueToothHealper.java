package com.chatbot.helper;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import java.util.Set;

public class BlueToothHealper {


    private boolean checkBlueToothIsEnable()
    {
       return getBlueToothAdapter().isEnabled();

    }

    public boolean enableBlueTooth(Context mContext)
    {
        if(!checkBlueToothIsEnable() || getBlueToothAdapter().getState()==BluetoothAdapter.STATE_OFF)
        {
            enableBlueToothViaIntent(mContext);
           return true;

        }else
            return false;
    }

    public boolean isDevicesHasBlueToothDevice(Context mContext)
    {
        boolean isBluetoothSupported = mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
        if(isBluetoothSupported){
        BluetoothAdapter mBluetoothAdapter = getBlueToothAdapter();
        return mBluetoothAdapter != null;
        }
        return false;

    }

    private BluetoothAdapter getBlueToothAdapter()
    {
//        BluetoothAdapter btAdapter = ((Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1)
//                ?((BluetoothManager)mContext.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter()
//                :(BluetoothAdapter.getDefaultAdapter()));

        return BluetoothAdapter.getDefaultAdapter();
    }

    private boolean getProfileConnected(int profile)
    {
        // profile  like BluetoothHeadset.HEADSET;
        return getBlueToothAdapter().getProfileConnectionState(profile) == BluetoothHeadset.STATE_CONNECTED;

    }

    private void enableBlueToothViaIntent(Context mContext)
    {
        Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        mContext.startActivity(btIntent);
    }


    public void enableBTDiscoverable(Context mContext)
    {
        Intent dIntent =  new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        dIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        mContext.startActivity(dIntent);
    }

    public Set<BluetoothDevice> getListOfPairedDevices()
    {
        // Get paired devices.
        Set<BluetoothDevice> pairedDevices = getBlueToothAdapter().getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
        return pairedDevices;
    }


    // require BT admin permission
    private void closeBlueTooth()
    {
        getBlueToothAdapter().disable();
    }

    public void searchNearByDevices() {
        getBlueToothAdapter().startDiscovery();
        // Implement two methods
  //      #registerScanReceiverReceiver
 //               and
 //       #BroadcastReceiver
    }

    private void registerScanReceiverReceiver(Context mContext) {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(mReceiver, filter);
    }

   private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            //Finding devices
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            }
        }
    };
}
