package com.leo.LicenseMonitorLocalNetwork.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.adapters.ServerListSimpleAdapter;
import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;
import com.leo.LicenseMonitorLocalNetwork.listenerimpl.ServerListOnItemClickListenerImpl;
import com.leo.LicenseMonitorLocalNetwork.listenerimpl.ServerListOnItemLongClickListenerImpl;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.DBUtil;

public class ServersFragment extends Fragment{

	private ListView serverList = null;
	private ServerListSimpleAdapter serverAdapter = null;
	
	private static ServersFragment instance = new ServersFragment();
	
	private ServersFragment(){
		
	}
	
	public static ServersFragment getInstance() {
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
		
//System.out.println("servers fragment oncreate view");		
		View v = inflater.inflate(R.layout.fragment_servers, container, false);
		showServerList(v);
			
		return v;
	}
	
	
	private void showServerList(View v){
		serverList = (ListView) v.findViewById(R.id.server_list);
		//simple adapter list of maps
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		//if static serverBeanList is empty
		if(LicLiteData.serverBeanList.size() == 0){
			ArrayList<ServerBean> serverBeans = DBUtil.loadServerFromDB(getActivity());
			if(serverBeans != null){
				LicLiteData.serverBeanList = serverBeans;
			}
			
		}
		
		//initialize list for simple adapter
		for(ServerBean serverBean : LicLiteData.serverBeanList){
			Map<String, String> map = new HashMap<String, String>();
			
			if(serverBean.getIsLogin() == LicLiteData.IS_LOGINED){
				map.put("item_server_pic", String.valueOf(R.drawable.server_login));
			}else{
				map.put("item_server_pic", String.valueOf(R.drawable.server_not_login));
			}
			map.put("item_server_name", serverBean.getServerName());
			map.put("item_server_login_time", "login since: " + serverBean.getTimeStamp());
			list.add(map);
		}
		
		ServerListSimpleAdapter.setMyData(list);
		serverAdapter = new ServerListSimpleAdapter(getActivity(), R.layout.item_server_list,
				new String[]{"item_server_pic", "item_server_name", "item_server_login_time"},
				new int[]{R.id.item_server_pic, R.id.item_server_name, R.id.item_server_login_time});
		
		
//		serverAdapter = new ServerListSimpleAdapter(getActivity(), list, R.layout.item_server_list,
//				new String[]{"item_server_pic", "item_server_name", "item_server_login_time"},
//				new int[]{R.id.item_server_pic, R.id.item_server_name, R.id.item_server_login_time});
		this.serverList.setAdapter(serverAdapter);
		
		//register list item onclick listener
		serverList.setOnItemClickListener(new ServerListOnItemClickListenerImpl(getActivity(), serverAdapter));
		serverList.setOnItemLongClickListener(new ServerListOnItemLongClickListenerImpl(getActivity(), serverAdapter));
	}
	
	@Override
	public View getView() {
		return super.getView();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
}






