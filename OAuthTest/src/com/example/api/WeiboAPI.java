package com.example.api;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.net.AsyncWeiboRunner;
import com.weibo.sdk.android.net.RequestListener;

public abstract class WeiboAPI {
	public static final String API_SERVER = "https://api.weibo.com/2";
	public static final String HTTPMETHOD_POST = "POST";
	public static final String HTTPMETHOD_GET = "GET";
	private Oauth2AccessToken oAuth2accessToken;
	private String accessToken;
	
	public WeiboAPI(Oauth2AccessToken oauth2AccessToken){
	    this.oAuth2accessToken=oauth2AccessToken;
	    if(oAuth2accessToken!=null){
	        accessToken=oAuth2accessToken.getToken();
	    }
	   
	}
	public enum FEATURE {
		ALL, ORIGINAL, PICTURE, VIDEO, MUSICE
	}

	public enum SRC_FILTER {
		ALL, WEIBO, WEIQUN
	}

	public enum TYPE_FILTER {
		ALL, ORIGAL
	}

	public enum AUTHOR_FILTER {
		ALL, ATTENTIONS, STRANGER
	}

	public enum TYPE {
		STATUSES, COMMENTS, MESSAGE
	}

	public enum EMOTION_TYPE {
		FACE, ANI, CARTOON
	}

	public enum LANGUAGE {
		cnname, twname
	}

	public enum SCHOOL_TYPE {
		COLLEGE, SENIOR, TECHNICAL, JUNIOR, PRIMARY
	}

	public enum CAPITAL {
		A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
	}

	public enum FRIEND_TYPE {
		ATTENTIONS, FELLOWS
	}

	public enum RANGE {
		ATTENTIONS, ATTENTION_TAGS, ALL
	}

	public enum USER_CATEGORY {
		DEFAULT, ent, hk_famous, model, cooking, sports, finance, tech, singer, writer, moderator, medium, stockplayer
	}

	public enum STATUSES_TYPE {
		ENTERTAINMENT, FUNNY, BEAUTY, VIDEO, CONSTELLATION, LOVELY, FASHION, CARS, CATE, MUSIC
	}

	public enum COUNT_TYPE {
		STATUS, 
		FOLLOWER, 
		CMT, 
		DM, 
		MENTION_STATUS, 
		MENTION_CMT
	}
	
	public enum SORT {
	    Oauth2AccessToken, 
	    SORT_AROUND
	}

	public enum SORT2 {
		SORT_BY_TIME, SORT_BY_HOT
	}
	
	public enum SORT3 {
		SORT_BY_TIME, SORT_BY_DISTENCE
	}
	
	public enum COMMENTS_TYPE {
		NONE, CUR_STATUSES, ORIGAL_STATUSES, BOTH
	}
	
	protected void request( final String url, final WeiboParameters params,
			final String httpMethod,RequestListener listener) {
		params.add("access_token", accessToken);
		AsyncWeiboRunner.request(url, params, httpMethod, listener);
	}
}
