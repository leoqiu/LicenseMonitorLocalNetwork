package com.leo.LicenseMonitorLocalNetwork.beans;

import ch.ethz.ssh2.Connection;


public class NotificationBean {

    //test
    private String test;

	private String notificationServerName = null;
	//lmstat cmd loc
	private String notificationLmstatCmdLoc = null;
	//lmgrd server loc
	private String notificationLmgrdServerLoc = null;
	//license name
	private String notificationLicenseName = null;
	//user name
	private String notificationLicenseUserName = null;
	//polling period
	private String notificationPollingPeriod = null;
	//is background thread started
	private boolean isNotificationRunning = false;
	//connection for running notification check background
	private Connection connection = null;

	public NotificationBean(String notificationServerName,
			String notificationLmstatCmdLoc, String notificationLmgrdServerLoc,
			String notificationLicenseName, String notificationLicenseUserName,
			String notificationPollingPeriod) {
		this.notificationServerName =  notificationServerName;
		this.notificationLmstatCmdLoc = notificationLmstatCmdLoc;
		this.notificationLmgrdServerLoc = notificationLmgrdServerLoc;
		this.notificationLicenseName = notificationLicenseName;
		this.notificationLicenseUserName = notificationLicenseUserName;
		this.notificationPollingPeriod = notificationPollingPeriod;
	}
	

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public boolean isNotificationRunning() {
		return isNotificationRunning;
	}

	public void setNotificationRunning(boolean isNotificationRunning) {
		this.isNotificationRunning = isNotificationRunning;
	}
	
	public String getNotificationServerName() {
		return notificationServerName;
	}

	public void setNotificationServerName(String notificationServerName) {
		this.notificationServerName = notificationServerName;
	}

	public String getNotificationLmstatCmdLoc() {
		return notificationLmstatCmdLoc;
	}

	public void setNotificationLmstatCmdLoc(String notificationLmstatCmdLoc) {
		this.notificationLmstatCmdLoc = notificationLmstatCmdLoc;
	}

	public String getNotificationLmgrdServerLoc() {
		return notificationLmgrdServerLoc;
	}

	public void setNotificationLmgrdServerLoc(String notificationLmgrdServerLoc) {
		this.notificationLmgrdServerLoc = notificationLmgrdServerLoc;
	}

	public String getNotificationLicenseName() {
		return notificationLicenseName;
	}

	public void setNotificationLicenseName(String notificationLicenseName) {
		this.notificationLicenseName = notificationLicenseName;
	}

	public String getNotificationLicenseUserName() {
		return notificationLicenseUserName;
	}

	public void setNotificationLicenseUserName(String notificationLicenseUserName) {
		this.notificationLicenseUserName = notificationLicenseUserName;
	}

	public String getNotificationPollingPeriod() {
		return notificationPollingPeriod;
	}

	public void setNotificationPollingPeriod(String notificationPollingPeriod) {
		this.notificationPollingPeriod = notificationPollingPeriod;
	}
	
	@Override
	public String toString() {
		String allStr = "";
		allStr += this.notificationServerName + " -- " + this.notificationLmstatCmdLoc + " -- " + 
		this.notificationLmgrdServerLoc + " -- " + this.notificationLicenseName + " -- " + this.notificationLicenseUserName +
		" -- " + this.notificationPollingPeriod + "\n";
		
		
		return allStr;
	}
}

























