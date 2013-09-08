package com.leo.LicenseMonitorLocalNetwork.activities;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.adapters.GroupFragmentImageAdapter;
import com.leo.LicenseMonitorLocalNetwork.fragments.AddServerFragment;
import com.leo.LicenseMonitorLocalNetwork.fragments.HistoryFragment;
import com.leo.LicenseMonitorLocalNetwork.fragments.AddNotificationFragment;
import com.leo.LicenseMonitorLocalNetwork.fragments.NotificationListFragment;
import com.leo.LicenseMonitorLocalNetwork.fragments.ServersFragment;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.UIUtil;



public class StartGroupActivity extends Activity {

	private GridView gridviewToolbar; // group grid tool bar
	private GroupFragmentImageAdapter groupFragmentImageAdapter = null; // picture adapter
//	private TextView content = null; // sub activity area
	private int group_img[] = new int[] { R.drawable.group_servers, R.drawable.group_notification_list, R.drawable.group_add,
			R.drawable.group_notifications, R.drawable.group_history}; // groupActivityImageAdapter images resources
	private int screenWidth = 0; // screen width
	private int screenHeight = 0; // screen height
	
	private TextView titleBar = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
    	
		//check sdcard storage
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){// use external stroage
			
			String mntSdcardDir = Environment.getExternalStorageDirectory().toString();
			//local lmstat file
			File dataDir = new File(mntSdcardDir + File.separator + LicLiteData.DIR);
			//local tmp file
			File tmpDataDir = new File(mntSdcardDir + File.separator + LicLiteData.DIR + File.separator + LicLiteData.TMP_DIR);
			//create local lmstat dir
			if(!dataDir.exists() || (mntSdcardDir != null)){
				dataDir.mkdir();
			}
			//create local lmstat tmp dir
			if(!tmpDataDir.exists() || (mntSdcardDir != null) ){
				tmpDataDir.mkdir();
			}
			
			LicLiteData.licLiteDataDir = mntSdcardDir + File.separator + LicLiteData.DIR;
			LicLiteData.licLiteDataTmpDir = LicLiteData.licLiteDataDir + File.separator + LicLiteData.TMP_DIR;
		}else{ //use internal storage
			LicLiteData.licLiteDataDir = this.getFilesDir().toString();
			LicLiteData.licLiteDataTmpDir = LicLiteData.licLiteDataDir + File.separator + LicLiteData.TMP_DIR;
		}
System.out.println("LicLiteData.licLiteDataDir--> " + LicLiteData.licLiteDataDir);
System.out.println("LicLiteData.licLiteDataTmpDir--> " + LicLiteData.licLiteDataTmpDir);

		//check data folder size

		if(UIUtil.getFileSize(new File(LicLiteData.licLiteDataDir)) >= LicLiteData.DATA_SIZE_UPPPER_LIMIT){
System.out.println("data folder size is exceed upper limit " + LicLiteData.DATA_SIZE_UPPPER_LIMIT + " mb");		
UIUtil.listAllFileNames();
		}

    	
//      super.requestWindowFeature(Window.FEATURE_NO_TITLE); // no title
        //customize title bar
    	super.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_start_group);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity_group_title_bar);
        titleBar = (TextView) this.findViewById(R.id.title_bar_value);
        
        this.gridviewToolbar = (GridView) this.findViewById(R.id.gridviewbar);
 //       this.content = (TextView) this.findViewById(R.id.content);
        
        //gridview setups
		this.gridviewToolbar.setNumColumns(this.group_img.length); // æ±‚å‡ºå�¯ä»¥ä¿�å­˜çš„ä¸ªæ•°
		this.gridviewToolbar.setSelector(new ColorDrawable(Color.TRANSPARENT));
		this.gridviewToolbar.setGravity(Gravity.CENTER);
		this.gridviewToolbar.setVerticalSpacing(0);
		
		
		this.screenWidth = super.getWindowManager().getDefaultDisplay().getWidth()/ this.group_img.length;
		LicLiteData.SCREEN_WIDTH = this.screenWidth;
		this.screenHeight = super.getWindowManager().getDefaultDisplay().getHeight() / 10 ;
		
		//set GroupActivityImageAdapter
		groupFragmentImageAdapter = new GroupFragmentImageAdapter(this, group_img, screenWidth, screenHeight, R.drawable.group_selected);
		this.gridviewToolbar.setAdapter(groupFragmentImageAdapter);
		this.switchActivity(0);
		this.gridviewToolbar.setOnItemClickListener(new GridviewToolbarOnItemClickListenerImpl());
    }

	private void switchActivity(int id) {
		// set background pic for selected fragment pic
		this.groupFragmentImageAdapter.setFocus(id); 
		
		switch (id) {
		case 0:
			replaceFragment(id);
			break;
		case 1:
			replaceFragment(id);
			break;
		case 2:
			replaceFragment(id);
			break;
		case 3:
			replaceFragment(id);
			break;
		case 4:
			replaceFragment(id);
			break;

		}		
	}
	
	
	
	private void replaceFragment(int position){
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
		if(position == 0) {
			transaction.replace(R.id.content, AddServerFragment.getInstance());
			titleBar.setText("add a server");

		}else if(position == 1){
			transaction.replace(R.id.content, AddNotificationFragment.getInstance());
			titleBar.setText("add a notification");
			
		}else if(position == 2){
			transaction.replace(R.id.content, ServersFragment.getInstance());
			//transaction.replace(R.id.content, ServersFragment.getInstance());
			titleBar.setText("servers");

		}else if(position == 3){
			transaction.replace(R.id.content, NotificationListFragment.getInstance());
			//transaction.replace(R.id.content, ServersFragment.getInstance());
			titleBar.setText("notifications");

		}else if(position == 4){
			transaction.replace(R.id.content, HistoryFragment.getInstance());
			titleBar.setText("history");
		}

//		transaction.addToBackStack(null);
		if(transaction != null)
			transaction.commit();
		
	}
	
	/**
	 * 
	 * @author shqiu
	 *
	 */
	private class GridviewToolbarOnItemClickListenerImpl implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			switchActivity(position);
			
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("on destroy...");
		
	}
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			this.exitDialog() ;
		}
		return false ;
	}
	
	/**
	 * 
	 */
	private void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setTitle("Exist").setMessage("Do you want to logout the logined sessions?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						UIUtil.dispose();
						StartGroupActivity.this.finish() ;
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						LicLiteData.autoServerNameSet.clear();
						LicLiteData.autoLmstatLocSet.clear();
						LicLiteData.autoLmgrdServerLocSet.clear();
						StartGroupActivity.this.finish() ;
					}
				}).create();

		dialog.show();
	}
}




































