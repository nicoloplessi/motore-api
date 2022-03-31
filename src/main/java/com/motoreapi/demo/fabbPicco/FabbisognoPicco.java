package com.motoreapi.demo.fabbPicco;

import java.util.ArrayList;

public class FabbisognoPicco {

	public String name;
    public String measureDescription;
    public String measureUnit;
    public String fromDate;
    public ArrayList<FabbPiccType> types;
    public ArrayList<FabbPiccDayMeasure> dayMeasures;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMeasureDescription() {
		return measureDescription;
	}
	public void setMeasureDescription(String measureDescription) {
		this.measureDescription = measureDescription;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public ArrayList<FabbPiccType> getTypes() {
		return types;
	}
	public void setTypes(ArrayList<FabbPiccType> types) {
		this.types = types;
	}
	public ArrayList<FabbPiccDayMeasure> getDayMeasures() {
		return dayMeasures;
	}
	public void setDayMeasures(ArrayList<FabbPiccDayMeasure> dayMeasures) {
		this.dayMeasures = dayMeasures;
	}
    
    
}
