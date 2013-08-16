package com.example.oauthtest.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;

import com.example.entity.AccessTokenInfo;
import com.example.oauthtest.Constants;

public class AccessTokenUtil {
	
	private static final int GET_ACCESS_TOKEN = 1;
	private static final String ACCESS_TOKEN_URL = "https://api.weibo.com/oauth2/access_token";
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	private String result;
	private AccessTokenInfo accessTokenInfo = null;
	private Handler refreshUIHandler;
	
	public void getAccessTokenByCode(String code, Handler handler) {
		refreshUIHandler = handler;
		preparePostRequestParams(code);
		new Thread(new Runnable() {
			@Override
			public void run() {
				result = HttpUtil.queryStringForPost(ACCESS_TOKEN_URL, params);
				accessTokenInfo = (AccessTokenInfo) GsonUtil.parseJsonToObject(result, AccessTokenInfo.class);
				// 通过持有UI线程的Handler来传递子线程网络操作的返回值,并在UI线程中的
				// Handler类中处理数据,解决了子线程与主线程间不同步而导致的空指针异常问题				
				Message notifyMsg = refreshUIHandler.obtainMessage(GET_ACCESS_TOKEN, accessTokenInfo);
				refreshUIHandler.sendMessage(notifyMsg);
			}
		}).start();
	}
	
	private void preparePostRequestParams(String code) {
		params.add(new BasicNameValuePair("client_id", Constants.APP_KEY));
		params.add(new BasicNameValuePair("client_secret", Constants.APP_SECRET));
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		params.add(new BasicNameValuePair("redirect_uri", Constants.REDIRECT_URL));
		params.add(new BasicNameValuePair("code", code));
	}
	
}
