package com.nju.sparknoise.baidumap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polygon;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.nju.sparknoise.R;

import android.content.res.Resources;

public class NoiseOverlay{
	/**
	 * this class aim to make noise overlay on the map
	 */
	
	//�����ŵ�ͼ�����Ŀ��б�
	ArrayList<ArrayList<String>> blocks = new ArrayList<ArrayList<String>>();
	//����ÿ���鶥��ľ�γ���б�
	ArrayList<String> tempblock = new ArrayList<String>();
	//����ѭ������ͼ��Ϳɫ����ɫ�б�,
	int[] mapcolor={0xA047abec,0xA07ac940,0xA0c73e8a,0xA0f19149};
	
public NoiseOverlay(BaiduMap mBaiduMap , Resources resource) {
	
	InputStreamReader instream = new InputStreamReader(resource.openRawResource(R.raw.location_ref));
	BufferedReader buffer = new BufferedReader(instream);
	
	String line=null;
	try {
		while((line=buffer.readLine())!=null){
			if(line.equals("=")){
				blocks.add(tempblock);
				tempblock = new ArrayList<String>();
				continue;
			}
			tempblock.add(line);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	ArrayList<LatLng> pts;
	int i=0;
	
	for(ArrayList<String> eachblock : blocks){
		
		pts = new ArrayList<LatLng>();
		
		for(String location : eachblock){
			String[] temp = location.split(":");
			String[] loc = temp[1].split(",");
			
			LatLng Dot = new LatLng(Double.parseDouble(loc[1]),Double.parseDouble(loc[0]));
			pts.add(Dot);
		}
		
		
		//�����û����ƶ���ε�Option����  
		OverlayOptions polygonOption = new PolygonOptions()  
		    .points(pts)  
		    .stroke(new Stroke(1, mapcolor[i]))  
		    .fillColor(mapcolor[i]);
		//�ڵ�ͼ����Ӷ����Option��������ʾ  
		Marker marker=(Marker) mBaiduMap.addOverlay(polygonOption);
		
		
		if(i==3){
			i=0;
		}else
			i++;
	}
	
	
	
}
}
