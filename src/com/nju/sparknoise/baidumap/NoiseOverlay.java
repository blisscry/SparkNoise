package com.nju.sparknoise.baidumap;

import java.util.ArrayList;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

public class NoiseOverlay{
	/**
	 * this class aim to make noise overlay on the map
	 */
	
	/**
	 * Reference(缩放等级为18):
	 * ===================================
	 * block1
	 * A:118.965421,32.120539
	 * B:118.967784,32.120792
	 * C:118.968718,32.119156
	 * D:118.964909,32.118712
	 * E:118.965269,32.119859
	 * F:118.965682,32.119805
	 * ===================================
	 * block2
	 * A:118.965420,32.120539
	 * B:118.965681,32.119805
	 * C:118.965268,32.119859
	 * D:118.965049,32.119072
	 * E:118.964908,32.118712
	 * F:118.963858,32.11898
	 * G:118.963063,32.11958
	 * H:118.964546,32.120784
	 * I:118.965143,32.120581
	 * ==================================
	 * block3
	 * A:118.965345,32.121499
	 * B:118.965951,32.121419
	 * C:118.966333,32.12143
	 * D:118.967344,32.121541
	 * E:118.96777,32.120803
	 * F:118.965713,32.120551
	 * G:118.965417,32.120547
	 * H:118.964968,32.120616
	 * I:118.964546,32.120784
	 */
public NoiseOverlay(BaiduMap mBaiduMap) {
	LatLng A = new LatLng(32.120539, 118.965421);
	LatLng B = new LatLng(32.120792, 118.967784);
	LatLng C = new LatLng(32.119156, 118.968718);
	LatLng D = new LatLng(32.118712, 118.964909);
	LatLng E = new LatLng(32.119859, 118.965269);
	LatLng F = new LatLng(32.119805, 118.965682);
	
	ArrayList<LatLng> pts = new ArrayList<LatLng>();
	pts.add(A);
	pts.add(B);
	pts.add(C);
	pts.add(D);
	pts.add(E);
	pts.add(F);
	
	//构建用户绘制多边形的Option对象  
	OverlayOptions polygonOption = new PolygonOptions()  
	    .points(pts)  
	    .stroke(new Stroke(3, 0xAA00FF00))  
	    .fillColor(0xAA6d9eeb);
	//在地图上添加多边形Option，用于显示  
	mBaiduMap.addOverlay(polygonOption);
}
}
