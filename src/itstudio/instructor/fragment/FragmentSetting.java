package itstudio.instructor.fragment;

import itstudio.app.R;
import itstudio.instructor.config.Constants;
import itstudio.instructor.ui.SettingFeedBackActivity;
import itstudio.instructor.ui.UpdateActivity;
import itstudio.instructor.widget.CustomDialog;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

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
public class FragmentSetting extends Fragment {

	//
	private CustomDialog.Builder ibuilder;
	Dialog dialog = null;
	private View view;
	private Context context;
	private View aboutView;
	private View feedbackView;
	private View cleanView;
	private Button exit_btn;
	// 获取到下载url后，直接复制给MapApp,里面的全局变量

	private static FragmentSetting singleton;

	public static FragmentSetting getInstance(Context context) {
		if (singleton == null) {
			singleton = new FragmentSetting(context);
		}
		return singleton;
	}

	private FragmentSetting(Context context) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_setting, container, false);
		context = inflater.getContext();

		findById();
		setOnclickListener();

		return view;
	}

	private void findById() {
		aboutView = view.findViewById(R.id.about);
		feedbackView = view.findViewById(R.id.feedbackview);
		cleanView = view.findViewById(R.id.cleanview);
		exit_btn=(Button) view.findViewById(R.id.exit);

	}

	private void setOnclickListener() {
		MyOnClickListener cllick = new MyOnClickListener();
		feedbackView.setOnClickListener(cllick);
		aboutView.setOnClickListener(cllick);
		cleanView.setOnClickListener(cllick);
		exit_btn.setOnClickListener(cllick);

	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.about:
				intent.setClass(getActivity(), UpdateActivity.class);
				startActivity(intent);
				break;
			case R.id.feedbackview:
				intent.setClass(getActivity(), SettingFeedBackActivity.class);
				startActivity(intent);
				break;
			case R.id.cleanview:
				// intent.setClass(getActivity(),SettingFeedBackActivity.class);
				showCleanDialog();
				break;
			case R.id.exit:
				showExitDialog();
			
				break;

			default:
				break;
			}

		}
	}

	private void showCleanDialog() {

		ibuilder = new CustomDialog.Builder(getActivity());
		ibuilder.setTitle(R.string.cleanCache);
		ibuilder.setMessage(R.string.cleanCache_message);
		ibuilder.setPositiveButton(R.string.confirm, null);
		ibuilder.setNegativeButton(R.string.cancel, null);
		dialog = ibuilder.create();
		dialog.show();

	}
	private void showExitDialog() {

		ibuilder = new CustomDialog.Builder(getActivity());
		ibuilder.setTitle(R.string.prompt);
		ibuilder.setMessage(R.string.exit_question);
		ibuilder.setPositiveButton(R.string.confirm, new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Constants.isLogin=false;
				Constants.account="请登录";
				dialog.dismiss();
			}
		});
		ibuilder.setNegativeButton(R.string.cancel, null);
		dialog = ibuilder.create();
		dialog.show();

	}
}
