package com.leo.LicenseMonitorLocalNetwork.activities;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.adapters.GroupFragmentImageAdapter;
import com.leo.LicenseMonitorLocalNetwork.asyncs.RenderInTimeResultAsyncTask;
import com.leo.LicenseMonitorLocalNetwork.asyncs.RenderLocalResultAsyncTask;
import com.leo.LicenseMonitorLocalNetwork.fragments.RenderServersWithUsersFragment;
import com.leo.LicenseMonitorLocalNetwork.fragments.RenderServersWithoutUsersFragment;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;

public class RenderInTimeResultActivity extends Activity {

	private GridView gridviewToolbar; // group grid tool bar
	private GroupFragmentImageAdapter groupFragmentImageAdapter = null; // picture adapter
	
	// groupActivityImageAdapter images resources
	private int group_img[] = new int[] { R.drawable.render_no_user, R.drawable.render_has_user};
	private int screenWidth = 0; // screen width
	private int screenHeight = 0; // screen height
	
	private TextView titleBar = null;
	
	//two fragments
	private RenderServersWithoutUsersFragment renderServersWithoutUsersFragment = RenderServersWithoutUsersFragment.getInstance();
	private RenderServersWithUsersFragment renderServersWithUsersFragment = RenderServersWithUsersFragment.getInstance();
	
	//loading progress dialog
	private ProgressDialog progress = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// set window feature then can use custom title bar
		super.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		this.setContentView(R.layout.activity_render_in_time_result);
		this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.activity_group_title_bar);
		titleBar = (TextView) this.findViewById(R.id.title_bar_value);
		titleBar.setText("TEST TEST TEST");

		//Progress dialog for loading data
		progress = new ProgressDialog(this);
		progress.setMessage("Downloading data from server ...");
		progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progress.setMax(LicLiteData.LOADING_PROGRESS_MAX);
		progress.show();
		
		this.gridviewToolbar = (GridView) this.findViewById(R.id.gridviewbar_render_result);
		this.gridviewToolbar.setNumColumns(this.group_img.length);
		this.gridviewToolbar.setSelector(new ColorDrawable(Color.TRANSPARENT));
		this.gridviewToolbar.setGravity(Gravity.CENTER);
		this.gridviewToolbar.setVerticalSpacing(0);
		this.gridviewToolbar.setVisibility(View.INVISIBLE);
		
		//set gridview length and width
		this.screenWidth = super.getWindowManager().getDefaultDisplay().getWidth()/ this.group_img.length;
		LicLiteData.SCREEN_WIDTH = this.screenWidth;
		this.screenHeight = super.getWindowManager().getDefaultDisplay().getHeight() / 10 ;

		//set GroupActivityImageAdapter
		groupFragmentImageAdapter = new GroupFragmentImageAdapter(this, group_img, screenWidth, screenHeight, R.drawable.group_selected);
		this.gridviewToolbar.setAdapter(groupFragmentImageAdapter);
		this.switchActivity(0);
		this.gridviewToolbar.setOnItemClickListener(new GridviewToolbarOnItemClickListenerImpl());

		//get logined index from startgroup activity
		int loginIndex = this.getIntent().getIntExtra(LicLiteData.SERVER_LOGIN_INDEX, -1);
		int resultType = this.getIntent().getIntExtra(LicLiteData.RESULT_FLAG, -1);
		String dataFileName = this.getIntent().getStringExtra(LicLiteData.LOCAL_DATA_FILE_NAME);
		

		
		if(resultType == LicLiteData.IN_TIME_RESULT){
System.out.println("in time result...");
			RenderInTimeResultAsyncTask renderInTimeResultAsyncTask = new RenderInTimeResultAsyncTask(
					renderServersWithoutUsersFragment, renderServersWithUsersFragment, this.gridviewToolbar, progress, loginIndex);
			renderInTimeResultAsyncTask.execute();
		}else if(resultType == LicLiteData.LOCAL_RESULT){
			RenderLocalResultAsyncTask renderLocalResultAsyncTask = new RenderLocalResultAsyncTask(
					renderServersWithoutUsersFragment, renderServersWithUsersFragment, this.gridviewToolbar, progress, dataFileName);
			renderLocalResultAsyncTask.execute();
		}



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
		}		
	}
	
	
	private void replaceFragment(int position){
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
		if(position == 0) {
			transaction.replace(R.id.render_result_content, renderServersWithoutUsersFragment);
			titleBar.setText("servers avaliable with no user occupied");
		}else if(position == 1){
			transaction.replace(R.id.render_result_content, renderServersWithUsersFragment);
			titleBar.setText("servers avaliable with users occupied");
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
	
	/**
	 * 
	 * animation when press the back button
	 */

	@Override
	public void onBackPressed() {

		super.onBackPressed();

		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);

	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		this.renderServersWithoutUsersFragment.setLicliteWithoutAdapterList(null);
		this.renderServersWithoutUsersFragment.setSimplerAdapterHasNoUsers(null);
		this.renderServersWithoutUsersFragment = null;
System.out.println("render act on destroy ....");
	}


	@Override
	public void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
System.out.println("render act on detached from window...");
	}

}
