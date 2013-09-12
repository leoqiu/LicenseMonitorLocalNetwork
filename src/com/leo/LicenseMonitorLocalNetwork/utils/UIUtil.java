package com.leo.LicenseMonitorLocalNetwork.utils;


import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;
import ch.ethz.ssh2.Connection;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.adapters.ServerListSimpleAdapter;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationCmpBean;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationCmpResultBean;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationEntryBean;
import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;


public class UIUtil{
	
	/**
	 * show a toast
	 * 
	 * @param msg
	 * @param context
	 */
	public static void showToast(String msg, Context context){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	
	public static boolean isServerExist(String serverName, String lmstatLoc, String lmgrdServeLoc){
		boolean isExist = false;
		
		for(ServerBean serverBean : LicLiteData.serverBeanList){
			if(serverBean.getServerName().equals(serverName) && serverBean.getLmstatLoc().equals(lmstatLoc) 
					&& serverBean.getLmgrdServeLoc().equals(lmgrdServeLoc)){
				isExist = true;
				break;
			}
		}

		return isExist;
	}
	
	/**
	 * logout a specific server, and close its connection
	 * 
	 * @param position
	 */
	public static void logOutTheServer(int position, ServerListSimpleAdapter serverListSimpleAdapter){
		ServerBean serverBean = LicLiteData.serverBeanList.get(position);
		serverBean.setIsLogin(LicLiteData.IS_NOT_LOGINED);
		serverBean.getConnection().close();
		serverBean.setConnection(null);
		
		//notify ServerListSimpleAdapter data set changed
		ServerListSimpleAdapter
				.getMyData()
				.get(position)
				.put("item_server_pic", String.valueOf(R.drawable.server_not_login));
    	serverListSimpleAdapter.notifyDataSetChanged();
	}
	
	/**
	 * close all the ssh connections and
	 * clear LicLiteData.serverBeanList
	 * 
	 */
	public static void dispose(){
		//stop service
		
		
		int size = LicLiteData.serverBeanList.size();
		Connection connection;
		ServerBean serverBean;
		for(int i = 0; i < size; i++){
			serverBean = LicLiteData.serverBeanList.get(i);
			connection = serverBean.getConnection();
			if(connection != null){
				//isLogin control the service runnable
				serverBean.setIsLogin(LicLiteData.IS_NOT_LOGINED);
				serverBean.getConnection().close();
				serverBean.setConnection(null);
			}
		}
		LicLiteData.serverBeanList.clear();
		LicLiteData.autoServerNameSet.clear();
		LicLiteData.autoLmstatLocSet.clear();
		LicLiteData.autoLmgrdServerLocSet.clear();
//		stopAService(context);
	}
	
	/**
	 * 
	 * @param context
	 */
//	public static void stopAService(Context context){
//		
//		context.stopService(new Intent(context, LicSenseLiteService.class));
//	}
	
	/**
	 * 
	 *  Before save new server info, check if this server 
	 *  already exist in server list
	 * 
	 * @param serverName
	 * @return
	 */
//	public static boolean isServerExist(String serverName){
//		boolean isExist = false;
//		
//		for(ServerBean serverBean : LicLiteData.serverBeanList) {
//			if(serverBean.getServerName().equals(serverName)){
//				isExist = true;
//				break;
//			}
//		}
//		
//		
//		return isExist;
//	}
	
	
    /**
     * This is a recursive method.
     * If file is found, total file size is calculated.
     * If it is a folder, we recurse further.
     */
    public static double getFileSize(File folder) {
    	
    	DecimalFormat fmt =new DecimalFormat("#.##");
    	
        long fileSizeByte = 0;
 
        File[] filelist = folder.listFiles();
        for (int i = 0; i < filelist.length; i++) {
            if (filelist[i].isDirectory()) {
            	fileSizeByte += getFileSize(filelist[i]);
            } else {
            	fileSizeByte += filelist[i].length();
            }
        }
        
        double fileSizeMB=Double.valueOf(fmt.format(fileSizeByte /(1024*1024)));
 
        return fileSizeMB;
    }
    
    /**
     * 
     *
     */
    
	public static void listAllFileNames(){
		File folder = new File(LicLiteData.licLiteDataDir);
		File[] listOfFiles = folder.listFiles();  
		int size = listOfFiles.length;
		
		
		for(int i = 0; i < size; i ++){
			System.out.println("file name --> " + listOfFiles[i]);
		}
		
		for(int j = 0; j < LicLiteData.NUMBER_DATA_FILE_TO_BE_DELETE; j++){
			System.out.println("file deleted-> " + listOfFiles[j].delete());
		}
		
	}
	
	/**
	 * called at line 104 of RenderInTimeResultAsyncTask
	 * 
	 * generate cmd for rendering 
	 * 
	 * @param serverBean
	 */
	public static String generateCmd(ServerBean serverBean){
		String serverName = serverBean.getServerName();
		String lmstatLoc = serverBean.getLmstatLoc();
		String lmgrdServeLoc = serverBean.getLmgrdServeLoc();
		String cmd = null;
		
		//Temporary work around
        //for local network testing
		if(serverName.contains("192")){
			cmd = "cat " + lmstatLoc + "/lic.log" + " > /tmp/licliteserver-`date +\"%Y-%m-%d-%H-%M-%S\"`";
		}else{
			cmd = lmstatLoc + " -a -c " + lmgrdServeLoc + " > /tmp/licliteserver-`date +\"%Y-%m-%d-%H-%M-%S\"`";
		}
System.out.println("cmd --> " + cmd);
		return cmd;
	}
	
	public static void createDataDir(Activity activity){
		//check if LicLiteData.licLiteDataDir exists if not create one accordingly
System.out.println("hahahah --->  "+activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
System.out.println("sdcard mounted....");
			LicLiteData.licLiteDataDir = Environment.getExternalStorageDirectory().toString()
					+ File.separator + LicLiteData.DIR;
			File licliteDataDir = new File(LicLiteData.licLiteDataDir);		
			if(!licliteDataDir.exists()){
				licliteDataDir.mkdirs();
System.out.println("create data folder...");
			}
		}
		
System.out.println("sdcard unmounted....");
	}
	
	/**
	 * 
	 * 
	 * @param previousNotificationCmpBean
	 * @param secondNotificationCmpBean
	 * @return
	 */
	public static ArrayList<NotificationCmpResultBean> comparePreviousWithSecondNotificationBean(
			NotificationCmpBean previousNotificationCmpBean, NotificationCmpBean secondNotificationCmpBean) {
		
		ArrayList<NotificationCmpResultBean> notificationCmpResultBeanList = new ArrayList<NotificationCmpResultBean>();
		
		ArrayList<NotificationEntryBean> previousStrList = previousNotificationCmpBean.getUserUsageListCmp();
		ArrayList<NotificationEntryBean> secondStrList = secondNotificationCmpBean.getUserUsageListCmp();
		
		//iterate secondNotificationCmpBean
		NotificationEntryBean previousTmpEntry;
		NotificationEntryBean secondTmpEntry;
		for(int i = 0; i < previousStrList.size(); i++){
			previousTmpEntry = previousStrList.get(i);
			for(int j = 0; j < secondStrList.size(); j++){
				secondTmpEntry = secondStrList.get(j);
			
				if(secondTmpEntry.isChanged()){
					if(previousTmpEntry.getEntryValue().equals(secondTmpEntry.getEntryValue())){
						previousTmpEntry.setChanged(false);
						secondTmpEntry.setChanged(false);
							break;
					}
				}

			}
		}
		
		//the remaining item in previousList are checked out ones
		for(int i = 0; i < previousStrList.size(); i++){
			NotificationCmpResultBean notificationCmpResultBeanCheckout = null;
			if(previousStrList.get(i).isChanged()){
				notificationCmpResultBeanCheckout = new NotificationCmpResultBean(
							false, previousStrList.get(i).getEntryValue());
			}
			
			if(notificationCmpResultBeanCheckout != null)
				notificationCmpResultBeanList.add(notificationCmpResultBeanCheckout);
		}
		
		//the remaining item in secondList are checked in ones
		for(int i = 0; i < secondStrList.size(); i++){
			NotificationCmpResultBean notificationCmpResultBeanCheckin = null;
			
			if(secondStrList.get(i).isChanged()){
				notificationCmpResultBeanCheckin = new NotificationCmpResultBean(
						true, secondStrList.get(i).getEntryValue());
			}

			if(notificationCmpResultBeanCheckin != null)
				notificationCmpResultBeanList.add(notificationCmpResultBeanCheckin);
		}
		
		return notificationCmpResultBeanList;
	}
	
}










