package com.leo.LicenseMonitorLocalNetwork.fragments;

import com.leo.LicenseMonitorLocalNetwork.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RenderInTimeResultFragment extends Fragment {

	private static RenderInTimeResultFragment instance = new RenderInTimeResultFragment();
	
	
	public RenderInTimeResultFragment(){
		
	}
	
	public static RenderInTimeResultFragment getInstance(){
		return instance;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
System.out.println("on acitivity create ... ");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
System.out.println("on attach ... ");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_render_in_time_result, container, false);
		
		
		return v;
	}

}


















