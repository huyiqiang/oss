package com.s3cmd.s3.object.lowApiUpload;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.s3cmd.util.S3Client;

import java.util.List;
/* 通过调用 AmazonS3Client.abortMultipartUpload() 方法停止正在进行的分段上传。
  此方法将删除所有上传到 Amazon S3 的分段并释放资源
 可以停止在特定时间之前启动的正在进行的分段上传，而不是单独停止分段上传。
对于中止您启动但未完成或已中止的分段上传，此清除操作很有用*/
public class LowLevelAbortMultipartUpload {

    public static void main(String[] args) {
        String bucketName = "hyqhyq";

        try {
            AmazonS3 s3Client = S3Client.getS3();

            // 查找所有正在进行的多部分上传
            ListMultipartUploadsRequest allMultipartUploadsRequest = new ListMultipartUploadsRequest(bucketName);
            MultipartUploadListing multipartUploadListing = s3Client.listMultipartUploads(allMultipartUploadsRequest);

            List<MultipartUpload> uploads = multipartUploadListing.getMultipartUploads();
            System.out.println("*********在删除之前********, " + uploads.size() + " multipart uploads in progress.");

            // 中止每个上传.
            for (MultipartUpload u : uploads) {
                System.out.println("*********正在上传***********: Key = \"" + u.getKey() + "\", id = " + u.getUploadId());
                s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, u.getKey(), u.getUploadId()));
                System.out.println("*******已删除的上传*********: Key = \"" + u.getKey() + "\", id = " + u.getUploadId());
            }

            // 验证所有正在进行的多部分上传都已被中止.
            multipartUploadListing = s3Client.listMultipartUploads(allMultipartUploadsRequest);
            uploads = multipartUploadListing.getMultipartUploads();
            System.out.println("********在中止上传之后***********, " + uploads.size() + " multipart uploads in progress.");
        } catch (AmazonServiceException e) {
            // 调用被成功传输，但是Amazon S3不能处理它，所以它返回一个错误响应。
            e.printStackTrace();
        } catch (Exception e) {
            // 无法联系到Amazon S3获取响应，或者客户端无法解析来自Amazon S3的响应。
            e.printStackTrace();
        }
    }
}