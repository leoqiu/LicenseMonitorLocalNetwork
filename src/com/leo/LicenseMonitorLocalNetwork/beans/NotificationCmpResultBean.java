package com.leo.LicenseMonitorLocalNetwork.beans;



public class NotificationCmpResultBean {

	@Override
	public String toString() {
		
		return "" + this.isCheckedIn + " - " + this.checkedStr;
	}

	private boolean isCheckedIn;
	private String checkedStr;
	
	public NotificationCmpResultBean(boolean isCheckedIn, String checkedStr) {
		this.isCheckedIn = isCheckedIn;
		this.checkedStr = checkedStr;
	}

	public boolean isCheckedIn() {
		return isCheckedIn;
	}

	public void setCheckedIn(boolean isCheckedIn) {
		this.isCheckedIn = isCheckedIn;
	}

	public String getCheckedStr() {
		return checkedStr;
	}

	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}
}
