package com.example.oauthtest.util;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	
	public static String queryStringForPost(String url, List<NameValuePair> params) {
		
		HttpPost request = HttpUtil.getHttpPost(url);
		String result = "";
		try {
			System.out.println("5555555555555");
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// �����Ӧ����
			System.out.println("6666666666666");
			HttpResponse response = HttpUtil.getHttpResponse(request);
			System.out.println("999999");
			System.out.println("response.getStatusLine().getStatusCode() :" + response.getStatusLine().getStatusCode());
			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() == 200) {
				// �����Ӧ
				result = EntityUtils.toString(response.getEntity(), "utf-8");
				response.getEntity().consumeContent();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣��";
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣��";
		}
		return result;
	}
	
	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}
	
	// ������������Ӧ����response
	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		// ����������ͨ�ų�ʱΪ10��
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10000);
		System.out.println("777777777");
		HttpResponse response = httpClient.execute(request);
		System.out.println("8888888888");
		return response;
	}
	
}