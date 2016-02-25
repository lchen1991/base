package com.base.app.fragment;

import com.base.app.R;
import com.base.app.bean.BaseUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseFragment extends Fragment implements BaseUI{

	@Override
	public void onUICallback(int type, Object object) {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view,null);
		TextView tv =(TextView)view.findViewById(R.id.iv_error_info_title);
		tv.setText(getArguments().getString("title"));
		return view;
	}

}
