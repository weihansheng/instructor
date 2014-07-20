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
public class FragmentMessage extends Fragment  {

	//
	private View view;
	private Context context;
	// 获取到下载url后，直接复制给MapApp,里面的全局变量

	private static FragmentMessage singleton;

	public static FragmentMessage getInstance(Context context){
		if(singleton==null){
			singleton=new FragmentMessage(context);
		}
		return singleton;
	}
	
	private FragmentMessage(Context context) {
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_drafts, container, false);
		context = inflater.getContext();
		
		
		return view;
	}

}
