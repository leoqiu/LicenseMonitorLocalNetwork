package com.leo.LicenseMonitorLocalNetwork.staticdata;

/**
 * 
 */

import java.util.ArrayList;
import java.util.HashSet;

import com.leo.LicenseMonitorLocalNetwork.beans.NotificationBean;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationCmpBean;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationCmpResultBean;
import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;

/**
 * LicLiteData, for all the program global data
 * 
 * @author leo
 *
 */
public final class LicLiteData {
	
	//LicLicense server related
	public final static int DEFAULT_SERVER_PORT = 20;
	
	//ServerBean separator
	public final static String SERVER_BEAN_ITEM_SEPARATOR = ",";
	public final static String SERVER_BEAN_SEPARATOR = ";";
	
	public final static String SERVER_LOGIN_INDEX = "serverLoginIndex";
	

	
	//cmd array
//	public static final String[] CMDS = { "ls -a", "ls -l", "ls -lrt",
//		"ifconfig", "pwd", "./WorkingSimpton/lmutil lmstat -a", "./LicSense/lmutil lmstat -a" };
	
	//progress bar message
	public final static String LOGIN_IN_PROGRESS = "logining to server...";
	
	//login dialog
	public final static String LOGIN_DIALOG = "Login to ";
	
	//
	public final static String LIST_MENU_TITLE = "Do something to ";
	
	//for app data write and read
	public static final String BASE_DIR = "/data/data/com.leo.licsenselitev3";
	
	//server history related
	public final static String SERVER_INDEX = "serverIndex";

	//different types of show results
	public final static String IS_HISTORY_RESULT = "IS_HISTORY_RESULT";
	
	public final static String HISTORY_FILE_NAME = "HISTORY_FILE_NAME";
	


	
	//ServerBean
	public final static int IS_LOGINED = 1;
	public final static int IS_NOT_LOGINED = 0;
	
	//
	public static ArrayList<ServerBean> serverBeanList = new ArrayList<ServerBean>();
	
	//AutoCompleteEditText arrayadapter
//	public static String[] autoServerNameArr = {"192.168.20.133", "192.168.1.111", "cadance.xxx.com", 
//		"192.168.20.121", "cadance.zzz.com"};
//	public static String[] autoLmgrdCmdArr = {"ls -a", "ls -l", "ls -lrt",
//		"ifconfig", "pwd", "./WorkingSimpton/lmutil lmstat -a", "./LicSense/lmutil lmstat -a"};
	public static HashSet<String> autoServerNameSet = new HashSet<String>();
	public static HashSet<String> autoLmstatLocSet = new HashSet<String>();
	public static HashSet<String> autoLmgrdServerLocSet = new HashSet<String>();
	
	/*
	 * database
	 */
	//public final static LGeneralDBManager lGeneralDBManager = new LGeneralDBManager(new LDBHelper(getActivity(), LicLiteData.GENERAL_DB));
	
	public final static String GENERAL_DB = "general_db";
	
	//servers - table
	public final static String TABLE_SERVERS = "servers";
	public final static String SERVER_NAME = "server_name";
	public final static String LMSTAT_CMD_LOC = "lmstat_cmd_loc";
	public final static String LMGRD_SERVER_LOC = "lmgrd_server_loc";
	public final static String SERVER_LOC = "server_loc";
	public final static String SERVER_TIME_ZONE = "server_time_zone";
	public final static String SERVER_TIME_OUT = "server_time_out";
	public final static String SERVER_RETRY_TIMES = "server_retry_times";
	public final static String TIME_STAMP = "time_stamp";

	//auto_word - table
	public final static String TABLE_AUTO_WORD = "auto_word";
	public final static String AUTO_SERVER_NAME = "auto_server_name";
	public final static String AUTO_LMSTAT_CMD_LOC = "auto_lmstat_cmd_loc";
	public final static String AUTO_LMGRD_SERVER_LOC = "auto_lmgrd_server_loc";
	
	//notification table	
	public final static String TABLE_NOTIFICATIONS = "notifications";
	public final static String NOTIFICATION_SERVER_NAME = "notification_server_name";
	public final static String NOTIFICATION_LMSTAT_CMD_LOC = "notification_lmstat_cmd_loc";
	public final static String NOTIFICATION_LMGRD_SERVER_LOC = "notification_lmgrd_server_loc";
	public final static String NOTIFICATION_LICENSE_NAME = "notification_license_name";
	public final static String NOTIFICATION_LICENSE_USER_NAME = "notification_license_user_name";
	public final static String NOTIFICATION_POLLING_PERIOD = "notification_polling_period";
	
	//notification list
	public static ArrayList<NotificationBean> notificationList = new ArrayList<NotificationBean>();
	public static final String NOTIFICATION_RUNNING_INDEX = "notification_running_index";
	
	//loading in time results
	public static String licLiteDataDir = null;
	public static String licLiteDataTmpDir = null;
	public static final String DIR = "liclitedata";
	public static final String TMP_DIR = "tmp";
	
	
	//performance timer
	public static int startTime = 0;
	public static int endTime = 0;

	//for autocomplete text view dropdown list width
	public static int SCREEN_WIDTH = 0;
	
	//loading progress bar max length
	public final static int LOADING_PROGRESS_MAX = 6;
	public final static int LOADING_PROGRESS_LOCAL_MAX = 4;
	public final static int LOADING_PROGRESS_DOWNLOAD_DATA_FROM_SERVER_TIME = 2;
	public final static int LOADING_PROGRESS_PARSING_TIME = 3;
	public final static int LOADING_PROGRESS_ITERATING_TIME = 1;
	public final static int PARSING_DATA = -199;
	public final static int ITERATING_DATA = -200;
	
	//cmd
	public final static String CMD_GET_OUT_PUT_FILE_NAME = "ls /home/leo/Desktop/log -a | grep licliteserver-*";
	
	//loading data file remotely and locally
	public final static String RESULT_FLAG = "result_flag";
	public final static String LOCAL_DATA_FILE_NAME = "local_data_file_name";
	public final static int IN_TIME_RESULT = 0;
	public final static int LOCAL_RESULT = 1;
	
	public final static int CONNECTION_AVALIABILITY_TIMEOUT = 150;
	
	//data size management
	public final static double DATA_SIZE_UPPPER_LIMIT = 20.0;
	public final static int NUMBER_DATA_FILE_TO_BE_DELETE = 5;
	
	public final static String DEFAULT_PARSED_INFO = "cannot display";
	
	//notification monitoring related
	//previous notification bean, static
	public final static String NOTIFICATION_TMP_FILE = "notificationtmp";
	public static NotificationCmpBean previousNotificationBean = null;
	public static ArrayList<NotificationCmpResultBean> notificationCmpResultBeanList = null;
}









