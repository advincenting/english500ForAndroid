
package net.blogjava.vincent;

import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class LoadingActivity extends Activity {

	private Animation mAnimation;
	private int marginTop;
	private List<ImageView> mImageViews;
	private LinearLayout mLinearLayout;
	private int[] imageId = {R.drawable.l, R.drawable.a, R.drawable.o, R.drawable.d, 
			                 R.drawable.i, R.drawable.n, R.drawable.g};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        marginTop = dm.heightPixels - 80;
        
    
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.myanim);
        
        mLinearLayout = new LinearLayout(this);
        mLinearLayout.setBackgroundResource(R.drawable.background);
        
        mImageViews = new ArrayList<ImageView>();
        
   
        imageInit(mLinearLayout);
        

        setContentView(mLinearLayout);
        
 
        startAnimation();
            
    }
    
    
    private void imageInit(LinearLayout layout) {
    	
  
    	layout.setGravity(Gravity.CENTER_HORIZONTAL);
    	
  
    	LinearLayout.LayoutParams mParams1 = new LinearLayout.LayoutParams(40, 40);
    	mParams1.setMargins(30, marginTop, 0, 0);
    	
    	LinearLayout.LayoutParams mParams2 = new LinearLayout.LayoutParams(40, 40);
    	mParams2.setMargins(0, marginTop, 0, 0);

    
    	ImageView l = new ImageView(this);
    
    	l.setLayoutParams(mParams1);
    	layout.addView(l);
    	mImageViews.add(l);
    	
    	ImageView o = new ImageView(this);
    	o.setLayoutParams(mParams2);
    	layout.addView(o);
    	mImageViews.add(o);
    	
    	ImageView a = new ImageView(this);
    	a.setLayoutParams(mParams2);
    	layout.addView(a);
    	mImageViews.add(a);
    	
    	ImageView d = new ImageView(this);
    	d.setLayoutParams(mParams2);
    	layout.addView(d);
    	mImageViews.add(d);
    	
    	ImageView i = new ImageView(this);
    	i.setLayoutParams(mParams2);
    	layout.addView(i);
    	mImageViews.add(i);
    	
    	ImageView n = new ImageView(this);
    	n.setLayoutParams(mParams2);
    	layout.addView(n);
    	mImageViews.add(n);
    	
    	ImageView g = new ImageView(this);
    	g.setLayoutParams(mParams2);
    	layout.addView(g);
    	mImageViews.add(g);
  	
    }
    
    
    private void imageClear() {
    	for(ImageView image:mImageViews) {
    	
    		image.setImageDrawable(null);
  
    		image.destroyDrawingCache();		
    	}
    }
    
    Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
			
				mImageViews.get(0).setImageDrawable(LoadingActivity.this.getResources().getDrawable(R.drawable.l));
				mImageViews.get(0).setAnimation(mAnimation);
			
				break;
			case 1:
				mImageViews.get(1).setImageDrawable(LoadingActivity.this.getResources().getDrawable(R.drawable.o));
				mImageViews.get(1).setAnimation(mAnimation);
				break;
			case 2:
				mImageViews.get(2).setImageDrawable(LoadingActivity.this.getResources().getDrawable(R.drawable.a));
				mImageViews.get(2).setAnimation(mAnimation);
				break;
			case 3:
				mImageViews.get(3).setImageDrawable(LoadingActivity.this.getResources().getDrawable(R.drawable.d));
				mImageViews.get(3).setAnimation(mAnimation);
				break;
			case 4:
				mImageViews.get(4).setImageDrawable(LoadingActivity.this.getResources().getDrawable(R.drawable.i));
				mImageViews.get(4).setAnimation(mAnimation);
				break;
			case 5:
				mImageViews.get(5).setImageDrawable(LoadingActivity.this.getResources().getDrawable(R.drawable.n));
				mImageViews.get(5).setAnimation(mAnimation);
				break;
			case 6:
				mImageViews.get(6).setImageDrawable(LoadingActivity.this.getResources().getDrawable(R.drawable.g));
				mImageViews.get(6).setAnimation(mAnimation);
				break;
				
			case 100:
				imageClear();
			default:
				break;
			}
		}
    	
    	
    };
    
    public void startAnimation() {
    	new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//super.run();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int runCount = 0;
				int dely =0;
				while(true) {
					dely++;
					if(runCount < 2) {
						for(int i = 0; i < 7; i++) {
							mHandler.sendEmptyMessage(i);
							
							try {
								Thread.sleep(400);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						runCount++;
					} else {
						mHandler.sendEmptyMessage(100);
						runCount = 0;
					}
					if(dely>7){
						
						break;
					}
				}
	
				
				Intent it = new Intent(LoadingActivity.this, BookActivity.class);
				startActivityForResult(it, 0);
				overridePendingTransition(R.anim.fade, R.anim.hold);
			}
    		
    	}.start();
    }
    
    
}