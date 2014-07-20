package itstudio.instructor.adapter;

import java.util.List;

import itstudio.instructor.entity.News;
import itstudio.instructor.entity.Notice;
import itstudio.instructor.image.local.GroupAdapter.ViewHolder;
import itstudio.instructor.widget.RoundedImageView;
import itstudio.app.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsListViewAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private List<News> newsList;
	private News news ;

	public NewsListViewAdapter(Context context, List<News> newsList,DisplayImageOptions options) {
		this.newsList = newsList;
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.options = options;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsList.size() ;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final PicModelHodler holder ;
		news = newsList.get(position);
		
		if(convertView == null){
			holder = new PicModelHodler();
			convertView = mInflater.inflate(R.layout.listview_item_news, null);
			holder.imageView = (RoundedImageView) convertView.findViewById(R.id.image);
			holder.txt_author = (TextView) convertView.findViewById(R.id.txt_author);
			holder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
			holder.txt_short_content = (TextView) convertView.findViewById(R.id.txt_short_content);
			holder.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
		}
		else{
			holder = (PicModelHodler) convertView.getTag();
		}
		
		holder.txt_author.setText(news.getAuthor());
		holder.txt_title.setText(news.getTitle());
		holder.txt_short_content.setText(news.getContent());
		holder.txt_date.setText(news.getDate());
		//给ImageView设置路径Tag,这是异步加载图片的小技巧
		convertView.setTag(holder);
		imageLoader.displayImage(news.getHeadUrl(),holder.imageView, options, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// holder.progressBar.setProgress(0);
						// holder.progressBar.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						// holder.progressBar.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// holder.progressBar.setVisibility(View.GONE);
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {
						// holder.progressBar.setProgress(Math.round(100.0f *
						// current / total));
					}
				});
		
		return convertView;
	}

	private final class PicModelHodler {
		public RoundedImageView imageView;
		public TextView txt_author;
		public TextView txt_title;
		public TextView txt_date;
		public TextView txt_short_content;
	}
}
