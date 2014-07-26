package itstudio.instructor.ui;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.storm.ninegag.view.swipeback.ImageViewActivity;
import itstudio.app.R;
import itstudio.instructor.adapter.AddImageAdapter;
import itstudio.instructor.fragment.FragmentSend;
import itstudio.instructor.image.dispose.CompressionImage;
import itstudio.instructor.image.local.localImageFirstActivity;
import itstudio.instructor.util.FileUtil;
import itstudio.instructor.widget.ActionSheet;
import itstudio.instructor.widget.ActionSheet.OnActionSheetSelected;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainReviseInfoActivity extends Activity implements OnActionSheetSelected{
	private View back_layout;
	private View revise_user_image_layout;
	private ImageView revise_user_image;
	private Context context;
	public static final int REQUEST_TAKE_PHOTO = 101;
	public static final int REQUEST_LOCAL_IMG = 102;
	public static final int RESULT_CUT_IMG = 103;
	String allPath;
	private List<String> picList = new ArrayList<String>();
	Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revise_info);
		context=this;
		findViewById();
		setOnOnClickListener();
	}

	private void findViewById() {
		back_layout = findViewById(R.id.login_back_layout);
		revise_user_image_layout=findViewById(R.id.layout_revise_user_image);
		revise_user_image=(ImageView) findViewById(R.id.revise_user_image);
		

	}

	private void setOnOnClickListener() {
		MyOnClick myOnClick = new MyOnClick();
		back_layout.setOnClickListener(myOnClick);
		revise_user_image_layout.setOnClickListener(myOnClick);
	}

	class MyOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_back_layout:
				finish();
				break;
			case R.id.layout_revise_user_image:
				Intent intent = new Intent(MainReviseInfoActivity.this, ImageViewActivity.class);
				/*intent.putExtra(ImageViewActivity.IMAGE_URL,
						picList.get(position));*/
				//startActivity(intent);
				ActionSheet.showSheet(context, MainReviseInfoActivity.this);
				break;

			default:
				break;
			}

		}

	}
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
			startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
		} else {

			Toast.makeText(context, "插入sd卡 先", Toast.LENGTH_LONG).show();
		}

	}

	private void getLocalPohot() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent,REQUEST_LOCAL_IMG);
	}
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的，小马不懂C C++
		 * 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么 制做的了...吼吼
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_CUT_IMG);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);

			/**
			 * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似
			 */

			/*
			 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 * photo.compress(Bitmap.CompressFormat.JPEG, 60, stream); byte[] b
			 * = stream.toByteArray(); // 将图片流以字符串形式存储下来
			 * 
			 * tp = new String(Base64Coder.encodeLines(b));
			 * 这个地方大家可以写下给服务器上传图片的实现，直接把tp直接上传就可以了， 服务器处理的方法是服务器那边的事了，吼吼
			 * 
			 * 如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换 为我们可以用的图片类型就OK啦...吼吼
			 * Bitmap dBitmap = BitmapFactory.decodeFile(tp); Drawable drawable
			 * = new BitmapDrawable(dBitmap);
			 */
			revise_user_image.setImageDrawable(drawable);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		System.out.println("fgasfgashf" + resultCode);

		switch (requestCode) {
		// 如果是直接从相册获取
		case REQUEST_LOCAL_IMG:
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			
			break;
		// 如果是调用相机拍照时
		case REQUEST_TAKE_PHOTO:
			 //Bitmap bmp = BitmapFactory.decodeFile(allPath); 
			 //revise_user_image.setImageBitmap(bmp);
			 Uri imageUri = Uri.fromFile(new File(allPath));
			 startPhotoZoom(imageUri);
			break;
		// 取得裁剪后的图片
		case RESULT_CUT_IMG:
			/**
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
			 * 当前功能时，会报NullException，小马只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
			 * 
			 */
			if (data != null) {
				setPicToView(data);
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	


}


