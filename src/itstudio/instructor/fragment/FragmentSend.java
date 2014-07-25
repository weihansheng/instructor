package itstudio.instructor.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.storm.ninegag.view.swipeback.ImageViewActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import itstudio.app.R;
import itstudio.instructor.adapter.AddImageAdapter;
import itstudio.instructor.config.Constants;
import itstudio.instructor.image.dispose.CompressionImage;
import itstudio.instructor.image.local.localImageFirstActivity;
import itstudio.instructor.ui.SendNewsActivity;
import itstudio.instructor.ui.UpdateActivity;
import itstudio.instructor.util.FileUtil;
import itstudio.instructor.widget.ActionSheet;
import itstudio.instructor.widget.ActionSheet.OnActionSheetSelected;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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
public class FragmentSend extends Fragment implements OnActionSheetSelected {

	//
	private View view;
	private Context context;
	// 获取到下载url后，直接复制给MapApp,里面的全局变量

	EditText editTitle = null;
	EditText editContent = null;
	TextView txtLeft = null;
	TextView txtSubmit = null;

	final int MAX_LENGTH = 100;
	int Rest_Length = MAX_LENGTH;

	GridView gridview;
	String allPath;

	final int REQUEST_IMAGEVIEW = 101;
	final int REQUEST_LOCAL_IMG = 105;
	public static int RESULT_LOCAL_IMG = 106;
	DisplayImageOptions options;
	private List<String> picList = new ArrayList<String>();
	Bitmap bitmap;

	private static FragmentSend singleton;

	public static FragmentSend getInstance(Context context) {
		if (singleton == null) {
			singleton = new FragmentSend(context);
		}
		return singleton;
	}

	private FragmentSend(Context context) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_edit_news, container, false);
		context = inflater.getContext();
		if (picList.size() == 0) {

			picList.add("drawable://" + R.drawable.qzone_photo_btn_add);
		}
		initImageOptions();
		initView(view);
		Constants.isEditDrafts = true;
		return view;
	}

	private void initImageOptions() {
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(200))
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
	}

	public void initView(View view) {
		// TODO Auto-generated method stub
		editTitle = (EditText) view.findViewById(R.id.title_hint);
		editContent = (EditText) view.findViewById(R.id.content_edt);
		editContent.addTextChangedListener(listener1);
		txtLeft = (TextView) view.findViewById(R.id.num_hint);
		txtSubmit = (TextView) view.findViewById(R.id.submit_txt2);
		gridview = (GridView) view.findViewById(R.id.gridview);
		gridview.setAdapter(new AddImageAdapter(picList, context, options));

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				System.out.println(position);
				if (position == picList.size() - 1) {
					ActionSheet.showSheet(context, FragmentSend.this);
				} else {

					Intent intent = new Intent(context, ImageViewActivity.class);
					intent.putExtra(ImageViewActivity.IMAGE_URL,
							picList.get(position));
					startActivityForResult(intent, REQUEST_IMAGEVIEW);
				}
			}

		});
	}

	// 监听还可以输入多少个字
	TextWatcher listener1 = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			if (Rest_Length > 0) {
				Rest_Length = MAX_LENGTH - editContent.getText().length();
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			txtLeft.setText("还可以输入" + Rest_Length + "字");
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			txtLeft.setText("还可以输入" + Rest_Length + "字");
			/*
			 * Animation shakeAnim =
			 * AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_y);
			 * submitText.startAnimation(shakeAnim);
			 */
		}
	};

	BaseAdapter adapter = new BaseAdapter() {

		// 取得适配器中的视图
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 将适配器中缓冲的视图控件返回
			View view = convertView;

			if (view == null) {
				// 将gallery_item.xml适配到View中
				LayoutInflater inflater = LayoutInflater.from(context);
				view = inflater.inflate(R.layout.grid_view, null);
			}

			// 查找gallery_item.xml中的ImageView控件
			ImageView imageView = (ImageView) view.findViewById(R.id.book_name);
			// imageView.setImageResource(picList.get(position));

			return view;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public int getCount() {

			return picList.size();
		}
	};

	@Override
	public void onClick(int whichButton) {
		// TODO Auto-generated method stub
		switch (whichButton) {

		case R.id.local_pic:

			getLocalPohot();
			break;

		case R.id.take_photo:
			takePhoto();
			break;
		case R.id.cancel:
			break;

		default:
			break;
		}

	}

	private void takePhoto() {

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String path = Environment.getExternalStorageDirectory()
				+ CompressionImage.FILE_PATH;

		if (FileUtil.makeDir(path)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String str = df.format(new Date()) + ".png";// new Date()为获取当前系统时
			allPath = Environment.getExternalStorageDirectory()
					+ CompressionImage.FILE_PATH + "/" + str;
			Uri imageUri = Uri.fromFile(new File(path, str));
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(cameraIntent, 1);
		} else {

			Toast.makeText(context, "插入sd卡 先", Toast.LENGTH_LONG).show();
		}

	}

	private void getLocalPohot() {
		Intent intent = new Intent();
		intent.setClass(context, localImageFirstActivity.class);
		// List<String> tempList = picList;
		intent.putStringArrayListExtra("alredyChoose",
				(ArrayList<String>) picList);
		startActivityForResult(intent, REQUEST_LOCAL_IMG);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		System.out.println("fgasfgashf" + resultCode);
		if (resultCode == Activity.RESULT_OK) {
			picList.remove(picList.size() - 1);
			picList.add(allPath);
			picList.add("drawable://" + R.drawable.qzone_photo_btn_add);
			AddImageAdapter adapter = new AddImageAdapter(picList, context,
					options);
			gridview.setAdapter(adapter);
			// String path = new Camera().getPhoto(); //费时

		} else if (resultCode == ImageViewActivity.IMAGEVIEW_CANCEL) {
			String path = data.getStringExtra(ImageViewActivity.IMAGE_URL);
			picList.remove(path);
			AddImageAdapter adapter = new AddImageAdapter(picList, context,
					options);
			gridview.setAdapter(adapter);
		} else if (resultCode == RESULT_LOCAL_IMG) {
			System.out.println("huilaile..............");

			picList = data.getStringArrayListExtra("alredyChoose");
			picList.add("drawable://" + R.drawable.qzone_photo_btn_add);
			AddImageAdapter adapter = new AddImageAdapter(picList, context,
					options);
			gridview.setAdapter(adapter);
		}
	}

}
