package itstudio.instructor.ui;

import itstudio.app.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainReviseInfoActivity extends Activity{
	private View back_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revise_info);
		findViewById();
		setOnOnClickListener();
	}

	private void findViewById() {
		back_layout = findViewById(R.id.login_back_layout);

	}

	private void setOnOnClickListener() {
		MyOnClick myOnClick = new MyOnClick();
		back_layout.setOnClickListener(myOnClick);
	}

	class MyOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_back_layout:
				finish();
				break;

			default:
				break;
			}

		}

	}

}


