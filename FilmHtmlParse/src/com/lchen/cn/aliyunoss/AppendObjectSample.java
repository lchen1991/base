package com.lchen.cn.aliyunoss;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.OSSObject;

/**
 * This sample demonstrates how to upload an object by append mode 
 * to Aliyun OSS using the OSS SDK for Java.
 */
public class AppendObjectSample {
    
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "ubuSFRjBoEkKJgfC";
    private static String accessKeySecret = "SIVkFeOyOFGWZ9syuDCrni4mxWBavb";
    
    private static String bucketName = "bttiantang";
//    private static String homePage = "homePage";
//    private static String param = "";
    private static String key = "homePage";
    
    public static void main(String[] args) throws IOException {        
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        
        try {            
            /*
             * Append an object from specfied input stream, keep in mind that
             * position should be set to zero at first time.
             */
            String content = "Thank you for using Aliyun Object Storage Service";
            InputStream instream = new ByteArrayInputStream(content.getBytes());
            Long firstPosition = 0L;
            System.out.println("Begin to append object at position(" + firstPosition + ")");
            AppendObjectResult appendObjectResult = client.appendObject(
                    new AppendObjectRequest(bucketName, key, instream).withPosition(0L));
            System.out.println("\tNext position=" + appendObjectResult.getNextPosition() + 
                    ", CRC64=" + appendObjectResult.getObjectCRC64() + "\n");
            
            /*
             * Continue to append the object from specfied file descriptor at last position
             */
            
            Long nextPosition = appendObjectResult.getNextPosition();
            System.out.println("Continue to append object at last position(" + nextPosition + "):");
            appendObjectResult = client.appendObject(
                    new AppendObjectRequest(bucketName, key, createTempFile(key,"123456222222222"))
                    .withPosition(nextPosition));
            System.out.println("\tNext position=" + appendObjectResult.getNextPosition() + 
                    ", CRC64=" + appendObjectResult.getObjectCRC64());
            
            /*
             * View object type of the appendable object
             */
            OSSObject object = client.getObject(bucketName, key);
            System.out.println("\tObject type=" + object.getObjectMetadata().getObjectType() + "\n");
            // Do not forget to close object input stream if not use it any more
            displayTextInputStream(object.getObjectContent());
            object.getObjectContent().close();
            
            /*
             * Delete the appendable object
             */
            System.out.println("Deleting an appendable object");
            //client.deleteObject(bucketName, key);
            
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
    
    private static File createTempFile(String param,String data) throws IOException {
        File file = File.createTempFile(key, ".json");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write(data);
        writer.close();

        return file;
    }
    
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("\t" + line);
        }
        System.out.println();
        reader.close();
    }
}
