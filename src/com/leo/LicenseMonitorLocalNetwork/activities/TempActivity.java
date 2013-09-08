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
import com.leo.LicenseMonitorLocalNetwork.fragments.RenderServersWithUsersFragment;
import com.leo.LicenseMonitorLocalNetwork.fragments.RenderServersWithoutUsersFragment;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;

public class TempActivity extends Activity{

	private GridView gridviewToolbar; // group grid tool bar
	private GroupFragmentImageAdapter groupFragmentImageAdapter = null; // picture adapter
	
	// groupActivityImageAdapter images resources
	private int group_img[] = new int[] { R.drawable.render_no_user, R.drawable.render_has_user};
	private int screenWidth = 0; // screen width
	private int screenHeight = 0; // screen height
	
	private TextView titleBar = null;
	
	private ProgressDialog bar = null;
	
	
//loading progressbar
//private ProgressBar bar = null;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// set window feature then can use custom title bar
		super.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		this.setContentView(R.layout.activity_temp);
		this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.activity_group_title_bar);
		titleBar = (TextView) this.findViewById(R.id.title_bar_value);
		titleBar.setText("TEST TEST TEST");

//Progressbar for loading data
//bar = (ProgressBar) this.findViewById(R.id.progressbar_loading_intime_result);
//bar.setMax(LicLiteData.LOADING_PROGRESS_MAX);

		//Progress dialog
		bar = new ProgressDialog(this);
		bar.setMessage("LEO ....");
		bar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		bar.show();
		bar.setMax(5);
		bar.incrementProgressBy(3);
		
		this.gridviewToolbar = (GridView) this.findViewById(R.id.gridviewbar_render_result);
		this.gridviewToolbar.setNumColumns(this.group_img.length);
		this.gridviewToolbar.setSelector(new ColorDrawable(Color.TRANSPARENT));
		this.gridviewToolbar.setGravity(Gravity.CENTER);
		this.gridviewToolbar.setVerticalSpacing(0);
		
		this.screenWidth = super.getWindowManager().getDefaultDisplay().getWidth()/ this.group_img.length;
		LicLiteData.SCREEN_WIDTH = this.screenWidth;
		this.screenHeight = super.getWindowManager().getDefaultDisplay().getHeight() / 10 ;

		//set GroupActivityImageAdapter
		groupFragmentImageAdapter = new GroupFragmentImageAdapter(this, group_img, screenWidth, screenHeight, R.drawable.group_selected);
		this.gridviewToolbar.setAdapter(groupFragmentImageAdapter);

		this.gridviewToolbar.setOnItemClickListener(new GridviewToolbarOnItemClickListenerImpl());

//		RenderInTimeResultAsyncTask renderInTimeResultAsyncTask = new RenderInTimeResultAsyncTask(
//				renderServersWithoutUsersFragment, this.gridviewToolbar, bar);
//		renderInTimeResultAsyncTask.execute();

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
			
			//switchActivity(position);
			
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
	
	

}
