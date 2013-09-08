package com.leo.LicenseMonitorLocalNetwork.beans;

public class LicLiteUserInfoBean {
	private String userName;
	private String userServerName;
	private String userStartTime;
	private String userNumOfLicenses;

	public LicLiteUserInfoBean(String userName, String userServerName, String userStartTime, String userNumOfLicenses){
		this.userName = userName;
		this.userServerName = userServerName;
		this.userStartTime = userStartTime;
		this.userNumOfLicenses = userNumOfLicenses;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserServerName() {
		return userServerName;
	}
	public void setUserServerName(String userServerName) {
		this.userServerName = userServerName;
	}
	public String getUserStartTime() {
		return userStartTime;
	}
	public void setUserStartTime(String userStartTime) {
		this.userStartTime = userStartTime;
	}
	
	public String getUserNumOfLicenses() {
		return userNumOfLicenses;
	}

	public void setUserNumOfLicenses(String userNumOfLicenses) {
		this.userNumOfLicenses = userNumOfLicenses;
	}

	@Override
	public String toString(){
		String str = "";
		
		str = "User name: " + this.userName + "\nuserServerName: " + this.userServerName + 
				"\nuserStartTime: " + userStartTime +"\nuserNumOfLicenses: " + this.userNumOfLicenses;
		
		return str;
	}
}
