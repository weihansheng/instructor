package itstudio.instructor.ui;

import itstudio.app.R;
import itstudio.instructor.adapter.UserInfoListViewAdapter;
import itstudio.instructor.fragment.FragmentInstructor;
import itstudio.instructor.widget.PullScrollView;
import itstudio.instructor.widget.PullScrollView.ScrollViewListener;
import itstudio.instructor.xlistview.XListView2;
import itstudio.instructor.xlistview.XListView2.IXListViewListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainUserInfoActivity extends Activity implements
		IXListViewListener, PullScrollView.OnTurnListener {

	private UserInfoListViewAdapter mAdapter;
	public static MainUserInfoActivity mactivity;

	private XListView2 mListView;
	private List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	private PullScrollView mScrollView;
	private ImageView mHeadImg;
	private LinearLayout back_layout;
	private LinearLayout bt_revise_user_info;
	private LinearLayout layout_follow;
	private LinearLayout layout_fans;
	private LinearLayout layout_post;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mactivity = this;
		setContentView(R.layout.activity_user_info);
		findViewById();
		setOnclick();
		initView();

	}

	protected void findViewById() {
		back_layout = (LinearLayout) findViewById(R.id.user_info_back_layout);
		bt_revise_user_info = (LinearLayout) findViewById(R.id.revise_user_info_layout);
		layout_follow=(LinearLayout) findViewById(R.id.user_info_follow_layout);
		layout_fans=(LinearLayout) findViewById(R.id.user_info_fans_layout);
		layout_post=(LinearLayout) findViewById(R.id.user_info_post_layout);

	}

	protected void setOnclick() {
		MyOnClick onclick = new MyOnClick();
		back_layout.setOnClickListener(onclick);
		bt_revise_user_info.setOnClickListener(onclick);
		layout_follow.setOnClickListener(onclick);
		layout_fans.setOnClickListener(onclick);
		layout_post.setOnClickListener(onclick);

	}

	class MyOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.user_info_back_layout:
				finish();
				break;

			case R.id.revise_user_info_layout:
				intent.setClass(getApplicationContext(),MainReviseInfoActivity.class);
				startActivity(intent);
				break;
			case R.id.user_info_follow_layout:
				intent.setClass(getApplicationContext(), MyFollowActivity.class);
				startActivity(intent);
				break;
			case R.id.user_info_fans_layout:
				intent.setClass(getApplicationContext(), MyFansActivity.class);
				startActivity(intent);
				break;
			case R.id.user_info_post_layout:
				intent.setClass(getApplicationContext(), MyPostActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
			

		}

	}

	// 填充listview
	protected void initView() {
		mScrollView = (PullScrollView) findViewById(R.id.user_info_scroll_view_test);
		mHeadImg = (ImageView) findViewById(R.id.background_img_user_info_test);
		mScrollView.smoothScrollTo(0, 20);
		mScrollView.setHeader(mHeadImg);
		mScrollView.setOnTurnListener(this);
		//mScrollView.setScrollViewListener(this);
		//mScrollView.getLayoutParams().height = 900;
		mListView = (XListView2) findViewById(R.id.listviewTest);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(false);// 取消下拉刷新
	

		// mListView = (QQListView) findViewById(R.id.xlist_user_info);

		/*
		 * mListView.setPullLoadEnable(true);
		 * mListView.setPullRefreshEnable(false);// 取消下拉刷新
		 */
		for (int i = 0; i < 20; i++) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("list_item_title", "标题" + i);
			hashMap.put("list_item_content", "客户端发布啦");
			hashMap.put("list_item_time", "2014.05.13");
			data.add(hashMap);
		}

		

		//获取屏幕分辨率
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
	

		mAdapter = new UserInfoListViewAdapter(this, data,
				R.layout.listview_item_user_info_delete_btn, new String[] {
						"list_item_title", "list_item_content",
						"list_item_time" }, new int[] { R.id.list_item_title,
						R.id.list_item_content, R.id.list_item_time },
				dm.widthPixels);

		mListView.setAdapter(mAdapter);
		mListView.setXListViewListener(this);
		mAdapter.notifyDataSetChanged();

		setListViewHeightBasedOnChildren(mListView);

	}

	// 解决listview与scrollview冲突问题
	private void setListViewHeightBasedOnChildren(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

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

	@Override
	public void onTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollChanged(PullScrollView scrollView, int x, int y, int oldx, int oldy) {
	    // We take the last son in the scrollview
	    View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
	    int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

	    // if diff is zero, then the bottom has been reached
	    if (diff == 0) {
	        // do stuff
	    }
	}

}
