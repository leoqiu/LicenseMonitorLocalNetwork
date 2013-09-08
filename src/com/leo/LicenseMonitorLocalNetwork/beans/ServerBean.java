package com.leo.LicenseMonitorLocalNetwork.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import ch.ethz.ssh2.Connection;

import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;


public class ServerBean {

	private String serverName;
	private String lmstatLoc;
	private String lmgrdServeLoc;
	private String serverLoc;
	private String serverTimeZone;
	private String timeOut;
	private String retryTimes;
	private String timeStamp;
	// 0 means not login, 1 means logined
	private int isLogin;
	private Connection connection = null;

	public ServerBean(String serverName, String lmstatLoc, String lmgrdServeLoc,
			String serverLoc, String serverTimeZone, String timeOut, String retryTimes) {
		this.serverName = serverName;
		this.lmstatLoc = lmstatLoc;
		this.lmgrdServeLoc = lmgrdServeLoc;
		this.serverLoc = serverLoc;
		this.serverTimeZone = serverTimeZone;
		this.timeOut = timeOut;
		this.retryTimes = retryTimes;
		
		this.timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
		this.isLogin = LicLiteData.IS_NOT_LOGINED;
	}

	public ServerBean(String serverName, String lmstatLoc, String lmgrdServeLoc,
			String serverLoc, String serverTimeZone, String timeOut, String retryTimes, String timeStamp) {
		this.serverName = serverName;
		this.lmstatLoc = lmstatLoc;
		this.lmgrdServeLoc = lmgrdServeLoc;
		this.serverLoc = serverLoc;
		this.serverTimeZone = serverTimeZone;
		this.timeOut = timeOut;
		this.retryTimes = retryTimes;
		
		this.timeStamp = timeStamp;
		this.isLogin = LicLiteData.IS_NOT_LOGINED;
	}



	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public int getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(int isLogin) {
		this.isLogin = isLogin;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getLmstatLoc() {
		return lmstatLoc;
	}

	public void setLmstatLoc(String lmstatLoc) {
		this.lmstatLoc = lmstatLoc;
	}

	public String getLmgrdServeLoc() {
		return lmgrdServeLoc;
	}

	public void setLmgrdServeLoc(String lmgrdServeLoc) {
		this.lmgrdServeLoc = lmgrdServeLoc;
	}

	public String getServerLoc() {
		return serverLoc;
	}

	public void setServerLoc(String serverLoc) {
		this.serverLoc = serverLoc;
	}

	public String getServerTimeZone() {
		return serverTimeZone;
	}

	public void setServerTimeZone(String serverTimeZone) {
		this.serverTimeZone = serverTimeZone;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(String retryTimes) {
		this.retryTimes = retryTimes;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String toString() {
		return serverName + LicLiteData.SERVER_BEAN_ITEM_SEPARATOR + lmstatLoc
				+ LicLiteData.SERVER_BEAN_ITEM_SEPARATOR + lmgrdServeLoc
				+ LicLiteData.SERVER_BEAN_ITEM_SEPARATOR + serverLoc
				+ LicLiteData.SERVER_BEAN_ITEM_SEPARATOR + serverTimeZone
				+ LicLiteData.SERVER_BEAN_ITEM_SEPARATOR + timeOut
				+ LicLiteData.SERVER_BEAN_ITEM_SEPARATOR + retryTimes
				+ LicLiteData.SERVER_BEAN_ITEM_SEPARATOR + timeStamp
				+ LicLiteData.SERVER_BEAN_ITEM_SEPARATOR + isLogin;

	}

}
