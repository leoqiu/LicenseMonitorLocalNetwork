package com.leo.LicenseMonitorLocalNetwork.beans;

public class NotificationEntryBean {

	@Override
	public String toString() {
		
		return "[" + this.isChanged + " , " + this.entryValue + "]";
	}


	private boolean isChanged;
	private String entryValue;
	
	
	public NotificationEntryBean(String entryValue) {
		// TODO Auto-generated constructor stub
		this.isChanged = true;
		this.entryValue = entryValue; 
	}

	public boolean isChanged() {
		return isChanged;
	}


	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}


	public String getEntryValue() {
		return entryValue;
	}


	public void setEntryValue(String entryValue) {
		this.entryValue = entryValue;
	}
	
}
