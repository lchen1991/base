package com.lchen.cn.aliyunoss;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;

/**
 * This sample demonstrates how to create an empty folder under 
 * specfied bucket to Aliyun OSS using the OSS SDK for Java.
 */
public class CreateFolderSample {
    
	private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "ubuSFRjBoEkKJgfC";
    private static String accessKeySecret = "SIVkFeOyOFGWZ9syuDCrni4mxWBavb";
    
    private static String bucketName = "bttiantang";

    public static void main(String[] args) throws IOException {        
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        
        try {
            /*
             * Create an empty folder without request body, note that the key must be 
             * suffixed with a slash
             */
            final String keySuffixWithSlash = "MyObjectKey/";
            client.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[9999]));
            System.out.println("Creating an empty folder " + keySuffixWithSlash + "\n");
            
            /*
             * Verify whether the size of the empty folder is zero 
             */
            OSSObject object = client.getObject(bucketName, keySuffixWithSlash);
            System.out.println("Size of the empty folder '" + object.getKey() + "' is " + 
                    object.getObjectMetadata().getContentLength());
            object.getObjectContent().close();

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
    }
}
