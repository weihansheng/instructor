package itstudio.instructor.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itstudio.app.R;
import itstudio.instructor.adapter.InstructorListViewAdapter;
import itstudio.instructor.xlistview.XListView;
import itstudio.instructor.xlistview.XListView.IXListViewListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
* @Description 公众号 Fragment

* @author MR.Wang

* @date 2014-7-5 上午1:13:26 

* @version V1.0
*/

public class FragmentPublic extends Fragment  implements IXListViewListener,OnClickListener{

	View parentView;
	private XListView instructor_listview;

	private static FragmentInstructor singleton;

	public static FragmentInstructor getInstance() {
		if (singleton == null) {
			singleton = new FragmentInstructor();
		}
		return singleton;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (parentView == null) {
			parentView = inflater.inflate(R.layout.fragment_instructor,
					container, false);
		}
		ViewGroup parent = (ViewGroup) parentView.getParent();
		if (parent != null) {
			parent.removeView(parentView);
		}

		// 填充listview
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 7; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("user_image", R.drawable.pic_local_head5);
			hashMap.put("instructor_name", "猪八戒");
			hashMap.put("instructor_jianjie", "爱吃是福，爱吃就是我的最大的优点。");
			data.add(hashMap);
		}
		InstructorListViewAdapter listAdapter = new InstructorListViewAdapter(getActivity(), data,
				R.layout.fragment_instructor_item_list,
				new String[] { "user_image","instructor_name", "instructor_jianjie"
						 }, new int[] {R.id.user_image, R.id.instructor_name,
						R.id.instructor_jianjie});
		instructor_listview = (XListView) parentView
				.findViewById(R.id.Fragment_instructor_listview);
		instructor_listview.setPullLoadEnable(true);
		instructor_listview.setPullRefreshEnable(false);// 取消下拉刷新
		instructor_listview.setAdapter(listAdapter);
		instructor_listview.setXListViewListener(this);
		instructor_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				// intent.setClass(getActivity(),
				// FragmentInstructorDetail.class);
				System.out.println("点击了listview");
				intent.setClass(getActivity(), FragmentInstructorDetail.class);
				startActivity(intent);

			}
		});
		
		
		return parentView;
	}

	// 设置下拉刷新时间
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
				// mAdapter.notifyDataSetChanged();
			}
		}, 2000);

	}

	// 设置loadmore刷新时间
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 2000);

	}

	private void onLoad() {
		instructor_listview.stopRefresh();
		instructor_listview.stopLoadMore();
		instructor_listview.setRefreshTime("刚刚");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
