package com.test.springboot.common.util;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ch.comm.constant.Const;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class Utils {
	
	public static int getAgeByCardno(String cardno) {
		if (StringUtils.isBlank(cardno) || cardno.length() != 18) {
			return 0;
		}
		String birthYear = cardno.substring(6, 10);
		String birthDate = cardno.substring(10, 14);
		String year = DateFormatUtils.format(System.currentTimeMillis(), "yyyy");
		String date = DateFormatUtils.format(System.currentTimeMillis(), "MMdd");
		int age = Integer.parseInt(year) - Integer.parseInt(birthYear);
		if (Integer.parseInt(birthDate) > Integer.parseInt(date)) {
			age--;
		}
		return age;
	}
	
	public static String getSexByCardno(String cardno) {
		if (StringUtils.isBlank(cardno) || cardno.length() != 18) {
			return "";
		}
		String sex = "";
		if (cardno.length() == 18) {
			String i = cardno.substring(16, 17);
			if (0 == Integer.parseInt(i) % 2) {
				sex = "女";
			} else {
				sex = "男";
			}
		}
		return sex;
	}
	
	public static String getAddressByLogLat(String log, String lat ){  
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项) 
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+log+"&type=100";  
        String res = "";     
        try {     
            URL url = new URL(urlString);    
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();    
            conn.setDoOutput(true);    
            conn.setRequestMethod("POST");    
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));    
            String line;    
           while ((line = in.readLine()) != null) {    
               res += line+"\n";    
         }    
            in.close();    
        } catch (Exception e) {    
            LOG.remoteLog("error in wapaction,and e is ", e, Const.REMOTE_LOG_PLATFORM_CK_LOAN_ADMIN);
        }   
//        System.out.println(res);  
        JSONObject jsonObject = JSONObject.parseObject(res);
        JSONArray jsonArray = jsonObject.getJSONArray("addrList");
        JSONObject j_2 = jsonArray.getJSONObject(0);
        String allAdd = j_2.getString("admName");  
        String name = j_2.getString("name");  
        allAdd = allAdd.replace(",", "");
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(allAdd)) {
			sb.append(allAdd);
		}
        if (StringUtils.isNotBlank(name)) {
			sb.append(name);
		}
        return sb.toString();    
    }  
	
	public static String getAddressByIp(String ip) {
		HttpResponse<String> body;
		try {
			body = Unirest.post("http://ip.taobao.com/service/getIpInfo.php").field("ip", ip).asString();
			System.out.println(body.getBody());
			JSONObject json = JSONObject.parseObject(body.getBody());
			JSONObject data = json.getJSONObject("data");
			String country = data.getString("country");
			String region = data.getString("region");
			String city = data.getString("city");
			String isp = data.getString("isp");
			if (null == country) {
				country = "";
			}
			region = null == region ? "" : region + "省";
			city = null == city ? "" : city + "市";
			if (null == isp) {
				isp = "";
			}
			StringBuffer sb = new StringBuffer();
			sb.append(country).append(region).append(city).append("·").append(isp);
			return sb.toString();
		} catch (UnirestException e) {
			LOG.remoteLog(e, Const.REMOTE_LOG_PLATFORM_CK_LOAN_ADMIN);
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getAddressByLogLat("113.919946", "22.53375"));  
	}
}
