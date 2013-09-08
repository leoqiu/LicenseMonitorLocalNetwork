package com.leo.LicenseMonitorLocalNetwork.fragments;

import android.app.Activity;
import android.app.Fragment;
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

public class RenderServersWithoutUsersFragment extends Fragment{

	private final static RenderServersWithoutUsersFragment instance = new RenderServersWithoutUsersFragment();
	
	private ListView serverWithoutListView = null;
	private AutoCompleteTextView autoCompleteTextViewWithoutUsers = null;
//	private List<Map<String, String>> licliteWithoutAdapterList = null;
	private SimpleAdapter simplerAdapterHasNoUsers = null;

	private RenderServersWithoutUsersFragment() {
		
	}
	
	public static RenderServersWithoutUsersFragment getInstance(){
		return instance;
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
		
		View v = inflater.inflate(R.layout.fragment_render_without_users, container, false);
		this.serverWithoutListView = (ListView) v.findViewById(R.id.listview_servers_wihtout_users);
		this.serverWithoutListView.setOnItemClickListener(new ServerWithoutListViewOnItemClickListenerImpl());
		
		this.autoCompleteTextViewWithoutUsers = (AutoCompleteTextView) v
				.findViewById(R.id.autocomplete_fragment_render_without_users);
		this.autoCompleteTextViewWithoutUsers
				.setOnItemClickListener(new AutoCompleteWithoutUsersOnItemClickListenerImpl());
		this.autoCompleteTextViewWithoutUsers.setDropDownWidth(0);
		this.autoCompleteTextViewWithoutUsers.setHint("search by feature name");
		this.autoCompleteTextViewWithoutUsers.setSelected(false);
		
		if(this.simplerAdapterHasNoUsers != null) {
			this.serverWithoutListView.setAdapter(simplerAdapterHasNoUsers);
			this.autoCompleteTextViewWithoutUsers.setAdapter(simplerAdapterHasNoUsers);
		}

		
		
		
		return v;
	}
	
	
	private class ServerWithoutListViewOnItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
//System.out.println("click on ... " + position + " adapter size -> " + simplerAdapterHasNoUsers.getCount());
//HashMap<String, String> map = (HashMap<String, String>)simplerAdapterHasNoUsers.getItem(position);
//System.out.println(" adapter size -> " + map.get("server_feature_name_value"));
		}
		
	}
	
	/**
	 * 
	 * @author shqiu
	 *
	 */
	private class AutoCompleteWithoutUsersOnItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			autoCompleteTextViewWithoutUsers.setText(null);
		}
		
	} 
	
	public ListView getServerWithoutListView() {
		return serverWithoutListView;
	}

	public void setServerWithoutListView(ListView serverWithoutListView) {
		this.serverWithoutListView = serverWithoutListView;
	}
	
//	public List<Map<String, String>> getLicliteWithoutAdapterList() {
//		return this.licliteWithoutAdapterList;
//	}
//
//	public void setLicliteWithoutAdapterList(
//			List<Map<String, String>> licliteWithoutAdapterList) {
//		this.licliteWithoutAdapterList = licliteWithoutAdapterList;
//	}

	public SimpleAdapter getSimplerAdapterHasNoUsers() {
		return this.simplerAdapterHasNoUsers;
	}

	public void setSimplerAdapterHasNoUsers(SimpleAdapter simplerAdapterHasNoUsers) {
		this.simplerAdapterHasNoUsers = simplerAdapterHasNoUsers;
	}
	
	public AutoCompleteTextView getAutoCompleteTextViewWithoutUsers() {
		return autoCompleteTextViewWithoutUsers;
	}

	public void setAutoCompleteTextViewWithoutUsers(
			AutoCompleteTextView autoCompleteTextViewWithoutUsers) {
		this.autoCompleteTextViewWithoutUsers = autoCompleteTextViewWithoutUsers;
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


