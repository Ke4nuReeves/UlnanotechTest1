package com.dmitriytitov.ulnanotechtest1;

import android.support.annotation.NonNull;

/**
 * Created by Dmitriy Titov on 17.07.2017.
 */

public class RemoteDevice implements Comparable{
    private String name;
    private String address;
    private int rssi;

    public RemoteDevice(String name, String address, int rssi) {
        this.name = name;
        this.address = address;
        this.rssi = rssi;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        RemoteDevice anotherDevice = (RemoteDevice) o;
        if (this.rssi < anotherDevice.getRssi()) {
            return 1;
        } else if (this.rssi > anotherDevice.getRssi()) {
            return -1;
        } else {
            return 0;
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getRssi() {
        return rssi;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n"
                + "Address: " + address + "\n"
                + "RSSI: " + rssi;
    }
}
