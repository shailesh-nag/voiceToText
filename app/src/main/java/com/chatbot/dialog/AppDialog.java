package com.chatbot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.chatbot.R;
import com.chatbot.adapter.BluetoothDeviceAdapter;
import com.chatbot.interfaces.IDialogClick;
import com.chatbot.interfaces.IRecyclerViewCallback;
import com.chatbot.model.BluetoothDevice;

import java.util.List;

public class AppDialog {

    private static Dialog mDialog;
    private static BluetoothDeviceAdapter adapter;
    public static Dialog showDeviceDialog(Context mContext, List<BluetoothDevice> list, IDialogClick mCallback)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_list,null);
        RecyclerView rclList =view.findViewById(R.id.rcl);
        rclList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL,false));
         adapter=  new BluetoothDeviceAdapter(list);
        adapter.setOnItemClickListener(new IRecyclerViewCallback()
        {

            @Override
            public void onItemClick(int position, Object result) {
                if(mCallback!=null) mCallback.onItemSelected(position,result);
            }
        });
        rclList.setAdapter(new BluetoothDeviceAdapter(list));
         mDialog = new Dialog(mContext);
        mDialog.setContentView(view);
        mDialog.show();
        return mDialog;
    }

    public static void notifyAdapter()
    {
       if(adapter!=null) adapter.notifyDataSetChanged();
    }

    public static ArrayAdapter<String> showDialog(Context mContext, List<String> mItems)
    {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mContext);
        builderSingle.setIcon(R.drawable.ic_launcher_background);
        builderSingle.setTitle("Select One Devices:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.select_dialog_singlechoice, mItems);
//        arrayAdapter.add("Hardik");
//        arrayAdapter.add("Archit");
//        arrayAdapter.add("Jignesh");
//        arrayAdapter.add("Umang");
//        arrayAdapter.add("Gatti");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


                builderSingle.setSingleChoiceItems(arrayAdapter,0, ( dialog, which) -> {
                            String strName = arrayAdapter.getItem(which);
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(mContext);
                            builderInner.setMessage(strName);
                            builderInner.setTitle("Your Selected Item is");
                            builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builderInner.show();
                        });

        Dialog mDialog = builderSingle.create();
        builderSingle.show();

        return arrayAdapter;
    }
}
