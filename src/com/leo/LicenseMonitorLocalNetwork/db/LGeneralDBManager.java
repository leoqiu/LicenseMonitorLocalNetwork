package com.leo.LicenseMonitorLocalNetwork.db;


import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leo.LicenseMonitorLocalNetwork.beans.AutoWordBean;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationBean;
import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;

/**
 * LDBManager encapsulates all the db related
 * operations
 * 
 * @author leo
 *
 */
public class LGeneralDBManager {

	private SQLiteDatabase dbReadable;
	private SQLiteDatabase dbWritable;
	
	public LGeneralDBManager(LDBHelper dbHelper){
		dbReadable = dbHelper.getReadableDatabase();
		dbWritable = dbHelper.getWritableDatabase();
	}
	
	/**
	 * Check if a table exist or not
	 * 
	 * @param tableName
	 * @return boolean
	 */
	private boolean isTableExist(String tableName){
		boolean isExist = false;

		Cursor cursor = null;
		try {
			cursor = dbReadable.rawQuery("select count(*) from "+ tableName, null);
			isExist = true;
		} catch (Exception e) {
			isExist = false;
		} finally {
			if(cursor != null)
				cursor.close();
		}
		
		return isExist;
	}
	
	/**
	 * Create table - servers
	 */
	private void createTableServers() {
		
		String sqlServers = "CREATE TABLE " + LicLiteData.TABLE_SERVERS + 
					       " (_id  INTEGER  PRIMARY KEY, " +
						   LicLiteData.SERVER_NAME +" varchar(30), " +
						   LicLiteData.LMSTAT_CMD_LOC + " varchar(30), " +
						   LicLiteData.LMGRD_SERVER_LOC + " varchar(10), " +
						   LicLiteData.SERVER_LOC + " varchar(30), " +
						   LicLiteData.SERVER_TIME_ZONE + " varchar(20), " +
						   LicLiteData.SERVER_TIME_OUT + " varchar(3), " +
						   LicLiteData.SERVER_RETRY_TIMES + " varchar(3), " +
						   LicLiteData.TIME_STAMP + " varchar(30))";
		
		dbReadable.execSQL(sqlServers);
	}
	
	/**
	 * Create table - auto_word
	 */	
	private void createTableAutoWord(){
		String sqlAutoWord = "CREATE TABLE " + LicLiteData.TABLE_AUTO_WORD + 
				" (_id INTEGER PRIMARY KEY, " +
				LicLiteData.AUTO_SERVER_NAME + " varchar(15), " +
				LicLiteData.AUTO_LMSTAT_CMD_LOC +" varchar(15), " +
				LicLiteData.AUTO_LMGRD_SERVER_LOC +" varchar(15))";	
		
		dbReadable.execSQL(sqlAutoWord);
		

	}
	
	/**
	 * Create table - notifications
	 * 
	 */
	private void createTableNotifications(){
		String sqlNotifications = "CREATE TABLE " + LicLiteData.TABLE_NOTIFICATIONS + 
				" (_id INTEGER PRIMARY KEY, " +
				LicLiteData.NOTIFICATION_SERVER_NAME + " varchar(15), " +
				LicLiteData.NOTIFICATION_LMSTAT_CMD_LOC +" varchar(15), " +
				LicLiteData.NOTIFICATION_LMGRD_SERVER_LOC +" varchar(15), " +
				LicLiteData.NOTIFICATION_LICENSE_NAME +" varchar(15), " +
				LicLiteData.NOTIFICATION_LICENSE_USER_NAME +" varchar(15), " +
				LicLiteData.NOTIFICATION_POLLING_PERIOD +" varchar(15))";
		
		dbReadable.execSQL(sqlNotifications);
	}
	
	
	/**
	 * Insert a server bean intgho the table - servers
	 * @param
	 */
	public long insertIntoAutoWord(AutoWordBean autoWordBean){
		
		if(!isTableExist(LicLiteData.TABLE_AUTO_WORD)){
			createTableAutoWord();
System.out.println("create table serves...");
		}
		
		ContentValues values = new ContentValues();	
		values.put(LicLiteData.AUTO_SERVER_NAME, autoWordBean.getAutoServerName());
		values.put(LicLiteData.AUTO_LMSTAT_CMD_LOC, autoWordBean.getAutoLmstatLoc());
		values.put(LicLiteData.AUTO_LMGRD_SERVER_LOC, autoWordBean.getAutoLmgrdServeLoc());
		
		long isInserted = dbWritable.insert(LicLiteData.TABLE_AUTO_WORD, null, values);
		return isInserted;
	}
	
	/**
	 * Insert a server bean intgho the table - servers
	 * @param serverBean
	 */
	public long insertIntoServers(ServerBean serverBean){
		
		if(!isTableExist(LicLiteData.TABLE_SERVERS)){
			createTableServers();
System.out.println("create table serves...");
		}
		
		ContentValues values = new ContentValues();
		
		values.put(LicLiteData.SERVER_NAME, serverBean.getServerName());
		values.put(LicLiteData.LMSTAT_CMD_LOC, serverBean.getLmstatLoc());
		values.put(LicLiteData.LMGRD_SERVER_LOC, serverBean.getLmgrdServeLoc());
		values.put(LicLiteData.SERVER_LOC, serverBean.getServerLoc());
		values.put(LicLiteData.SERVER_TIME_ZONE, serverBean.getServerTimeZone());
		values.put(LicLiteData.SERVER_TIME_OUT, serverBean.getTimeOut());
		values.put(LicLiteData.SERVER_RETRY_TIMES, serverBean.getRetryTimes());
		values.put(LicLiteData.TIME_STAMP, serverBean.getTimeStamp());
		
		long isInserted = dbWritable.insert(LicLiteData.TABLE_SERVERS, null, values);
		return isInserted;
	}
	
	/**
	 * insert a NotificationBean into table notifications
	 * 
	 * @param notificationBean
	 * @return
	 */
	public long insertIntoNotifications(NotificationBean notificationBean){
		
		if(!isTableExist(LicLiteData.TABLE_NOTIFICATIONS)){
			createTableNotifications();
System.out.println("create table notifications...");		
		}
		
		ContentValues values = new ContentValues();
		values.put(LicLiteData.NOTIFICATION_SERVER_NAME, notificationBean.getNotificationServerName());
		values.put(LicLiteData.NOTIFICATION_LMSTAT_CMD_LOC, notificationBean.getNotificationLmstatCmdLoc());
		values.put(LicLiteData.NOTIFICATION_LMGRD_SERVER_LOC, notificationBean.getNotificationLmgrdServerLoc());
		values.put(LicLiteData.NOTIFICATION_LICENSE_NAME, notificationBean.getNotificationLicenseName());
		values.put(LicLiteData.NOTIFICATION_LICENSE_USER_NAME, notificationBean.getNotificationLicenseUserName());
		values.put(LicLiteData.NOTIFICATION_POLLING_PERIOD, notificationBean.getNotificationPollingPeriod());
		
		long isInserted = dbWritable.insert(LicLiteData.TABLE_NOTIFICATIONS, null, values);
		return isInserted;
	}

	/**
	 * delete a row by serverName from database
	 * 
	 * @param serverName
	 */
	public int deleteServer(String serverName, String lmstatLoc, String lmgrdServeLoc){
		return dbWritable.delete("servers", LicLiteData.SERVER_NAME+"=? AND " + LicLiteData.LMSTAT_CMD_LOC + "=? AND " + LicLiteData.LMGRD_SERVER_LOC + "=?"
				, new String[]{serverName, lmstatLoc, lmgrdServeLoc});
	}
	

	/**
	 * 
	 * delete notification from db by params
	 * 
	 * @param notificationServerName
	 * @param notificationLmgrdServerLoc
	 * @param notificationLicenseName
	 * @param notificationLicenseUserName
	 * @return
	 */
	public int deleteNotification(String notificationServerName, String notificationLmgrdServerLoc,
			String notificationLicenseName, String notificationLicenseUserName){
		return dbWritable.delete("notifications",
				LicLiteData.NOTIFICATION_SERVER_NAME + "=? AND "
						+ LicLiteData.NOTIFICATION_LMGRD_SERVER_LOC + "=? AND "
						+ LicLiteData.NOTIFICATION_LICENSE_NAME + "=? AND "
						+ LicLiteData.NOTIFICATION_LICENSE_USER_NAME + "=?",
				new String[] { notificationServerName,
						notificationLmgrdServerLoc, notificationLicenseName,
						notificationLicenseUserName });
	}
	
	public ArrayList<AutoWordBean> queryAutoWordBean(String tableAutoWord){
		ArrayList<AutoWordBean> autoWordBeans = new ArrayList<AutoWordBean>();
		
		//if no such table return null
		if(!isTableExist(tableAutoWord)){
			System.out.println("table doesn't exist..");
			return null;
		}
		
		Cursor cursor = dbReadable.query(LicLiteData.TABLE_AUTO_WORD, 
				new String[]{LicLiteData.AUTO_SERVER_NAME,LicLiteData.AUTO_LMSTAT_CMD_LOC, LicLiteData.AUTO_LMGRD_SERVER_LOC}, 
				null, null, null, null, null);
		
		while(cursor.moveToNext()){
			String autoWordName = cursor.getString(cursor.getColumnIndex(LicLiteData.AUTO_SERVER_NAME));
			String autoLmstatLoc = cursor.getString(cursor.getColumnIndex(LicLiteData.AUTO_LMSTAT_CMD_LOC));
			String autoLmgrdServerLoc = cursor.getString(cursor.getColumnIndex(LicLiteData.AUTO_LMGRD_SERVER_LOC));

			AutoWordBean autoWordBean = new AutoWordBean(autoWordName, autoLmstatLoc, autoLmgrdServerLoc);
			autoWordBeans.add(autoWordBean);
			
		}
		
		return autoWordBeans;
	}
	
	/**
	 * Query all the rows from database and return a 
	 * array list of @ServerBean
	 * 
	 * @return ArrayList<ServerBean>
	 */
	public ArrayList<ServerBean> queryServerBean(String tableServer){
		
		ArrayList<ServerBean> serverBeans = new ArrayList<ServerBean>();
		
		//if no such table return null
		if(!isTableExist(tableServer)){
			System.out.println("table doesn't exist..");
			return null;
		}
		
		//Cursor cursor = db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
		//Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		//Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		Cursor cursor = dbReadable.query(LicLiteData.TABLE_SERVERS, 
				new String[]{LicLiteData.SERVER_NAME,LicLiteData.LMSTAT_CMD_LOC, LicLiteData.LMGRD_SERVER_LOC, 
				LicLiteData.SERVER_LOC, LicLiteData.SERVER_TIME_ZONE, LicLiteData.SERVER_TIME_OUT, 
				LicLiteData.SERVER_RETRY_TIMES, LicLiteData.TIME_STAMP}, null, null, null, null, LicLiteData.TIME_STAMP);
		while(cursor.moveToNext()){
			String serverName = cursor.getString(cursor.getColumnIndex(LicLiteData.SERVER_NAME));
			String serverCmd = cursor.getString(cursor.getColumnIndex(LicLiteData.LMSTAT_CMD_LOC));
			String serverPort = cursor.getString(cursor.getColumnIndex(LicLiteData.LMGRD_SERVER_LOC));
			String serverLoc = cursor.getString(cursor.getColumnIndex(LicLiteData.SERVER_LOC));
			String serverTimeZone = cursor.getString(cursor.getColumnIndex(LicLiteData.SERVER_TIME_ZONE));
			String timeOut = cursor.getString(cursor.getColumnIndex(LicLiteData.SERVER_TIME_OUT));
			String retryTimes = cursor.getString(cursor.getColumnIndex(LicLiteData.SERVER_RETRY_TIMES));
			String timeStamp = cursor.getString(cursor.getColumnIndex(LicLiteData.TIME_STAMP));

			ServerBean serverBean = new ServerBean(serverName, serverCmd,
					serverPort, serverLoc, serverTimeZone, timeOut, retryTimes, timeStamp);
			
			serverBeans.add(serverBean);
			
		}
		return serverBeans;
	}
	
	public ArrayList<NotificationBean> queryNotificationBean(String tableServer){
		
		ArrayList<NotificationBean> notificationBeans = new ArrayList<NotificationBean>();
		
		//if no such table return null
		if(!isTableExist(tableServer)){
			System.out.println("table doesn't exist..");
			return null;
		}
		
		//Cursor cursor = db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
		//Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		//Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
			Cursor cursor = dbReadable.query(LicLiteData.TABLE_NOTIFICATIONS, 
					new String[]{
					LicLiteData.NOTIFICATION_SERVER_NAME,LicLiteData.NOTIFICATION_LMSTAT_CMD_LOC, 
					LicLiteData.NOTIFICATION_LMGRD_SERVER_LOC, LicLiteData.NOTIFICATION_LICENSE_NAME,
					LicLiteData.NOTIFICATION_LICENSE_USER_NAME, LicLiteData.NOTIFICATION_POLLING_PERIOD}
					, null, null, null, null, null);
		while(cursor.moveToNext()){
			String notificationServerName = cursor.getString(cursor.getColumnIndex(LicLiteData.NOTIFICATION_SERVER_NAME));
			String notificationLmstatCmdLoc = cursor.getString(cursor.getColumnIndex(LicLiteData.NOTIFICATION_LMSTAT_CMD_LOC));
			String notificationLmgrdServerLoc = cursor.getString(cursor.getColumnIndex(LicLiteData.NOTIFICATION_LMGRD_SERVER_LOC));
			String notificationLicenseName = cursor.getString(cursor.getColumnIndex(LicLiteData.NOTIFICATION_LICENSE_NAME));
			String notificationiLicenseUserName = cursor.getString(cursor.getColumnIndex(LicLiteData.NOTIFICATION_LICENSE_USER_NAME));
			String notificationPollingPeriod = cursor.getString(cursor.getColumnIndex(LicLiteData.NOTIFICATION_POLLING_PERIOD));

			NotificationBean notificationBean = new NotificationBean(notificationServerName, notificationLmstatCmdLoc,
					notificationLmgrdServerLoc, notificationLicenseName, notificationiLicenseUserName, notificationPollingPeriod);
			
			notificationBeans.add(notificationBean);
			
		}
		return notificationBeans;
	}
	
	/**
	 * close db connection after db opration
	 */
	public void close(){
		if(dbReadable != null && dbReadable.isOpen()){
			dbReadable.close();
			dbReadable = null;
		}
		
		if(dbWritable != null && dbWritable.isOpen()){
			dbWritable.close();
			dbWritable = null;
		}
	}

}











