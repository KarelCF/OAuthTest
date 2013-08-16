package com.example.oauthtest.adapter;

import java.util.List;

import com.example.entity.Bilateral.Users;
import com.example.oauthtest.R;
import com.example.oauthtest.util.ImageUrlsPicker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BilateralListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Users> bilateralUsers;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	
	
	
	public BilateralListAdapter(Context context, List<Users> bilateralUsers) {
		
		this.context = context;
		this.bilateralUsers = bilateralUsers;
		// 需要对imageLoader进行初始化, 否则会报错:"ImageLoader must be init with configuration before using"
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		initLoaderOptions();
	}
	
	private void initLoaderOptions() {
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}

	@Override
	public int getCount() {
		return bilateralUsers.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.bilateral_item, null, false);
			holder = new ViewHolder();
			holder.bilateralImage = (ImageView) convertView.findViewById(R.id.bilateralImage);
			holder.bilateralName = (TextView) convertView.findViewById(R.id.bilateralName);
			holder.bilateralGender = (TextView) convertView.findViewById(R.id.bilateralGender);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Users user = bilateralUsers.get(position); 
		
		imageLoader.displayImage(user.getBilateralImageUrl(), holder.bilateralImage, options);
		holder.bilateralName.setText(user.getName());	
		holder.bilateralGender.setText(user.getGender());
		
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView bilateralImage;
		TextView bilateralName;
		TextView bilateralGender;
	}
	
}
