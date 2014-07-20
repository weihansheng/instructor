package itstudio.instructor.adapter;

import itstudio.app.R;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewsCommentAdapter extends BaseAdapter {
	private class ViewHolder {
		ImageView itemIcon;
		TextView itemName;
		TextView itemTime;
		TextView itemContent;
		LinearLayout itemCommentLayout;
	}

	// 单行的布局
	private int mResource;
	private Context mContext;
	// 展现列表的数据
	private List<? extends Map<String, ?>> mData;
	// map中的key
	private String[] mForm;
	// view中的id
	private int[] mTo;

	/**
	 * 
	 * @param context
	 * @param data
	 *            列表展现的数据
	 * @param resource
	 *            单行的布局
	 * @param from
	 *            map中的key
	 * @param to
	 *            view中的id
	 */
	public NewsCommentAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {

		mContext = context;
		mData = data;
		mForm = from;
		mResource = resource;
		mTo = to;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			// 使用自定义的list_items作为layout
			convertView = LayoutInflater.from(mContext).inflate(mResource,
					parent, false);
			// 使用减少findciew的次数
			holder = new ViewHolder();
			holder.itemIcon = (ImageView) convertView.findViewById(mTo[0]);
			holder.itemName = (TextView) convertView.findViewById(mTo[1]);
			holder.itemTime = (TextView) convertView.findViewById(mTo[2]);
			holder.itemContent = (TextView) convertView.findViewById(mTo[3]);
			holder.itemCommentLayout = (LinearLayout) convertView
					.findViewById(R.id.comment_layout);
			// 设置标记
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置数据
		final Map<String, ?> dataSet = mData.get(position);
		if (dataSet == null) {
			return null;
		}
		// 设置改行数据
		final Object data0 = dataSet.get(mForm[0]);
		final Object data1 = dataSet.get(mForm[1]);
		final Object data2 = dataSet.get(mForm[2]);
		final Object data3 = dataSet.get(mForm[3]);
		// 设置数据岛view
		holder.itemIcon.setImageResource((Integer) data0);
		holder.itemName.setText(data1.toString());
		holder.itemTime.setTag(data2.toString());
		holder.itemContent.setText(data3.toString());
		// 设置按钮事件
		// 取消焦点
		holder.itemCommentLayout.setFocusable(false);
		holder.itemCommentLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "点击了回复", Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

}
