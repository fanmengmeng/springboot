package com.test.springboot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ch.comm.constant.Const;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class SmsUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    private static final String SPID = "107065";
    private static final String LOGIN_NAME = "gzagrz";
    private static final String PASSWORD = "94FCDD5F006BF9FEA2DA0F2E35D01903";
    private static final String URL = "http://api.jsguodu.com:8080/sms/sendUtf.do";

    public static void sendCheckcode(String telephone, String code, String template) {
    	String content = null;
		try {
			content = String.format(template, code);
			String url = String.format(
					"%s?spId=%s&loginName=%s&password=%s&content=%s&mobiles=%s", 
					URL, SPID, LOGIN_NAME, PASSWORD, content, telephone);
			HttpResponse<String> response = Unirest.get(url).asString();
			if (null == response || !response.getBody().contains("result=1000")) {
				throw new RuntimeException("短信发送失败！" + response.getBody());
			}
			logger.info(String.format("telephone:%s, code:%s, content:%s, responseBody:%s", telephone, code, content, response.getBody()));
		} catch (Exception e) {
			LOG.remoteLog(String.format("telephone:%s, code:%s, content:%s", telephone, code, content), e, Const.REMOTE_LOG_PLATFORM_CK_LOAN_ADMIN);
		}
	}

    /*public static void main(String[] args) {
		sendCheckcode("18529179047", "蔡奕辉", "【速租】尊敬的%s用户，您的订单将在3天后到期，请及时充值。");
	}*/
}



