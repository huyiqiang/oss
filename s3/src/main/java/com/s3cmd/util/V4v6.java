package com.s3cmd.util;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class V4v6 {
    public static void main(String[] args) throws IOException {
//      System.setProperty("java.net.preferIPv4Stack", "true");
//      System.setProperty("java.net.preferIPv6Addresses", "true");
     Socket socket = new Socket("s3.dev.cn", 8080);
        System.out.println(InetAddress.getByName("s3.dev.cn"));
        System.out.println(InetAddress.getLocalHost());
    }
}
