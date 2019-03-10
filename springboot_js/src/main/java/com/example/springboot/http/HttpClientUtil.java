package com.example.springboot.http;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * HttpClient请求处理工具类
 * @author 
 */
public class HttpClientUtil {

	private static Logger log = Logger.getLogger(HttpClientUtil.class);

	private static String httpUrl = "";
	/**
	 * 向指定URL提交请求，传递指定的参数，参数格式可以为JSON
	 * @param url
	 * @return
	 */
	public static String post(String url, String param){
		
		String resp = "";
		try
		{
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost post2 = new HttpPost(url);
			StringEntity strEntity = new StringEntity(param, "UTF-8");
			post2.setEntity(strEntity);
			HttpResponse response = httpClient.execute(post2);
			HttpEntity entity = response.getEntity();
			StringBuffer respStr = new StringBuffer("");
			if (entity != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
				String temp = null;
				while ((temp = bufferedReader.readLine()) != null) {
					respStr.append(temp);
				}
			}
			resp = respStr.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return resp;
	}

	public static HttpEntity getEntityByPost(String url, String param) throws IOException {
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost post2 = new HttpPost(url);
			StringEntity strEntity = new StringEntity(param, "UTF-8");
			post2.setEntity(strEntity);
			HttpResponse response = httpClient.execute(post2);
			HttpEntity entity = response.getEntity();
			return entity;
	}

	/**
	 * Get请求
	 * @param url url地址()
	 * @param params 请求参数
	 * @return
	 */
	public static String getRequest(String url, Map<String, Object> params) {
		
		StringBuffer sb = new StringBuffer();
		String lastUrl = httpUrl+url;
		if (params != null && params.size() > 0) {
				Iterator<String> iter = params.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					sb.append(key);
					sb.append("=");
					sb.append(params.get(key));
					sb.append("&");
				}
				
				String param = sb.toString();
				param = param.substring(0,param.length()-1);
			    lastUrl +="?"+param;	
		}
		
		log.debug("lastUrl:"+lastUrl);
		
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

		HttpGet httpGet = new HttpGet(lastUrl);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();// 设置请求和传输超时时间
	
		httpGet.setConfig(requestConfig);

		log.debug(httpGet.getRequestLine());
		
		String json = "";
		try {
			// 执行get请求
			HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
			// 获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			
			// 响应状态
			log.debug("status:" + httpResponse.getStatusLine());
			// 判断响应实体是否为空
			if (entity != null) {
				//把原Http实体的内容缓冲到了内存中
                entity = new BufferedHttpEntity(entity);
				json = EntityUtils.toString(entity,"UTF-8");
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		log.debug("json:"+ json);
		return json;
	}
	
	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}

	/**
	 * Post请求
	 * @param url url地址(info/addc.do)
	 * @param params 请求参数
	 * @return
	 */
	public static String postRequest(String url, Map<String, String> params) {
		
		String lastUrl = httpUrl+url;
		
		log.debug("lastUrl:"+lastUrl);
		
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(lastUrl);
		
		log.debug(httpPost.getRequestLine());
		
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();// 设置请求和传输超时时间
		httpPost.setConfig(requestConfig);
		
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			Iterator<String> iter = params.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
		}

		UrlEncodedFormEntity entity;
		String json = "";
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
						
			httpPost.setEntity(entity);
			HttpResponse httpResponse;
			// post请求
			httpResponse = closeableHttpClient.execute(httpPost);

			// 响应状态
			log.debug("status:" + httpResponse.getStatusLine());
						
			// getEntity()
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				
				httpEntity = new BufferedHttpEntity(httpEntity);
				
				json = EntityUtils.toString(httpEntity, "UTF-8");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try {
				// 释放资源
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.debug("json:"+ json);
		return json;
	}
	
	/**
	 * Post请求
	 * 
	 * @param url
	 *            url地址(info/addc.do)
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static String postRequestA(String url, Map<String, String> params)
	{
		
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(url);
		
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();// 设置请求和传输超时时间
		httpPost.setConfig(requestConfig);
		
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0)
		{
			Iterator<String> iter = params.keySet().iterator();
			while (iter.hasNext())
			{
				String key = iter.next();
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
		}
		
		UrlEncodedFormEntity entity;
		String json = "";
		try
		{
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			
			httpPost.setEntity(entity);
			HttpResponse httpResponse;
			// post请求
			httpResponse = closeableHttpClient.execute(httpPost);
			
			// getEntity()
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null)
			{
				httpEntity = new BufferedHttpEntity(httpEntity);
				json = EntityUtils.toString(httpEntity, "UTF-8");
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				// 释放资源
				closeableHttpClient.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return json;
	}
}
