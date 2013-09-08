package com.leo.LicenseMonitorLocalNetwork.adapters;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;

public class NotificationListSimpleAdapter extends SimpleAdapter{

	private static List<Map<String, String>> myData = null;
	
	public static List<Map<String, String>> getMyData() {
		return myData;
	}

	public static void setMyData(List<Map<String, String>> myData) {
		NotificationListSimpleAdapter.myData = myData;
	}
	
	public static void addData(Map<String, String> map){
		NotificationListSimpleAdapter.myData.add(map);
	}
	
	public static void remove(int pos){
		NotificationListSimpleAdapter.myData.remove(pos);
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = (View) super.getView(position, convertView, parent);
		TextView textView = (TextView) v.findViewById(R.id.textview_is_notification_running_bg);
		
		if(LicLiteData.notificationList.get(position).isNotificationRunning()){
			textView.setTextColor(Color.GREEN);
			textView.setText("Notification check is running ...");
		}else{
			textView.setTextColor(Color.RED);
			textView.setText("Notification check is NOT running ...");
		}
		
		return v;
	}

	public NotificationListSimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		
	}
	
	public NotificationListSimpleAdapter(Context context, int resource, String[] from,
			int[] to) {
		super(context, NotificationListSimpleAdapter.myData, resource, from, to);
		
	}

}




