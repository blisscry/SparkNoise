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
		//�����ޱ���
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    //����ȫ��
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.activity_loading);
//	    iv = (ImageView)this.findViewById(R.id.iv);
	    timer = new Timer(true);
//	    timer.schedule(hello, 200, 150); //�ӳ�200����ִ�У�ÿ150����ִ��һ��
	}
}
