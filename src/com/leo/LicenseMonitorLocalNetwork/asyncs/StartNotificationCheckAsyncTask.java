package com.leo.LicenseMonitorLocalNetwork.asyncs;

import ch.ethz.ssh2.Connection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.adapters.NotificationListSimpleAdapter;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationBean;
import com.leo.LicenseMonitorLocalNetwork.networks.SshManager;
import com.leo.LicenseMonitorLocalNetwork.services.LicSenseLiteNotificationService;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.UIUtil;

public class StartNotificationCheckAsyncTask extends AsyncTask<Void, Void, Boolean>{
	
	private static final int TIME_OUT_NUM = 10;
	
	private SshManager sshManager = null;
	private int position = -999;
	private Activity activity = null;
	private NotificationListSimpleAdapter notificationListSimpleAdapter = null;
	
	private ProgressDialog progress = null;
	
	public StartNotificationCheckAsyncTask(SshManager sshManager,
			NotificationListSimpleAdapter notificationListSimpleAdapter, int position,
			Activity activity, ProgressDialog progress) {
		this.sshManager = sshManager;
		this.notificationListSimpleAdapter = notificationListSimpleAdapter;
		this.position = position;
		this.activity = activity;
		this.progress = progress;
	}
	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		Boolean isNotificationStarted = false;
		
		NotificationBean notificationBean = LicLiteData.notificationList.get(position);
		int timeOut = TIME_OUT_NUM * 1000;
		
		Connection connection = sshManager.getAuthenticatedSshConn(timeOut);
		
		//connection != null, means login successfully
		if(connection != null){
			isNotificationStarted = true;
			//set static serverBeanList
			notificationBean.setConnection(connection);
			notificationBean.setNotificationRunning(true);
			
System.out.println("start notification connection is --->  timeout is ----> " + connection + "  " + timeOut );			
			//start service
			Intent licServiceItent = new Intent(activity, LicSenseLiteNotificationService.class);
			licServiceItent.putExtra(LicLiteData.NOTIFICATION_RUNNING_INDEX, position);
			activity.startService(licServiceItent);
		}	
		
		return isNotificationStarted;

	}

	/**
	 * We can not update main thread in doInBackgroud, do it in onPostExecute
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		progress.dismiss();
		if(result.equals(true)){
			//notify ServerListSimpleAdapter data set changed	    	
			notificationListSimpleAdapter.notifyDataSetChanged();
	    	
			UIUtil.showToast(activity.getResources().getString(
					R.string.login_successfully),  activity);
		}else
			UIUtil.showToast(activity.getResources().getString(
					R.string.login_failed),  activity);
	}


}





