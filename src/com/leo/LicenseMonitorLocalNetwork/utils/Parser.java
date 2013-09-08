package com.leo.LicenseMonitorLocalNetwork.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.leo.LicenseMonitorLocalNetwork.beans.LicLiteServerInfoBean;
import com.leo.LicenseMonitorLocalNetwork.beans.LicLiteUserInfoBean;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationCmpBean;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationEntryBean;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;

public class Parser {
	
	//for feature section and feature user section
	private static String regexFeature = "(Users\\s{1}of\\s{1}((?:\\w+|\\-|\\/|\\.)+?))" +
			"(:\\s*?\\(Total\\s{1}of\\s{1}(\\d+)\\s{1}license(?:s|)\\s{1}issued;)" +
			"(\\s*?Total\\s{1}of\\s{1}(\\d+)\\s{1}license(?:s|)\\s{1}in\\s{1}use\\))";
	private static Pattern patternFeature = Pattern.compile(regexFeature);
	private static Matcher matcherFeature = null;
	
	private static String regexUser = "^(\\w+?)\\s" +
			"((?:\\w+|\\s+|\\/|\\:|\\.|\\(|\\)|\\-)+?)" +
			"(,\\s{1}start\\s{1}((?:\\w+|\\s+|\\/|\\:|\\,)+))";
	private static Pattern patternUser = Pattern.compile(regexUser);
	private static Matcher matcherUser = null;	
	
	
	/**
	 * 
	 * @return
	 */
	public static List<LicLiteServerInfoBean> parseDownloadDataFile(String fileName){
		List<LicLiteServerInfoBean> licliteServerInfoList = new ArrayList<LicLiteServerInfoBean>();
		LicLiteData.startTime = (int) (System.currentTimeMillis() / 1000);
		//NetWorkUtil.executeCmd(conn, cmd4);
//NetWorkUtil.executeSCPCmd(conn, cmd6);
		
		//local sdcard data file
		String licliteData = LicLiteData.licLiteDataDir + File.separator + fileName;
		File licLocalFile = new File(licliteData);		
		FileReader reader = null;		
		String line = null;		
//System.out.println("licliteData --> " + licliteData + " local file exist --> " + licLocalFile.exists());		
		LicLiteServerInfoBean licLiteServerInfoBean = null;
		LicLiteUserInfoBean licLiteUserInfoBean = null;
		int featureAccount = 0;
		int featureAccountAddOneConfirm = 0;
		try {
			
			if(licLocalFile.exists()){
				reader = new FileReader(licLocalFile);
				BufferedReader bf = new BufferedReader(reader);
				while((line = bf.readLine()) != null){
					line = line.trim();

					if(!line.isEmpty()){
	
						if(line.contains("Users of ")){
							featureAccount++;
							licLiteServerInfoBean = new LicLiteServerInfoBean(null, null, null);
							matcherFeature = patternFeature.matcher(line);
							String featureName = null;
							String featureNumIssued = null;
							String featureNumInUsed = null;
								while(matcherFeature.find()){
									featureName = matcherFeature.group(2);
									featureNumIssued = matcherFeature.group(4);
									featureNumInUsed = matcherFeature.group(6);
								}
							licLiteServerInfoBean.setFeatureName(featureName);
							licLiteServerInfoBean.setFeatureNumIssued(featureNumIssued);
							licLiteServerInfoBean.setFeatureNumInUsed(featureNumInUsed);

						}
						
						if(line.contains(", start ")){
							licLiteUserInfoBean = new LicLiteUserInfoBean(null, null, null, null);
								matcherUser = patternUser.matcher(line);
							String userName = LicLiteData.DEFAULT_PARSED_INFO;
							String userServerName = LicLiteData.DEFAULT_PARSED_INFO;
							String userStartTimeAndUserNumOfLicenses = null;
							String userStartTime = LicLiteData.DEFAULT_PARSED_INFO;
							String userNumOfLicenses = null;
								while(matcherUser.find()){
									userName = matcherUser.group(1);
									userServerName = matcherUser.group(2);
									userStartTimeAndUserNumOfLicenses = matcherUser.group(4);					
								}
							licLiteUserInfoBean.setUserName(userName);
							licLiteUserInfoBean.setUserServerName(userServerName);
							/*
							 * check if there is more than 1 license that user in used
							 */
							
							if(userStartTimeAndUserNumOfLicenses != null && userStartTimeAndUserNumOfLicenses.contains(", ")){
								String[] tmpArr = userStartTimeAndUserNumOfLicenses.split(", ");
								userStartTime = tmpArr[0];
								userNumOfLicenses = tmpArr[1];
							}else{
								if(userStartTimeAndUserNumOfLicenses != null)
									userStartTime = userStartTimeAndUserNumOfLicenses;
								userNumOfLicenses = "1 license";
							}
							
							
							licLiteUserInfoBean.setUserStartTime(userStartTime);
							licLiteUserInfoBean.setUserNumOfLicenses(userNumOfLicenses);
							licLiteServerInfoBean.getLicLiteUserInfoBeanList().add(licLiteUserInfoBean);

						}	
						
						
						//check featureAccount add one
						if((featureAccountAddOneConfirm+1) == featureAccount){
							licliteServerInfoList.add(licLiteServerInfoBean);							
							featureAccountAddOneConfirm++;
						}		
						
					}
						
				}
				
				reader.close();
				bf.close();
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
		}
		
		return licliteServerInfoList;
	}
	
	
	/**
	 * 
	 * 
	 * @param notificationTmpFile
	 * @param featureNameLic
	 * @param userNameLic
	 * @return
	 */
	public static NotificationCmpBean parseDownloadDataFileToNotificationCmpBean(String notificationTmpFile, 
			String featureNameLic, String userNameLic){
		LicLiteData.startTime = (int) (System.currentTimeMillis() / 1000);
		
		//LicLiteNotificationCmpBean
		NotificationCmpBean notificationCmpBean = new NotificationCmpBean(null);
		boolean isReachFeature = false;
		
		//local sdcard data file
		File licLocalFile = new File(notificationTmpFile);		
		FileReader reader = null;		
		String line = null;				

		try {
			
			if(licLocalFile.exists()){
				reader = new FileReader(licLocalFile);
				BufferedReader bf = new BufferedReader(reader);
				while((line = bf.readLine()) != null){
					line = line.trim();

					if(!line.isEmpty()){
						//
						if(line.contains("Users of " + featureNameLic + ":")){
							//create comparison table
							notificationCmpBean.setFeatureNameCmp(featureNameLic);
							isReachFeature = true;
							
						}
						
						if(line.contains(", start ") && line.contains(userNameLic) && isReachFeature){
							//add user start using license entries
							notificationCmpBean.getUserUsageListCmp().add(new NotificationEntryBean(line));
							
						}	
						
						if(isReachFeature && 
								!line.contains(", start ") && //not inline user information
								!line.contains("Users of " + featureNameLic + ":") && //not the next feature line
								!line.contains("vendor") &&
								!line.contains("floating license")){
							isReachFeature = false;
							break;
						}
						
					}
						
				}
				
				reader.close();
				bf.close();
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
		}
//LicLiteData.endTime = (int) (System.currentTimeMillis() / 1000);	
//System.out.println("parsing time cost is : " + (LicLiteData.endTime - LicLiteData.startTime));
		return notificationCmpBean;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
