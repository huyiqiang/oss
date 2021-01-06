package com.s3cmd.s3.object.highApiUpload;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.s3cmd.util.S3Client;

import java.io.File;

//使用高级别分段上传 API (TransferManager 类) 上传对象
public class AmazonS {
    /**
     * 大文件分片上传
     *
     * @param bucketName s3的buckename
     * @param fileName 上传创建的文件名称
     * @param filePath 源文件磁盘路径
     */
    public static void partUpload(String bucketName, String fileName, String filePath){

        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, new File(filePath));
        //- 监听上传过程
        request.setGeneralProgressListener(new ProgressListener() {
            public void progressChanged(ProgressEvent progressEvent) {
                System.out.println("传输字节数: "+progressEvent.getBytesTransferred());
            }
        });
        TransferManager tm = new TransferManager(S3Client.getS3());
        Upload upload = tm.upload(request);
        try {
            //- 可以阻止并等待上传完成
            upload.waitForCompletion();
        } catch (AmazonClientException amazonClientException) {
            amazonClientException.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //- 释放资源
        tm.shutdownNow();
    }

    public static void main(String[] args) {
        partUpload("hyqhyq","123.zip","E:\\AI\\大数据AI软件\\123\\123.zip");
    }

}
