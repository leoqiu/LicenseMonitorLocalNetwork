package com.leo.LicenseMonitorLocalNetwork.asyncs;

import android.os.AsyncTask;

import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;
import com.leo.LicenseMonitorLocalNetwork.db.LGeneralDBManager;


public class InsertServerBeanIntoDBAsyncTask extends AsyncTask<Object, Object, String>{

	private LGeneralDBManager lGeneralDBManager = null;
	private ServerBean serverBean = null;
	
	public InsertServerBeanIntoDBAsyncTask(LGeneralDBManager lGeneralDBManager, ServerBean serverBean){
		this.lGeneralDBManager = lGeneralDBManager;
		this.serverBean = serverBean;
	}
	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Object... arg0) {
		
		lGeneralDBManager.insertIntoServers(serverBean);
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		lGeneralDBManager.close();
	}
	
}




