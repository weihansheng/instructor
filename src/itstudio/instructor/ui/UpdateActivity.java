package itstudio.instructor.ui;

import itstudio.app.R;
import itstudio.instructor.config.MyApplication;
import itstudio.instructor.service.DownloadService;
import itstudio.instructor.service.DownloadService.DownloadBinder;
import itstudio.instructor.widget.CustomDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UpdateActivity extends Activity {
	
	private int currentVersionCode;
	private DownloadBinder binder;
	private View back_layout;
	private View check_view;
	private TextView progress_text;
	private boolean isBinded;
	private CustomDialog.Builder ibuilder;
	Dialog dialog = null;
	private AlertDialog.Builder abuilder;
	// 获取到下载url后，直接复制给MapApp,里面的全局变量
	private String downloadUrl;
	private boolean isDestroy = true;
	private MyApplication app;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		
		app = (MyApplication) getApplication();
		progress_text=(TextView) findViewById(R.id.progress);
		check_view = (View) findViewById(R.id.check_update);
		back_layout=(View) findViewById(R.id.setting_back_layout);
		back_layout.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		check_view.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(isBinded){
					cancel();
					return;
				}
				PackageManager manager = UpdateActivity.this.getPackageManager();
				try {
					PackageInfo info = manager.getPackageInfo(UpdateActivity.this.getPackageName(), 0);
					String appVersion = info.versionName; // 版本名
					currentVersionCode = info.versionCode; // 版本号
					System.out.println(currentVersionCode + " " + appVersion);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch blockd
					e.printStackTrace();
				}
				//上面是获取manifest中的版本数据，我是使用versionCode
				//在从服务器获取到最新版本的versionCode,比较
				showUpdateDialog();
			}
		});
	}
	private void cancel(){
		
		binder.cancel();
		binder.cancelNotification();
		app.setDownload(false);
		if (isBinded) {
			unbindService(conn);
		}
		if (binder != null && binder.isCanceled()) {
			Intent it = new Intent(UpdateActivity.this, DownloadService.class);
			stopService(it);
		}
		isBinded = false;
		progress_text.setText(getResources().getString(R.string.update));
		
	}
	private void showUpdateDialog() {

		ibuilder = new CustomDialog.Builder(UpdateActivity.this);
		ibuilder.setTitle(R.string.update);
		ibuilder.setMessage(R.string.update_message_test);
		ibuilder.setPositiveButton(R.string.confirm, new MyOnClickListener());
		ibuilder.setNegativeButton(R.string.cancel, null);
		dialog = ibuilder.create();
		dialog.show();
		
	}
	class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			app.setDownload(true);
			Intent it = new Intent(UpdateActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
			dialog.dismiss();
		}


		
	}
	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			isBinded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			binder = (DownloadBinder) service;
			// 开始下载
			isBinded = true;
			binder.addCallback(callback);
			binder.start();

		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isDestroy && app.isDownload()) {
			Intent it = new Intent(UpdateActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
		}
		//System.out.println(" notification  onresume");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if (isDestroy && app.isDownload()) {
			Intent it = new Intent(UpdateActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
		}
		System.out.println(" notification  onNewIntent");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println(" notification  onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isDestroy = false;
		System.out.println(" notification  onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isBinded) {
			System.out.println(" onDestroy   unbindservice");
			unbindService(conn);
		}
		if (binder != null && binder.isCanceled()) {
			System.out.println(" onDestroy  stopservice");
			Intent it = new Intent(this, DownloadService.class);
			stopService(it);
		}
	}

	private ICallbackResult callback = new ICallbackResult() {

		@Override
		public void OnBackResult(Object result) {
			// TODO Auto-generated method stub
			if ("finish".equals(result)) {
				finish();
				return;
			}
			int i = (Integer) result;
			mHandler.sendEmptyMessage(i);
		}

	};

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progress_text.setText("当前进度 ： " + msg.what + "%"+"取消");

		};
	};

	public interface ICallbackResult {
		public void OnBackResult(Object result);
	}
}
