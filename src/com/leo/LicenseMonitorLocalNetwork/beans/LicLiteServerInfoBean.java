package com.leo.LicenseMonitorLocalNetwork.beans;

import java.util.ArrayList;
import java.util.List;

public class LicLiteServerInfoBean {

	private String featureName;
	private String featureNumIssued;
	private String featureNumInUsed;
	private ArrayList<LicLiteUserInfoBean> licLiteUserInfoBeanList;
	
	
	public LicLiteServerInfoBean(String featureName, String featureNumIssued, String featureNumInUsed){
		
		this.featureName = featureName;
		this.featureNumIssued = featureNumIssued;
		this.featureNumInUsed = featureNumInUsed;
		this.licLiteUserInfoBeanList = new ArrayList<LicLiteUserInfoBean>();
	}
	
	
	public String getFeatureName() {
		return featureName;
	}


	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}


	public String getFeatureNumIssued() {
		return featureNumIssued;
	}


	public void setFeatureNumIssued(String featureNumIssued) {
		this.featureNumIssued = featureNumIssued;
	}


	public String getFeatureNumInUsed() {
		return featureNumInUsed;
	}


	public void setFeatureNumInUsed(String featureNumInUsed) {
		this.featureNumInUsed = featureNumInUsed;
	}


	public ArrayList<LicLiteUserInfoBean> getLicLiteUserInfoBeanList() {
		return licLiteUserInfoBeanList;
	}


	public void setLicLiteUserInfoBeanList(
			ArrayList<LicLiteUserInfoBean> licLiteUserInfoBeanList) {
		this.licLiteUserInfoBeanList = licLiteUserInfoBeanList;
	}

	
	@Override
	public String toString(){
		
		String listStr = "";
		if(this.licLiteUserInfoBeanList != null){
			
			int size = this.licLiteUserInfoBeanList.size();
			for(int i = 0; i < size; i++){
				listStr += this.licLiteUserInfoBeanList.get(i);
			}
		}
		
		return "Name-> " + this.featureName + " issued-> "
		+ this.featureNumIssued + " inUsed-> " + this.featureNumInUsed + " =+.+= " + listStr;

	}
	
	
}
