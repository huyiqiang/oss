package com.s3cmd.s3.bucket;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.s3cmd.util.S3Client;

import java.util.List;

public class CreateBucketTest {

        AWSCredentials credentials = null;
        public AmazonS3 getS3Client(String access_key, String secret_key,
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

        // ============================ bucket ================================
        public void testCreateBucket(AmazonS3 s3Client, String bucketName) {
            System.out.println("======================创建 Bucket==========================");
            Bucket bucket = s3Client.createBucket(bucketName);
            System.out.println(bucket);
            System.out.println("创建 Bucket : " + bucket.getName());
            System.out.println("======================创建 Bucket==========================");
        }

    public void testListBuckets(AmazonS3 s3Client) {
        System.out.println("======================枚举 Buckets==========================");
        List<Bucket> buckets = s3Client.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(String.format("%s - %s - %s", bucket.getName(), bucket.getOwner(), bucket.getCreationDate()));
        }
        System.out.println("======================枚举 Buckets==========================");
    }


        public static void main(String args[]){
            String access_key = "0A52F27732A14E82BFB03744C6324AC55773";
            String secret_key = "E7C1FC19586040D58C0AF8E494FC58EE1561";
            String endpoint = "http://s3.dev.com:8080";
            CreateBucketTest createBucketTest =new CreateBucketTest();
           AmazonS3 s3Client = createBucketTest.getS3Client(access_key, secret_key, endpoint);
//            AmazonS3 s3Client = S3Client.getS3();
            String bucketName = "demo";
           createBucketTest.testCreateBucket(s3Client,bucketName);

            System.out.println("创建后查询");
            createBucketTest.testListBuckets(s3Client);

        }
}
