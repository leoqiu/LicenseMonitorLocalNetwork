package com.leo.LicenseMonitorLocalNetwork.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;

public class RenderNotificationResultActivity extends Activity{

	private List<Map<String, String>> listOfMap = new ArrayList<Map<String, String>>();
	private ListView notificationResultListView;
	private SimpleAdapter simpleAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_render_notification_result);
		notificationResultListView = (ListView) this.findViewById(R.id.listview_notification_results);
//TextView checkInOrOutTextView = (TextView) this.findViewById(R.id.notification_license_checkin_or_out);
System.out.println("notificationResultListView --> " + notificationResultListView);		
System.out.println("LicLiteData.notificationCmpResultBeanList --> " + LicLiteData.notificationCmpResultBeanList.size());	
//System.out.println("checkInOrOutTextView --> " + checkInOrOutTextView);
		for(int i = 0; i < LicLiteData.notificationCmpResultBeanList.size(); i++){
			
			Map<String, String> map = new HashMap<String, String>();		
			if(LicLiteData.notificationCmpResultBeanList.get(i).isCheckedIn()){
				map.put("notification_license_checkin_or_out", "+");
				map.put("notification_license_license_info",
						LicLiteData.notificationCmpResultBeanList.get(i).getCheckedStr());
//				checkInOrOutTextView.setTextColor(Color.GREEN);			
			}else{
				map.put("notification_license_checkin_or_out", "-");
				map.put("notification_license_license_info",
						LicLiteData.notificationCmpResultBeanList.get(i).getCheckedStr());
//				checkInOrOutTextView.setTextColor(Color.RED);
			}
			listOfMap.add(map);
		}
		
		this.simpleAdapter = new SimpleAdapter(this, this.listOfMap, R.layout.item_render_notification_result,
							 new String[]{"notification_license_checkin_or_out", "notification_license_license_info"}, 
							 new int[]{R.id.notification_license_checkin_or_out, R.id.notification_license_license_info});

System.out.println("simpleAdapter --> " + simpleAdapter);		
		this.notificationResultListView.setAdapter(simpleAdapter);
	}


	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

}
