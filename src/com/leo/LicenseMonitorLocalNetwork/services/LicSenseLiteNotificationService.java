package com.leo.LicenseMonitorLocalNetwork.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.activities.RenderNotificationResultActivity;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationBean;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationCmpBean;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationCmpResultBean;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.NetWorkUtil;
import com.leo.LicenseMonitorLocalNetwork.utils.Parser;
import com.leo.LicenseMonitorLocalNetwork.utils.UIUtil;

public class LicSenseLiteNotificationService extends Service {

	private int serviceOnWhichIndex = -999;
	private NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.pic_m).setContentTitle("this is my notificatin title")
			.setContentText("this is my notificatin text")
			//define notification sound
			.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)); 
	
	
	@Override
	public void onCreate() {
		System.out.println("### notification service onCreate()...");
		//this.serviceOnWhichIndex = Intent.get
	}

	@Override
	public void onDestroy() {
		System.out.println("### notification service onDestroy()... " + Thread.currentThread().getId());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("### Running notification onStartCommand");
		//continue executing...
		serviceOnWhichIndex = intent.getIntExtra(LicLiteData.NOTIFICATION_RUNNING_INDEX, -1);
		
		Runnable myThread = new Runnable(){

			int notificationBeanListIndex = serviceOnWhichIndex;
			@Override
			public void run() {
				try {
					//wait for later computation to complete
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				NotificationBean notificationBean = LicLiteData.notificationList.get(notificationBeanListIndex);
				int notificationPollingPeriod = Integer.parseInt(notificationBean.getNotificationPollingPeriod()) * 1000;
				while(notificationBean.getConnection() != null){
					
					monitoringLicenseAndSendNotification(notificationBean);

					try {
						Thread.sleep(notificationPollingPeriod);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			
		};
		new Thread(myThread).start();
		
//		return Service.START_CONTINUATION_MASK;
		return Service.START_NOT_STICKY;
//		return Service.START_STICKY;
//		return Service.START_REDELIVER_INTENT;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 
	 * 1. download lmstat file
	 * 2. parse and compare current and previous notification bean
	 * 3. send notification if there is any difference between the two beans
	 * 
	 */
	private void monitoringLicenseAndSendNotification(NotificationBean notificationBean){
		
//System.out.println("execute cmd --  " + notificationBean.getConnection() + "  ---  " + notificationBean.getNotificationLmstatCmdLoc());		
		
		//download lmstat file and delete it remotely		
//		NetWorkUtil.executeSCPCmdForNotification(notificationBean.getConnection(),
//			notificationBean.getNotificationLmstatCmdLoc(),LicLiteData.licLiteDataTmpDir);

//local one_server.log and two_server.log test
if(isGetingOneServerFile()){
System.out.println("downloading one_server.log ...");
	NetWorkUtil.executeSCPCmdForNotification(notificationBean.getConnection(),
			notificationBean.getNotificationLmstatCmdLoc(),LicLiteData.licLiteDataTmpDir);
}else{
	String cmdTwoServerLog = "cat /home/leo/Desktop/log/two_server.log > /home/leo/Desktop/log/notificationtmp";
	NetWorkUtil.executeSCPCmdForNotification(notificationBean.getConnection(),
			cmdTwoServerLog , LicLiteData.licLiteDataTmpDir);
System.out.println("downloading two_server.log ...");
}
		
		
		
		//parse file to generate notification bean
		String notificationTmpFile = LicLiteData.licLiteDataTmpDir + File.separator + LicLiteData.NOTIFICATION_TMP_FILE;
//System.out.println("notificationTmpFile --> " + notificationTmpFile);
		NotificationCmpBean notificationCmpBean = Parser.parseDownloadDataFileToNotificationCmpBean(notificationTmpFile,
			notificationBean.getNotificationLicenseName(),notificationBean.getNotificationLicenseUserName());
//System.out.println("notificationCmpBean --> " + notificationCmpBean.toString());
//System.out.println("notificationCmpBean --> " + notificationTmpFile);
//System.out.println("NotificationLicenseName --> " + notificationBean.getNotificationLicenseName());
//System.out.println("NotificationLicenseUserName --> " + notificationBean.getNotificationLicenseUserName());
//System.out.println("licenseName, userName, cmpbeanStr -> " + notificationBean.getNotificationLicenseName() 
//+ " -- " + notificationBean.getNotificationLicenseUserName() + "\n" + notificationCmpBean.toString());		
		if(notificationCmpBean.getFeatureNameCmp() == null){
			//stop service
			
			//toast say : "wrong feature name or user name"
		}else {
			if(LicLiteData.previousNotificationBean == null){
				LicLiteData.previousNotificationBean = notificationCmpBean;
			} else {
				//compare previous and current bean to see if need to send notification
				//compare LicLiteData.previousNotificationBean as 1st bean with notificationCmpBean as 2nd bean
//System.out.println("LicLiteData.previousNotificationBean --> " + LicLiteData.previousNotificationBean.toString());
//System.out.println("notificationCmpBean --> " + notificationCmpBean.toString());
				ArrayList<NotificationCmpResultBean> tmpCmpResultBeanList = UIUtil
					.comparePreviousWithSecondNotificationBean(LicLiteData.previousNotificationBean , notificationCmpBean);
//System.out.println("tmpCmpResultBeanList --> " + tmpCmpResultBeanList);				
				if(tmpCmpResultBeanList.size() > 0){
//System.out.println("sending notification...");
					LicLiteData.notificationCmpResultBeanList = tmpCmpResultBeanList;
					
					sendNotification();
				} else {
//System.out.println("not sending notification...");
				}
				//set the ischanged file to true for future comparison
				for(int i = 0; i < notificationCmpBean.getUserUsageListCmp().size(); i++){
					notificationCmpBean.getUserUsageListCmp().get(i).setChanged(true);
				}
				//notificationCmpBean overwrite LicLiteData.previousNotificationBean
				LicLiteData.previousNotificationBean = notificationCmpBean;
			} 
			
		}
		
		//delete download local lmstat file
		//this one doesn't needed anymore
		
		
	}

	/**
	 * Tmp method for local test
	 * @return
	 */
private boolean isGetingOneServerFile(){
	Random ran = new Random();
	if((ran.nextInt() % 2) == 0)
		return true;
	else
		return false;
}
	
	/**
	 * 
	 * 
	 *
	 */
	private  void sendNotification(){
		int myNotificationId = -99;
		//create a notification
	
		//create an explicit intent for a activity in your app
		Intent resultIntent = new Intent(this , RenderNotificationResultActivity.class);
		NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(RenderNotificationResultActivity.class);
		
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);		
		mBuilder.setContentIntent(resultPendingIntent);
		//dismiss notification once click on it
		mBuilder.setAutoCancel(true);
		
		int numOfNotification = LicLiteData.notificationCmpResultBeanList.size();
		mBuilder.setContentText("checkin checkout licenses number + " + numOfNotification).setNumber(numOfNotification);
		mNotificationManager.notify(myNotificationId, mBuilder.build());
	//	mNotificationManager.notify(R.drawable.pic_m, mBuilder.build());
		
	}	


}










