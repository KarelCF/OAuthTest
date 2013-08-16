package com.example.entity;

import java.util.List;

public class Bilateral {
	
	private int total_number;
	private List<Users> users;
	
	public int getTotalNumber() {
		return total_number;
	}
	
	public List<Users> getUser() {
		return users;
	}
	
	public static class Users {
		private long id;
		private String name;
		private String gender;
		private String avatar_hd;
		
		public long getId() {
			return id;
		}
		public String getName() {
			return name;
		}
		public String getGender() {
			return gender;
		}
		public String getBilateralImageUrl() {
			return avatar_hd;
		}			
	}
	
}
