package com.leo.LicenseMonitorLocalNetwork.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class ServerListSimpleAdapter extends SimpleAdapter{

	//data source
	private static List<Map<String, String>> myData = null;

	public ServerListSimpleAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
	}
	
	public ServerListSimpleAdapter(Context context, int resource, String[] from, int[] to) {
		super(context, myData, resource, from, to);
	}

	public static List<Map<String, String>> getMyData() {
		return myData;
	}

	public static void setMyData(List<Map<String, String>> myData) {
		ServerListSimpleAdapter.myData = myData;
	}
	
	public static void updateMyData(int pos){
		ServerListSimpleAdapter.myData.remove(pos);
	}
	
	@Override
	public int getCount() {
		return myData.size();
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = super.getView(position, convertView, parent);
		
		return v;
	}
	
	
}



