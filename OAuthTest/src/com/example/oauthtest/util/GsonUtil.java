package com.example.oauthtest.util;

import com.google.gson.Gson;

public class GsonUtil {
	
	private static Gson gson = new Gson();
	
	public static <T> Object parseJsonToObject(String json, Class<T> classOfT ) {
		System.out.println(json);
		Object targetObjectFromJson = (Object) gson.fromJson(json, classOfT);
		return targetObjectFromJson;
	}
	
}
