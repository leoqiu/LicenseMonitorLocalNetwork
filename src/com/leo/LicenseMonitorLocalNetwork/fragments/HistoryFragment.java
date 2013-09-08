package com.leo.LicenseMonitorLocalNetwork.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.activities.RenderInTimeResultActivity;
import com.leo.LicenseMonitorLocalNetwork.beans.HistoryBean;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.UIUtil;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HistoryFragment extends Fragment {

	private Activity activity = null;
	
	private static HistoryFragment instance = new HistoryFragment();
	
	private ListView historyList = null;
	private AutoCompleteTextView autoCompleteTextViewHistory = null;
	
	private SimpleAdapter simplerHistoryListAdapter = null;
	private ArrayList<Map<String , String>> historyInfoListOfMapForSimpleAdapter = new ArrayList<Map<String , String>>();
	
	private ArrayList<HistoryBean> historyBeanList = null;
	
	private HistoryFragment(){
		
	}
	
	public static HistoryFragment getInstance(){
		return instance;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = this.getActivity();
		
		View v = inflater.inflate(R.layout.fragment_history, container, false);
		this.historyList = (ListView) v.findViewById(R.id.list_history);
		this.autoCompleteTextViewHistory = (AutoCompleteTextView) v.findViewById(R.id.autocomplete_fragment_history);
		this.autoCompleteTextViewHistory.setDropDownHeight(0);
		this.autoCompleteTextViewHistory.setHint("Search history by hour of timestamp...");
		
		//get simple adapter
		historyBeanList = getHistoryBeanList();
		historyInfoListOfMapForSimpleAdapter.clear();
		for(HistoryBean bean : historyBeanList){
			Map<String, String> map = new HashMap<String, String>();
			map.put("textview_lmgrd_server", bean.getLmgrdServerName());
			map.put("textview_data_timestamp", bean.getDataTimestamp());
			historyInfoListOfMapForSimpleAdapter.add(map);
		}
		
System.out.println("how many history -> " + historyInfoListOfMapForSimpleAdapter.size());	

		simplerHistoryListAdapter = new SimpleAdapter(
				historyList.getContext(), historyInfoListOfMapForSimpleAdapter, R.layout.item_history_list, 
				new String[] {"textview_lmgrd_server","textview_data_timestamp"}, 
				new int[] {R.id.textview_lmgrd_server,R.id.textview_data_timestamp});
		
		this.historyList.setAdapter(simplerHistoryListAdapter);
		this.autoCompleteTextViewHistory.setAdapter(simplerHistoryListAdapter);
		this.historyList.setOnItemClickListener(new HistoryListOnItemClickListenerImpl());
		
		
		return v;
	}
	
	
	private class HistoryListOnItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			System.out.println("click on --> " + historyBeanList.get(position).getFileName());
System.out.println("activity --> " + activity);
			Intent intent = new Intent(activity, RenderInTimeResultActivity.class);
			intent.putExtra(LicLiteData.LOCAL_DATA_FILE_NAME, historyBeanList.get(position).getFileName());
			//get in time result locally
			intent.putExtra(LicLiteData.RESULT_FLAG, LicLiteData.LOCAL_RESULT);
			activity.startActivity(intent);
			activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		}
		
	} 
	
	/**
	 * Get HistoryBeans
	 * 
	 * 
	 * @return
	 */
	private ArrayList<HistoryBean> getHistoryBeanList(){
		ArrayList<HistoryBean> historyBeans = new ArrayList<HistoryBean>();
System.out.println("historyBeans size is -> " + historyBeans.size());		

		File folder = new File(LicLiteData.licLiteDataDir);

		File[] listOfFiles = folder.listFiles(); 
		int lastIndex = listOfFiles.length - 1;

		
		String timeStamp = null;
		
		for(int i = lastIndex ; i >= 0; i-- ){
			String fileName = listOfFiles[i].getName();
			if(!fileName.equals(LicLiteData.TMP_DIR)){
				String[] strArr = fileName.split("-");
				timeStamp = strArr[2] + "/" + strArr[3] + "/" + strArr[1] + "  " + strArr[4] + ":" + 
				strArr[5] + ":" + strArr[6];
				HistoryBean bean = new HistoryBean("WHICH LMGRD SERVER???", timeStamp, fileName);
				historyBeans.add(bean);
			}
		}
System.out.println("historyBeans size is -> " + historyBeans.size());		
		return historyBeans;
	}

}












