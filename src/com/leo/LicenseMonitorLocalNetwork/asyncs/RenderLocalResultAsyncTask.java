package com.leo.LicenseMonitorLocalNetwork.asyncs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.beans.LicLiteServerInfoBean;
import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;
import com.leo.LicenseMonitorLocalNetwork.fragments.RenderServersWithUsersFragment;
import com.leo.LicenseMonitorLocalNetwork.fragments.RenderServersWithoutUsersFragment;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.NetWorkUtil;
import com.leo.LicenseMonitorLocalNetwork.utils.Parser;

public class RenderLocalResultAsyncTask extends AsyncTask<Object, Integer, String> {
	private ProgressDialog progress = null;
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);		
		
		if(values[0] == LicLiteData.PARSING_DATA){
			progress.setMessage("Parsinging data ...");
		}
		
		if(values[0] == LicLiteData.ITERATING_DATA){
			progress.setMessage("Iterating data ...");
		}
		
		if(values[0] > 0)
			progress.incrementProgressBy(values[0]);
	}

// private memebers
	private RenderServersWithoutUsersFragment renderServersWithoutUsersFragment = null;
	private RenderServersWithUsersFragment renderServersWithUsersFragment = null;
	private GridView gridviewToolbar = null;
	private String dataFileName = null;
	
	// simpler adapter list
	List<Map<String, String>> licliteWithoutAdapterList = new ArrayList<Map<String, String>>();
//	List<Map<String, String>> licliteWithGroupAdapterList = new ArrayList<Map<String, String>>();
//	List<ArrayList<HashMap<String, String>>> licliteWithChildAdapterList = new ArrayList<ArrayList<HashMap<String, String>>>();
	List<Map<String, Object>> licliteWithAdapterList = new ArrayList<Map<String, Object>>();
	
	
	public RenderLocalResultAsyncTask(RenderServersWithoutUsersFragment 
			renderServersWithoutUsersFragment, RenderServersWithUsersFragment renderServersWithUsersFragment,
			GridView gridviewToolbar, ProgressDialog progress, String dataFileName) {
		
		this.renderServersWithoutUsersFragment = renderServersWithoutUsersFragment;
		this.renderServersWithUsersFragment = renderServersWithUsersFragment;
		this.gridviewToolbar = gridviewToolbar;
		this.progress = progress;
		this.dataFileName = dataFileName;
	}

	@Override
	protected void onPreExecute() {
		progress.setMax(LicLiteData.LOADING_PROGRESS_LOCAL_MAX);
		this.progress.setCancelable(false);
	}

	@Override
	protected String doInBackground(Object... params) {

		//parsing data locally
		this.publishProgress(LicLiteData.PARSING_DATA);
		List<LicLiteServerInfoBean> licliteServerInfoList = Parser.parseDownloadDataFile(dataFileName);
		this.publishProgress(LicLiteData.LOADING_PROGRESS_PARSING_TIME);

		//iterating data locally
		this.publishProgress(LicLiteData.ITERATING_DATA);
		int size = licliteServerInfoList.size();
		LicLiteServerInfoBean licLiteWitUserInfoBeanInside = null;
		for (int i = 0; i < size; i++) {
			// if without user info
			licLiteWitUserInfoBeanInside = licliteServerInfoList.get(i);
			if (licLiteWitUserInfoBeanInside.getLicLiteUserInfoBeanList()
					.size() == 0) {
				// System.out.println(licLiteWitUserInfoBeanHasNon.toString());
				Map<String, String> map = new HashMap<String, String>();
				map.put("server_feature_name_value",
						licLiteWitUserInfoBeanInside.getFeatureName());
				map.put("server_feature_num_issue_value",
						licLiteWitUserInfoBeanInside.getFeatureNumIssued());
				map.put("server_feature_num_in_used_value",
						licLiteWitUserInfoBeanInside.getFeatureNumInUsed());
				licliteWithoutAdapterList.add(map);
			} else { // if with user info expandable list
				
				Map<String, Object> withUsersMap = new HashMap<String, Object>();
				withUsersMap.put("server_feature_name_value",licLiteWitUserInfoBeanInside.getFeatureName());
				withUsersMap.put("server_feature_num_issue_value",licLiteWitUserInfoBeanInside.getFeatureNumIssued());
				withUsersMap.put("server_feature_num_in_used_value",licLiteWitUserInfoBeanInside.getFeatureNumInUsed());
				withUsersMap.put("user_array_list", licLiteWitUserInfoBeanInside.getLicLiteUserInfoBeanList());
				licliteWithAdapterList.add(withUsersMap);
				
			}
		}
		this.publishProgress(LicLiteData.LOADING_PROGRESS_ITERATING_TIME);

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progress.dismiss();
		this.gridviewToolbar.setVisibility(View.VISIBLE);
LicLiteData.endTime = (int) (System.currentTimeMillis() / 1000);
System.out.println("time cost is ----> "
+ (LicLiteData.endTime - LicLiteData.startTime));

		//initialize renderServersWithoutUsersFragment listview
		SimpleAdapter simplerAdapterHasNoUsers = new SimpleAdapter(
				this.gridviewToolbar.getContext(), licliteWithoutAdapterList,
				R.layout.item_server_info_listview, new String[] {
						"server_feature_name_value",
						"server_feature_num_issue_value",
						"server_feature_num_in_used_value" }, new int[] {
						R.id.server_feature_name_value,
						R.id.server_feature_num_issue_value,
						R.id.server_feature_num_in_used_value });
//		this.renderServersWithoutUsersFragment
//				.setLicliteWithoutAdapterList(licliteWithoutAdapterList);
		this.renderServersWithoutUsersFragment
				.setSimplerAdapterHasNoUsers(simplerAdapterHasNoUsers);
		this.renderServersWithoutUsersFragment.getServerWithoutListView()
				.setAdapter(simplerAdapterHasNoUsers);
		this.renderServersWithoutUsersFragment.getAutoCompleteTextViewWithoutUsers().setAdapter(simplerAdapterHasNoUsers);
		
		//initialize renderServersWithUsersFragment  
		SimpleAdapter simplerAdapterHasUsers = new SimpleAdapter(
				this.gridviewToolbar.getContext(), licliteWithAdapterList,
				R.layout.item_server_info_listview, new String[] {
						"server_feature_name_value",
						"server_feature_num_issue_value",
						"server_feature_num_in_used_value" }, new int[] {
						R.id.server_feature_name_value,
						R.id.server_feature_num_issue_value,
						R.id.server_feature_num_in_used_value });
		this.renderServersWithUsersFragment.setSimplerAdapterHasUsers(simplerAdapterHasUsers);
	}


	/**
	 * Get latest liclite data file name for parsing
	 * 
	 * @return
	 */
	private String getLatestFileName(){
		File folder = new File(LicLiteData.licLiteDataDir);
		File[] listOfFiles = folder.listFiles();  
		
		return listOfFiles[listOfFiles.length - 1].getName();
		
	}
}
