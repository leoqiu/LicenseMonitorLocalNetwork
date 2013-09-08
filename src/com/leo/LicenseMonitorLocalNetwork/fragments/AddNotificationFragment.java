package com.leo.LicenseMonitorLocalNetwork.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.adapters.NotificationListSimpleAdapter;
import com.leo.LicenseMonitorLocalNetwork.beans.NotificationBean;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.DBUtil;
import com.leo.LicenseMonitorLocalNetwork.utils.UIUtil;

public class AddNotificationFragment extends Fragment {

	private static AddNotificationFragment instance = new AddNotificationFragment();

	// elements in this fragment
	// server name
	private AutoCompleteTextView autoServerNameNotification = null;
	// lmstat cmd loc
	private AutoCompleteTextView autoLmstatCmdLocNotification = null;
	// lmgrd server loc
	private AutoCompleteTextView autoLmgrdServerLocNotification = null;
	// license name
	private AutoCompleteTextView autoLicenseNameNotification = null;
	// user name
	private AutoCompleteTextView autoLicenseUserNameNotification = null;
	// polling period
	private Spinner spinnerPollingPeriod = null;
//notification expandable list
//private ExpandableListView expandableListViewNotificationList = null;
	//notification list
//	private ListView listViewNotificationList = null;
	
	// add notification button
	private Button addNotificationButton = null;
	
	//private NotificationExpandableListAdapter notificationExpandableListAdapter = null;
	private NotificationListSimpleAdapter notificationListSimpleAdapter = null;
	ArrayList<Map<String, String>> myNotificationListData = new ArrayList<Map<String, String>>();
	
	// String result from elements
	private String notificationServerNameStr = null;
	private String notificationLmstatCmdLocStr = null;
	private String notificationLmgrdServerLocStr = null;
	private String notificationLicenseNameStr = null;
	private String notificationLicenseUserNameStr = null;
	private String notificationPollingPeriodStr = null;

	private AddNotificationFragment() {
	}

	public static AddNotificationFragment getInstance() {
		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_add_notification, container,
				false);

		// locate all the elements
		autoServerNameNotification = (AutoCompleteTextView) v
				.findViewById(R.id.auto_server_name_notification);

		autoLmstatCmdLocNotification = (AutoCompleteTextView) v
				.findViewById(R.id.auto_lmstat_cmd_loc_notification);
		autoLmgrdServerLocNotification = (AutoCompleteTextView) v
				.findViewById(R.id.auto__lmgrd_server_loc_notification);
		autoLicenseNameNotification = (AutoCompleteTextView) v
				.findViewById(R.id.auto_license_name_notification);
		autoLicenseUserNameNotification = (AutoCompleteTextView) v
				.findViewById(R.id.auto_license_user_name_notification);
		spinnerPollingPeriod = (Spinner) v
				.findViewById(R.id.spinner_polling_period_notification);
//		expandableListViewNotificationList = (ExpandableListView) v
//				.findViewById(R.id.expandableListview_notification_list);
//		listViewNotificationList = (ListView) v.findViewById(R.id.Listview_notification_list);
		addNotificationButton = (Button) v.findViewById(R.id.button_add_notification);

		// set spinner polling period adapter
		ArrayAdapter<CharSequence> spinnerNotificationPollingPeriod = ArrayAdapter
				.createFromResource(getActivity(),
						R.array.spinner_notification_polling_period,
						android.R.layout.simple_spinner_dropdown_item);
		spinnerPollingPeriod.setAdapter(spinnerNotificationPollingPeriod);

		// register button event
		addNotificationButton.setOnClickListener(new AddNotificationOnClickListenerImpl(this
						.getActivity()));

		return v;
	}

	private class AddNotificationOnClickListenerImpl implements OnClickListener {

		private Context context = null;

		public AddNotificationOnClickListenerImpl(Context context) {
			this.context = context;
		}

		@Override
		public void onClick(View view) {

			notificationServerNameStr = autoServerNameNotification.getText()
					.toString();
			notificationLmstatCmdLocStr = autoLmstatCmdLocNotification
					.getText().toString();
			notificationLmgrdServerLocStr = autoLmgrdServerLocNotification
					.getText().toString();
			notificationLicenseNameStr = autoLicenseNameNotification.getText()
					.toString();
			notificationLicenseUserNameStr = autoLicenseUserNameNotification
					.getText().toString();
			notificationPollingPeriodStr = spinnerPollingPeriod
					.getItemAtPosition(
							spinnerPollingPeriod.getSelectedItemPosition())
					.toString();

			if (notificationServerNameStr.equals("")
					|| notificationLmstatCmdLocStr.equals("")
					|| notificationLmgrdServerLocStr.equals("")
					|| notificationLicenseNameStr.equals("")
					|| notificationLicenseUserNameStr.equals("")
					|| notificationPollingPeriodStr.equals("")) {
				UIUtil.showToast("Please fill all the information above ...",
						context);
			} else {

				// create NotificationBean
				NotificationBean notificationBean = new NotificationBean(
						notificationServerNameStr, notificationLmstatCmdLocStr,
						notificationLmgrdServerLocStr,
						notificationLicenseNameStr,
						notificationLicenseUserNameStr,
						notificationPollingPeriodStr);

				if (DBUtil.insertNotificationBeanIntoDBAndNotificationList(
						notificationBean, context)) {
					cleanTextViews();
					// add this new notification bean to expandable list
//					addDataIntoNotificationList(notificationBean);
					UIUtil.showToast("Notification added successfully ...",
							context);
				}

				System.out.println("add notification ...");
			}

		}

		
		
		
		
		// add new notification bean into notification list and update
		// notification list
		private void addDataIntoNotificationList(NotificationBean notificationBean) {			
			LicLiteData.notificationList.add(notificationBean);
			
		
			Map<String, String> map = new HashMap<String, String>();
			map.put("notification_license_server_value", notificationBean.getNotificationServerName()
							+ " & "+ notificationBean.getNotificationLmgrdServerLoc());
			map.put("notification_license_name_value", notificationBean.getNotificationLicenseName());
			map.put("notification_license_user_name_value", notificationBean.getNotificationLicenseUserName());
			map.put("notification_polling_period_value", notificationBean.getNotificationPollingPeriod());
			
//notificationListSimpleAdapter
			
			if(notificationListSimpleAdapter == null){
				
				myNotificationListData.add(map);
				NotificationListSimpleAdapter.setMyData(myNotificationListData);
				notificationListSimpleAdapter = new NotificationListSimpleAdapter(
						context, R.layout.listview_notification, 
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
System.out.println("notificationListSimpleAdapter --> " + notificationListSimpleAdapter);
//				listViewNotificationList.setAdapter(notificationListSimpleAdapter);
			}else{
				NotificationListSimpleAdapter.getMyData().add(map);
				notificationListSimpleAdapter.notifyDataSetChanged();	
System.out.println("notificationListSimpleAdapter.getCount() --> " + notificationListSimpleAdapter.getCount());
				//listViewNotificationList.invalidateViews();
			}
		}
		
		// add new notification bean into notification list and update
		// notification expandable list
		/**
		 * #1. add new notification bean into notification list #2. update
		 * notification expandable list
		 * 
		 */
//		private void updateNotificationList(NotificationBean notificationBean) {
//			LicLiteData.notificationList.add(notificationBean);
//			
//			notificationExpandableListAdapter = buildNotificationExpandableListAdapter(LicLiteData.notificationList);
////notificationExpandableListAdapter.notifyDataSetChanged();
//			expandableListViewNotificationList.setAdapter(notificationExpandableListAdapter);
//		}

//		private NotificationExpandableListAdapter buildNotificationExpandableListAdapter(
//				ArrayList<NotificationBean> notificationList) {
//			
//			
//			List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
//			List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
//			
//			//groupData, only one group item
//			Map<String, String> mapGroup = new HashMap<String, String>();
//			mapGroup.put("notification_list_label", "Notification monitor list");
//			groupData.add(mapGroup);
//			
//			//childData, size of notificationList
//			Iterator<NotificationBean> iterNotificationBean = notificationList.iterator();
//			//only one group so only one childSubList belong to that one group
//			List<Map<String, String>> childSubList = new ArrayList<Map<String, String>>();
//			while(iterNotificationBean.hasNext()){
//				NotificationBean notificationBean = iterNotificationBean.next();
//				
//				Map<String, String> mapChild = new HashMap<String, String>();
//				mapChild.put("notification_child_license_name_label", "License Name: ");
//				mapChild.put("notification_child_license_user_name_label", "User Name: ");
//				mapChild.put("notification_child_polling_period_label", "Polling Period: ");
//				mapChild.put("notification_child_license_name_value", notificationBean.getNotificationLicenseName());
//				mapChild.put("notification_child_license_user_name_value", notificationBean.getNotificationLicenseUserName());
//				mapChild.put("notification_child_polling_period_value", notificationBean.getNotificationPollingPeriod());
//System.out.println("notificationBean.getNotificationPollingPeriod() -> " + notificationBean.getNotificationPollingPeriod());
//				childSubList.add(mapChild);
//			}
//			childData.add(childSubList);
//			
//			NotificationExpandableListAdapter notificationExpandableListAdapter = new NotificationExpandableListAdapter(
//					context,
//					groupData, R.layout.expandablelist_group_notification, R.layout.expandablelist_group_notification, 
//					new String[]{"notification_list_label"}, new int[]{R.id.notification_group_text},
//					childData, R.layout.expandablelist_child_notification,
//					new String[]{
//							"notification_child_license_name_label",
//							"notification_child_license_user_name_label",
//							"notification_child_polling_period_label",
//							"notification_child_license_name_value", 
//							"notification_child_license_user_name_value", 
//							"notification_child_polling_period_value"}, 
//					new int[] {
//							R.id.notification_child_license_name_label,
//							R.id.notification_child_license_user_name_label,
//							R.id.notification_child_polling_period_label,
//							R.id.notification_child_license_name_value,
//							R.id.notification_child_license_user_name__value,
//							R.id.notification_child_polling_period_value }
//			
//					);
//			
//			return notificationExpandableListAdapter;
//		}

		// clear textviews
		private void cleanTextViews() {
			autoServerNameNotification.setText("");
			autoLmstatCmdLocNotification.setText("");
			autoLmgrdServerLocNotification.setText("");
			autoLicenseNameNotification.setText("");
			autoLicenseUserNameNotification.setText("");
		}

	}

}
