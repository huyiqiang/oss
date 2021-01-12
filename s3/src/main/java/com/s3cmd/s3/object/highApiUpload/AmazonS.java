package com.s3cmd.s3.object.highApiUpload;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
        String access_key = "6DEB63B3F2314C57A5377C29D4782E5D1533";
        String secret_key = "261845A5C87543449964EFD5ED575AD41512";
        String endpoint = "http://s3.dev.com:8080";
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, new File(filePath));
        //- 监听上传过程
      /*  request.setGeneralProgressListener(new ProgressListener() {
            public void progressChanged(ProgressEvent progressEvent) {
                System.out.println("传输字节数: "+progressEvent.getBytesTransferred());
            }
        });*/
        TransferManager tm = new TransferManager(S3Client.getS3Client(access_key,secret_key,endpoint));
        ObjectMetadata objectMetadata =new ObjectMetadata();
        objectMetadata.setHeader("x-amz-tagging","mm=bb");
        request.setMetadata(objectMetadata);
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
        partUpload("hyqhyq","pip-master.zip","E:\\AI\\大数据AI软件\\pip-master.zip");
    }

}
