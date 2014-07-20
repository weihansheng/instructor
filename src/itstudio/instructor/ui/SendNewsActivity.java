package itstudio.instructor.ui;

import itstudio.app.R;
import itstudio.instructor.adapter.AddImageAdapter;
import itstudio.instructor.image.dispose.CompressionImage;
import itstudio.instructor.image.local.localImageFirstActivity;
import itstudio.instructor.widget.ActionSheet;
import itstudio.instructor.util.FileUtil;
import itstudio.instructor.widget.ActionSheet.OnActionSheetSelected;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import me.storm.ninegag.view.swipeback.ImageViewActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SendNewsActivity extends Activity implements OnActionSheetSelected{
	
	EditText editTitle = null;
	EditText editContent = null;
	TextView txtLeft = null;
	TextView txtSubmit = null;
	
	final int MAX_LENGTH = 100;
	int Rest_Length = MAX_LENGTH;
	
	GridView gridview ;
	String allPath;
	
	final int REQUEST_IMAGEVIEW = 101;
	final int REQUEST_LOCAL_IMG = 105;
	public static int RESULT_LOCAL_IMG = 106;
	DisplayImageOptions options;
	private List <String> picList = new ArrayList<String>();
	Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_news);
		picList.add("drawable://"+R.drawable.qzone_photo_btn_add);
		initImageOptions();
		initView();
		
	}
	 private void initImageOptions(){
			options = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisc(true).considerExifParams(true)
			.displayer(new FadeInBitmapDisplayer(200))
			.bitmapConfig(Bitmap.Config.ALPHA_8).build();
	 }
	public void initView() {
		// TODO Auto-generated method stub
		editTitle=(EditText) findViewById(R.id.title_hint);
		editContent=(EditText) findViewById(R.id.content_edt);
		editContent.addTextChangedListener(listener1);
		txtLeft = (TextView) findViewById(R.id.num_hint);
		txtSubmit = (TextView) findViewById(R.id.submit_txt2);
		gridview = (GridView) findViewById(R.id.gridview);  
		gridview.setAdapter(new AddImageAdapter(picList, getApplicationContext(),options));
		
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				System.out.println(position);
				if(position==picList.size()-1){
					Toast.makeText(getApplicationContext(), "选择图片", Toast.LENGTH_SHORT).show();
					ActionSheet.showSheet(SendNewsActivity.this, SendNewsActivity.this);
				}
				else{
					
					Intent intent = new Intent(SendNewsActivity.this, ImageViewActivity.class);
	                intent.putExtra(ImageViewActivity.IMAGE_URL, picList.get(position));
	                startActivityForResult(intent, REQUEST_IMAGEVIEW);
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	TextWatcher listener1 = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			if(Rest_Length>0){
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
			/*Animation shakeAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_y);
			submitText.startAnimation(shakeAnim);*/
		}
	};
	
	BaseAdapter adapter = new BaseAdapter()
    {

        // 取得适配器中的视图
        @Override
        public View getView( int position, View convertView, ViewGroup parent)
        {
            //将适配器中缓冲的视图控件返回
            View view = convertView;

            if (view == null)
            {
                // 将gallery_item.xml适配到View中
                LayoutInflater inflater = LayoutInflater
                        . from(getApplicationContext());
                view = inflater.inflate(R.layout.grid_view , null);
            }

            // 查找gallery_item.xml中的ImageView控件
            ImageView imageView = (ImageView) view
                    .findViewById(R.id.book_name);
            //imageView.setImageResource(picList.get(position));

           
            return view;
        }

        @Override
        public long getItemId( int position)
        {

            return position;
        }

        @Override
        public Object getItem( int position)
        {
            return position;
        }

        @Override
        public int getCount()
        {

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




	
	private void takePhoto(){
		
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String path = Environment.getExternalStorageDirectory()+CompressionImage.FILE_PATH;
		
		if(FileUtil.makeDir(path)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String str= df.format(new Date())+".png";// new Date()为获取当前系统时
			allPath = Environment.getExternalStorageDirectory()+CompressionImage.FILE_PATH+"/"+str;
			Uri imageUri = Uri.fromFile(new File(path,str));  
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(cameraIntent, 1);
		}
		else{
			
			Toast.makeText(SendNewsActivity.this, "插入sd卡 先", Toast.LENGTH_LONG).show();
		}
		
	}
	private void getLocalPohot(){
		Intent intent= new Intent();
		intent.setClass(SendNewsActivity.this, localImageFirstActivity.class);
		//List<String> tempList = picList;
		intent.putStringArrayListExtra("alredyChoose", (ArrayList<String>)picList);
		startActivityForResult(intent, REQUEST_LOCAL_IMG);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		System.out.println("fgasfgashf"+resultCode);
		if (resultCode == Activity.RESULT_OK) {
			picList.remove(picList.size()-1);
			picList.add(allPath);
			picList.add("drawable://"+R.drawable.qzone_photo_btn_add);
			AddImageAdapter adapter = new AddImageAdapter(picList, getApplicationContext(),options);
			gridview.setAdapter(adapter);
			//String path = new Camera().getPhoto(); //费时
			
		}
		else if(resultCode == ImageViewActivity.IMAGEVIEW_CANCEL){
			String path = data.getStringExtra(ImageViewActivity.IMAGE_URL);
			picList.remove(path);
			AddImageAdapter adapter = new AddImageAdapter(picList, getApplicationContext(),options);
			gridview.setAdapter(adapter);
		}
		else if(resultCode == RESULT_LOCAL_IMG){
			System.out.println("huilaile..............");
			
			picList = data.getStringArrayListExtra("alredyChoose");
			picList.add("drawable://"+R.drawable.qzone_photo_btn_add);
			AddImageAdapter adapter = new AddImageAdapter(picList, getApplicationContext(),options);
			gridview.setAdapter(adapter);
		}
	}
}
