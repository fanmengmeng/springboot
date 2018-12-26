package com.test.springboot.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import com.test.springboot.common.constant.Const;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class LOG {
	
	public static String logEnv = Const.REMOTE_LOG_ENV_PROD;

	private static  Logger logger = LoggerFactory.getLogger("com.yllm");
	
	public static void error(String msg, Throwable e) {
		logger.error(msg, e);
	}
	
	public static void error(String msg) {
		logger.error(msg);
	} 
	
	public static void info(String msg, Throwable e) {
		logger.info(msg, e);
	}
	
	public static void info(String msg) {
		logger.info(msg);
	} 
	
	public static void debug(String msg, Throwable e) {
		logger.debug(msg, e);
	}
	
	public static void debug(String msg) {
		logger.debug(msg);
	} 
	
	public static void remoteLog(String content, Exception ex, String platform) {
		new Thread(() -> {
			try {
				error(content, ex);
				StringWriter sw = new StringWriter();   
	            PrintWriter pw = new PrintWriter(sw, true);   
	            ex.printStackTrace(pw);   
	            pw.flush();   
	            sw.flush();   
				Unirest.post("http://api.cainiaolc.com/log/collect").field("content", StringUtils.join(content, "\n", sw.toString())).field("env", logEnv).field("platform", platform).field("time", DateUtil.formatDateTime(new Date())).asString();
			} catch (UnirestException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public static void remoteLog(String content, String platform) {
		new Thread(() -> {
			try {
				error(content);
				Unirest.post("http://api.cainiaolc.com/log/collect").field("content", content).field("env", logEnv).field("platform", platform).field("time", DateUtil.formatDateTime(new Date())).asString();
			} catch (UnirestException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public static void remoteLog(Exception ex, String platform) {
		new Thread(() -> {
			try {
				error("", ex);
				StringWriter sw = new StringWriter();   
	            PrintWriter pw = new PrintWriter(sw, true);   
	            ex.printStackTrace(pw);   
	            pw.flush();   
	            sw.flush();   
				Unirest.post("http://api.cainiaolc.com/log/collect").field("content", sw.toString()).field("env", logEnv).field("platform", platform).field("time", DateUtil.formatDateTime(new Date())).asString();
			} catch (UnirestException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
}
