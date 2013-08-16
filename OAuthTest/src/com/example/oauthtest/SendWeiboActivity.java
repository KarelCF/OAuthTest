package com.example.oauthtest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.example.api.StatusesAPI;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendWeiboActivity extends Activity {
	
	private EditText weiboContentEditText = null;
	private Button getImageBtn = null;
	private Button sendWeiboBtn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendweiboactivity);
		
		weiboContentEditText = (EditText) findViewById(R.id.weiboContentEditText);
		getImageBtn = (Button) findViewById(R.id.getImageBtn);
		sendWeiboBtn = (Button) findViewById(R.id.sendWeiboBtn);
		
		sendWeiboBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//        		sendWeiboMsg(MainActivity.accessToken);
            	new AlertDialog.Builder(SendWeiboActivity.this)
            	.setTitle("确认发送").setMessage("").setPositiveButton("确认", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendWeiboMsg(MainActivity.accessToken);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				}).show();
            }
        });
		
	}
	
    private void sendWeiboMsg(Oauth2AccessToken accessToken) {
    	StatusesAPI statusesApi = new StatusesAPI(accessToken);
    	String content = weiboContentEditText.getText().toString();
    	statusesApi.update(content, null, null, new RequestListener() {
			
			@Override
			public void onIOException(IOException arg0) {
			}
			
			@Override
			public void onError(WeiboException arg0) {
			}
			
			@Override
			public void onComplete4binary(ByteArrayOutputStream arg0) {
			}
			
			@Override
			public void onComplete(String response) {
				Toast.makeText(SendWeiboActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
			}
		});
    }
    
}
