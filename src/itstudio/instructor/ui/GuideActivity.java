package itstudio.instructor.ui;

import itstudio.instructor.adapter.GuidePagerAdapter;
import itstudio.app.R;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideActivity extends Activity {

	// 翻页控件
	private ViewPager mViewPager;
	//
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;
	private ImageView mPage3;
	//
	private LinearLayout linearLayout_indicator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 去掉标题栏全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);
		

		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mPage0 = (ImageView) findViewById(R.id.page0);
		mPage1 = (ImageView) findViewById(R.id.page1);
		mPage2 = (ImageView) findViewById(R.id.page2);
		mPage3 = (ImageView) findViewById(R.id.page3);
		linearLayout_indicator = (LinearLayout) this
				.findViewById(R.id.layout_indicator);
		/*
		 * 这里是每一页要显示的布局，根据应用需要和特点自由设计显示的内容 以及需要显示多少页等
		 */
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.guide1, null);
		View view2 = mLi.inflate(R.layout.guide2, null);
		View view3 = mLi.inflate(R.layout.guide3, null);
		View view5 = mLi.inflate(R.layout.guide4, null);
		/*
		 * 这里将每一页显示的view存放到ArrayList集合中 可以在ViewPager适配器中顺序调用展示
		 */
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view5);
		// 填充ViewPager的数据适配器
		GuidePagerAdapter mPagerAdapter = new GuidePagerAdapter(views,GuideActivity.this);
		mViewPager.setAdapter(mPagerAdapter);
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int page) {

			// 翻页时当前page,改变当前状态园点图片
			switch (page) {
			case 0:
				mPage0.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_focused));
				mPage1.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_normal));
				break;
			case 1:
				mPage1.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_focused));
				mPage0.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_normal));
				mPage2.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_normal));
				break;
			case 2:
				mPage2.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_focused));
				mPage1.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_normal));
				mPage3.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_normal));
				break;
			case 3:
				mPage3.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_focused));
				mPage2.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_normal));
				break;
/*				
			case 4:
				linearLayout_indicator.setVisibility(View.INVISIBLE);

				break;*/
			}
			
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}

}
