package itstudio.instructor.fragment;

import java.util.ArrayList;
import java.util.List;

import itstudio.instructor.config.Constants;
import itstudio.instructor.ui.LoginActivity;
import itstudio.instructor.ui.MainActivity;
import itstudio.instructor.ui.MainUserInfoActivity;
import itstudio.instructor.widget.CustomScrollView;
import itstudio.instructor.widget.NoScrollGridView;
import itstudio.app.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Description 侧滑左Fragment
 * 
 * @author MR.Wang
 * 
 * @date 2014-7-5 上午1:13:57
 * 
 * @version V1.0
 */

public class LeftSlidingMenuFragment extends Fragment {

	// toolboxLayout
	private RelativeLayout toolboxHome;
	private View toolboxIntructor;
	private View toolboxPublic;
	private View toolboxCollect;
	private View toolboxSetting;
	private View toolboxSend;
	private View toolboxDrafts;
	private View toolboxMessage;

	private View currentView; // 当前选中的View
	private View rootView; // 当前整个View
	private View headImageView; // 左侧图像 ImageView
	private TextView nameTv; // 左侧名字 textView
	private TextView titleTv; // 顶部栏目标题 textView
	private int cur_pos = 0;// 当前显示的一行

	private CustomScrollView scrollView = null;

	public LeftSlidingMenuFragment() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.main_left_fragment, container,
				false);

		findViewById();
		setOnclick();
		return rootView;
	}

	protected void findViewById() {

		// 设置CustomScrollView
		ImageView backgroundImageView = (ImageView) rootView
				.findViewById(R.id.personal_background_image);
		scrollView = (CustomScrollView) rootView
				.findViewById(R.id.personal_scrollView);
		scrollView.setImageView(backgroundImageView);

		// findbyID
		toolboxHome = (RelativeLayout) rootView
				.findViewById(R.id.toolbox_layout_home);
		toolboxIntructor = rootView
				.findViewById(R.id.toolbox_layout_instructor);
		toolboxPublic = rootView.findViewById(R.id.toolbox_layout_public);
		toolboxCollect = rootView.findViewById(R.id.toolbox_layout_collect);
		toolboxSetting = rootView.findViewById(R.id.toolbox_layout_setting);
		toolboxSend = rootView.findViewById(R.id.toolbox_layout_send);
		toolboxDrafts = rootView.findViewById(R.id.toolbox_layout_drafts);
		toolboxMessage = rootView.findViewById(R.id.toolbox_layout_message);

		headImageView = rootView.findViewById(R.id.headImageView);
		nameTv = (TextView) rootView.findViewById(R.id.nameTextView);

		titleTv = MainActivity.titleTextView;
		currentView = toolboxHome;
		currentView.setSelected(true);
	}

	protected void setOnclick() {

		// 添加监听事件
		MyOClick click = new MyOClick();
		toolboxHome.setOnClickListener(click);
		toolboxIntructor.setOnClickListener(click);
		toolboxPublic.setOnClickListener(click);
		toolboxCollect.setOnClickListener(click);
		toolboxSetting.setOnClickListener(click);
		toolboxSend.setOnClickListener(click);
		toolboxDrafts.setOnClickListener(click);
		toolboxMessage.setOnClickListener(click);
		headImageView.setOnClickListener(click);

	}

	class MyOClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment newContent = null;
			Intent intent=new Intent();
			switch (v.getId()) {

			case R.id.toolbox_layout_home:
				newContent = FragmentHome.getInstance(getActivity());
				titleTv.setText(R.string.home);
				currentView.setSelected(false);
				currentView = toolboxHome;
				currentView.setSelected(true);
				break;

			case R.id.toolbox_layout_instructor:
				newContent = FragmentInstructor.getInstance();
				titleTv.setText(R.string.instructor);
				currentView.setSelected(false);
				currentView = toolboxIntructor;
				currentView.setSelected(true);
				break;

			case R.id.toolbox_layout_public:
				newContent = new FragmentPublic();
				titleTv.setText(R.string.public_num);
				currentView.setSelected(false);
				currentView = toolboxPublic;
				currentView.setSelected(true);
				break;

			case R.id.toolbox_layout_send:
				newContent = FragmentSend.getInstance(getActivity());
				titleTv.setText(R.string.edit);
				currentView.setSelected(false);
				currentView = toolboxSend;
				currentView.setSelected(true);
				break;

			case R.id.toolbox_layout_drafts:
				newContent = FragmentDrafts.getInstance(getActivity());
				titleTv.setText(R.string.drafts);
				currentView.setSelected(false);
				currentView = toolboxDrafts;
				currentView.setSelected(true);
				break;

			case R.id.toolbox_layout_message:
				newContent = FragmentMessage.getInstance(getActivity());
				/*
				intent.setClass(getActivity(), FragmentMessage.class);
	    		startActivity(intent);*/
				titleTv.setText(R.string.collect);
				currentView.setSelected(false);
				currentView = toolboxMessage;
				currentView.setSelected(true);
				break;
			case R.id.toolbox_layout_collect:
				newContent = FragmentCollect.getInstance(getActivity());
				titleTv.setText(R.string.collect);
				currentView.setSelected(false);
				currentView = toolboxCollect;
				currentView.setSelected(true);
				break;
			case R.id.toolbox_layout_setting:
				newContent = FragmentSetting.getInstance(getActivity());
				titleTv.setText(R.string.setting);
				currentView.setSelected(false);
				currentView = toolboxSetting;
				currentView.setSelected(true);
				break;
			case R.id.headImageView:
				if (!Constants.isLogin) {
					//intent.setClass(getActivity(), LoginActivity.class);
					intent.setClass(getActivity(), MainUserInfoActivity.class);
					startActivity(intent);

				}
				break;

			default:
				break;
			}

			if (newContent != null)
				switchFragment(newContent);
		}

	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		MainActivity ra = (MainActivity) getActivity();
		ra.switchContent(fragment);
	}

}
