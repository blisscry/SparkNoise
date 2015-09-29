package com.nju.sparknoise;

import java.util.Timer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class LoadingActivity extends Activity{
	
	ImageView iv;
	Timer timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置无标题
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    //设置全屏
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.activity_loading);
//	    iv = (ImageView)this.findViewById(R.id.iv);
	    timer = new Timer(true);
//	    timer.schedule(hello, 200, 150); //延迟200毫秒执行，每150毫秒执行一次
	}
}
