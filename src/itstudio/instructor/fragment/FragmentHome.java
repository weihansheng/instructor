package itstudio.instructor.fragment;

import itstudio.app.R;
import itstudio.instructor.adapter.NoticePagerAdapter;
import itstudio.instructor.adapter.NewsListViewAdapter;
import itstudio.instructor.entity.News;
import itstudio.instructor.entity.Notice;
import itstudio.instructor.ui.NewsDetailActivity;
import itstudio.instructor.ui.SendNewsActivity;
import itstudio.instructor.util.FileUtil;
import itstudio.instructor.util.FixedSpeedScroller;
import itstudio.instructor.widget.ChildViewPager;
import itstudio.instructor.widget.ChildViewPager.OnSingleTouchListener;
import itstudio.instructor.xlistview.XListView;
import itstudio.instructor.xlistview.XListView.IXListViewListener;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;


/**

* @Description 首页 Fragment

* @author MR.Wang

* @date 2014-7-5 上午12:32:06 

* @version V1.0
*/

@SuppressLint("ValidFragment")
public class FragmentHome extends Fragment  implements
AbsListView.OnItemClickListener , IXListViewListener ,OnSingleTouchListener{

	private View rootView;
	private XListView listView;
	private Context context;
	private ChildViewPager viewPager;
	private List<View> dots;
	private TextView tv_title;
	private int currentItem = 0;
	private ScheduledExecutorService scheduledExecutorService;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private NewsListViewAdapter adpater;
	private List<Notice> notices;
	private List<News> newsList;
	private Notice notice ;
	

	private static FragmentHome singleton;

	public static FragmentHome getInstance(Context context){
		if(singleton==null){
			singleton=new FragmentHome(context);
		}
		return singleton;
	}
	private FragmentHome(Context context) {
		this.context = context;

		notices = new ArrayList<Notice>();

		notice = new Notice();
		notice.setPicUrl("drawable://" + R.drawable.pic_slide_1);
		notice.setTitle("新生入学指导");
		notices.add(notice);
		
		notice = new Notice();
		notice.setPicUrl("drawable://" + R.drawable.pic_slide_2);
		notice.setTitle("军训集合通知");
		notices.add(notice);
		
		notice = new Notice();
		notice.setPicUrl("drawable://" + R.drawable.pic_slide_3);
		notice.setTitle("学生食堂饭价将下调");
		notices.add(notice);
		
		
		notice = new Notice();
		notice.setPicUrl("drawable://" + R.drawable.pic_slide_4);
		notice.setTitle("五一放假通知");
		notices.add(notice);
		
		
		newsList = new ArrayList<News>();
		News news ;
		
		news = new News();
		news.setAuthor("孙悟空");
		news.setTitle("美猴王驾到，显出原型来");
		news.setContent("美猴王驾到，显出原型来,美猴王驾到，显出原型来,美猴王驾到，显出原型来");
		news.setDate("2014/5/6");
		news.setHeadUrl("drawable://" + R.drawable.pic_local_head1);
		newsList.add(news);
		
		news = new News();
		news.setAuthor("孙悟空");
		news.setTitle("美猴王驾到，显出原型来");
		news.setContent("美猴王驾到，显出原型来,美猴王驾到，显出原型来,美猴王驾到，显出原型来");
		news.setDate("2014/5/6");
		news.setHeadUrl("drawable://" + R.drawable.pic_local_head2);
		newsList.add(news);
		
		news = new News();
		news.setAuthor("孙悟空");
		news.setTitle("美猴王驾到，显出原型来");
		news.setContent("美猴王驾到，显出原型来,美猴王驾到，显出原型来,美猴王驾到，显出原型来");
		news.setDate("2014/5/6");
		news.setHeadUrl("drawable://" + R.drawable.pic_local_head3);
		newsList.add(news);
		
		news = new News();
		news.setAuthor("孙悟空");
		news.setTitle("美猴王驾到，显出原型来");
		news.setContent("美猴王驾到，显出原型来,美猴王驾到，显出原型来,美猴王驾到，显出原型来");
		news.setDate("2014/5/6");
		news.setHeadUrl("drawable://" + R.drawable.pic_local_head4);
		newsList.add(news);
		
		news = new News();
		news.setAuthor("孙悟空");
		news.setTitle("美猴王驾到，显出原型来");
		news.setContent("美猴王驾到，显出原型来,美猴王驾到，显出原型来,美猴王驾到，显出原型来");
		news.setDate("2014/5/6");
		news.setHeadUrl("drawable://" + R.drawable.pic_local_head5);
		newsList.add(news);
		
		news = new News();
		news.setAuthor("孙悟空");
		news.setTitle("美猴王驾到，显出原型来");
		news.setContent("美猴王驾到，显出原型来,美猴王驾到，显出原型来,美猴王驾到，显出原型来");
		news.setDate("2014/5/6");
		news.setHeadUrl("drawable://" + R.drawable.pic_local_head6);
		newsList.add(news);
		
		news = new News();
		news.setAuthor("孙悟空");
		news.setTitle("美猴王驾到，显出原型来");
		news.setContent("美猴王驾到，显出原型来,美猴王驾到，显出原型来,美猴王驾到，显出原型来");
		news.setDate("2014/5/6");
		news.setHeadUrl("drawable://" + R.drawable.pic_local_head7);
		newsList.add(news);
		
		news = new News();
		news.setAuthor("孙悟空");
		news.setTitle("美猴王驾到，显出原型来");
		news.setContent("美猴王驾到，显出原型来,美猴王驾到，显出原型来,美猴王驾到，显出原型来");
		news.setDate("2014/5/6");
		news.setHeadUrl("drawable://" + R.drawable.pic_local_head8);
		newsList.add(news);
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initImageOptions();

	}
	 private void initImageOptions(){
			options = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.showImageOnLoading(R.drawable.bg_load_loading)
			.cacheOnDisc(true).considerExifParams(true)
			.displayer(new FadeInBitmapDisplayer(200))
			.bitmapConfig(Bitmap.Config.ALPHA_8).build();
	 }
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 if(rootView==null){ 
			 rootView = inflater.inflate(R.layout.fragment_home, container, false);
			 initView(rootView);
		}
		 ViewGroup parent = (ViewGroup) rootView.getParent();  
	     if (parent != null) {  
	    	 parent.removeView(rootView);  
	     }  
			 
		//rootView = inflater.inflate(R.layout.fragment_home, container, false);
		//initView(rootView);
		return rootView;
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		File cacheDir = StorageUtils.getCacheDirectory(getActivity());
		if(FileUtil.getDirSize(cacheDir)>2){
			imageLoader.clearDiscCache();
		}
	}
	
	
	/**
	 * 初始化布局文件中的控件
	 */
	private void initView(View view) {
		
		// initViewPager
		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		View header = layoutInflater.inflate(R.layout.viewpaper_image,
				listView, false);
		
		dots = new ArrayList<View>();
		dots.add(header.findViewById(R.id.v_dot0));
		dots.add(header.findViewById(R.id.v_dot1));
		dots.add(header.findViewById(R.id.v_dot2));
		dots.add(header.findViewById(R.id.v_dot3));
		tv_title = (TextView) header.findViewById(R.id.tv_titles);
		tv_title.setText(notices.get(0).getTitle());//
		viewPager = (ChildViewPager) header.findViewById(R.id.vp);
		setViewPagerScrollSpeed();
		viewPager.setAdapter(new NoticePagerAdapter(notices,
				getActivity(),options));
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		
		viewPager.setOnSingleTouchListener(this);
		
		
		//initListView
		listView = (XListView) view.findViewById(R.id.list);
		listView.addHeaderView(header);
		listView.setPullLoadEnable(true);
//		mListView.setPullRefreshEnable(false);
		adpater = new NewsListViewAdapter(getActivity(), newsList, options);
		
		AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter (adpater);
		animAdapter.setAbsListView(listView);
		animAdapter.setInitialDelayMillis(300);
		listView.setAdapter(animAdapter);
		listView.setXListViewListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
/*				Intent intent = new Intent();
				intent.setClass(context, SendNewsActivity.class);
				startActivity(intent);*/
				Intent intent = new Intent();
				intent.setClass(context, NewsDetailActivity.class);
				startActivity(intent);
				// TODO Auto-generated method stub

			}
		});
		listView.setOnScrollListener((new PauseOnScrollListener(imageLoader,
				false, false)));
	}


	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem, true);
		};
	};

	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3, 5,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	/**
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % notices.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(final int position) {
			currentItem = position;

			Animation animation = new AlphaAnimation(1.0f, 0);
			animation.setDuration(300);
			animation.setInterpolator(new DecelerateInterpolator());
			animation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					tv_title.setText(notices.get(position).getTitle());
					Animation animation1 = new AlphaAnimation(0, 1.0f);
					animation1.setDuration(300);
					animation1.setInterpolator(new AccelerateInterpolator());
					tv_title.startAnimation(animation1);
				}
			});
			tv_title.startAnimation(animation);

			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;

		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	
	

	/**
	 * 设置ViewPager的滑动速度
	 * 
	 * */
	private void setViewPagerScrollSpeed() {
		try {
			Field mScroller = null;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					viewPager.getContext());
			mScroller.set(viewPager, scroller);
		} catch (NoSuchFieldException e) {

		} catch (IllegalArgumentException e) {

		} catch (IllegalAccessException e) {

		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see itstudio.instructor.xlistview.XListView.IXListViewListener#onRefresh()
	 */
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
	/* (non-Javadoc)
	 * @see itstudio.instructor.xlistview.XListView.IXListViewListener#onLoadMore()
	 */
	@Override
	public void onLoadMore() {
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
	}
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime("刚刚");
	}
	/* (non-Javadoc)
	 * @see itstudio.travel.widget.ChildViewPager.OnSingleTouchListener#onSingleTouch()
	 */
	@Override
	public void onSingleTouch() {
		// TODO Auto-generated method stub
		System.out.println("当前 点击的是"+viewPager.getCurrentItem());
		Toast.makeText(context, R.string.wait_none, Toast.LENGTH_SHORT).show();
	}
}
