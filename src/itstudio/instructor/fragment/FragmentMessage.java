package itstudio.instructor.fragment;




import itstudio.app.R;
import itstudio.instructor.widget.PagerSlidingTabStrip;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
* @Description 消息 Fragment

* @author MR.Wang

* @date 2014-7-5 上午1:13:26 

* @version V1.0
*/

@SuppressLint("ValidFragment")
public class FragmentMessage extends Fragment  {
	
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

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
		view = inflater.inflate(R.layout.fragment_message, container, false);
		context = inflater.getContext();
		
		
		tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs_signup);
		pager = (ViewPager) view.findViewById(R.id.pagers_signup);
		adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
		pager.setAdapter(adapter);
		tabs.setShouldExpand(true);
		tabs.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
		tabs.setIndicatorColor(Color.parseColor("#40a3ff"));  //下划线颜色
		tabs.setIndicatorHeight(9);
		tabs.setViewPager(pager);
		
		
		return view;
	}
	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "给我的评论","我的评论"};

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			return FragmentMessageTab.newInstance(position,getActivity());
		}

	}

}
