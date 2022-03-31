package com.motoreapi.demo.fabbPicco;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RawFabbPiccBean {

	@JsonProperty("NAME") 
    public String nAME;
	
    @JsonProperty("MEASURE_DESCRIPTION") 
    public String mEASURE_DESCRIPTION;
    @JsonProperty("MEASURE_UNIT") 
    public String mEASURE_UNIT;
    @JsonProperty("TYPES_ID_PRODTERM") 
    public String tYPES_ID_PRODTERM;
    @JsonProperty("TYPES_NAME_PRODTERM") 
    public String tYPES_NAME_PRODTERM;
    @JsonProperty("TYPES_HEXCOLOR_PRODTERM") 
    public String tYPES_HEXCOLOR_PRODTERM;
    @JsonProperty("TYPES_ID_PMPG") 
    public String tYPES_ID_PMPG;
    @JsonProperty("TYPES_NAME_PMPG") 
    public String tYPES_NAME_PMPG;
    @JsonProperty("TYPES_HEXCOLOR_PMPG") 
    public String tYPES_HEXCOLOR_PMPG;
    @JsonProperty("TYPES_ID_ESTERO") 
    public String tYPES_ID_ESTERO;
    @JsonProperty("TYPES_NAME_ESTERO") 
    public String tYPES_NAME_ESTERO;
    @JsonProperty("TYPES_HEXCOLOR_ESTERO") 
    public String tYPES_HEXCOLOR_ESTERO;
    @JsonProperty("TYPES_ID_PV") 
    public String tYPES_ID_PV;
    @JsonProperty("TYPES_NAME_PV") 
    public String tYPES_NAME_PV;
    @JsonProperty("TYPES_HEXCOLOR_PV") 
    public String tYPES_HEXCOLOR_PV;
    @JsonProperty("TYPES_ID_WIND") 
    public String tYPES_ID_WIND;
    @JsonProperty("TYPES_NAME_WIND") 
    public String tYPES_NAME_WIND;
    @JsonProperty("TYPES_HEXCOLOR_WIND") 
    public String tYPES_HEXCOLOR_WIND;
    @JsonProperty("TYPES_ID_GEO") 
    public String tYPES_ID_GEO;
    @JsonProperty("TYPES_NAME_GEO") 
    public String tYPES_NAME_GEO;
    @JsonProperty("TYPES_HEXCOLOR_GEO") 
    public String tYPES_HEXCOLOR_GEO;
    @JsonProperty("TYPES_ID_HYDRO") 
    public String tYPES_ID_HYDRO;
    @JsonProperty("TYPES_NAME_HYDRO") 
    public String tYPES_NAME_HYDRO;
    @JsonProperty("TYPES_HEXCOLOR_HYDRO") 
    public String tYPES_HEXCOLOR_HYDRO;
    @JsonProperty("TYPES_ID_AUTOPRODUZIONE") 
    public String tYPES_ID_AUTOPRODUZIONE;
    @JsonProperty("TYPES_NAME_AUTOPRODUZIONE") 
    public String tYPES_NAME_AUTOPRODUZIONE;
    @JsonProperty("TYPES_HEXCOLOR_AUTOPRODUZIONE") 
    public String tYPES_HEXCOLOR_AUTOPRODUZIONE;
    @JsonProperty("DATA_QUARTO") 
    public String dATA_QUARTO;
    @JsonProperty("LBL_DATA_QUARTO") 
    public String lBL_DATA_QUARTO;
    @JsonProperty("PRODTERM") 
    public double pRODTERM;
    @JsonProperty("PMPG") 
    public double pMPG;
    @JsonProperty("ESTERO") 
    public double eSTERO;
    @JsonProperty("PV") 
    public int pV;
    @JsonProperty("WIND") 
    public double wIND;
    @JsonProperty("GEO") 
    public double gEO;
    @JsonProperty("HYDRO") 
    public double hYDRO;
    @JsonProperty("AUTOPRODUZIONE") 
    public double aUTOPRODUZIONE;
	public String getnAME() {
		return nAME;
	}
	public void setnAME(String nAME) {
		this.nAME = nAME;
	}
	public String getmEASURE_DESCRIPTION() {
		return mEASURE_DESCRIPTION;
	}
	public void setmEASURE_DESCRIPTION(String mEASURE_DESCRIPTION) {
		this.mEASURE_DESCRIPTION = mEASURE_DESCRIPTION;
	}
	public String getmEASURE_UNIT() {
		return mEASURE_UNIT;
	}
	public void setmEASURE_UNIT(String mEASURE_UNIT) {
		this.mEASURE_UNIT = mEASURE_UNIT;
	}
	public String gettYPES_ID_PRODTERM() {
		return tYPES_ID_PRODTERM;
	}
	public void settYPES_ID_PRODTERM(String tYPES_ID_PRODTERM) {
		this.tYPES_ID_PRODTERM = tYPES_ID_PRODTERM;
	}
	public String gettYPES_NAME_PRODTERM() {
		return tYPES_NAME_PRODTERM;
	}
	public void settYPES_NAME_PRODTERM(String tYPES_NAME_PRODTERM) {
		this.tYPES_NAME_PRODTERM = tYPES_NAME_PRODTERM;
	}
	public String gettYPES_HEXCOLOR_PRODTERM() {
		return tYPES_HEXCOLOR_PRODTERM;
	}
	public void settYPES_HEXCOLOR_PRODTERM(String tYPES_HEXCOLOR_PRODTERM) {
		this.tYPES_HEXCOLOR_PRODTERM = tYPES_HEXCOLOR_PRODTERM;
	}
	public String gettYPES_ID_PMPG() {
		return tYPES_ID_PMPG;
	}
	public void settYPES_ID_PMPG(String tYPES_ID_PMPG) {
		this.tYPES_ID_PMPG = tYPES_ID_PMPG;
	}
	public String gettYPES_NAME_PMPG() {
		return tYPES_NAME_PMPG;
	}
	public void settYPES_NAME_PMPG(String tYPES_NAME_PMPG) {
		this.tYPES_NAME_PMPG = tYPES_NAME_PMPG;
	}
	public String gettYPES_HEXCOLOR_PMPG() {
		return tYPES_HEXCOLOR_PMPG;
	}
	public void settYPES_HEXCOLOR_PMPG(String tYPES_HEXCOLOR_PMPG) {
		this.tYPES_HEXCOLOR_PMPG = tYPES_HEXCOLOR_PMPG;
	}
	public String gettYPES_ID_ESTERO() {
		return tYPES_ID_ESTERO;
	}
	public void settYPES_ID_ESTERO(String tYPES_ID_ESTERO) {
		this.tYPES_ID_ESTERO = tYPES_ID_ESTERO;
	}
	public String gettYPES_NAME_ESTERO() {
		return tYPES_NAME_ESTERO;
	}
	public void settYPES_NAME_ESTERO(String tYPES_NAME_ESTERO) {
		this.tYPES_NAME_ESTERO = tYPES_NAME_ESTERO;
	}
	public String gettYPES_HEXCOLOR_ESTERO() {
		return tYPES_HEXCOLOR_ESTERO;
	}
	public void settYPES_HEXCOLOR_ESTERO(String tYPES_HEXCOLOR_ESTERO) {
		this.tYPES_HEXCOLOR_ESTERO = tYPES_HEXCOLOR_ESTERO;
	}
	public String gettYPES_ID_PV() {
		return tYPES_ID_PV;
	}
	public void settYPES_ID_PV(String tYPES_ID_PV) {
		this.tYPES_ID_PV = tYPES_ID_PV;
	}
	public String gettYPES_NAME_PV() {
		return tYPES_NAME_PV;
	}
	public void settYPES_NAME_PV(String tYPES_NAME_PV) {
		this.tYPES_NAME_PV = tYPES_NAME_PV;
	}
	public String gettYPES_HEXCOLOR_PV() {
		return tYPES_HEXCOLOR_PV;
	}
	public void settYPES_HEXCOLOR_PV(String tYPES_HEXCOLOR_PV) {
		this.tYPES_HEXCOLOR_PV = tYPES_HEXCOLOR_PV;
	}
	public String gettYPES_ID_WIND() {
		return tYPES_ID_WIND;
	}
	public void settYPES_ID_WIND(String tYPES_ID_WIND) {
		this.tYPES_ID_WIND = tYPES_ID_WIND;
	}
	public String gettYPES_NAME_WIND() {
		return tYPES_NAME_WIND;
	}
	public void settYPES_NAME_WIND(String tYPES_NAME_WIND) {
		this.tYPES_NAME_WIND = tYPES_NAME_WIND;
	}
	public String gettYPES_HEXCOLOR_WIND() {
		return tYPES_HEXCOLOR_WIND;
	}
	public void settYPES_HEXCOLOR_WIND(String tYPES_HEXCOLOR_WIND) {
		this.tYPES_HEXCOLOR_WIND = tYPES_HEXCOLOR_WIND;
	}
	public String gettYPES_ID_GEO() {
		return tYPES_ID_GEO;
	}
	public void settYPES_ID_GEO(String tYPES_ID_GEO) {
		this.tYPES_ID_GEO = tYPES_ID_GEO;
	}
	public String gettYPES_NAME_GEO() {
		return tYPES_NAME_GEO;
	}
	public void settYPES_NAME_GEO(String tYPES_NAME_GEO) {
		this.tYPES_NAME_GEO = tYPES_NAME_GEO;
	}
	public String gettYPES_HEXCOLOR_GEO() {
		return tYPES_HEXCOLOR_GEO;
	}
	public void settYPES_HEXCOLOR_GEO(String tYPES_HEXCOLOR_GEO) {
		this.tYPES_HEXCOLOR_GEO = tYPES_HEXCOLOR_GEO;
	}
	public String gettYPES_ID_HYDRO() {
		return tYPES_ID_HYDRO;
	}
	public void settYPES_ID_HYDRO(String tYPES_ID_HYDRO) {
		this.tYPES_ID_HYDRO = tYPES_ID_HYDRO;
	}
	public String gettYPES_NAME_HYDRO() {
		return tYPES_NAME_HYDRO;
	}
	public void settYPES_NAME_HYDRO(String tYPES_NAME_HYDRO) {
		this.tYPES_NAME_HYDRO = tYPES_NAME_HYDRO;
	}
	public String gettYPES_HEXCOLOR_HYDRO() {
		return tYPES_HEXCOLOR_HYDRO;
	}
	public void settYPES_HEXCOLOR_HYDRO(String tYPES_HEXCOLOR_HYDRO) {
		this.tYPES_HEXCOLOR_HYDRO = tYPES_HEXCOLOR_HYDRO;
	}
	public String gettYPES_ID_AUTOPRODUZIONE() {
		return tYPES_ID_AUTOPRODUZIONE;
	}
	public void settYPES_ID_AUTOPRODUZIONE(String tYPES_ID_AUTOPRODUZIONE) {
		this.tYPES_ID_AUTOPRODUZIONE = tYPES_ID_AUTOPRODUZIONE;
	}
	public String gettYPES_NAME_AUTOPRODUZIONE() {
		return tYPES_NAME_AUTOPRODUZIONE;
	}
	public void settYPES_NAME_AUTOPRODUZIONE(String tYPES_NAME_AUTOPRODUZIONE) {
		this.tYPES_NAME_AUTOPRODUZIONE = tYPES_NAME_AUTOPRODUZIONE;
	}
	public String gettYPES_HEXCOLOR_AUTOPRODUZIONE() {
		return tYPES_HEXCOLOR_AUTOPRODUZIONE;
	}
	public void settYPES_HEXCOLOR_AUTOPRODUZIONE(String tYPES_HEXCOLOR_AUTOPRODUZIONE) {
		this.tYPES_HEXCOLOR_AUTOPRODUZIONE = tYPES_HEXCOLOR_AUTOPRODUZIONE;
	}
	public String getdATA_QUARTO() {
		return dATA_QUARTO;
	}
	public void setdATA_QUARTO(String dATA_QUARTO) {
		this.dATA_QUARTO = dATA_QUARTO;
	}
	public String getlBL_DATA_QUARTO() {
		return lBL_DATA_QUARTO;
	}
	public void setlBL_DATA_QUARTO(String lBL_DATA_QUARTO) {
		this.lBL_DATA_QUARTO = lBL_DATA_QUARTO;
	}
	public double getpRODTERM() {
		return pRODTERM;
	}
	public void setpRODTERM(double pRODTERM) {
		this.pRODTERM = pRODTERM;
	}
	public double getpMPG() {
		return pMPG;
	}
	public void setpMPG(double pMPG) {
		this.pMPG = pMPG;
	}
	public double geteSTERO() {
		return eSTERO;
	}
	public void seteSTERO(double eSTERO) {
		this.eSTERO = eSTERO;
	}
	public int getpV() {
		return pV;
	}
	public void setpV(int pV) {
		this.pV = pV;
	}
	public double getwIND() {
		return wIND;
	}
	public void setwIND(double wIND) {
		this.wIND = wIND;
	}
	public double getgEO() {
		return gEO;
	}
	public void setgEO(double gEO) {
		this.gEO = gEO;
	}
	public double gethYDRO() {
		return hYDRO;
	}
	public void sethYDRO(double hYDRO) {
		this.hYDRO = hYDRO;
	}
	public double getaUTOPRODUZIONE() {
		return aUTOPRODUZIONE;
	}
	public void setaUTOPRODUZIONE(double aUTOPRODUZIONE) {
		this.aUTOPRODUZIONE = aUTOPRODUZIONE;
	}
    
    

}
