package itstudio.instructor.image.local;

import itstudio.app.R;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class localImageSecondActivity extends Activity {
	private GridView mGridView;
	private List<String> list;
	private List<String> alredyChoose;
	private ChildAdapter adapter;
	public static TextView txtAlready;
	private Button btnCancel;
	private Button btnOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_pic_gridview_second);
		
		mGridView = (GridView) findViewById(R.id.child_grid);
		btnOk = (Button) findViewById(R.id.btn_ok);
		btnCancel= (Button) findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(clickListener);
		btnOk.setOnClickListener(clickListener);
		list = getIntent().getStringArrayListExtra("data");
		alredyChoose = getIntent().getStringArrayListExtra("alredyChoose");
		txtAlready = (TextView) findViewById(R.id.title);
		adapter = new ChildAdapter(this, list, alredyChoose,mGridView);
		mGridView.setAdapter(adapter);
		
	}

	@Override
	public void onBackPressed() {
		getChooseItem();
		super.onBackPressed();
	}
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.btn_ok:
				getChooseItem();
				finish();
				break;
			case R.id.btn_cancel:
				
				finish();
				break;

			default:
				break;
			}
		}
	};
	
	private void getChooseItem(){
		if(adapter.getSelectItems().size()>0){
			
			//List<String> chooseList= new ArrayList<String>();
			for(int index:adapter.getSelectItems()){
				alredyChoose.add(list.get(index));
			}
			Intent intent = new Intent();
			intent.putStringArrayListExtra("alredyChoose", (ArrayList<String>) alredyChoose);
			setResult(RESULT_OK,intent);
		}
	}
}
