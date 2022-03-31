package com.motoreapi.demo.home;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentValue{
    public String textColor;
    public String measureUnit;
    @JsonProperty("C_V_MEASURE_DESCRIPTION") 
    public String c_V_MEASURE_DESCRIPTION;
    @JsonProperty("PRODUZIONE") 
    public double pRODUZIONE;
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getC_V_MEASURE_DESCRIPTION() {
		return c_V_MEASURE_DESCRIPTION;
	}
	public void setC_V_MEASURE_DESCRIPTION(String c_V_MEASURE_DESCRIPTION) {
		this.c_V_MEASURE_DESCRIPTION = c_V_MEASURE_DESCRIPTION;
	}
	public double getpRODUZIONE() {
		return pRODUZIONE;
	}
	public void setpRODUZIONE(double pRODUZIONE) {
		this.pRODUZIONE = pRODUZIONE;
	}
    
    
}