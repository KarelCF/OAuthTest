package com.example.oauthtest.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.entity.AccessTokenInfo;
import com.example.oauthtest.ConstantS;

public class AccessTokenUtil {
	
	private static final String ACCESS_TOKEN_URL = "https://api.weibo.com/oauth2/access_token";
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	public AccessTokenInfo getAccessTokenByCode(String code) {
		preparePostRequestParams(code);
		String result = HttpUtil.queryStringForPost(ACCESS_TOKEN_URL, params);
		AccessTokenInfo accessTokenInfo = (AccessTokenInfo) GsonUtil.parseJsonToObject(result, AccessTokenInfo.class);
		return accessTokenInfo;
	}
	
	private void preparePostRequestParams(String code) {
		params.add(new BasicNameValuePair("client_id", ConstantS.APP_KEY));
		params.add(new BasicNameValuePair("client_secret", ConstantS.APP_SECRET));
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		params.add(new BasicNameValuePair("redirect_uri", ConstantS.REDIRECT_URL));
		params.add(new BasicNameValuePair("code", code));
	}
	
}
