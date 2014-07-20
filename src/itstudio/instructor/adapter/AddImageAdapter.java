/**
 * 
 */
package itstudio.instructor.adapter;


import itstudio.app.R;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**   
 * @Description  
 * @author MR.Wang  
 * @date 2014-7-15 下午1:08:35 
 * @version V1.0   
 */
public class AddImageAdapter  extends BaseAdapter{
	
	
	private Context context;
	private  List<String> adImageUrl;
	DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private LayoutInflater mInflater;
	
	/**
	 * 
	 */
	public AddImageAdapter(List<String> adImageUrl, Context context,DisplayImageOptions options) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.adImageUrl = adImageUrl;
		this.options = options;

	}
	
    // 取得适配器中的视图
    @Override
    public View getView( int position, View convertView, ViewGroup parent)
    {
        //将适配器中缓冲的视图控件返回

        if (convertView == null)
        {
        	mInflater = LayoutInflater.from(context);
        	convertView = mInflater.inflate(R.layout.grid_view, null);
            
        }
        PicModelHodler holder = null;
		holder = new PicModelHodler();
		ImageView imageView = (ImageView) convertView.findViewById(R.id.book_name);
		holder.imageView = imageView;
		String path = "file:///"+adImageUrl.get(position);
		if(position == adImageUrl.size()-1){
			path = adImageUrl.get(position);
		}
		imageLoader.displayImage(path ,holder.imageView, options);
       
        return convertView;
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

        return adImageUrl.size();
    }
	private final class PicModelHodler {
		public ImageView imageView;
	}
}
