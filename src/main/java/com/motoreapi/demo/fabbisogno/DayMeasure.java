package com.motoreapi.demo.fabbisogno;

import java.util.ArrayList;

public class DayMeasure{
    public String timeLabel;
    public ArrayList<Measure> measures;
	public String getTimeLabel() {
		return timeLabel;
	}
	public void setTimeLabel(String timeLabel) {
		this.timeLabel = timeLabel;
	}
	public ArrayList<Measure> getMeasures() {
		return measures;
	}
	public void setMeasures(ArrayList<Measure> measures) {
		this.measures = measures;
	}
    
    
}