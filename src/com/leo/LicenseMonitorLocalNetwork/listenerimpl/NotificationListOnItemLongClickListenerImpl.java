package com.leo.LicenseMonitorLocalNetwork.listenerimpl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.adapters.NotificationListSimpleAdapter;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationBean;
import com.leo.LicenseMonitorLocalNetwork.services.LicSenseLiteNotificationService;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.DBUtil;
import com.leo.LicenseMonitorLocalNetwork.utils.UIUtil;

public class NotificationListOnItemLongClickListenerImpl implements OnItemLongClickListener{

	private Activity activity = null;
	private NotificationListSimpleAdapter notificationListSimpleAdapter = null;
	
	
	public NotificationListOnItemLongClickListenerImpl(Activity activity, NotificationListSimpleAdapter notificationListSimpleAdapter) {
		this.activity = activity;
		this.notificationListSimpleAdapter = notificationListSimpleAdapter;
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int pos,
			long id) {
		final int clickedPos = pos;
		final NotificationBean notificationBean = LicLiteData.notificationList.get(clickedPos);
		final String notificationServerName = notificationBean.getNotificationServerName();
		final String notificationLmgrdServerLoc = notificationBean.getNotificationLmgrdServerLoc();
		final String notificationLicenseName = notificationBean.getNotificationLicenseName();
		final String notificationLicenseUserName = notificationBean.getNotificationLicenseUserName();
		
		
		if(notificationBean.isNotificationRunning()){
			new AlertDialog.Builder(activity)
	        .setTitle(R.string.on_long_click_title)
	        .setItems(R.array.notification_running_list_item, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	//UIUtil.logOutTheServer(clickedPos, serverListSimpleAdapter); 
				    System.out.println("stop this running notification ..."); 
				    
				    stopNotificationService(clickedPos);

				    
	            }
	        }).create().show();
		}else{
			new AlertDialog.Builder(activity)
		    .setTitle(R.string.on_long_click_title)
		    .setItems(R.array.list_menu_not_login_not_has_history_items, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            /* delete*/
					deleteFromList(clickedPos, notificationServerName,notificationLmgrdServerLoc, 
									notificationLicenseName, notificationLicenseUserName);
		        }
		    }).create().show();
		}
		
		return false;
	}

	/**
	 * Stop running notification service
	 * 
	 * @param position
	 */
	private void stopNotificationService(int position){
		NotificationBean notificationBean = LicLiteData.notificationList.get(position);
		if(notificationBean.getConnection() != null){
			notificationBean.setConnection(null);
			notificationBean.setNotificationRunning(false);
			notificationListSimpleAdapter.notifyDataSetChanged();
		}
		
		//stop service
		activity.stopService(new Intent(activity, LicSenseLiteNotificationService.class));
	}
	
	
	
	private void deleteFromList(int clickedPos,
			String notificationServerName, String notificationLmgrdServerLoc,
			String notificationLicenseName, String notificationLicenseUserName) {
		
		NotificationListSimpleAdapter.remove(clickedPos);
		this.notificationListSimpleAdapter.notifyDataSetChanged();
		
		LicLiteData.notificationList.remove(clickedPos);
		//delete from db
		DBUtil.deleteNotificationFromDB(activity, notificationServerName,
				notificationLmgrdServerLoc, notificationLicenseName, notificationLicenseUserName);
	}
	
}




