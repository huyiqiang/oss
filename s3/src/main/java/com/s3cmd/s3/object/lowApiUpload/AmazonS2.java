package com.s3cmd.s3.object.lowApiUpload;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.s3cmd.util.S3Client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;


/*使用低级别 Java 类上传文件*/
/*1.使用AmazonS3Client.initiateMultipartUpload() 方法初始化分段上传,并传入InitiateMultipartUploadRequest对象。
2.保存AmazonS3Client.initiateMultipartUpload()方法返回的上传ID。随后的每个分段上传操作提供此上传ID。
3.上传对象的分段。对于每个分段,调用 AmazonS3Client.uploadPart() 方法。使用 UploadPartRequest 对象提供分段上传信息。
4.对于每个分段，在列表中保存来自 AmazonS3Client.uploadPart() 方法的响应的 ETag。您使用 ETag 值完成分段上传。
5.调用 AmazonS3Client.completeMultipartUpload() 方法来完成分段上传。*/

public class AmazonS2 {
    private static BufferedWriter bufferedWriter;
    private static FileWriter fileWriter;

    public static void partUpload(String bucketName, String keyName, String filePath) {
        AmazonS3 s3Client = S3Client.getS3();
        File file = new File(filePath);
        long contentLength = file.length();
        long partSize = 15 * 1024 * 1024; // Set part size to 5 MB.
        try {
            /*创建一个ETag对象列表。将为每个上传的对象部分检索ETags，
            然后，在每个单独的部分被上传之后，将etag列表传递给请求以完成上传。*/
            List<PartETag> partETags = new ArrayList<PartETag>();
            // 初始化多部分上传
            InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, keyName);
            InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);

            // 上传文件部分
            long filePosition = 0;
            for (int i = 1; filePosition < contentLength; i++) {
                // 因为最后一个分片可能小于5mb，所以根据需要调整分片大小
                partSize = Math.min(partSize, (contentLength - filePosition));
                // 创建上传部件的请求.
                UploadPartRequest uploadRequest = new UploadPartRequest()
                        .withBucketName(bucketName)
                        .withKey(keyName)
                        .withUploadId(initResponse.getUploadId())
                        .withPartNumber(i)
                        .withFileOffset(filePosition)
                        .withFile(file)
                        .withPartSize(partSize);

                // 上传该部分并将响应的ETag添加到我们的列表中
                UploadPartResult uploadResult = s3Client.uploadPart(uploadRequest);
                partETags.add(uploadResult.getPartETag());
                filePosition += partSize;
                //写谁
                File file1 = new File("E:\\AI", "11.txt");
                //谁写
                fileWriter = new FileWriter(file1, true);
                //高效的写
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("传输字节数: " + filePosition);
                bufferedWriter.newLine();
                bufferedWriter.write("已分片数" + i);
                bufferedWriter.newLine();
                bufferedWriter.write("*****************");
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            // 完成多部分上传.
            CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName, keyName,
                    initResponse.getUploadId(), partETags);
            s3Client.completeMultipartUpload(compRequest);
        } catch (AmazonServiceException e) {
            //调用被成功传输，但是Amazon S3不能处理它，所以它返回一个错误响应
            e.printStackTrace();
        } catch (SdkClientException e) {
            // 无法联系到Amazon S3获取响应，或者客户端无法解析来自Amazon S3的响应。
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        partUpload("hyqhyq", "1234.zip", "E:\\AI\\大数据AI软件\\1235.zip");
    }
}