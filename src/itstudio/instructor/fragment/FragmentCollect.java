package itstudio.instructor.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itstudio.app.R;
import itstudio.instructor.adapter.CollectListViewAdapter;
import itstudio.instructor.xlistview.XListView;
import itstudio.instructor.xlistview.XListView.IXListViewListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Description 设置 Fragment
 * 
 * @author MR.Wang
 * 
 * @date 2014-7-5 上午1:13:26
 * 
 * @version V1.0
 */

@SuppressLint("ValidFragment")
public class FragmentCollect extends Fragment implements IXListViewListener {

	private CollectListViewAdapter mAdapter;
	View parentView;
	private XListView mListView;
	private List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	public static FragmentCollect mactivity;

	//
	private View view;
	private Context context;
	// 获取到下载url后，直接复制给MapApp,里面的全局变量

	private static FragmentCollect singleton;

	public static FragmentCollect getInstance(Context context) {
		if (singleton == null) {
			singleton = new FragmentCollect(context);
		}
		return singleton;
	}

	private FragmentCollect(Context context) {

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
			parentView = inflater.inflate(R.layout.fragment_collect, container,
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
		for (int i = 0; i < 20; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("list_item_title", "标题" + i);
			hashMap.put("list_item_content", "客户端发布客户端发布客户端发布客户端发布客户端发布客户端发布客户端发布客户端发布客户端发布");
			hashMap.put("list_item_time", "2014-05-13");
			hashMap.put("list_item_author", "李宁");
			hashMap.put("list_item_img", R.drawable.pic_local_head2);
			data.add(hashMap);
		}
		mListView = (XListView) parentView.findViewById(R.id.listview_collect);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(false);// 取消下拉刷新

		// 获取屏幕分辨率
		DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm); 只能在activity中用
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		mAdapter = new CollectListViewAdapter(getActivity(), data,
				R.layout.listview_item_collect, new String[] {
						"list_item_title", "list_item_content",
						"list_item_time", "list_item_author","list_item_img"}, new int[] { R.id.list_item_title,
						R.id.list_item_content, R.id.list_item_time,R.id.list_item_author,R.id.list_item_img},
				dm.widthPixels);
		mListView.setXListViewListener(this);

		mListView.setAdapter(mAdapter);

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
