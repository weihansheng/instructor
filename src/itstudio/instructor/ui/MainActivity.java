package itstudio.instructor.ui;
import itstudio.instructor.config.Constants;
import itstudio.instructor.fragment.FragmentHome;
import itstudio.instructor.fragment.FragmentSetting;
import itstudio.instructor.fragment.LeftSlidingMenuFragment;
import itstudio.app.R;
import com.slidingmenu.lib.app2.SlidingFragmentActivity;
import com.slidingmenu.lib2.SlidingMenu;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
* @Description MainActivity

* @author MR.Wang

* @date 2014-7-5 上午9:43:33 

* @version V1.0
*/
public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {

	protected SlidingMenu mSlidingMenu;
	private ImageButton ivTitleBtnLeft;
	private Fragment mContent;
	public static TextView titleTextView;
	public static float density;
	public static float xdpi;
	public static float ydpi;
	public static float screenWidth;
	public static float screenHeight;
	public static float densityDPI;
	public static int screenWidthDip;
	public static int screenHeightDip;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getDisplayDp();
		initSlidingMenu();
		setContentView(R.layout.main_fragment);
		initView();
		

	}

	private void initView() {
		ivTitleBtnLeft = (ImageButton) this.findViewById(R.id.slidingmenu_left_title);
		ivTitleBtnLeft.setOnClickListener(this);
		titleTextView = (TextView) this.findViewById(R.id.main_title_name);

	}
	private void getDisplayDp() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		//dm = new DisplayMetrics();
		//getWindowManager().getDefaultDisplay().getMetrics(dm);
		density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;
		screenWidthDip = dm.widthPixels; // 屏幕宽（dip，如：320dip）
		screenHeightDip = dm.heightPixels; // 屏幕（dip，如：533dip）
		screenWidth = (int) (dm.widthPixels * density + 0.5f); // 屏幕宽（px，如：480px）
		screenHeight = (int) (dm.heightPixels * density + 0.5f); // 屏幕高（px，如：800px）
	};
	private void initSlidingMenu() {
		// customize the SlidingMenu
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT_OF);// 设置是左滑还是右滑
		//getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置手势模式
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置菜单宽度
		mSlidingMenu.setFadeDegree(0.5f);// 设置淡入淡出的比例
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);//
		mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		mSlidingMenu.setBehindScrollScale(0);// 设置滑动时拖拽效果
		
		setBehindContentView(R.layout.main_left_layout);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.main_left_fragment, new LeftSlidingMenuFragment())
		.commit();
		
		mContent =  FragmentHome.getInstance(MainActivity.this);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();
	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.slidingmenu_left_title:
			mSlidingMenu.showMenu(true);
			break;
		default:
			break;
		}

	}

	/**
	 * 左侧菜单点击切换首页的内容
	 */
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	/**
	 * @Description TODO
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		
		if (keyCode == KeyEvent.KEYCODE_BACK ) {  
			if(Constants.isEditDrafts){
				
				Toast.makeText(getApplicationContext(), "是否保存草稿", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
		
	}
}
