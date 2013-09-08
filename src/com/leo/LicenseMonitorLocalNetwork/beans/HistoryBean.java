package com.leo.LicenseMonitorLocalNetwork.beans;

public class HistoryBean {

	private String lmgrdServerName;
	private String dataTimestamp;
	private String fileName;

	public HistoryBean (String lmgrdServerName, String dataTimestamp, String fileName){
		this.lmgrdServerName = lmgrdServerName;
		this.dataTimestamp = dataTimestamp;
		this.fileName = fileName;
	}
	
	public String getLmgrdServerName() {
		return lmgrdServerName;
	}

	public void setLmgrdServerName(String lmgrdServerName) {
		this.lmgrdServerName = lmgrdServerName;
	}

	public String getDataTimestamp() {
		return dataTimestamp;
	}

	public void setDataTimestamp(String dataTimestamp) {
		this.dataTimestamp = dataTimestamp;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
