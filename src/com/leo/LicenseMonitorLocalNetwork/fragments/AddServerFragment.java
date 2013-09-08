package com.leo.LicenseMonitorLocalNetwork.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.leo.LicenseMonitorLocalNetwork.R;
import com.leo.LicenseMonitorLocalNetwork.asyncs.CheckServerAvaliabilityAsyncTask;
import com.leo.LicenseMonitorLocalNetwork.beans.AutoWordBean;
import com.leo.LicenseMonitorLocalNetwork.beans.ServerBean;
import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;
import com.leo.LicenseMonitorLocalNetwork.utils.DBUtil;
import com.leo.LicenseMonitorLocalNetwork.utils.UIUtil;

public class AddServerFragment extends Fragment {

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// views
	private AutoCompleteTextView autoServerName = null;
	private AutoCompleteTextView autoLmstatLoc = null;
	private AutoCompleteTextView autoLmgrdServeLoc = null;
	private Spinner spinnerServerLoc = null;
	private Spinner spinnerserverTimeZone = null;
	private EditText edittextServerTimeout = null;
	private EditText edittextServerRetryTimes = null;
	private Button addServerButton = null;

	// check server connectivity
	private ProgressBar connectivityProgressBar = null;
	private TextView canBeConnectedTextView = null;

	private String serverName = null;
	private String lmstatLoc = null;
	private String lmgrdServeLoc = null;
	private String serverLoc = null;
	private String serverTimeZone = null;
	private String serverTimeout = null;
	private String serverRetryTimes = null;

	private ArrayAdapter<String> autoServerNameAdapter = null;
	private ArrayAdapter<String> autoLmstatCmdLocAdapter = null;
	private ArrayAdapter<String> autoLmgrdServerLocAdapter = null;

	private static AddServerFragment instance = new AddServerFragment();

	private AddServerFragment() {

	}

	public static AddServerFragment getInstance() {
		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_add_server, container,
				false);

		autoServerName = (AutoCompleteTextView) v
				.findViewById(R.id.auto_server_name);
		autoLmstatLoc = (AutoCompleteTextView) v
				.findViewById(R.id.auto_lmstat_loc);
		autoLmgrdServeLoc = (AutoCompleteTextView) v
				.findViewById(R.id.auto_lmgrd_server_location);
		spinnerServerLoc = (Spinner) v
				.findViewById(R.id.spinner_server_location);
		spinnerserverTimeZone = (Spinner) v
				.findViewById(R.id.spinner_server_time_zone);
		edittextServerTimeout = (EditText) v
				.findViewById(R.id.edittext_server_timeout);
		edittextServerRetryTimes = (EditText) v
				.findViewById(R.id.edittext_server_retry);
		addServerButton = (Button) v.findViewById(R.id.button_add_server);

		connectivityProgressBar = (ProgressBar) v
				.findViewById(R.id.progressbar_server_connectivity);
		canBeConnectedTextView = (TextView) v
				.findViewById(R.id.server_can_be_connected_label);

		// autoedittext adapter setup
		// first loaded, initialize auto adapters
		if (LicLiteData.autoServerNameSet.size() == 0
				|| LicLiteData.autoLmstatLocSet.size() == 0 || LicLiteData.autoLmgrdServerLocSet.size() == 0) {
			DBUtil.initializeAutoArrayList(getActivity());
		}

		// create and set up auto adapters
		autoServerNameAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line,
				LicLiteData.autoServerNameSet.toArray(new String[0]));
		autoServerName.setAdapter(autoServerNameAdapter);
		autoLmstatCmdLocAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line,
				LicLiteData.autoLmstatLocSet.toArray(new String[0]));
		autoLmstatLoc.setAdapter(autoLmstatCmdLocAdapter);
		autoLmgrdServerLocAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line,
				LicLiteData.autoLmgrdServerLocSet.toArray(new String[0]));
		autoLmgrdServeLoc.setAdapter(autoLmgrdServerLocAdapter);
		
		// spinner adapter setup
		ArrayAdapter<CharSequence> spinnerServerLocAdapter = ArrayAdapter
				.createFromResource(getActivity(),
						R.array.spinner_server_location_array,
						android.R.layout.simple_spinner_dropdown_item);
		spinnerServerLoc.setAdapter(spinnerServerLocAdapter);
		ArrayAdapter<CharSequence> spinnerserverTimeZoneAdapter = ArrayAdapter
				.createFromResource(getActivity(),
						R.array.spinner_server_time_zone_array,
						android.R.layout.simple_spinner_dropdown_item);
		spinnerserverTimeZone.setAdapter(spinnerserverTimeZoneAdapter);

		// set Add Server button listener
		addServerButton
				.setOnClickListener(new AddServerButtonOnClickListenerImpl());
		// set autoServerName lose focus event listener
		autoServerName
				.setOnFocusChangeListener(new TestServerAvaliabilityOnFocusChangeListenerImpl());

		return v;
	}

	/**
	 * Test if the server is reachable
	 * 
	 * @author shqiu
	 * 
	 */
	private class TestServerAvaliabilityOnFocusChangeListenerImpl implements
			OnFocusChangeListener {

		public TestServerAvaliabilityOnFocusChangeListenerImpl() {

		}

		@Override
		public void onFocusChange(View view, boolean isFocused) {

			if (!isFocused) {
				String hostName = autoServerName.getText().toString();
				if(!hostName.equals("")){
					System.out.println("is host reachable --> " + hostName
							+ " --- ");

					CheckServerAvaliabilityAsyncTask checkServerAvaliabilityAsyncTask = new CheckServerAvaliabilityAsyncTask(hostName, connectivityProgressBar, canBeConnectedTextView);
					checkServerAvaliabilityAsyncTask.execute();
				}
			}
		}

	}

	/**
	 * Add server bean into servers table listener impl
	 * 
	 * @author shqiu
	 * 
	 */
	private class AddServerButtonOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View view) {
			serverName = autoServerName.getText().toString();
			lmstatLoc = autoLmstatLoc.getText().toString();
			lmgrdServeLoc = autoLmgrdServeLoc.getText().toString();
			serverLoc = spinnerServerLoc.getItemAtPosition(
					spinnerServerLoc.getSelectedItemPosition()).toString();
			serverTimeZone = spinnerserverTimeZone.getItemAtPosition(
					spinnerserverTimeZone.getSelectedItemPosition()).toString();
			serverTimeout = edittextServerTimeout.getText().toString();
			serverRetryTimes = edittextServerRetryTimes.getText().toString();

			if (serverName.equals("") || lmstatLoc.equals("") || lmgrdServeLoc.equals("")) {
				UIUtil.showToast(
						getActivity().getResources().getString(
								R.string.add_server_no_servername_or_lmgrdcmd),
						getActivity());
			} else {

				if (serverTimeout.equals("")) {
					serverTimeout = getActivity().getResources().getString(
							R.string.add_server_default_server_timeout);
				}
				if (serverRetryTimes.equals("")) {
					serverRetryTimes = getActivity().getResources().getString(
							R.string.add_server_default_server_retry_times);
				}

				if (!UIUtil.isServerExist(serverName, lmstatLoc, lmgrdServeLoc)) {
					ServerBean serverBean = new ServerBean(serverName,
							lmstatLoc, lmgrdServeLoc, serverLoc, serverTimeZone,
							serverTimeout, serverRetryTimes);
					AutoWordBean autoWordBean = new AutoWordBean(serverName,
							lmstatLoc, lmgrdServeLoc);
					// insert auto word bean into table - auto_word
					if (DBUtil.insertBeansIntoDBAndServerList(serverBean,
							autoWordBean, autoServerNameAdapter,
							autoLmstatCmdLocAdapter, autoLmgrdServerLocAdapter, getActivity())) {
						clearAllEditText();
						UIUtil.showToast(getActivity().getResources()
								.getString(R.string.add_server_successfully),
								getActivity());
					}

				} else {
					UIUtil.showToast(
							getActivity().getResources().getString(
									R.string.add_server_server_exist),
							getActivity());
				}

			}
		}
	}

	/**
	 * clear all the edit text fields
	 */
	private void clearAllEditText() {
		autoServerName.setText("");
		autoLmstatLoc.setText("");
		autoLmgrdServeLoc.setText("");
		edittextServerTimeout.setText("");
		edittextServerRetryTimes.setText("");
	}

}
