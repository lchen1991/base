package com.lchen.cn;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.google.gson.Gson;

/**
 * This sample demonstrates how to upload/download an object to/from 
 * Aliyun OSS using the OSS SDK for Java.
 */
public class SimpleGetObjectSample {
    
	 private static String endpoint = "oss-cn-beijing.aliyuncs.com";
	    private static String accessKeyId = "ubuSFRjBoEkKJgfC";
	    private static String accessKeySecret = "SIVkFeOyOFGWZ9syuDCrni4mxWBavb";
	    
	    private static String bucketName = "bttiantang";
	    private static String key = "homePage";
    
    public static void main(String[] args) throws IOException {
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        
        try {
            
            /**
             * Note that there are two ways of uploading an object to your bucket, the one 
             * by specifying an input stream as content source, the other by specifying a file.
             */
            
            /*
             * Upload an object to your bucket from an input stream
             */
//            System.out.println("Uploading a new object to OSS from an input stream\n");
//            String content = "Thank you for using Aliyun Object Storage Service";
//            client.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));
            
            /*
             * Upload an object to your bucket from a file
             */
        	
//        	HtmlParseFromBttt htmlParseFromBttt = new HtmlParseFromBttt();
//        	
//        	BtHomePageInfo data = htmlParseFromBttt.getHtmlResourcePage("http://www.bttiantang.com/");
//        	Gson gson =new Gson();
//        	
//        	System.out.println("Uploading a new object to OSS from a file\n");
//            ObjectMetadata  objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentType("application/json");
//            client.putObject(new PutObjectRequest(bucketName, key, createSampleFile(key,gson.toJson(data)),objectMetadata));
//            
//            
//            List<MovieClassify> ms = data.getMovieClassifys();
//            ms.remove(ms.size()-1);
//            
//        	for (MovieClassify movieClassify:ms) {
//        		List<FilmInfo> filmInfos = htmlParseFromBttt.getFilmListInfo(movieClassify.getClassifyHref(), null);
//        		 client.putObject(new PutObjectRequest(bucketName, 
//        				 key+"&"+movieClassify.getClassify(), createSampleFile(key+"&"+movieClassify.getClassify(),gson.toJson(filmInfos)),objectMetadata));
//			
//        		 System.out.println("Downloading an object");
//                 OSSObject object = client.getObject(new GetObjectRequest(bucketName, key+"&"+movieClassify.getClassify()));
//                 System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
//                 displayTextInputStream(object.getObjectContent());
//                
//        	}
            
            /*
             * Download an object from your bucket
             */
            System.out.println("Downloading an object");
            OSSObject object = client.getObject(new GetObjectRequest(bucketName, "homePage&2015"));
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            displayTextInputStream(object.getObjectContent());
            
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
    
    private static File createSampleFile(String fileName,String data) throws IOException {
        File file = File.createTempFile(fileName, ".json");
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
