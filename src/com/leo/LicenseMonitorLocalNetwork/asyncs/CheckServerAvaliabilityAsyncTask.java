package com.leo.LicenseMonitorLocalNetwork.asyncs;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;

public class CheckServerAvaliabilityAsyncTask extends AsyncTask<Void, Void, Boolean>{

	private String hostName;
	private ProgressBar connectivityProgressBar = null;
	private TextView canBeConnectedTextView = null;
	
	
//	public CheckServerAvaliabilityAsyncTask(String hostName,
//			ProgressBar connectivityProgressBar, TextView canBeConnectedTextView) {
//		this.hostName = hostName;
//		this.connectivityProgressBar = connectivityProgressBar;
//		this.canBeConnectedTextView = canBeConnectedTextView;
//	} 
	
	public CheckServerAvaliabilityAsyncTask(String hostName, ProgressBar connectivityProgressBar, TextView canBeConnectedTextView) {
		this.hostName = hostName;
		this.connectivityProgressBar = connectivityProgressBar;
		this.canBeConnectedTextView = canBeConnectedTextView;

	} 
	
	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		connectivityProgressBar.setVisibility(View.VISIBLE);
		canBeConnectedTextView.setVisibility(View.INVISIBLE);
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {	
		
		Connection conn = new Connection(hostName);
//Connection conn2 = new Connection("192.168.1.1999");
		ConnectionInfo connectionInfo = null;
		try {
//System.out.println("conn2 info is --> " + conn2.getConnectionInfo());
//connectionInfo = conn.getConnectionInfo();
			connectionInfo = conn.connect(null, LicLiteData.CONNECTION_AVALIABILITY_TIMEOUT, 0);
//			connectionInfo = conn.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(connectionInfo != null){
			conn.close();
			conn = null;
			connectionInfo = null;
System.out.println("can be connected ... ");
			return true;
		}else{
System.out.println("can not be connected ... ");
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
		connectivityProgressBar.setVisibility(View.GONE);
System.out.println("result --> " + result);
		
		if(result == true){
			canBeConnectedTextView.setText("server avaliable");
			canBeConnectedTextView.setTextColor(Color.GREEN);
		}else{
			canBeConnectedTextView.setText("server unavaliable");
			canBeConnectedTextView.setTextColor(Color.RED);		
		}
		canBeConnectedTextView.setVisibility(View.VISIBLE);
	}
	
}




