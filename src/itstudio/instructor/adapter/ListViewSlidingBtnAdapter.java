package itstudio.instructor.adapter;

import itstudio.app.R;

import java.util.List;
import java.util.Map;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewSlidingBtnAdapter extends BaseAdapter
		 {
	/**
	 * 这个用来填充list
	 */
	private List<Integer> colors;
	/**
	 * context上下文,用来获得convertView
	 */
	/**
	 * 屏幕宽度,由于我们用的是HorizontalScrollView,所以按钮选项应该在屏幕外
	 */
	private int mScreentWidth;
	private int downX = 0;
	private int upX = 0;
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
	 * 为删除按钮提供一个回调接口
	 */
	/**
	 * 当前手指触摸的位置
	 */
	private int mCurrentViewPos;

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param screenWidth
	 */

	public ListViewSlidingBtnAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to, int screenWidth) {

		mContext = context;
		mData = data;
		mForm = from;
		mResource = resource;
		mTo = to;
		mScreentWidth = screenWidth;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		// 如果没有设置过,初始化convertView
		if (convertView == null) {
			// 获得设置的view
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.listview_item_user_info_delete_btn, parent, false);

			// 初始化holder
			holder = new ViewHolder();

			convertView.setTag(holder);
		} else// 有直接获得ViewHolder
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.hSView = (HorizontalScrollView) convertView
				.findViewById(R.id.hsv);

		holder.action = convertView.findViewById(R.id.ll_action);
		holder.btTwo = (Button) convertView.findViewById(R.id.button2);

		// 把位置放到view中,这样点击事件就可以知道点击的是哪一条item
		holder.btTwo.setTag(position);

		holder.tvTitle = (TextView) convertView
				.findViewById(R.id.list_item_title);
		holder.tvContent = (TextView) convertView
				.findViewById(R.id.list_item_content);
		holder.tvTime = (TextView) convertView
				.findViewById(R.id.list_item_time);

		// 设置数据
		final Map<String, ?> dataSet = mData.get(position);
		if (dataSet == null) {
			return null;
		}
		// 设置改行数据
		final Object data0 = dataSet.get(mForm[0]);
		final Object data1 = dataSet.get(mForm[1]);
		final Object data2 = dataSet.get(mForm[2]);
		// 设置数据岛view
		holder.tvTitle.setText(data0.toString());
		holder.tvContent.setText(data1.toString());
		holder.tvTime.setText(data2.toString());

		// 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
		holder.content = convertView.findViewById(R.id.ll_content);
		LayoutParams lp = holder.content.getLayoutParams();
		lp.width = mScreentWidth;

		holder.btTwo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext,
						"删除了" + holder.tvTitle.getText().toString(), 0).show();
				mData.remove(position);
				System.out.println(position);
				ListViewSlidingBtnAdapter.this.notifyDataSetChanged();

			}
		});
		// 设置监听事件
		convertView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					downX = (int) event.getX();
					break;
				case MotionEvent.ACTION_UP:
					upX = (int) event.getX();
					// 获得ViewHolder
					ViewHolder viewHolder = (ViewHolder) v.getTag();

					// 获得HorizontalScrollView滑动的水平方向值.
					int scrollX = viewHolder.hSView.getScrollX();

					// 获得操作区域的长度
					int actionW = viewHolder.action.getWidth();
					if (Math.abs(upX - downX) < 10) {
						Toast.makeText(mContext, "点击了listview", 0).show();
						/*
						 * viewHolder.hSView.scrollTo(0, 0);
						 * viewHolder.hSView.smoothScrollTo(0, 0);
						 */
					}

					// 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
					// 如果水平方向的移动值<操作区域的长度的一半,就复原
					if (scrollX < actionW / 2) {
						viewHolder.hSView.smoothScrollTo(0, 0);
					} else// 否则的话显示操作区域
					{
						viewHolder.hSView.smoothScrollTo(actionW, 0);

					}
					return true;

				}
				return false;
			}
		});

		// 这里防止删除一条item后,ListView处于操作状态,直接还原
		if (holder.hSView.getScrollX() != 0) {
			holder.hSView.scrollTo(0, 0);
		}

		return convertView;
	}

	// 主要是避免了不断的view获取初始化.
	class ViewHolder {
		public HorizontalScrollView hSView;

		public View content;
		public TextView tvTitle;
		public TextView tvContent;
		public TextView tvTime;

		public View action;
		public Button btTwo;
	}

}
