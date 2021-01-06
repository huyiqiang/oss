package com.s3cmd.s3.object.highApiUpload;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.s3cmd.util.S3Client;

import java.util.Date;
//使用高级别 Java API（TransferManager 类）停止一周前在特定存储桶上启动的正在进行的所有分段上传，该时间可自行调整
public class HighLevelAbortMultipartUpload {

    public static void main(String[] args) {
        String bucketName = "hyqhyq";

        try {
            AmazonS3 s3Client = S3Client.getS3();
            TransferManager tm = new TransferManager(s3Client);

            // sevenDays是以毫秒为单位的七天的持续时间。
            long sevenDays = 1000 * 60 * 60 * 24 * 7;
            Date oneWeekAgo = new Date(System.currentTimeMillis() - sevenDays);
            tm.abortMultipartUploads(bucketName, oneWeekAgo);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}