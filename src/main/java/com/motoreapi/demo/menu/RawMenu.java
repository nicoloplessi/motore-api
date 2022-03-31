package com.motoreapi.demo.menu;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RawMenu {
	 	@JsonProperty("SHOULD_SHOW_SECTION") 
	    public int sHOULD_SHOW_SECTION;
	    @JsonProperty("GRAPH_TYPE_ID") 
	    public String gRAPH_TYPE_ID;
	    @JsonProperty("DATA_TYPE_ID") 
	    public int dATA_TYPE_ID;
	    @JsonProperty("TITLE_LABEL") 
	    public String tITLE_LABEL;
	    @JsonProperty("TITLE_LABEL_COLOR") 
	    public String tITLE_LABEL_COLOR;
	    @JsonProperty("GRAPH_ICON_IMAGE") 
	    public String gRAPH_ICON_IMAGE;
	    @JsonProperty("C_V_TEXT_COLOR") 
	    public String c_V_TEXT_COLOR;
	    @JsonProperty("C_V_MEASURE_UNIT") 
	    public String c_V_MEASURE_UNIT;
	    @JsonProperty("C_V_MEASURE_DESCRIPTION") 
	    public String c_V_MEASURE_DESCRIPTION;
	    @JsonProperty("SALDO") 
	    public Double sALDO;
	    
	    
		public Double getSALDO() {
			return sALDO;
		}
		public void setSALDO(Double sALDO) {
			this.sALDO = sALDO;
		}
		public int getsHOULD_SHOW_SECTION() {
			return sHOULD_SHOW_SECTION;
		}
		public void setsHOULD_SHOW_SECTION(int sHOULD_SHOW_SECTION) {
			this.sHOULD_SHOW_SECTION = sHOULD_SHOW_SECTION;
		}
		public String getgRAPH_TYPE_ID() {
			return gRAPH_TYPE_ID;
		}
		public void setgRAPH_TYPE_ID(String gRAPH_TYPE_ID) {
			this.gRAPH_TYPE_ID = gRAPH_TYPE_ID;
		}
		public int getdATA_TYPE_ID() {
			return dATA_TYPE_ID;
		}
		public void setdATA_TYPE_ID(int dATA_TYPE_ID) {
			this.dATA_TYPE_ID = dATA_TYPE_ID;
		}
		public String gettITLE_LABEL() {
			return tITLE_LABEL;
		}
		public void settITLE_LABEL(String tITLE_LABEL) {
			this.tITLE_LABEL = tITLE_LABEL;
		}
		public String gettITLE_LABEL_COLOR() {
			return tITLE_LABEL_COLOR;
		}
		public void settITLE_LABEL_COLOR(String tITLE_LABEL_COLOR) {
			this.tITLE_LABEL_COLOR = tITLE_LABEL_COLOR;
		}
		public String getgRAPH_ICON_IMAGE() {
			return gRAPH_ICON_IMAGE;
		}
		public void setgRAPH_ICON_IMAGE(String gRAPH_ICON_IMAGE) {
			this.gRAPH_ICON_IMAGE = gRAPH_ICON_IMAGE;
		}
		public String getC_V_TEXT_COLOR() {
			return c_V_TEXT_COLOR;
		}
		public void setC_V_TEXT_COLOR(String c_V_TEXT_COLOR) {
			this.c_V_TEXT_COLOR = c_V_TEXT_COLOR;
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
	    
	    
}
