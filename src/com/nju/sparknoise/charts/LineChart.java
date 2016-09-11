package com.nju.sparknoise.charts;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class LineChart {
	
	String chartseries;
	
	
		
	public LineChart(String series) {
		chartseries=series;
	}
	
	
	public Intent generateChart(Context context){
		List<Double[]> time = new ArrayList<Double[]>();
		time.add( new Double[]{  });
		
	    double[] x = new double[]{6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
	    
	    double[] values = new double[] {40,43,45,50,60,78,85,54,70,63,61,75,82,77,72,60,54,40,30};
	    
//	    int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW };
	    int colors = Color.BLUE;
//	    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,
//	        PointStyle.TRIANGLE, PointStyle.SQUARE };
	    PointStyle styles = PointStyle.X;
	    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
	    int length = renderer.getSeriesRendererCount();
	    for (int i = 0; i < length; i++) {
	      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
	    }
	    setChartSettings(renderer, "Noise Level", "Time", "Decibel", 6, 24, 20, 100,
	        Color.LTGRAY, Color.LTGRAY);
	    renderer.setXLabels(19);
	    renderer.setYLabels(9);
	    renderer.setShowGrid(true);
	    renderer.setXLabelsAlign(Align.RIGHT);
	    renderer.setYLabelsAlign(Align.RIGHT);
	    renderer.setZoomButtonsVisible(true);
	    renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
	    renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });

	    XYMultipleSeriesDataset dataset = buildDataset(chartseries, x, values);
	    XYSeries series = dataset.getSeriesAt(0);
	    series.addAnnotation("Vacation", 6, 30);
	    Intent intent = ChartFactory.getCubicLineChartIntent(context, dataset, renderer,0.33f,
	        "Average temperature");
	    return intent;
	}
	
	public XYMultipleSeriesDataset buildDataset(String title,double[] xValue,double[] yValue){
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		
		XYSeries series = new XYSeries(title);
		int serieslength = xValue.length;
		for (int i = 0;i<serieslength;i++){
			series.add(xValue[i], yValue[i]);
			
		}
		
		dataset.addSeries(series);
		return dataset;
	}
	
	
	public XYMultipleSeriesRenderer buildRenderer(int colors,PointStyle styles){
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setPointSize(5f);
	    renderer.setMargins(new int[] { 20, 30, 15, 20 });
	      XYSeriesRenderer r = new XYSeriesRenderer();
	      r.setColor(colors);
	      r.setPointStyle(styles);
	      renderer.addSeriesRenderer(r);
	    
		return renderer;
	}
	
	
	  protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
		      String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
		      int labelsColor) {
		    renderer.setChartTitle(title);
		    renderer.setXTitle(xTitle);
		    renderer.setYTitle(yTitle);
		    renderer.setXAxisMin(xMin);
		    renderer.setXAxisMax(xMax);
		    renderer.setYAxisMin(yMin);
		    renderer.setYAxisMax(yMax);
		    renderer.setAxesColor(axesColor);
		    renderer.setLabelsColor(labelsColor);
	}

	
	
	
}
