package com.motoreapi.demo.menu;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentValueMenu{
    public String textColor;
    @JsonProperty("C_V_MEASURE_UNIT") 
    public String c_V_MEASURE_UNIT;
    @JsonProperty("C_V_MEASURE_DESCRIPTION") 
    public String c_V_MEASURE_DESCRIPTION;
    @JsonProperty("SALDO") 
    public Double sALDO;
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getC_V_MEASURE_UNIT() {
		return c_V_MEASURE_UNIT;
	}
	public void setC_V_MEASURE_UNIT(String c_V_MEASURE_UNIT) {
		this.c_V_MEASURE_UNIT = c_V_MEASURE_UNIT;
	}
	public String getC_V_MEASURE_DESCRIPTION() {
		return c_V_MEASURE_DESCRIPTION;
	}
	public void setC_V_MEASURE_DESCRIPTION(String c_V_MEASURE_DESCRIPTION) {
		this.c_V_MEASURE_DESCRIPTION = c_V_MEASURE_DESCRIPTION;
	}
	public Double getsALDO() {
		return sALDO;
	}
	public void setsALDO(Double sALDO) {
		this.sALDO = sALDO;
	}
    
    
}