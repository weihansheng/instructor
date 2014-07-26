package itstudio.instructor.fragment;

import itstudio.app.R;
import itstudio.instructor.adapter.MessageCommentAdapter;
import itstudio.instructor.xlistview.XListView;
import itstudio.instructor.xlistview.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @Description 消息 Fragment
 * 
 * @author MR.Wang
 * 
 * @date 2014-7-5 上午1:13:26
 * 
 * @version V1.0
 */

@SuppressLint("ValidFragment")
public class FragmentMessage extends Fragment implements IXListViewListener{

	public static FragmentMessage mactivity; 
	private ViewPager pager;
	private XListView message_listview;
	private MessageCommentAdapter listAdapter;
	private List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

	//
	private View view;
	private Context context;
	// 获取到下载url后，直接复制给MapApp,里面的全局变量

	private static FragmentMessage singleton;

	public static FragmentMessage getInstance(Context context) {
		if (singleton == null) {
			singleton = new FragmentMessage(context);
		}
		return singleton;
	}

	private FragmentMessage(Context context) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mactivity=this;
		view = inflater.inflate(R.layout.fragment_message, container, false);
		context = inflater.getContext();

		findById();
		setOnclick();
		initView(view);
		

		return view;
	}

	private void setOnclick() {
		// TODO Auto-generated method stub

	}

	private void findById() {
		// TODO Auto-generated method stub

	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		
		// 填充adapter
		message_listview=(XListView) view.findViewById(R.id.listview_message);
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("commenter_image_message", R.drawable.pic_local_head5);
			hashMap.put("commenter_name_message", "林雨婷");
			hashMap.put("comment_time_message", "2014-04-16");
			hashMap.put("comment_content_message", "支持、支持、支持！");
			data.add(hashMap);
		}
		listAdapter = new MessageCommentAdapter(getActivity(), data,
				R.layout.fragment_message_listitem, new String[] {
						"commenter_image_message", "commenter_name_message", "comment_time_message",
						"comment_content_message" }, new int[] { R.id.commenter_image_message,
						R.id.commenter_name_message, R.id.comment_time_message,
						R.id.comment_content_message });

		message_listview.setAdapter(listAdapter);
		message_listview.setPullLoadEnable(true);
		message_listview.setPullRefreshEnable(false);// 取消下拉刷新
		message_listview.setAdapter(listAdapter);
		message_listview.setXListViewListener(this);
		message_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				System.out.println("点击了instructor的listview");
				/*intent.setClass(getActivity(), FragmentInstructorDetail.class);
				startActivity(intent);*/

			}
		});
		

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
		message_listview.stopRefresh();
		message_listview.stopLoadMore();
		message_listview.setRefreshTime("刚刚");
	}

}
