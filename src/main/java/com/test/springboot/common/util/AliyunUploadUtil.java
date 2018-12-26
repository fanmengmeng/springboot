package com.test.springboot.common.util;

import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.ch.config.ApplicationCache;

public class AliyunUploadUtil {
	
	private static String endpoint = ApplicationCache.endpoint;
	// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维
	private static String accessKeyId = ApplicationCache.accessKeyId;
	
	private static String accessKeySecret = ApplicationCache.accessKeySecret;
	
	private static String bucketName = ApplicationCache.bucketName;
	
	private static String filepath = ApplicationCache.filepath;
	
	public static String upload(InputStream  instream, String fileName){
		try {
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			// 上传文件
			ossClient.putObject(bucketName, fileName , instream);
			// 关闭client
			ossClient.shutdown();
			return filepath + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
