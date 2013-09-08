package com.leo.LicenseMonitorLocalNetwork.beans;



import java.util.ArrayList;

/**
 * For comparing two notification beans
 * 
 * @author shqiu
 *
 */
public class NotificationCmpBean {

	private String featureNameCmp;
	private ArrayList<NotificationEntryBean> userUsageListCmp;
	
	
	public NotificationCmpBean(String featureNameCmp) {
		this.featureNameCmp = featureNameCmp;
		this.userUsageListCmp = new ArrayList<NotificationEntryBean>();
	}

	public String getFeatureNameCmp() {
		return featureNameCmp;
	}


	public void setFeatureNameCmp(String featureNameCmp) {
		this.featureNameCmp = featureNameCmp;
	}


	public ArrayList<NotificationEntryBean> getUserUsageListCmp() {
		return userUsageListCmp;
	}


	public void setUserUsageListCmp(ArrayList<NotificationEntryBean> userUsageListCmp) {
		this.userUsageListCmp = userUsageListCmp;
	}
	
	@Override
	public String toString() {
		
		String str = this.featureNameCmp + " || ";
		String str2 = "";
		
		for(int i = 0; i < this.getUserUsageListCmp().size(); i++){
			str2 += this.getUserUsageListCmp().get(i).isChanged()+ " - " + this.getUserUsageListCmp().get(i).getEntryValue() + "; ";
		}
		
		return str + str2;
	}
}




















