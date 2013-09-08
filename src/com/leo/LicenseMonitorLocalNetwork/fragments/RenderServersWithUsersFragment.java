package com.leo.LicenseMonitorLocalNetwork.fragments;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.beans.LicLiteUserInfoBean;

public class RenderServersWithUsersFragment extends Fragment{

	private final static RenderServersWithUsersFragment instance = new RenderServersWithUsersFragment();

	private ListView serverWithListView = null;	
	private AutoCompleteTextView autoCompleteTextViewWithUsers = null;
	private SimpleAdapter simplerAdapterHasUsers = null;
	
	private RenderServersWithUsersFragment() {

	}
	
	public static RenderServersWithUsersFragment getInstance(){
		return instance;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_render_with_users, container, false);
		
		this.serverWithListView = (ListView) v.findViewById(R.id.listview_servers_with_users);
		this.serverWithListView.setOnItemClickListener(new ServerWithListViewOnItemClickListenerImpl());
		
		this.autoCompleteTextViewWithUsers = (AutoCompleteTextView) v.findViewById(R.id.autocomplete_fragment_render_with_users);
		this.autoCompleteTextViewWithUsers.setDropDownWidth(0);
		this.autoCompleteTextViewWithUsers.setHint("search by feature name");
		
		if(this.simplerAdapterHasUsers != null) {
			this.serverWithListView.setAdapter(simplerAdapterHasUsers);
			this.autoCompleteTextViewWithUsers.setAdapter(simplerAdapterHasUsers);
		}
		
		return v;
	}
	
	
	private class ServerWithListViewOnItemClickListenerImpl implements OnItemClickListener{
		//ArrayList<LicLiteUserInfoBean>
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			Map<String, Object> adapterMap = (Map<String, Object>) simplerAdapterHasUsers.getItem(position);
			ArrayList<LicLiteUserInfoBean> userInfoBeanList = (ArrayList<LicLiteUserInfoBean>) adapterMap.get("user_array_list");
			Context context = serverWithListView.getContext();
			List<HashMap<String, String>>  usersInfoListOfMapForSimpleAdapter = new ArrayList<HashMap<String, String>>();
			
			int size = userInfoBeanList.size();
			for(int i = 0; i < size; i++){
				LicLiteUserInfoBean bean = userInfoBeanList.get(i);
				HashMap<String, String> userInfoMap = new HashMap<String, String>();
				userInfoMap.put("user_name_value", bean.getUserName());
				userInfoMap.put("user_server_value", bean.getUserServerName());
				userInfoMap.put("user_start_time_value", bean.getUserStartTime());
				userInfoMap.put("user_num_license_in_used_value", bean.getUserNumOfLicenses());
				usersInfoListOfMapForSimpleAdapter.add(userInfoMap);
			}
			
			SimpleAdapter simplerAdapterUsersInfoListView = new SimpleAdapter(
					context, usersInfoListOfMapForSimpleAdapter, R.layout.item_users_info_listview, 
					new String[] {"user_name_value","user_server_value",
					"user_start_time_value","user_num_license_in_used_value"}, 
					new int[] {R.id.user_name_value,R.id.user_server_value,
					R.id.user_start_time_value,R.id.user_num_license_in_used_value});
			
			LayoutInflater layoutInflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myDialogView = layoutInflater.inflate(R.layout.alert_dialog_render_users_info, null);
			ListView userInfoListView = (ListView) myDialogView.findViewById(R.id.listview_render_users_info);
AutoCompleteTextView autoCompleteLookingForUsersInfo = (AutoCompleteTextView)myDialogView.findViewById(R.id.autocomplete_alert_render_users_info);
autoCompleteLookingForUsersInfo.setDropDownWidth(0);
autoCompleteLookingForUsersInfo.setHint("Search by user name");
autoCompleteLookingForUsersInfo.setAdapter(simplerAdapterUsersInfoListView);

			userInfoListView.setAdapter(simplerAdapterUsersInfoListView);
			
			Dialog dialog = new AlertDialog.Builder(context)
            .setTitle("Users using this feature").setNegativeButton("Ok", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					
				}
            	
            }).setView(myDialogView).create();
			
			dialog.show();
		}
		
	}
	
	/**
	 * getter and setter
	 * 
	 * @return
	 */
	public AutoCompleteTextView getAutoCompleteTextViewWithUsers() {
		return autoCompleteTextViewWithUsers;
	}

	public void setAutoCompleteTextViewWithUsers(
			AutoCompleteTextView autoCompleteTextViewWithUsers) {
		this.autoCompleteTextViewWithUsers = autoCompleteTextViewWithUsers;
	}

	public ListView getServerWithListView() {
		return serverWithListView;
	}

	public void setServerWithListView(ListView serverWithListView) {
		this.serverWithListView = serverWithListView;
	}

	public SimpleAdapter getSimplerAdapterHasUsers() {
		return simplerAdapterHasUsers;
	}

	public void setSimplerAdapterHasUsers(SimpleAdapter simplerAdapterHasUsers) {
		this.simplerAdapterHasUsers = simplerAdapterHasUsers;
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
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onDetach() {

		super.onDetach();
	}
}


