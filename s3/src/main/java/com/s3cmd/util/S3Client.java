package com.s3cmd.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class S3Client {
    public static AmazonS3 getS3(){
        String accessKey = "8u7hbtovl4s2equs2dkn";
        String secretKey = "8u7hbnowgpnves8k3s63";
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        clientConfig.setSignerOverride("S3SignerType"); // 用于兼容 ceph API
        AmazonS3 s3Client = new AmazonS3Client(credentials, clientConfig);
        s3Client.setEndpoint("s3.test.cn");
        return s3Client;
    }

    static AWSCredentials credentials = null;
    public static AmazonS3 getS3Client(String access_key, String secret_key,
                                String endpoint) {
        ClientConfiguration conf = null;
        if (conf == null) {
            conf = new ClientConfiguration();
            credentials = new BasicAWSCredentials(access_key, secret_key);
        }
        AmazonS3 s3 = new AmazonS3Client(credentials, conf);
        s3.setRegion(Region.getRegion(Regions.CN_NORTH_1));
        if (endpoint != null)
            s3.setEndpoint(endpoint);
        return s3;
    }
}
