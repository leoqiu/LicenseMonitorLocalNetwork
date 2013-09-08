package com.leo.LicenseMonitorLocalNetwork.beans;

public class AutoWordBean {

	private String autoServerName = null;
	private String autoLmstatLoc = null;
	private String autoLmgrdServeLoc = null;
	
	
	public AutoWordBean (String autoServerName, String autoLmstatLoc, String autoLmgrdServeLoc){
		
		this.autoServerName = autoServerName;
		this.autoLmstatLoc = autoLmstatLoc;
		this.autoLmgrdServeLoc = autoLmgrdServeLoc;
	}
	
	public String getAutoServerName() {
		return autoServerName;
	}

	public void setAutoServerName(String autoServerName) {
		this.autoServerName = autoServerName;
	}

	public String getAutoLmstatLoc() {
		return autoLmstatLoc;
	}

	public void setAutoLmstatLoc(String autoLmstatLoc) {
		this.autoLmstatLoc = autoLmstatLoc;
	}

	public String getAutoLmgrdServeLoc() {
		return autoLmgrdServeLoc;
	}

	public void setAutoLmgrdServeLoc(String autoLmgrdServeLoc) {
		this.autoLmgrdServeLoc = autoLmgrdServeLoc;
	}
	
	@Override
	public String toString(){
		return this.autoServerName + " - " + this.autoLmstatLoc + " - " + this.autoLmgrdServeLoc;
	}
}
