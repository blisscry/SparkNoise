package com.nju.sparknoise;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuDrawable.Stroke;
import com.balysv.materialmenu.MaterialMenuIcon;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
    //用户设置连续点击返回退出的初始值
    private long exitTime=0;
	
	/** DrawerLayout */
	private DrawerLayout mDrawerLayout;
	/** 左边栏菜单 */
	private ListView mMenuListView;
	/** 右边栏 */
	private RelativeLayout right_drawer;
	/** 菜单列表 */
	private String[] mMenuTitles;
	/** Material Design风格 */
	private MaterialMenuIcon mMaterialMenuIcon;
	/** 菜单打开/关闭状态 */
	private boolean isDirection_left = false;
	/** 右边栏打开/关闭状态 */
	private boolean isDirection_right = false;
	private View showView;
	
	
    private double leftup_lati_xianlin=32.135625;
    private double leftup_longi_xianlin=118.957666;
    private double leftdown_lati_xianlin=32.114395;
    private double leftdown_longi_xianlin=leftup_longi_xianlin;
    private double rightup_lati_xianlin=leftup_lati_xianlin;
    private double rightup_longi_xianlin=118.972048;
    private double rightdown_lati_xianlin=leftdown_lati_xianlin;
    private double rightdown_longi_xianlin=rightup_longi_xianlin;

    //此处为南大仙林校区图书馆位置坐标
//    private double centre_lati_xianlin=32.119762;
//    private double centre_longi_xianlin=118.966739;
    
    //此处为强制定位到南大仙林校区中心地点
    private double centre_lati_xianlin=32.123852;
    private double centre_longi_xianlin=118.965212;
    
    /*
     * 2015年9月6日21:39:06
     * 添加鼓楼校区地图许可
     * 地图左上角边缘经、纬度：118.780700、32.065296
     * 地图左下角边缘经、纬度：118.780700、32.056246
     * 地图右上角边缘经、纬度：118.790600、32.065296
     * 地图右下角边缘经、纬度：118.790600、32.056246
     */
    
    //定位相关
 	LocationClient mLocClient;
 	public MyLocationListenner myListener = new MyLocationListenner();
 	private LocationMode mCurrentMode;
 	BitmapDescriptor mCurrentMarker;

 	MapView mMapView;
 	BaiduMap mBaiduMap;

 	// UI相关
 	OnCheckedChangeListener radioButtonListener;
 	Button requestLocButton;
 	boolean isFirstLoc = true;//是否首次定位
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mMenuListView = (ListView) findViewById(R.id.navigation_drawer);
		right_drawer = (RelativeLayout) findViewById(R.id.right_drawer);
		this.showView = mMenuListView;

//		// 初始化菜单列表
//		mMenuTitles = getResources().getStringArray(R.array.item_title);
//		mMenuListView.setAdapter(new ArrayAdapter<String>(this,
//				R.layout.drawer_list_item, mMenuTitles));
//		mMenuListView.setOnItemClickListener(new DrawerItemClickListener());

		// 设置抽屉打开时，主要内容区被自定义阴影覆盖
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// 设置ActionBar可见，并且切换菜单和内容视图
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mMaterialMenuIcon = new MaterialMenuIcon(this, Color.WHITE, Stroke.THIN);
		mDrawerLayout.setDrawerListener(new DrawerLayoutStateListener());

//		if (savedInstanceState == null) {
//			selectItem(0);
//		}
		
//      requestLocButton = (Button) findViewById(R.id.button1);
//		mCurrentMode = LocationMode.NORMAL;
//		requestLocButton.setText("普通");
////		requestLocButton.setba
//		OnClickListener btnClickListener = new OnClickListener() {
//			public void onClick(View v) {
//				switch (mCurrentMode) {
//				case NORMAL:
//					requestLocButton.setText("跟随");
//					mCurrentMode = LocationMode.FOLLOWING;
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, mCurrentMarker));
//					break;
//				case COMPASS:
//					requestLocButton.setText("普通");
//					mCurrentMode = LocationMode.NORMAL;
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, mCurrentMarker));
//					break;
//				case FOLLOWING:
//					requestLocButton.setText("罗盘");
//					mCurrentMode = LocationMode.COMPASS;
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, mCurrentMarker));
//					break;
//				}
//			}
//		};
//		requestLocButton.setOnClickListener(btnClickListener);
//
//		RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup);
//		radioButtonListener = new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				if (checkedId == R.id.defaulticon) {
//					// 传入null则，回复默认图标
//					mCurrentMarker = null;
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, null));
//				}
//				if (checkedId == R.id.customicon) {
//					// 修改为自定义marker
//					mCurrentMarker = BitmapDescriptorFactory
//							.fromResource(R.drawable.icon_geo);
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, mCurrentMarker));
//				}
//			}
//		};
//		group.setOnCheckedChangeListener(radioButtonListener);
		
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
//		LatLng cenpt = new LatLng(centre_lati_xianlin,centre_longi_xianlin);
		
		//此处的target为设置地图中心地点的经纬度坐标
		//此处的zoom为设置地图缩放等级，经多次对比调试以后float16.3为最佳缩放水平
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom((float) 16.3).build()));
		
		
	}
	
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不再处理新接收的位置
			if (location == null || mMapView == null)
				return;
			
			double templati=location.getLatitude();
			double templongi=location.getLongitude();
			Log.v("initial", templati+"--"+templongi);
			if(!(templati>leftdown_lati_xianlin&&templati<leftup_lati_xianlin&&templongi>leftdown_longi_xianlin&&templongi<rightdown_longi_xianlin)){
				templati=centre_lati_xianlin;
				templongi=centre_longi_xianlin;
			}
			Log.v("afterjudge", templati+"--"+templongi);
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(templati)
					.longitude(templongi).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				//如果是第一次定位，则将地图中心点定位至强制中心点
				isFirstLoc = false;
				LatLng ll = new LatLng(centre_lati_xianlin,
						centre_longi_xianlin);
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		//退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
	
	/**
	 * DrawerLayout状态变化监听
	 */
	private class DrawerLayoutStateListener extends
			DrawerLayout.SimpleDrawerListener {
		/**
		 * 当导航菜单滑动的时候被执行
		 */
		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			showView = drawerView;
			if (drawerView == mMenuListView) {// 根据isDirection_left决定执行动画
				mMaterialMenuIcon.setTransformationOffset(
						MaterialMenuDrawable.AnimationState.BURGER_ARROW,
						isDirection_left ? 2 - slideOffset : slideOffset);
			} else if (drawerView == right_drawer) {// 根据isDirection_right决定执行动画
				mMaterialMenuIcon.setTransformationOffset(
						MaterialMenuDrawable.AnimationState.BURGER_ARROW,
						isDirection_right ? 2 - slideOffset : slideOffset);
			}
		}

		/**
		 * 当导航菜单打开时执行
		 */
		@Override
		public void onDrawerOpened(android.view.View drawerView) {
			if (drawerView == mMenuListView) {
				isDirection_left = true;
			} else if (drawerView == right_drawer) {
				isDirection_right = true;
			}
		}

		/**
		 * 当导航菜单关闭时执行
		 */
		@Override
		public void onDrawerClosed(android.view.View drawerView) {
			if (drawerView == mMenuListView) {
				isDirection_left = false;
			} else if (drawerView == right_drawer) {
				isDirection_right = false;
				showView = mMenuListView;
			}
		}
	}

	
	@Override
	public void onNavigationDrawerItemSelected(String title) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(title))
				.commit();
	}

	public void onSectionAttached(String title) {
//		switch (number) {
//		case 1:
//			mTitle = getString(R.string.title_section1);
//			break;
//		case 2:
//			mTitle = getString(R.string.title_section2);
//			break;
//		case 3:
//			mTitle = getString(R.string.title_section3);
//			break;
//		}
		mTitle = title;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_TITLE = "section_title";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(String title) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putString(ARG_SECTION_TITLE, title);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getString(ARG_SECTION_TITLE));
		}
	}

	
    //==========================================================================================
    //设置连续点击两次返回键退出程序
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
    	if(keyCode==KeyEvent.KEYCODE_BACK){
    		exit();
		return false;
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
    private void exit(){
    	if((System.currentTimeMillis()-exitTime)>2000){
    		Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
    		exitTime = System.currentTimeMillis();
    	}else{
    		finish();
    		new Handler().postDelayed(new Runnable() {
				public void run() {
					System.exit(0);
				}
			}, 200);
			
//			   延时还有如下两种方式
//				一、线程
//				new Thread(new Runnable(){    
//				     public void run(){    
//				         Thread.sleep(XXXX);    
//				         handler.sendMessage();----告诉主线程执行任务
//				     }    
//				 }).start    
//				二、延时器
//				TimerTask task = new TimerTask(){    
//				     public void run(){    
//				     //execute the task     
//				     }    
//				 };    
//				Timer timer = new Timer();  
//				   timer.schedule(task, delay);  
			
			
			//立刻杀死进程
//    		System.exit(0);
    		//或者如下
//    		android.os.Process.killProcess(android.os.Process.myPid());  
    	}
    }
    //==========================================================================================
}
