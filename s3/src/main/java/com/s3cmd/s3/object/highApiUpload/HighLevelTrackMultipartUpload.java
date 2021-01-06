package com.s3cmd.s3.object.highApiUpload;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.s3cmd.util.S3Client;

import java.io.File;

public class HighLevelTrackMultipartUpload {

    public static void main(String[] args) throws Exception {
        String bucketName = "hyqhyq";
        String keyName = "1234.zip";
        String filePath = "E:\\AI\\大数据AI软件\\1234.zip";
        try {
            TransferManager tm = new TransferManager(S3Client.getS3());
            PutObjectRequest request = new PutObjectRequest(bucketName, keyName, new File(filePath));
            // 若要在传输字节时接收通知，在请求中添加一个ProgressListener。
            request.setGeneralProgressListener(new ProgressListener() {
                public void progressChanged(ProgressEvent progressEvent) {
                    System.out.println("Transferred bytes: " + progressEvent.getBytesTransferred());
                }
            });
            // TransferManager异步处理所有传输，因此这个调用立即返回。
            Upload upload = tm.upload(request);
            // 可以选择等待上传完成后再继续。
            upload.waitForCompletion();
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}