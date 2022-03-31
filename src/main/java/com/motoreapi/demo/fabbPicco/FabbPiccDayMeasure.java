package com.motoreapi.demo.fabbPicco;

import java.util.ArrayList;

public class FabbPiccDayMeasure {

	public String referenceDate;
    public String labelPiccoOra;
    public String timeLabel;
    public ArrayList<FabbPiccMeasure> measures;
    
    
	public String getReferenceDate() {
		return referenceDate;
	}
	public void setReferenceDate(String referenceDate) {
		this.referenceDate = referenceDate;
	}
	public String getLabelPiccoOra() {
		return labelPiccoOra;
	}
	public void setLabelPiccoOra(String labelPiccoOra) {
		this.labelPiccoOra = labelPiccoOra;
	}
	public String getTimeLabel() {
		return timeLabel;
	}
	public void setTimeLabel(String timeLabel) {
		this.timeLabel = timeLabel;
	}
	public ArrayList<FabbPiccMeasure> getMeasures() {
		return measures;
	}
	public void setMeasures(ArrayList<FabbPiccMeasure> measures) {
		this.measures = measures;
	}
	@Override
	public String toString() {
		return "FabbPiccDayMeasure [referenceDate=" + referenceDate + ", labelPiccoOra=" + labelPiccoOra
				+ ", timeLabel=" + timeLabel + ", measures=" + measures + "]";
	}
    
    
}
