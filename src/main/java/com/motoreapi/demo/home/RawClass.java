package com.motoreapi.demo.home;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RawClass {
	 	@JsonProperty("SHOULD_SHOW_SECTION") 
	    public int sHOULD_SHOW_SECTION;
	    @JsonProperty("CATEGORY_TYPE_ID") 
	    public int cATEGORY_TYPE_ID;
	    @JsonProperty("INTEGRATION_ID") 
	    public String iNTEGRATION_ID;
	    @JsonProperty("TITLE_LABEL_COLOR") 
	    public String tITLE_LABEL_COLOR;
	    @JsonProperty("CATEGORY_IMG") 
	    public String cATEGORY_IMG;
	    @JsonProperty("C_V_TEXT_COLOR") 
	    public String c_V_TEXT_COLOR;
	    @JsonProperty("C_V_MEASURE_UNIT") 
	    public String c_V_MEASURE_UNIT;
	    @JsonProperty("C_V_MEASURE_DESCRIPTION") 
	    public String c_V_MEASURE_DESCRIPTION;
	    @JsonProperty("PRODUZIONE") 
	    public double pRODUZIONE;
	    @JsonProperty("SALDO")
	    public double saldo;
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
		public String getiNTEGRATION_ID() {
			return iNTEGRATION_ID;
		}
		public void setiNTEGRATION_ID(String iNTEGRATION_ID) {
			this.iNTEGRATION_ID = iNTEGRATION_ID;
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
		public double getpRODUZIONE() {
			return pRODUZIONE;
		}
		public void setpRODUZIONE(double pRODUZIONE) {
			this.pRODUZIONE = pRODUZIONE;
		}
		
		
		public double getSaldo() {
			return saldo;
		}
		public void setSaldo(double saldo) {
			this.saldo = saldo;
		}
		@Override
		public String toString() {
			return "RawClass [sHOULD_SHOW_SECTION=" + sHOULD_SHOW_SECTION + ", cATEGORY_TYPE_ID=" + cATEGORY_TYPE_ID
					+ ", iNTEGRATION_ID=" + iNTEGRATION_ID + ", tITLE_LABEL_COLOR=" + tITLE_LABEL_COLOR
					+ ", cATEGORY_IMG=" + cATEGORY_IMG + ", c_V_TEXT_COLOR=" + c_V_TEXT_COLOR + ", c_V_MEASURE_UNIT="
					+ c_V_MEASURE_UNIT + ", c_V_MEASURE_DESCRIPTION=" + c_V_MEASURE_DESCRIPTION + ", pRODUZIONE="
					+ pRODUZIONE + "]";
		}
	    
	    
}
