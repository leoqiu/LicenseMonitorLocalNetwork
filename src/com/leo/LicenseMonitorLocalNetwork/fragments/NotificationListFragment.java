package com.leo.LicenseMonitorLocalNetwork.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.adapters.NotificationListSimpleAdapter;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationBean;
import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;
import com.leo.LicenseMonitorLocalNetwork.listenerimpl.NotificationListOnItemClickListenerImpl;
import com.leo.LicenseMonitorLocalNetwork.listenerimpl.NotificationListOnItemLongClickListenerImpl;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.DBUtil;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NotificationListFragment extends Fragment {

	private ListView notificationList = null;
	private NotificationListSimpleAdapter notificationSimpleAdapter = null;
	
	
	private static NotificationListFragment instance = new NotificationListFragment();
	
	private NotificationListFragment(){
		
	}
	
	public static NotificationListFragment getInstance(){
		return instance;
	}
	
	@Override
	public View getView() {
		
		return super.getView();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//System.out.println("notification list oncreate view...");		
		View v = inflater.inflate(R.layout.fragment_notifications, container, false);
		showNotificationList(v);
		
		return v;
	}

	private void showNotificationList(View v){
		this.notificationList = (ListView) v.findViewById(R.id.listview_notification_list);
		//NotificationListSimpleAdapter data
		List<Map<String, String>> myData = new ArrayList<Map<String, String>>();
		
		
		//if static serverBeanList is empty
		if(LicLiteData.notificationList.size() == 0){
			ArrayList<NotificationBean> notificationBeans = DBUtil.loadNotificationsFromDB(getActivity());
			if(notificationBeans != null){
				LicLiteData.notificationList = notificationBeans;
			}
		}
		
		for(NotificationBean notificationBean : LicLiteData.notificationList){
			Map<String, String> map = new HashMap<String, String>();
			map.put("notification_license_server_value", notificationBean.getNotificationServerName()
							+ " & "+ notificationBean.getNotificationLmgrdServerLoc());
			map.put("notification_license_name_value", notificationBean.getNotificationLicenseName());
			map.put("notification_license_user_name_value", notificationBean.getNotificationLicenseUserName());
			map.put("notification_polling_period_value", notificationBean.getNotificationPollingPeriod());
			
			myData.add(map);
		}
		
		NotificationListSimpleAdapter.setMyData(myData);
		this.notificationSimpleAdapter = new NotificationListSimpleAdapter(
				this.getActivity(), R.layout.listview_notification, 
				new String[]{
						"notification_license_server_value",
						"notification_license_name_value", 
						"notification_license_user_name_value", 
						"notification_polling_period_value"} , 
				new int[]{
						R.id.notification_license_server_value,
						R.id.notification_license_name_value, 
						R.id.notification_license_user_name_value, 
						R.id.notification_polling_period_value });
		
		this.notificationList.setAdapter(notificationSimpleAdapter);
		this.notificationList.setOnItemClickListener(new NotificationListOnItemClickListenerImpl(getActivity(), notificationSimpleAdapter));
		this.notificationList.setOnItemLongClickListener(new NotificationListOnItemLongClickListenerImpl(getActivity(), notificationSimpleAdapter));
	}
	
	@Override
	public void onDetach() {
	
		super.onDetach();
	}
	
	
	
	
}
