package com.dmitriytitov.ulnanotechtest1;

import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter bAdapter;
    private ArrayAdapter<RemoteDevice> arrayAdapter;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bAdapter = BluetoothAdapter.getDefaultAdapter();

        ListView deviceList = (ListView) findViewById(R.id.device_list);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceList.setAdapter(arrayAdapter);

        Button refreshButton = (Button) findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btIsReady()) {
                    if (bAdapter.isDiscovering()) {
                        Toast toast = Toast.makeText(v.getContext(), "Wait for the end of the discover", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        discoverDevices();
                    }
                }
            }
        });

        receiver = new ActionReceiver(arrayAdapter);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);

        if (btIsReady()) {
            discoverDevices();
        }
    }


    private boolean btIsReady() {
        if (bAdapter != null) {
            if (bAdapter.isEnabled()) {
                return true;
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                return false;
            }
        } else {
            Toast toast = Toast.makeText(this, "Device have not bluetooth module", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }


    private void discoverDevices() {
        arrayAdapter.clear();
        bAdapter.startDiscovery();
        Toast toast = Toast.makeText(this, "Discover started", Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (btIsReady()) {
                discoverDevices();
            }
        } else {
            Toast toast = Toast.makeText(this, "Bluetooth isn't ready", Toast.LENGTH_LONG);
            toast.show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
