package net.blogjava.vincent;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class BookActivity extends Activity {
	private PageWidget mPageWidget;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	BookPageFactory pagefactory;
	public static final String ENCODING = "GBK"; 
	 
	public String getFromAssets(String fileName){  
        String result = "";  
            try {  
                InputStream in = getResources().getAssets().open(fileName);  
      
                int lenght = in.available();  
         
                byte[]  buffer = new byte[lenght];  
            
                in.read(buffer);  
              
                try{ 
   		         FileOutputStream fout =openFileOutput("english.txt", MODE_PRIVATE); 
   		         fout.write(buffer); 
   		          fout.close(); 
   		         } 
   		        catch(Exception e){ 
   		         e.printStackTrace(); 
   		        } 
   		     
   		        
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return result;  
    }  
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mPageWidget = new PageWidget(this);
		setContentView(mPageWidget);

		mCurPageBitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap
				.createBitmap(480, 800, Bitmap.Config.ARGB_8888);

		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		pagefactory = new BookPageFactory(480, 800);

		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.bg));
		try {
			getFromAssets("english.txt");
			pagefactory.openbook("data/data/net.blogjava.vincent/files/english.txt");
			pagefactory.onDraw(mCurPageCanvas);
		} catch (IOException e1) {
			Toast.makeText(this, "Data is not avliable now, please contact me!",
					Toast.LENGTH_LONG).show();
		}

		mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);

		mPageWidget.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent e) {
				Log.i("event",e.getAction()+"");
				boolean ret = false;
				if (v == mPageWidget) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						mPageWidget.abortAnimation();
						mPageWidget.calcCornerXY(e.getX(), e.getY());
 
						pagefactory.onDraw(mCurPageCanvas);
						if (mPageWidget.DragToRight()) {
							try {
								pagefactory.prePage();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (pagefactory.isfirstPage())
								return false;
							pagefactory.onDraw(mNextPageCanvas);
						} else {
							try {
								pagefactory.nextPage();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (pagefactory.islastPage()) {
								return false;
							}
							pagefactory.onDraw(mNextPageCanvas);
						}
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}

					ret = mPageWidget.doTouchEvent(e);
					return ret;
				}

				return false;
			}

		});

	}
}