package com.motoreapi.demo.Zonali;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class ZonaliDataBean {

	@JsonProperty("FROMZONE")
	@Expose
	public String FROMZONE;
	@JsonProperty("FROMZONELABEL")
	@Expose
	public String FROMZONELABEL;
	@JsonProperty("FROMZONELABEL_CK")
	@Expose
	public String FROMZONELABEL_CK;
	@JsonProperty("FROMLATITUDE")
	@Expose
	public String FROMLATITUDE;
	@JsonProperty("FROMLONGITUDE")
	@Expose
	public String FROMLONGITUDE;
	@JsonProperty("TOZONE")
	@Expose
	public String TOZONE;
	@JsonProperty("TOZONELABEL")
	@Expose
	public String TOZONELABEL;
	@JsonProperty("TOZONELABEL_CK")
	@Expose
	public String TOZONELABEL_CK;
	 @JsonProperty("TOLATITUDE")
	@Expose
	public String TOLATITUDE;
	@JsonProperty("TOLONGITUDE")
	@Expose
	public String TOLONGITUDE;
	@JsonProperty("HEXCOLOR")
	@Expose
	public String HEXCOLOR;
	@JsonProperty("TOHEXCOLOR")
	@Expose
	public String TOHEXCOLOR;
	@JsonProperty("SCAMBIO")
	@Expose
	public String SCAMBIO;
	@JsonProperty("HEXCOLOR_1")
	public String HEXCOLOR_1;
	
	public ZonaliDataBean() {
		
	}
	public ZonaliDataBean(String fROMZONE, String fROMZONELABEL, String fROMLATITUDE, String fROMLONGITUDE,
			String tOZONE, String tOZONELABEL, String tOLATITUDE, String tOLONGITUDE, String hEXCOLOR,
			String tOHEXCOLOR, String sCAMBIO, String hexColor_1) {
		
		this.FROMZONE = fROMZONE;
		this.FROMZONELABEL = fROMZONELABEL;
		this.FROMLATITUDE = fROMLATITUDE;
		this.FROMLONGITUDE = fROMLONGITUDE;
		this.TOZONE = tOZONE;
		this.TOZONELABEL = tOZONELABEL;
		this.TOLATITUDE = tOLATITUDE;
		this.TOLONGITUDE = tOLONGITUDE;
		this.HEXCOLOR = hEXCOLOR;
		this.TOHEXCOLOR = tOHEXCOLOR;
		this.SCAMBIO = sCAMBIO;
		this.HEXCOLOR_1=hexColor_1;
	}
	
	
	
	public String getHexColor_1() {
		return HEXCOLOR_1;
	}
	public void setHexColor_1(String hexColor_1) {
		this.HEXCOLOR_1 = hexColor_1;
	}
	public String getFROMZONELABEL_CK() {
		return FROMZONELABEL_CK;
	}
	public void setFROMZONELABEL_CK(String fROMZONELABEL_CK) {
		FROMZONELABEL_CK = fROMZONELABEL_CK;
	}
	public String getTOZONELABEL_CK() {
		return TOZONELABEL_CK;
	}
	public void setTOZONELABEL_CK(String tOZONELABEL_CK) {
		TOZONELABEL_CK = tOZONELABEL_CK;
	}
	public String getSCAMBIO() {
		return SCAMBIO;
	}
	public void setSCAMBIO(String sCAMBIO) {
		SCAMBIO = sCAMBIO;
	}
	public String getFROMZONE() {
		return FROMZONE;
	}
	public void setFROMZONE(String fROMZONE) {
		FROMZONE = fROMZONE;
	}
	public String getFROMZONELABEL() {
		return FROMZONELABEL;
	}
	public void setFROMZONELABEL(String fROMZONELABEL) {
		FROMZONELABEL = fROMZONELABEL;
	}
	public String getFROMLATITUDE() {
		return FROMLATITUDE;
	}
	public void setFROMLATITUDE(String fROMLATITUDE) {
		FROMLATITUDE = fROMLATITUDE;
	}
	public String getFROMLONGITUDE() {
		return FROMLONGITUDE;
	}
	public void setFROMLONGITUDE(String fROMLONGITUDE) {
		FROMLONGITUDE = fROMLONGITUDE;
	}
	public String getTOZONE() {
		return TOZONE;
	}
	public void setTOZONE(String tOZONE) {
		TOZONE = tOZONE;
	}
	public String getTOZONELABEL() {
		return TOZONELABEL;
	}
	public void setTOZONELABEL(String tOZONELABEL) {
		TOZONELABEL = tOZONELABEL;
	}
	public String getTOLATITUDE() {
		return TOLATITUDE;
	}
	public void setTOLATITUDE(String tOLATITUDE) {
		TOLATITUDE = tOLATITUDE;
	}
	public String getTOLONGITUDE() {
		return TOLONGITUDE;
	}
	public void setTOLONGITUDE(String tOLONGITUDE) {
		TOLONGITUDE = tOLONGITUDE;
	}
	public String getHEXCOLOR() {
		return HEXCOLOR;
	}
	public void setHEXCOLOR(String hEXCOLOR) {
		HEXCOLOR = hEXCOLOR;
	}
	public String getTOHEXCOLOR() {
		return TOHEXCOLOR;
	}
	public void setTOHEXCOLOR(String tOHEXCOLOR) {
		TOHEXCOLOR = tOHEXCOLOR;
	}
	@Override
	public String toString() {
		return "ZonaliDataBean [FROMZONE=" + FROMZONE + ", FROMZONELABEL=" + FROMZONELABEL + ", FROMLATITUDE="
				+ FROMLATITUDE + ", FROMLONGITUDE=" + FROMLONGITUDE + ", TOZONE=" + TOZONE + ", TOZONELABEL="
				+ TOZONELABEL + ", TOLATITUDE=" + TOLATITUDE + ", TOLONGITUDE=" + TOLONGITUDE + ", HEXCOLOR=" + HEXCOLOR
				+ ", TOHEXCOLOR=" + TOHEXCOLOR + "]";
	}
	
}
