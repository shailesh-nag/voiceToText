package com.chatbot.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chatbot.R;
import com.chatbot.helper.BlueToothHealper;
import com.chatbot.interfaces.IRecyclerViewCallback;
import com.chatbot.model.BluetoothDevice;

import java.util.ArrayList;
import java.util.List;

public class BluetoothDeviceAdapter extends RecyclerView.Adapter<BluetoothDeviceAdapter.Items> {
    private List<BluetoothDevice> mDevices = new ArrayList<>();

    public BluetoothDeviceAdapter(List<BluetoothDevice> list)
    {
        this.mDevices = list;
    }

    @NonNull
    @Override
    public Items onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_devices,viewGroup,false);
        return new Items(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Items items,final int post) {
           items.updateItem(mDevices.get(post));
           items.itemView.setOnClickListener(view ->
        {
            if(mItemClickCallback!=null)
                mItemClickCallback.onItemClick(post,mDevices.get(post));
        });
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    private IRecyclerViewCallback mItemClickCallback;
    public void setOnItemClickListener(IRecyclerViewCallback iRecyclerViewCallback) {
       this.mItemClickCallback  = iRecyclerViewCallback;

    }

    public class Items extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAddress;

        public Items(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }

        public void updateItem(BluetoothDevice bluetoothDevice) {
            tvName.setText(bluetoothDevice.getName());
            tvAddress.setText(bluetoothDevice.getMacAddress());
        }
    }
}
