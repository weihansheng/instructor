package itstudio.instructor.fragment;

import itstudio.app.R;
import itstudio.instructor.ui.UpdateActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
* @Description 设置 Fragment

* @author MR.Wang

* @date 2014-7-5 上午1:13:26 

* @version V1.0
*/

@SuppressLint("ValidFragment")
public class FragmentSetting extends Fragment  {

	//
	private View view;
	private Context context;
	private View aboutView;
	// 获取到下载url后，直接复制给MapApp,里面的全局变量

	private static FragmentSetting singleton;

	public static FragmentSetting getInstance(Context context){
		if(singleton==null){
			singleton=new FragmentSetting(context);
		}
		return singleton;
	}
	
	private FragmentSetting(Context context) {
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_setting, container, false);
		context = inflater.getContext();
		aboutView =  view.findViewById(R.id.about);
		
		aboutView.setOnClickListener(new MyOnClickListener());
		
		return view;
	}
	class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(getActivity(), UpdateActivity.class);
			startActivity(it);
		}
	}
}
