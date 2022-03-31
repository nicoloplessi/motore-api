package com.motoreapi.demo.home;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HomeSection{
    @JsonProperty("SHOULD_SHOW_SECTION") 
    public int sHOULD_SHOW_SECTION;
    @JsonProperty("CATEGORY_TYPE_ID") 
    public int cATEGORY_TYPE_ID;
    public String titleLabel;
    @JsonProperty("TITLE_LABEL_COLOR") 
    public String tITLE_LABEL_COLOR;
    @JsonProperty("CATEGORY_IMG") 
    public String cATEGORY_IMG;
    public CurrentValue currentValue;
	
    
    public int getsHOULD_SHOW_SECTION() {
		return sHOULD_SHOW_SECTION;
	}
	public void setsHOULD_SHOW_SECTION(int sHOULD_SHOW_SECTION) {
		this.sHOULD_SHOW_SECTION = sHOULD_SHOW_SECTION;
	}
	public int getcATEGORY_TYPE_ID() {
		return cATEGORY_TYPE_ID;
	}
	public void setcATEGORY_TYPE_ID(int cATEGORY_TYPE_ID) {
		this.cATEGORY_TYPE_ID = cATEGORY_TYPE_ID;
	}
	public String getTitleLabel() {
		return titleLabel;
	}
	public void setTitleLabel(String titleLabel) {
		this.titleLabel = titleLabel;
	}
	public String gettITLE_LABEL_COLOR() {
		return tITLE_LABEL_COLOR;
	}
	public void settITLE_LABEL_COLOR(String tITLE_LABEL_COLOR) {
		this.tITLE_LABEL_COLOR = tITLE_LABEL_COLOR;
	}
	public String getcATEGORY_IMG() {
		return cATEGORY_IMG;
	}
	public void setcATEGORY_IMG(String cATEGORY_IMG) {
		this.cATEGORY_IMG = cATEGORY_IMG;
	}
	public CurrentValue getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(CurrentValue currentValue) {
		this.currentValue = currentValue;
	}
    
    
}

	


