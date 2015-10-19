package com.nju.sparknoise.mainUI;

import java.util.Timer;

import com.nju.sparknoise.R;
import com.nju.sparknoise.R.anim;
import com.nju.sparknoise.R.drawable;
import com.nju.sparknoise.R.id;
import com.nju.sparknoise.R.layout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class LoadingActivity extends Activity{
	
	ImageView iv;
	Timer timer;
	
//	int[] images={
//    		R.drawable.loading_0,
//    		R.drawable.loading_1,
//    		R.drawable.loading_2,
//    		R.drawable.loading_3,
//    		R.drawable.loading_4,
//    		R.drawable.loading_5,
//    		R.drawable.loading_6,
//    		R.drawable.loading_7,
//    		R.drawable.loading_8,
//    		R.drawable.loading_9,
//    		R.drawable.loading_10,
//    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//�����ޱ���
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    //����ȫ��
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.activity_loading);
	    
	    final ImageView im=(ImageView) findViewById(R.id.loadingimg);
	    
	    @SuppressWarnings("deprecation")
		AnimationDrawable ad = (AnimationDrawable) getResources().getDrawable(R.drawable.loadinganimation);
	    im.setBackground(ad);
	    ad.start();
//	    im.setBackgroundResource(R.drawable.loadinganimation);
	    
	    //��������ʵ�ֶ����ķ�ʽ�����������߳��н��У�����ᱨ��=��=
//	    Thread loadingthread = new Thread(){
//				public void run() {
//				    for(int i=0;i<=10;i++){
//					    im.setImageResource(images[i]);
//					    Timer timer = new Timer();
//					    System.out.println("!!!"+i);
//					    try {
//							Thread.sleep(200);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//				    }
//				}
//	    };
//	    
//	    loadingthread.start();
	    
//		new Handler().postDelayed(new Runnable() {
//			public void run() {
//				for(int i=0;i<=10;i++){
//				    im.setImageResource(images[i]);
////				    Timer timer = new Timer();
//				    System.out.println("!!!"+i);
//				    try {
//						Thread.sleep(200);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			    }
//			}
//		}, 500);
	    

	    //������ʱ
	    Handler x = new Handler();
	    x.postDelayed(new SplashHandler(), 1000);
	}
	
	class SplashHandler implements Runnable{
		public void run() {
			startActivity(new Intent(LoadingActivity.this,MainActivity.class));
			finish();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out); 
		}
	}
	
//	new CountDownTimer(2000,1000) {
//		 
//		@Override
//		public void onTick(long millisUntilFinished) {
//		}
//		@Override
//		public void onFinish() {
//		Intent intent = new Intent();
//		intent.setClass(MainActivity.this, SecondActivity.class);
//		startActivity(intent);
//		 
//		int VERSION=Integer.parseInt(android.os.Build.VERSION.SDK);
//		if(VERSION >= 5){
//		MainActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
//		}
//		finish();
//		}
//		}.start();
		
}
