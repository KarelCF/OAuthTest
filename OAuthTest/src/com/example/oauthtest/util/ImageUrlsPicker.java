package com.example.oauthtest.util;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.Bilateral.Users;

public class ImageUrlsPicker {
    
	private static ImageUrlsPicker imageUrlsPicker = null;
	private List<String> imageUrlsList = new ArrayList<String>();
	private String[] imageUrls;
	
	private ImageUrlsPicker() {}
	
	public static ImageUrlsPicker getInstance() {
		if (imageUrlsPicker == null)
			imageUrlsPicker = new ImageUrlsPicker();
		return imageUrlsPicker;
	}
	
	public String[] pickUpUrls(List<Users> users) {
		for (int i = 0; i < users.size(); i++)
			imageUrlsList.add(users.get(i).getBilateralImageUrl());
		imageUrls = (String[]) imageUrlsList.toArray(new String[imageUrlsList.size()]);
		return imageUrls;
	}
	
}
