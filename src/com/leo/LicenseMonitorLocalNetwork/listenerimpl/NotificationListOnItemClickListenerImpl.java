package com.leo.LicenseMonitorLocalNetwork.listenerimpl;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.adapters.NotificationListSimpleAdapter;
import com.leo.LicenseMonitorLocalNetwork.asyncs.CheckServerAvaliabilityAsyncTask;
import com.leo.LicenseMonitorLocalNetwork.asyncs.GetSshSessionAsyncTask;
import com.leo.LicenseMonitorLocalNetwork.asyncs.StartNotificationCheckAsyncTask;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationBean;
import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;
import com.leo.LicenseMonitorLocalNetwork.networks.SshManager;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.UIUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class NotificationListOnItemClickListenerImpl implements OnItemClickListener{

	private Activity activity = null;
	private NotificationListSimpleAdapter notificationListSimpleAdapter = null;
	//start notification check progress
	//login progress dialog
	private ProgressDialog progress = null;
	
	public NotificationListOnItemClickListenerImpl(Activity activity, NotificationListSimpleAdapter notificationListSimpleAdapter){
		this.activity = activity;
		this.notificationListSimpleAdapter = notificationListSimpleAdapter;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

		final int clickedPos = pos;
		
		//get the server name for rendering on login dialog and for future conn use
		final NotificationBean notificationBean = LicLiteData.notificationList.get(pos);
		final String serverName = notificationBean.getNotificationServerName();
		
		LayoutInflater factory = LayoutInflater.from(activity);
	    final View startNotificationEntryView = factory.inflate(R.layout.alert_dialog_start_notification, null);
		
	    //check the server in which the notification will be ran availability
	    ProgressBar notificationServerAvailabilityProgressBar = (ProgressBar) startNotificationEntryView
				.findViewById(R.id.progressbar_notification_availability);
		TextView notificationServerAvailabilityTextView = (TextView) startNotificationEntryView
				.findViewById(R.id.textview_notification_availability);
		CheckServerAvaliabilityAsyncTask checkServerAvaliabilityAsyncTask = new CheckServerAvaliabilityAsyncTask(
		notificationBean.getNotificationServerName(),notificationServerAvailabilityProgressBar, notificationServerAvailabilityTextView);
		checkServerAvaliabilityAsyncTask.execute();
		
		if(!notificationBean.isNotificationRunning()){
			 new AlertDialog.Builder(activity)
		     .setIconAttribute(android.R.attr.alertDialogIcon)
		     .setTitle("Start this notification")
		     .setView(startNotificationEntryView)
		     .setPositiveButton(R.string.alert_dialog_login_ok_button, new DialogInterface.OnClickListener() {
		    	 //click ok button event to popup login dialog
		     public void onClick(DialogInterface dialog, int whichButton) {
		
		         /* User clicked OK so do some stuff */
		    	 EditText userNameEditView = (EditText)startNotificationEntryView.findViewById(R.id.username_edit);
		    	 EditText pwdEditView = (EditText)startNotificationEntryView.findViewById(R.id.password_edit);
		    	 String userName = userNameEditView.getText().toString();
		    	 String passWord = pwdEditView.getText().toString();
		    	 
		    	 if(userName.equals("") || passWord.equals("")){
		    		 UIUtil.showToast(activity.getResources().getString(R.string.login_no_user_or_pwd), activity);
		    	 }else{
//get conn for background thread
		    		 
		    		 SshManager sshManager = new SshManager(serverName, userName, passWord);
		    		 progress = new ProgressDialog(activity);
		    		 progress.setCancelable(false);
		    		 progress.setMessage(LicLiteData.LOGIN_IN_PROGRESS);
		    		 progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		    		 progress.show();
		    		 StartNotificationCheckAsyncTask startNotificationCheckAsyncTask = new StartNotificationCheckAsyncTask(
							sshManager, notificationListSimpleAdapter, clickedPos, activity, progress);	 
		    		 startNotificationCheckAsyncTask.execute();
		    		 		 
		    	 }
		    	 
		     }
			 /*
			  * set cancel click event
			  */
			 }).setNegativeButton(R.string.alert_dialog_login_cancel_button, new DialogInterface.OnClickListener() {
				 
			     public void onClick(DialogInterface dialog, int whichButton) {
			    	 
	 
			    	 dialog.dismiss();
			     } 
			 }).create().show();
		}else{
			//do nothing...
		}
		
		

		
		System.out.println("notification list on click on pos --> " + pos);
		
	}
	
	
}
