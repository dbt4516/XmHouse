package com.cnc.xmhouse.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by hongzhan on 2016/8/9.
 */
public class Highchart {

    private Map<String, Object> xAxis = new HashMap<>();
    private Map<String, Object> chart = new HashMap<>();
    private Map<String, Object> tooltip = new HashMap<>();
    private List<Object> series=new LinkedList<>();


    public Highchart(List<String> xAxisList,List<Object>series,String chart) {
        this.chart.put("type",chart);
        xAxis.put("categories", xAxisList);
        this.series=series;
    }

    public Map<String, Object> getChart() {
        return chart;
    }

    public void setChart(Map<String, Object> chart) {
        this.chart = chart;
    }

    public Map<String, Object> getxAxis() {
        return xAxis;
    }

    public void setxAxis(Map<String, Object> xAxis) {
        this.xAxis = xAxis;
    }

    public List<Object> getSeries() {
        return series;
    }

    public void setSeries(List<Object> series) {
        this.series = series;
    }

    public Map<String, Object> getTooltip() {
        return tooltip;
    }

    public void setTooltip(Map<String, Object> tooltip) {
        this.tooltip = tooltip;
    }
}
