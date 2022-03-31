package com.motoreapi.demo.fontiprimarie;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataFpRaw {
	
	    @JsonProperty("DATA_ORA") 
	    public String dATA_ORA;
	    @JsonProperty("LBL_DATA_QUARTO") 
	    public String lBL_DATA_QUARTO;
	    @JsonProperty("THERMOMIXED") 
	    public String tHERMOMIXED;
	    @JsonProperty("ESTERO") 
	    public String eSTERO;
	    @JsonProperty("PV") 
	    public String pV;
	    @JsonProperty("WIND") 
	    public String wIND;
	    @JsonProperty("GEO") 
	    public String gEO;
	    @JsonProperty("HYDRO") 
	    public String hYDRO;
	    @JsonProperty("AUTOPRODUZIONE") 
	    public String aUTOPRODUZIONE;
	    @JsonProperty("POMPAGGI") 
	    public String pOMPAGGI;
	    
	    @JsonProperty("LBL_DATA_ORA") 
	    public String lBL_DATA_ORA;
	    
		public String getdATA_ORA() {
			return dATA_ORA;
		}
		public void setdATA_ORA(String dATA_ORA) {
			this.dATA_ORA = dATA_ORA;
		}
		public String getlBL_DATA_QUARTO() {
			return lBL_DATA_QUARTO;
		}
		public void setlBL_DATA_QUARTO(String lBL_DATA_QUARTO) {
			this.lBL_DATA_QUARTO = lBL_DATA_QUARTO;
		}
		public String gettHERMOMIXED() {
			return tHERMOMIXED;
		}
		public void settHERMOMIXED(String tHERMOMIXED) {
			this.tHERMOMIXED = tHERMOMIXED;
		}
		public String geteSTERO() {
			return eSTERO;
		}
		public void seteSTERO(String eSTERO) {
			this.eSTERO = eSTERO;
		}
		public String getpV() {
			return pV;
		}
		public void setpV(String pV) {
			this.pV = pV;
		}
		public String getwIND() {
			return wIND;
		}
		public void setwIND(String wIND) {
			this.wIND = wIND;
		}
		public String getgEO() {
			return gEO;
		}
		public void setgEO(String gEO) {
			this.gEO = gEO;
		}
		public String gethYDRO() {
			return hYDRO;
		}
		public void sethYDRO(String hYDRO) {
			this.hYDRO = hYDRO;
		}
		public String getaUTOPRODUZIONE() {
			return aUTOPRODUZIONE;
		}
		public void setaUTOPRODUZIONE(String aUTOPRODUZIONE) {
			this.aUTOPRODUZIONE = aUTOPRODUZIONE;
		}
		public String getpOMPAGGI() {
			return pOMPAGGI;
		}
		public void setpOMPAGGI(String pOMPAGGI) {
			this.pOMPAGGI = pOMPAGGI;
		}
	    
	    
	

}
