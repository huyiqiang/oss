package com.s3cmd.s3.v6v4;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.IOException;
import java.net.InetAddress;

public class DualStackEndpoints {
    public static AmazonS3 getS3(){
        String accessKey = "C2F67720EB474012B1401FA096360F016474";
        String secretKey = "8A91DD6EA38840A2AA694487670A6CC66141";
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        clientConfig.setSignerOverride("S3SignerType"); // 用于兼容 ceph API
        AmazonS3 s3Client = new AmazonS3Client(credentials, clientConfig);
        s3Client.setEndpoint("obs-hazz.woyun.cn");
        return s3Client;
    }
    public static void main(String[] args) throws IOException {
//        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("java.net.preferIPv6Addresses", "true");
//       Socket socket = new Socket("2408:8720:804:2000:0:0:0:1", 80);
        String bucketName = "aaa";
        try {
            AmazonS3 s3Client = getS3();
            System.out.println("***********************************");
            System.out.println("bucket对象: "+s3Client.listObjects(bucketName).getObjectSummaries());
            System.out.println("对象存储ip地址: "+InetAddress.getByName("obs-hazz.woyun.cn"));
            System.out.println("本地使用ip地址: "+InetAddress.getLocalHost());
            System.out.println("***********************************");
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
