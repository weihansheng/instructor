package me.storm.ninegag.view.swipeback;

import itstudio.app.R;
import itstudio.instructor.widget.ProgressWheel;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by storm on 14-4-15.
 */
public class ImageViewActivity extends SwipeBackActivity {
    public static final String IMAGE_URL = "image_url";
    public static int IMAGEVIEW_CANCEL = 102;
    String imageUrl;
    
    PhotoView photoView;

    ProgressWheel progressWheel;

    CheckBox checkBox;
    private PhotoViewAttacher mAttacher;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);

        imageUrl = getIntent().getStringExtra(IMAGE_URL);
        
        photoView = (PhotoView) findViewById(R.id.photoView);
        progressWheel = (ProgressWheel) findViewById(R.id.progressWheel);
        mAttacher = new PhotoViewAttacher(photoView);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
            	if(!checkBox.isChecked()){
            		
            		Intent intent = new Intent();
            		intent.putExtra(IMAGE_URL, imageUrl);
            		setResult(ImageViewActivity.IMAGEVIEW_CANCEL,intent);
            	}
                finish();
            }
        });

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisc(true)
                .considerExifParams(true).build();
        ImageLoader.getInstance().displayImage("file:///"+imageUrl, photoView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressWheel.setVisibility(View.GONE);
                mAttacher.update();
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                progressWheel.setProgress(360 * current / total);
            }
        });
        
        
       checkBox =  (CheckBox) findViewById(R.id.child_checkbox);
       checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
					addAnimation(checkBox);
				
			}
		});
    }
	private void addAnimation(View view){
		float [] vaules = new float[]{0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f, 1.1f, 1.2f, 1.3f, 1.25f, 1.2f, 1.15f, 1.1f, 1.0f};
		AnimatorSet set = new AnimatorSet();
		set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", vaules), 
				ObjectAnimator.ofFloat(view, "scaleY", vaules));
				set.setDuration(150);
		set.start();
	}
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAttacher != null) {
            mAttacher.cleanup();
        }
    }
    
    @Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode == KeyEvent.KEYCODE_BACK) { 
    	if(!checkBox.isChecked()){
    		Intent intent = new Intent();
    		intent.putExtra(IMAGE_URL, imageUrl);
    		setResult(IMAGEVIEW_CANCEL,intent);
    	}
        finish();
    	}
    	return super.onKeyDown(keyCode,event); 
    }
}
