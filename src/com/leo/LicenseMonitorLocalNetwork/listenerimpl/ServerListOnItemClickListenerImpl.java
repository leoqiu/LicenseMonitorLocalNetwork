package com.leo.LicenseMonitorLocalNetwork.listenerimpl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.activities.RenderInTimeResultActivity;
import com.leo.LicenseMonitorLocalNetwork.activities.TempActivity;
import com.leo.LicenseMonitorLocalNetwork.adapters.ServerListSimpleAdapter;
import com.leo.LicenseMonitorLocalNetwork.asyncs.GetSshSessionAsyncTask;
import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;
import com.leo.LicenseMonitorLocalNetwork.networks.SshManager;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.UIUtil;

public class ServerListOnItemClickListenerImpl implements OnItemClickListener{

	
	private Activity activity = null;
	private ServerListSimpleAdapter serverListSimpleAdapter = null;
	//login progress dialog
	private ProgressDialog progress = null;
	
	public ServerListOnItemClickListenerImpl(Activity activity, ServerListSimpleAdapter serverListSimpleAdapter){
		this.activity = activity;
		this.serverListSimpleAdapter = serverListSimpleAdapter;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		 final int clickedPos = position;
		
		 LayoutInflater factory = LayoutInflater.from(activity);
		 final View textEntryView = factory.inflate(R.layout.alert_dialog_login, null);
		 //get the server name for rendering on login dialog and for future conn use
		 ServerBean serverBean = LicLiteData.serverBeanList.get(position);
		 
//TEMP...		 
		 if(serverBean.getIsLogin() != LicLiteData.IS_LOGINED){
			 final String serverName = serverBean.getServerName();
			 new AlertDialog.Builder(activity)
			     .setIconAttribute(android.R.attr.alertDialogIcon)
			     .setTitle(LicLiteData.LOGIN_DIALOG + serverName)
			     .setView(textEntryView)
			     .setPositiveButton(R.string.alert_dialog_login_ok_button, new DialogInterface.OnClickListener() {
			    	 //click ok button event to popup login dialog
			     public void onClick(DialogInterface dialog, int whichButton) {
			
			         /* User clicked OK so do some stuff */
			    	 EditText userNameEditView = (EditText)textEntryView.findViewById(R.id.username_edit);
			    	 EditText pwdEditView = (EditText)textEntryView.findViewById(R.id.password_edit);
			    	 String userName = userNameEditView.getText().toString();
			    	 String passWord = pwdEditView.getText().toString();
			    	 
			    	 if(userName.equals("") || passWord.equals("")){
			    		 UIUtil.showToast(activity.getResources().getString(R.string.login_no_user_or_pwd), activity);
			    	 }else{
			    		 SshManager sshManager = new SshManager(serverName, userName, passWord);
			    		 progress = new ProgressDialog(activity);
			    		 progress.setCancelable(false);
			    		 progress.setMessage(LicLiteData.LOGIN_IN_PROGRESS);
			    		 progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			    		 progress.show();
						 GetSshSessionAsyncTask getSshSessionAsyncTask = new GetSshSessionAsyncTask(
								sshManager, serverListSimpleAdapter, clickedPos, activity, progress);	 
						 getSshSessionAsyncTask.execute();
			
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
System.out.println("login .........");
//FragmentTransaction transaction =  activity.getFragmentManager().beginTransaction();
//transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
//        R.animator.fragment_slide_left_exit,
//        R.animator.fragment_slide_right_enter,
//        R.animator.fragment_slide_right_exit);
//transaction.replace(R.id.activity_start_content, RenderInTimeResultFragment.getInstance());
//transaction.addToBackStack(null);
//if(transaction != null)
//	transaction.commit();
//TextView titleBar = (TextView) activity.findViewById(R.id.title_bar_value);
//titleBar.setText("In time result");
//Intent intent = new Intent(activity, TempActivity.class);
Intent intent = new Intent(activity, RenderInTimeResultActivity.class);
intent.putExtra(LicLiteData.SERVER_LOGIN_INDEX, clickedPos);
//get in time result from server
intent.putExtra(LicLiteData.RESULT_FLAG, LicLiteData.IN_TIME_RESULT);
//intent.putExtra(LicLiteData.IS_HISTORY_RESULT, false);
activity.startActivity(intent);
activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);





		 }
		 
		 
		 
		 
		 
	}

	
	
	
	
	
}
