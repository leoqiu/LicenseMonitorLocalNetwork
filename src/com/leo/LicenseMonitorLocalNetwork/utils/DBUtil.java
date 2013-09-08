package com.leo.LicenseMonitorLocalNetwork.utils;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.leo.LicenseMonitorLocalNetwork.beans.AutoWordBean;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationBean;
import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;
import com.leo.LicenseMonitorLocalNetwork.db.LDBHelper;
import com.leo.LicenseMonitorLocalNetwork.db.LGeneralDBManager;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;

public class DBUtil {

	private static LGeneralDBManager lGeneralDBManager = null;
	
	/**
	 * Insert serverBean and autoWordBean into db
	 * 
	 * LGeneralDBManager is like a session, 
	 * create a new one -> use it -> recycle it
	 * 
	 * @param serverBean
	 * @param autoWordBean
	 * @param context
	 * @return boolean
	 */					
	public static boolean insertBeansIntoDBAndServerList(ServerBean serverBean, AutoWordBean autoWordBean, 
			ArrayAdapter<String> autoServerNameAdapter, ArrayAdapter<String> autoServerCmdAdapter, 
			ArrayAdapter<String> autoLmgrdServerLocAdapter, Context context){
		boolean isInserted = false;
		//insert server bean into table - servers
		// update static arraylist and db
		LicLiteData.serverBeanList.add(serverBean);
		LicLiteData.autoServerNameSet.add(autoWordBean.getAutoServerName());
		LicLiteData.autoLmstatLocSet.add(autoWordBean.getAutoLmstatLoc());
		LicLiteData.autoLmgrdServerLocSet.add(autoWordBean.getAutoLmgrdServeLoc());
		autoServerNameAdapter.notifyDataSetChanged();
		autoServerCmdAdapter.notifyDataSetChanged();
		autoLmgrdServerLocAdapter.notifyDataSetChanged();
		lGeneralDBManager = new LGeneralDBManager(new LDBHelper(context, LicLiteData.GENERAL_DB));
		if ((lGeneralDBManager.insertIntoServers(serverBean) != -1) && (lGeneralDBManager.insertIntoAutoWord(autoWordBean) != -1)) {
			isInserted = true;
			lGeneralDBManager.close();
		} else {
			// throw exception....
		}
		
		return isInserted;
	}
	
	/**
	 * insert
	 * 
	 * @param notificationBean
	 * @return
	 */
	public static boolean insertNotificationBeanIntoDBAndNotificationList(NotificationBean notificationBean, Context context){
		boolean isInserted = false;
		//update static list
		LicLiteData.notificationList.add(notificationBean);
		
		lGeneralDBManager = new LGeneralDBManager(new LDBHelper(context, LicLiteData.GENERAL_DB));
		
		//insert server bean into table - notifications
		if(lGeneralDBManager.insertIntoNotifications(notificationBean) != -1){
			isInserted = true;
			lGeneralDBManager.close();
		}else{
			//
		}
		
		
		return isInserted;
	}
	
	/**
	 * 
	 * @param context
	 * @return ArrayList<ServerBean>
	 */
	public static ArrayList<ServerBean> loadServerFromDB(Context context) {
		lGeneralDBManager = new LGeneralDBManager(new LDBHelper(context, LicLiteData.GENERAL_DB));
		ArrayList<ServerBean> serverBeans = lGeneralDBManager.queryServerBean(LicLiteData.TABLE_SERVERS);
		
	lGeneralDBManager.close();
		return serverBeans;	
	}
	
	/**
	 * query all notifications from db
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<NotificationBean> loadNotificationsFromDB(Context context){
		lGeneralDBManager = new LGeneralDBManager(new LDBHelper(context, LicLiteData.GENERAL_DB));
		ArrayList<NotificationBean> notificationBeans = lGeneralDBManager.queryNotificationBean(LicLiteData.TABLE_NOTIFICATIONS);
		
		lGeneralDBManager.close();
		return notificationBeans;
		
	}
	
	
	/**
	 * initialize auto edit text adapter
	 * 
	 *  AddServerFragment line 107
	 */
	public static void initializeAutoArrayList(Context context){
		lGeneralDBManager = new LGeneralDBManager(new LDBHelper(context, LicLiteData.GENERAL_DB));
		ArrayList<AutoWordBean> autoWordBeans = lGeneralDBManager.queryAutoWordBean(LicLiteData.TABLE_AUTO_WORD);
		
		if(autoWordBeans != null){
			for(AutoWordBean autoWordBean : autoWordBeans){
				LicLiteData.autoServerNameSet.add(autoWordBean.getAutoServerName());
				LicLiteData.autoLmstatLocSet.add(autoWordBean.getAutoLmstatLoc());
				LicLiteData.autoLmgrdServerLocSet.add(autoWordBean.getAutoLmgrdServeLoc());
			}
		}
		
		lGeneralDBManager.close();
	}
	
	/**
	 * delete a specific row from table - servers
	 * 
	 * @param context
	 * @param serverName
	 */
	public static void deleteServerFromDB(Context context, String serverName, String lmstatLoc, String lmgrdServeLoc){
		lGeneralDBManager = new LGeneralDBManager(new LDBHelper(context, LicLiteData.GENERAL_DB));
		if(lGeneralDBManager.deleteServer(serverName, lmstatLoc, lmgrdServeLoc) == 1){
			//
		}else{
			//throw exception
		}
	}
	
	public static void deleteNotificationFromDB(Context context,
			String notificationServerName, String notificationLmgrdServerLoc,
			String notificationLicenseName, String notificationLicenseUserName){
		lGeneralDBManager = new LGeneralDBManager(new LDBHelper(context, LicLiteData.GENERAL_DB));
		
		if(lGeneralDBManager.deleteNotification(notificationServerName, notificationLmgrdServerLoc,
				notificationLicenseName, notificationLicenseUserName) == 1){
			//
		}else{
			//throw exception
		}
	}
}




