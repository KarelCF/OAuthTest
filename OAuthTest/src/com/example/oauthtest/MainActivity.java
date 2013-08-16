package com.example.oauthtest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.example.api.FriendshipsAPI;
import com.example.api.StatusesAPI;
import com.example.api.UsersAPI;
import com.example.entity.AccessTokenInfo;
import com.example.entity.Bilateral;
import com.example.entity.Bilateral.Users;
import com.example.entity.ProfileInfo;
import com.example.oauthtest.adapter.BilateralListAdapter;
import com.example.oauthtest.util.AccessTokenUtil;
import com.example.oauthtest.util.GsonUtil;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.sso.SsoHandler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;


public class MainActivity extends Activity {
	
	private static final int PROFILE_INFO = 0;
	private static final int GET_ACCESS_TOKEN = 1;

	public static Oauth2AccessToken accessToken;
	
	private Weibo mWeibo;   
	private SsoHandler mSsoHandler;
	
	private Button loginBtn;
	private Button getBilateralBtn;
	private Button uploadBtn;
	private TextView tokenInfo;
	private TextView profileName;
	private ImageView profileImage;
	
	private ListView bilateralListView;
	private BilateralListAdapter bilateralListAdapter;
	
	private RefreshUIHandler handler = new RefreshUIHandler(this);
	
	private long uid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tokenInfo = (TextView) findViewById(R.id.tokenInfo);
		profileName = (TextView) findViewById(R.id.profile_name);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		getBilateralBtn = (Button) findViewById(R.id.getBilateralBtn);
		uploadBtn = (Button) findViewById(R.id.uploadBtn);
		profileImage = (ImageView) findViewById(R.id.profile_picture);
		bilateralListView = (ListView) findViewById(R.id.bilateralListView);
		
		mWeibo = Weibo.getInstance(Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		MainActivity.accessToken = AccessTokenKeeper.readAccessToken(this);  
		
		
		
		loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//            	 mSsoHandler = new SsoHandler(MainActivity.this, mWeibo);
//            	 mSsoHandler.authorize(new AuthDialogListener());
            	 mWeibo.anthorize(MainActivity.this, new AuthDialogListener());
            }
        });
		
		getBilateralBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (0 != uid) {
					getBilateral(uid, accessToken);
				} else {
					Toast.makeText(MainActivity.this, "请先通过微博登陆", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		uploadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (0 != uid) {
            		Intent intent = new Intent(MainActivity.this, SendWeiboActivity.class);
            		MainActivity.this.startActivity(intent);
				} else {
					Toast.makeText(MainActivity.this, "请先通过微博登陆", Toast.LENGTH_SHORT).show();
				}
            }
        });
		
	}
	
    class AuthDialogListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
        	String code = values.getString("code");
        	if (code != null) {
        		tokenInfo.setText("取得认证code: \r\n Code: " + code);
	        	Toast.makeText(MainActivity.this, "认证code成功", Toast.LENGTH_SHORT).show();
	        	AccessTokenUtil util = new AccessTokenUtil();
				util.getAccessTokenByCode(code, MainActivity.this.handler);
        	}
            
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
    
    private void getBilateral(long uid, Oauth2AccessToken accessToken) {
    	FriendshipsAPI friendshipsApi = new FriendshipsAPI(accessToken);
    	int perpageItemCount = 50;
    	int homePageLocation = 1;
    	friendshipsApi.bilateral(uid, perpageItemCount, homePageLocation, new RequestListener() {
			
			@Override
			public void onIOException(IOException e) {
				e.printStackTrace();
			}
			
			@Override
			public void onError(WeiboException e) {
				e.printStackTrace();
			}
			
			@Override
			public void onComplete4binary(ByteArrayOutputStream stream) {}
			
			@Override
			public void onComplete(String response) {
				Bilateral bilateral = (Bilateral) GsonUtil.parseJsonToObject(response, Bilateral.class);
				createBilateralList(bilateral);
			}
		});
    }
    
    private void createBilateralList(Bilateral bilateral) {
    	final List<Users> users = bilateral.getUser();
    	// 特别注意, 此处其实已经在子线程中运行,若要进行更新UI操作
    	// 可以使用UI线程的handler的post方法中传入一个Runnable来进行更新UI的操作    	
    	MainActivity.this.handler.post(new Runnable() {
			@Override
			public void run() {
				bilateralListAdapter = new BilateralListAdapter(MainActivity.this, users);
		    	bilateralListView.setAdapter(bilateralListAdapter);
			}
		});
    	
    }
    

    
    private void getProfileImageBitmap(String url) {
    	ProfileImageLoader loader = new ProfileImageLoader();
    	loader.execute(url);
    }
    
    private class ProfileImageLoader extends AsyncTask<String, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			return MainActivity.this.getBitMapFromUrl(params[0]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			MainActivity.this.profileImage.setImageBitmap(result);
		}
		
    }
    
    private Bitmap getBitMapFromUrl(String url) {
    	Bitmap bitmap = null;
    	try {
    		URL imgUrl = new URL(url);
        	HttpURLConnection  conn = (HttpURLConnection) imgUrl.openConnection();
			bitmap = BitmapFactory.decodeStream(conn.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return bitmap;
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
			case PROFILE_INFO:
				mainActivity.profileName.setText((String)msg.obj);
				break;
			case GET_ACCESS_TOKEN:
				AccessTokenInfo accessTokenInfo = (AccessTokenInfo) msg.obj;
			   	String token = accessTokenInfo.getAccess_token();
	            String expires_in = accessTokenInfo.getExpires_in();
	            mainActivity.uid = accessTokenInfo.getUid();
	            
	            MainActivity.accessToken = new Oauth2AccessToken(token, expires_in);
	            if (MainActivity.accessToken.isSessionValid()) {
	                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                		.format(new Date(MainActivity.accessToken.getExpiresTime()));
	                mainActivity.tokenInfo.setText("认证成功: \r\n access_token: " + token + "\r\n"
                		+ "expires_in: " + expires_in + "\r\n有效期：" + date);
	             
	                AccessTokenKeeper.keepAccessToken(mainActivity,
	                        accessToken);
	                Toast.makeText(mainActivity, "认证成功", Toast.LENGTH_SHORT)
	                        .show();
	            }
	            mainActivity.getUserInfo(mainActivity.uid, accessToken);
				break;
			}
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
				String userInfo = "Id:" + profileInfo.getId() + "\n" + profileInfo.getName() + ":" + profileInfo.getGender();
				Message userInfoMsg = MainActivity.this.handler.obtainMessage(PROFILE_INFO, userInfo);
				MainActivity.this.handler.sendMessage(userInfoMsg);
				String url = profileInfo.getProfileImageUrl();
				getProfileImageBitmap(url);
			}

			@Override
			public void onComplete4binary(ByteArrayOutputStream stream) {}
		});
    }
    
    public RefreshUIHandler getHandler() {
    	return handler;
    }
    
}
