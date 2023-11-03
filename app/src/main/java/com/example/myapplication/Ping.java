package com.example.myapplication;

import java.io.IOException;
import java.net.InetAddress;

public class Ping {

    public static boolean isHostReachable(String hostName, int timeout) {
        try {
            InetAddress address = InetAddress.getByName(hostName);
            return address.isReachable(timeout);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
