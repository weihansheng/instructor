/**
 * 
 */
package itstudio.instructor.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import itstudio.app.R;
import itstudio.instructor.adapter.InstructorDetailAdapter;
import itstudio.instructor.adapter.NewsCommentAdapter;
import itstudio.instructor.adapter.NewsListViewAdapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**   
 * @Description  
 * @author MR.Wang  
 * @date 2014-7-14 下午9:42:06 
 * @version V1.0   
 */
public class NewsDetailActivity extends Activity{
	
	private LinearLayout layout ;
	DisplayImageOptions options;
	private ImageButton btnBack;
	private ListView comment_listview;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	private NewsCommentAdapter listAdapter;
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.news_detail_activity);
	    initImageOptions();
	    
	    
	    btnBack = (ImageButton) findViewById(R.id.btn_back);
	    ViewOncliclListenter listenter = new ViewOncliclListenter();
	    btnBack.setOnClickListener(listenter);
	    layout = (LinearLayout) findViewById(R.id.imageview);
	    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  
	    lp.bottomMargin= 20;
	    lp.gravity= Gravity.CENTER_HORIZONTAL;  

	    ImageView imageView ;
	    for(int index=0;index<3;index++){
	    	
	    	imageView = new ImageView(NewsDetailActivity.this);
	    	imageView.setLayoutParams(lp);  
	    	imageLoader.displayImage("drawable://"+R.drawable.pic_slide_3,imageView, options);
	    	layout.addView(imageView);
	    	imageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
	    }
	    //填充adapter
	    for (int i = 0; i <10; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("commenter_image", R.drawable.pic_local_head5);
			hashMap.put("commenter_name", "林雨婷");
			hashMap.put("comment_time", "2014-04-16");
			hashMap.put("comment_content", "支持、支持、支持！");
			data.add(hashMap);
		}
		listAdapter = new NewsCommentAdapter(this, data,
				R.layout.news_detail_comment_item,
				new String[] { "commenter_image", "commenter_name",
						"comment_time","comment_content" }, new int[] { R.id.commenter_image,
						R.id.commenter_name, R.id.comment_time ,R.id.comment_content});
		
	    comment_listview=(ListView) findViewById(R.id.listview_comment);
	    comment_listview.setAdapter(listAdapter);
	    setListViewHeightBasedOnChildren(comment_listview);// 解决listview与scrollview冲突问题

	    
	    
	}
	// 解决listview与scrollview冲突问题
		private void setListViewHeightBasedOnChildren(ListView listView) {

			ListAdapter listAdapter = listView.getAdapter();
			if (listAdapter == null) {
				return;
			}

			int totalHeight = 0;
			for (int i = 0; i < listAdapter.getCount(); i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			listView.setLayoutParams(params);
		}

	 private void initImageOptions(){
			options = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.showImageOnLoading(R.drawable.bg_load_loading)
			.cacheOnDisc(true).considerExifParams(true)
			.displayer(new FadeInBitmapDisplayer(200))
			.bitmapConfig(Bitmap.Config.ALPHA_8).build();
	 }
	class ViewOncliclListenter implements OnClickListener {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.btn_back:
					finish();
					break;

				default:
					break;
				}
			}
		}
}
