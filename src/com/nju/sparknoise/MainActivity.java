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
    //�û�����������������˳��ĳ�ʼֵ
    private long exitTime=0;
	
	/** DrawerLayout */
	private DrawerLayout mDrawerLayout;
	/** ������˵� */
	private ListView mMenuListView;
	/** �ұ��� */
	private RelativeLayout right_drawer;
	/** �˵��б� */
	private String[] mMenuTitles;
	/** Material Design��� */
	private MaterialMenuIcon mMaterialMenuIcon;
	/** �˵���/�ر�״̬ */
	private boolean isDirection_left = false;
	/** �ұ�����/�ر�״̬ */
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

    //�˴�Ϊ�ϴ�����У��ͼ���λ������
//    private double centre_lati_xianlin=32.119762;
//    private double centre_longi_xianlin=118.966739;
    
    //�˴�Ϊǿ�ƶ�λ���ϴ�����У�����ĵص�
    private double centre_lati_xianlin=32.123852;
    private double centre_longi_xianlin=118.965212;
    
    /*
     * 2015��9��6��21:39:06
     * ��ӹ�¥У����ͼ���
     * ��ͼ���ϽǱ�Ե����γ�ȣ�118.780700��32.065296
     * ��ͼ���½Ǳ�Ե����γ�ȣ�118.780700��32.056246
     * ��ͼ���ϽǱ�Ե����γ�ȣ�118.790600��32.065296
     * ��ͼ���½Ǳ�Ե����γ�ȣ�118.790600��32.056246
     */
    
    //��λ���
 	LocationClient mLocClient;
 	public MyLocationListenner myListener = new MyLocationListenner();
 	private LocationMode mCurrentMode;
 	BitmapDescriptor mCurrentMarker;

 	MapView mMapView;
 	BaiduMap mBaiduMap;

 	// UI���
 	OnCheckedChangeListener radioButtonListener;
 	Button requestLocButton;
 	boolean isFirstLoc = true;//�Ƿ��״ζ�λ
    

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

//		// ��ʼ���˵��б�
//		mMenuTitles = getResources().getStringArray(R.array.item_title);
//		mMenuListView.setAdapter(new ArrayAdapter<String>(this,
//				R.layout.drawer_list_item, mMenuTitles));
//		mMenuListView.setOnItemClickListener(new DrawerItemClickListener());

		// ���ó����ʱ����Ҫ���������Զ�����Ӱ����
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// ����ActionBar�ɼ��������л��˵���������ͼ
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mMaterialMenuIcon = new MaterialMenuIcon(this, Color.WHITE, Stroke.THIN);
		mDrawerLayout.setDrawerListener(new DrawerLayoutStateListener());

//		if (savedInstanceState == null) {
//			selectItem(0);
//		}
		
//      requestLocButton = (Button) findViewById(R.id.button1);
//		mCurrentMode = LocationMode.NORMAL;
//		requestLocButton.setText("��ͨ");
////		requestLocButton.setba
//		OnClickListener btnClickListener = new OnClickListener() {
//			public void onClick(View v) {
//				switch (mCurrentMode) {
//				case NORMAL:
//					requestLocButton.setText("����");
//					mCurrentMode = LocationMode.FOLLOWING;
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, mCurrentMarker));
//					break;
//				case COMPASS:
//					requestLocButton.setText("��ͨ");
//					mCurrentMode = LocationMode.NORMAL;
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, mCurrentMarker));
//					break;
//				case FOLLOWING:
//					requestLocButton.setText("����");
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
//					// ����null�򣬻ظ�Ĭ��ͼ��
//					mCurrentMarker = null;
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, null));
//				}
//				if (checkedId == R.id.customicon) {
//					// �޸�Ϊ�Զ���marker
//					mCurrentMarker = BitmapDescriptorFactory
//							.fromResource(R.drawable.icon_geo);
//					mBaiduMap
//							.setMyLocationConfigeration(new MyLocationConfiguration(
//									mCurrentMode, true, mCurrentMarker));
//				}
//			}
//		};
//		group.setOnCheckedChangeListener(radioButtonListener);
		
		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
		// ��λ��ʼ��
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
//		LatLng cenpt = new LatLng(centre_lati_xianlin,centre_longi_xianlin);
		
		//�˴���targetΪ���õ�ͼ���ĵص�ľ�γ������
		//�˴���zoomΪ���õ�ͼ���ŵȼ�������ζԱȵ����Ժ�float16.3Ϊ�������ˮƽ
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom((float) 16.3).build()));
		
		
	}
	
	
	/**
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view ���ٺ��ٴ����½��յ�λ��
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
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
					.direction(100).latitude(templati)
					.longitude(templongi).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				//����ǵ�һ�ζ�λ���򽫵�ͼ���ĵ㶨λ��ǿ�����ĵ�
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
		//�˳�ʱ���ٶ�λ
		mLocClient.stop();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
	
	/**
	 * DrawerLayout״̬�仯����
	 */
	private class DrawerLayoutStateListener extends
			DrawerLayout.SimpleDrawerListener {
		/**
		 * �������˵�������ʱ��ִ��
		 */
		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			showView = drawerView;
			if (drawerView == mMenuListView) {// ����isDirection_left����ִ�ж���
				mMaterialMenuIcon.setTransformationOffset(
						MaterialMenuDrawable.AnimationState.BURGER_ARROW,
						isDirection_left ? 2 - slideOffset : slideOffset);
			} else if (drawerView == right_drawer) {// ����isDirection_right����ִ�ж���
				mMaterialMenuIcon.setTransformationOffset(
						MaterialMenuDrawable.AnimationState.BURGER_ARROW,
						isDirection_right ? 2 - slideOffset : slideOffset);
			}
		}

		/**
		 * �������˵���ʱִ��
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
		 * �������˵��ر�ʱִ��
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
    //��������������η��ؼ��˳�����
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
    		Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
    		exitTime = System.currentTimeMillis();
    	}else{
    		finish();
    		new Handler().postDelayed(new Runnable() {
				public void run() {
					System.exit(0);
				}
			}, 200);
			
//			   ��ʱ�����������ַ�ʽ
//				һ���߳�
//				new Thread(new Runnable(){    
//				     public void run(){    
//				         Thread.sleep(XXXX);    
//				         handler.sendMessage();----�������߳�ִ������
//				     }    
//				 }).start    
//				������ʱ��
//				TimerTask task = new TimerTask(){    
//				     public void run(){    
//				     //execute the task     
//				     }    
//				 };    
//				Timer timer = new Timer();  
//				   timer.schedule(task, delay);  
			
			
			//����ɱ������
//    		System.exit(0);
    		//��������
//    		android.os.Process.killProcess(android.os.Process.myPid());  
    	}
    }
    //==========================================================================================
}
