package itstudio.instructor.fragment;

import itstudio.app.R;
import itstudio.instructor.widget.PullScrollView;
import itstudio.instructor.xlistview.XListView;
import itstudio.instructor.xlistview.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class FragmentInstructorDetail extends Activity implements
		IXListViewListener, PullScrollView.OnTurnListener {
	private XListView mListView;
	private SimpleAdapter listAdapter;
	private List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	private Handler mHandler;
	private PullScrollView mScrollView;
	private ImageView mHeadImg;
	private LinearLayout back_layout;
	private Button bt_follow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置title
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.fragment_instructor_detail);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.fragment_instructor_detail_titlebar);
		findViewById();
		setOnclick();
		initView();

	}

	protected void findViewById() {
		back_layout = (LinearLayout) findViewById(R.id.instructor_detail_back_layout);
		bt_follow = (Button) findViewById(R.id.bt_jiaguanzhu);

	}

	protected void setOnclick() {
		MyOnClick onclick = new MyOnClick();
		back_layout.setOnClickListener(onclick);
		bt_follow.setOnClickListener(onclick);

	}

	class MyOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.instructor_detail_back_layout:
				finish();
				break;
			case R.id.bt_jiaguanzhu:
				if (bt_follow.isSelected()) {
					bt_follow.setSelected(false);
					bt_follow.setTextColor(Color.BLACK);
					bt_follow.setText("加关注");
					
				}else {
					bt_follow.setSelected(true);
					bt_follow.setText("已关注");
					bt_follow.setTextColor(Color.WHITE);
				}
				
				break;

			default:
				break;
			}

		}

	}

	// 填充listview
	protected void initView() {
		mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
		mHeadImg = (ImageView) findViewById(R.id.background_img);
		mScrollView.smoothScrollTo(0, 20);
		mScrollView.setHeader(mHeadImg);
		mScrollView.setOnTurnListener(this);
		mListView = (XListView) findViewById(R.id.xlist_test);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(false);// 取消下拉刷新
		for (int i = 0; i < 20; i++) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("list_item_title", "标题");
			hashMap.put("list_item_content", "客户端发布啦");
			hashMap.put("list_item_time", "2014.05.13");
			data.add(hashMap);
		}
		listAdapter = new SimpleAdapter(this, data,
				R.layout.fragment_instructor_detail_listitem, new String[] {
						"list_item_title", "list_item_content",
						"list_item_time" }, new int[] { R.id.list_item_title,
						R.id.list_item_content, R.id.list_item_time });
		mListView.setAdapter(listAdapter);
		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getApplicationContext(), "" + arg2,
						Toast.LENGTH_SHORT).show();
			}
		});
		setListViewHeightBasedOnChildren(mListView);// 解决listview与scrollview冲突问题
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

}
