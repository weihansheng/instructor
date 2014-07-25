package itstudio.instructor.fragment;

import itstudio.app.R;
import itstudio.instructor.adapter.DraftsListViewAdapter;
import itstudio.instructor.xlistview.XListView;
import itstudio.instructor.xlistview.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * @Description 草稿 Fragment
 * 
 * @author MR.Wang
 * 
 * @date 2014-7-5 上午1:13:26
 * 
 * @version V1.0
 */

@SuppressLint("ValidFragment")
public class FragmentDrafts extends Fragment implements IXListViewListener {

	private DraftsListViewAdapter mAdapter;
	View parentView;
	private XListView mListView;
	private List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	public static FragmentDrafts mactivity;

	//
	private View view;
	private Context context;
	// 获取到下载url后，直接复制给MapApp,里面的全局变量

	private static FragmentDrafts singleton;

	public static FragmentDrafts getInstance(Context context) {
		if (singleton == null) {
			singleton = new FragmentDrafts(context);
		}
		return singleton;
	}

	private FragmentDrafts(Context context) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mactivity = this;

		/*
		 * view = inflater.inflate(R.layout.fragment_drafts, container, false);
		 * context = inflater.getContext();
		 */
		if (parentView == null) {
			parentView = inflater.inflate(R.layout.fragment_drafts, container,
					false);
			initView(parentView);
		}
		ViewGroup parent = (ViewGroup) parentView.getParent();
		if (parent != null) {
			parent.removeView(parentView);
		}

		return parentView;

	}

	private void initView(View parentView2) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 7; i++) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("list_item_title", "标题" + i);
			hashMap.put("list_item_content", "客户端发布啦客户端发布啦客户端发布啦客户端发布啦客户端发布啦客户端发布啦客户端发布啦客户端发布啦");
			hashMap.put("list_item_time", "2014.05.13");
			data.add(hashMap);
		}
		mListView = (XListView) parentView.findViewById(R.id.listview_drafts);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(false);// 取消下拉刷新

		// 获取屏幕分辨率
		DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm); 只能在activity中用
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		mAdapter = new DraftsListViewAdapter(getActivity(), data,
				R.layout.listview_item_user_info_delete_btn, new String[] {
						"list_item_title", "list_item_content",
						"list_item_time" }, new int[] { R.id.list_item_title,
						R.id.list_item_content, R.id.list_item_time },
				dm.widthPixels);
		mListView.setXListViewListener(this);

		mListView.setAdapter(mAdapter);
		/*mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override   //点击事件放在了Ontouch中
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "点击了listview", 0).show();
				System.out.println("点击了drafts的listview");
			}
		});*/
		// mListView.setXListViewListener((IXListViewListener) this);

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
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
}
