package com.example.oauthtest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.entity.AccessTokenInfo;
import com.example.entity.ProfileInfo;
import com.example.oauthtest.util.AccessTokenUtil;
import com.example.oauthtest.util.GsonUtil;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.sso.SsoHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;


@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	private static final int PROFILE_NAME = 0;
	private static final int PROFILE_IMG = 1;
	
	public static Oauth2AccessToken accessToken;
	
	private Weibo mWeibo;   
	private SsoHandler mSsoHandler;
	
	private Button loginBtn;
	private TextView tokenInfo;
	private TextView profile_name;
	private ImageView profile_img;
	private RefreshUIHandler handler = new RefreshUIHandler(this);
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
        .detectDiskReads().detectDiskWrites().detectNetwork()  
        .penaltyLog().build());  

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects().penaltyLog()
        .penaltyDeath().build());  
		
		tokenInfo = (TextView) findViewById(R.id.TokenInfo);
		profile_name = (TextView) findViewById(R.id.profile_name);
		loginBtn = (Button) findViewById(R.id.LoginBtn);
		profile_img = (ImageView) findViewById(R.id.profile_picture);
		
		mWeibo = Weibo.getInstance(ConstantS.APP_KEY, ConstantS.REDIRECT_URL, ConstantS.SCOPE);
		MainActivity.accessToken = AccessTokenKeeper.readAccessToken(this);  
		
		loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//            	 mSsoHandler = new SsoHandler(MainActivity.this, mWeibo);
//            	 mSsoHandler.authorize(new AuthDialogListener());
            	 mWeibo.anthorize(MainActivity.this, new AuthDialogListener());
            }
        });
	}
	
	
    class AuthDialogListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
        	
        	String code = values.getString("code");
        	AccessTokenInfo accessTokenInfo = null;
        	if (code != null) {
        		tokenInfo.setText("取得认证code: \r\n Code: " + code);
	        	Toast.makeText(MainActivity.this, "认证code成功", Toast.LENGTH_SHORT).show();
	        	AccessTokenUtil util = new AccessTokenUtil();
				accessTokenInfo = util.getAccessTokenByCode(code);
        	}
        	
            String token = accessTokenInfo.getAccess_token();
            String expires_in = accessTokenInfo.getExpires_in();
            long uid = accessTokenInfo.getUid();
            
            MainActivity.accessToken = new Oauth2AccessToken(token, expires_in);
            if (MainActivity.accessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                	.format(new Date(MainActivity.accessToken.getExpiresTime()));
                tokenInfo.setText("认证成功: \r\n access_token: " + token + "\r\n"
                        + "expires_in: " + expires_in + "\r\n有效期：" + date);
             
                AccessTokenKeeper.keepAccessToken(MainActivity.this,
                        accessToken);
                Toast.makeText(MainActivity.this, "认证成功", Toast.LENGTH_SHORT)
                        .show();
            }
            getUserInfo(uid, accessToken);
        }

        @Override
        public void onError(WeiboDialogError e) {
            Toast.makeText(getApplicationContext(),
                    "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "Auth cancel",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(getApplicationContext(),
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }
    
    private void getUserInfo(long uid, Oauth2AccessToken accessToken) {
    	UsersAPI api = new UsersAPI(accessToken);
    	api.show(uid, new RequestListener() {
			
			@Override
			public void onIOException(IOException e) {
				e.printStackTrace();				
			}
			
			@Override
			public void onError(WeiboException e) {
				e.printStackTrace();				
			}
			
			@Override
			public void onComplete(String response) {
				ProfileInfo profileInfo = (ProfileInfo) GsonUtil.parseJsonToObject(response, ProfileInfo.class);
				Message msg = MainActivity.this.handler.obtainMessage(PROFILE_NAME, profileInfo.getName());
				MainActivity.this.handler.sendMessage(msg);
			}

			@Override
			public void onComplete4binary(ByteArrayOutputStream arg0) {
				// TODO Auto-generated method stub
			}
		});
    }
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    
    private static class RefreshUIHandler extends Handler {
    	// 持有弱引用，消除因Handler类不设置static关键字而产生的
    	// "This Handler class should be static or leaks might occur"警告
    	WeakReference<MainActivity> weakReference;

    	RefreshUIHandler(MainActivity activity) {
    		weakReference = new WeakReference<MainActivity>(activity);
        }
		@Override
		public void handleMessage(Message msg) {
			MainActivity mainActivity = weakReference.get();
			switch (msg.what) {
			case PROFILE_NAME:
				mainActivity.profile_name.setText((String)msg.obj);
				break;
			}
		}
    	
    }
    
}
