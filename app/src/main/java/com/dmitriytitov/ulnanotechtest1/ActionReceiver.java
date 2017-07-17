package com.dmitriytitov.ulnanotechtest1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Comparator;

public class ActionReceiver extends BroadcastReceiver {
    private final ArrayAdapter<RemoteDevice> arrayAdapter;

    ActionReceiver(ArrayAdapter<RemoteDevice> arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }

    public void onReceive(Context context, Intent intent){
        String action= intent.getAction();

        if(BluetoothDevice.ACTION_FOUND.equals(action)){
            BluetoothDevice device= intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);

            RemoteDevice remoteDevice = new RemoteDevice(device.getName(), device.getAddress(), rssi);

            arrayAdapter.add(remoteDevice);
            arrayAdapter.sort(new Comparator<RemoteDevice>() {
                @Override
                public int compare(RemoteDevice device1, RemoteDevice device2) {
                    return device1.compareTo(device2);
                }
            });
            arrayAdapter.notifyDataSetChanged();

        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            Toast toast = Toast.makeText(context, "Discover is complete", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
