package com.test.springboot.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONObject;

public class HttpUtils
{
    /**
     * get方法请求的超时
     */
    private static final int GETMETHOD_REQUEST_TIMEOUT = 10000;

    /**
     * http连接的超时
     */
    private static final String HTTP_CONNECTION_TIMEOUT = "http_client_timeout";

    /**
     * 发送HTTP消息
     * @param body String类型。表示需要发送出去的XML报文体。
     * @param sendUrl String类型。目的URL。
     * @return String 表示对方返回的报文。如果发送成功，则返回对方返回的报文；如果发送失败，则返回null
     * @throws IOException ioException
     */
    public String sendHttpReq(String body, String sendUrl) throws IOException
    {
        URL urlcon = null;
        HttpURLConnection con = null;
        PrintWriter out = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        InputStreamReader in = null;
        try
        {
            urlcon = new URL(sendUrl);
            con = (HttpURLConnection) urlcon.openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            con.setRequestProperty("Content-Type", "text/xml");
            con.setRequestProperty("Accept", "text/xml");
            con.setRequestProperty("version", "100");
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();
            
            //设置超时时间
            //setClientTimeout();

            bw =
                new BufferedWriter(
                    new OutputStreamWriter(con.getOutputStream()));
            out = new PrintWriter(bw);

            out.print(body);
            out.flush();
            out.close();

            StringBuffer sb = new StringBuffer();

            in = new InputStreamReader(con.getInputStream());

            br = new BufferedReader(in);
            String line = br.readLine();

            //modify by LiJinfeng.
            createSB(br, sb, line);

            return sb.toString();
        }
        catch (IOException e)
        {
            
            //modify by LiJinfeng
            closeObject(con, br, bw, in);
            closeOut(out);
            return null;
        }
        catch (Exception ex)
        {
           
            in.close();
            br.close();
            out.close();
            bw.close();
            con.disconnect();
            return null;
        }
        finally
        {
            closeObject(con, br, bw, in);
            closeOut(out);
        }
    }

    private void createSB(BufferedReader br, StringBuffer sb, String line)
        throws IOException
    {
        while (null != line)
        {
            sb.append(line);
            line = br.readLine();
            if (null != line)
            {
                sb.append(line);
            }
        }
    }

    private void closeOut(PrintWriter out)
    {
        if (null != out)
        {
            out.close();
        }
    }

    private void closeObject(HttpURLConnection con, BufferedReader br,
            BufferedWriter bw, InputStreamReader in) throws IOException
    {
        if (null != con)
        {
            con.disconnect();
        }
        if (null != br)
        {
            br.close();
        }
        if (null != bw)
        {
            bw.close();
        }
        if (null != in)
        {
            in.close();
        }
    }

    /**
     * 设置client超时时间
     */
    public static void setClientTimeout()
    {
        System.setProperty("sun.net.client.defaultConnectTimeout",
            Long.toString(GETMETHOD_REQUEST_TIMEOUT));
        System.setProperty("sun.net.client.defaultReadTimeout",
            Long.toString(GETMETHOD_REQUEST_TIMEOUT));
    }
    
    /**
     * post请求方法
     * 
     * @param url
     * @return
     * @throws IOException
     */
     public static String postMethod(String url, NameValuePair[] param)
             throws IOException
     {
         HttpClient httpClient = new HttpClient();

         // 设置httpClient的字符集
         httpClient.getParams().setParameter(
                 HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

         // 设置httpClient的链接超时
         httpClient.getHttpConnectionManager().getParams().setSoTimeout(
                 Integer.parseInt("5000"));

         PostMethod postMethod = new PostMethod(url);

         // 设置请求的字符集
         postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
         
         
         postMethod.setRequestHeader("Accept-Language", "zh-cn");
         
         postMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");

         // 设置post方法请求失败，自动重试三次
         postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                 new DefaultHttpMethodRetryHandler());
         postMethod.setRequestHeader("Connection", "close");

         // 设置post方法的请求超时
         postMethod.getParams()
                 .setParameter(HttpMethodParams.SO_TIMEOUT, 5000);

         try
         {

             postMethod.setRequestBody(param);

             int statusCode = httpClient.executeMethod(postMethod);

             // post方法执行失败
             if (HttpStatus.SC_OK != statusCode)
             {

                 return null;
             }

             // post方法执行成功,获取并返回返回值
             String responseBody = postMethod.getResponseBodyAsString();

             return responseBody;
         }
         // 发生HTTP异常
         catch (HttpException e)
         {

             return null;
         }
         // 发生IO异常
         catch (IOException e)
         {

             return null;
         }
         // 发生未知异常
         catch (Exception e)
         {

             return null;
         }
         finally
         {
             postMethod.releaseConnection();
         }
     }
     
     /**
      * 上传文件到服务器
      * @param fileUrl 上传文件路径
      * @param url	上传服务器
      * @return 返回上传文件服务器内容
      */
     public String postUpload(File fileUrl,String url)
     {
     	int timeout=20000;
     	
         String responseBody = "";
         HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setProxy("10.121.2.5",3128);
         PostMethod postmethod = new PostMethod(url);
      // 设置post方法的请求超时
 		postmethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
 		postmethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
         try {
 			Part[] parts ={ new FilePart("Fdata", fileUrl) };
 			postmethod.setRequestEntity(new MultipartRequestEntity(parts,postmethod.getParams()));
 			int statusCode =  httpClient.executeMethod(postmethod);
 			if (HttpStatus.SC_OK != statusCode)
 			{
 			    return "ERROR:"+statusCode;
 			}
 			responseBody = inputStream2String(postmethod.getResponseBodyAsStream());
 			
 		} catch (FileNotFoundException e) {
 			responseBody="ERROR:没有发现要上传的图片";
 		} catch (HttpException e) {
 			responseBody="ERROR:网络协议异常";
 		} catch (IOException e) {
 			e.printStackTrace();
 			responseBody="ERROR:服务器没有响应";
 		}finally{
 			postmethod.releaseConnection();
 		}
        
         return responseBody;
     }
     
 	public static String inputStream2String(InputStream is) throws IOException{ 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        int   i=-1; 
        while((i=is.read())!=-1){ 
        baos.write(i); 
        } 
       return   baos.toString(); 
	} 
	
 	
    
 	
 	public static String sendHttpsReq(String xml,String url){
		HttpClient httpclient=new HttpClient();
		httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

		PostMethod postmethod=new PostMethod(url);
		// 设置httpClient的字符集
		postmethod.addRequestHeader("Content-Type","text/xml; charset=utf-8");
		postmethod.setRequestHeader("Accept-Language", "zh-cn");
		postmethod.setRequestHeader("Accept", "text/xml");
		postmethod.setRequestHeader("Connection", "close");
		// 设置post方法的请求超时
		postmethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 20000);
		//设置超时时间
        setClientTimeout();
		// 设置post方法请求失败，自动重试
		postmethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		postmethod.setRequestBody(xml);
		
		String retValue = "";
		try {
			int statusCode=httpclient.executeMethod(postmethod);
			retValue = new String(postmethod.getResponseBodyAsString());
			
		} catch (HttpException e) {
			retValue="ERROR:网络协议异常";
		} catch (IOException e) {
			retValue="ERROR:服务器没有响应";
		}finally{
			postmethod.releaseConnection();
		}
		return retValue;
	}
 	
 	public static String sendHttpPost(String url){
 		String result = "";
		HttpClient client = new HttpClient(); 
		HttpMethod method = new PostMethod(url); 
		try {
			client.executeMethod(method);
			result = method.getResponseBodyAsString();
		} catch (HttpException e) {
			result ="ERROR:网络协议异常";
		} catch (IOException e) {
			result = "ERROR:服务器没有响应";
		} 
		return result;
 	}
 	
	public static JSONObject post(JSONObject param, String urlString){
		JSONObject resultJson = new JSONObject();
		URL url;
		HttpURLConnection httpConn;
		OutputStream out;
		InputStreamReader isr;
		BufferedReader br;
		String lineStr;
		try {
			url = new URL(urlString);
			httpConn = (HttpURLConnection) url.openConnection();
			//超时时间
			httpConn.setConnectTimeout(10000);
			httpConn.setReadTimeout(10000);
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			out = httpConn.getOutputStream();
			byte[] buf = param.toString().getBytes("utf-8");
			out.write(buf);
			isr = new InputStreamReader(httpConn.getInputStream(), "utf-8");
			br = new BufferedReader(isr);
			lineStr = "";
			String result = "";
			while ((lineStr = br.readLine()) != null) {
				result += lineStr;
			}
//			System.out.println("请求结果:"+result);
			br.close();
			out.close();
			httpConn.disconnect();
			resultJson = JSONObject.parseObject(result);
			return resultJson;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

