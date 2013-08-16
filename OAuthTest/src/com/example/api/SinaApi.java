package com.example.api;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class SinaApi extends DefaultApi10a {

	private static final String REQUEST_TOKEN_URL = "http://api.t.sina.com.cn/oauth/request_token";
	private static final String AUTHORIZE_URL = "http://api.t.sina.com.cn/oauth/authorize?oauth_token=%s";
	private static final String ACCESS_TOKEN_URL = "http://api.t.sina.com.cn/oauth/access_token";
	
	@Override
	public String getAccessTokenEndpoint() {
		return ACCESS_TOKEN_URL;
	}

	@Override
	public String getAuthorizationUrl(Token requestToken) {
		return String.format(AUTHORIZE_URL, requestToken.getToken());
	}

	@Override
	public String getRequestTokenEndpoint() {
		return REQUEST_TOKEN_URL;
	}

}
