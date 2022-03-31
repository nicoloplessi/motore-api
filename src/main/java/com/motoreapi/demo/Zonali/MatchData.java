package com.motoreapi.demo.Zonali;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class MatchData {

	
	
	@JsonProperty("DATA_ORA")
	
	public String DATA_ORA;
	
	@JsonProperty("HEXCOLOR_1")	
	public String hexColor_1;
	
	@JsonProperty("ORA")
	
	public String ORA;
	
	@JsonProperty("ZONA_DA")
	
	public String ZONA_DA;
	
	@JsonProperty("ZONA_A")
	
	public String ZONA_A;
	
	@JsonProperty("SCAMBIO")
	@Expose
	public float value;
	@JsonProperty("FROMZONE")
	@Expose
	public String fromZone;
	@JsonProperty("FROMZONELABEL")
	@Expose
	public String fromZoneLabel;
	@JsonProperty("FROMLATITUDE")
	@Expose
	public String fromLatitude;
	@JsonProperty("FROMLONGITUDE")
	@Expose
	public String fromLongitude;
	@JsonProperty("TOZONE")
	@Expose
	public String toZone;
	@JsonProperty("TOZONELABEL")
	@Expose
	public String toZoneLabel;
	 @JsonProperty("TOLATITUDE")
	@Expose
	public String toLatitude;
	@JsonProperty("TOLONGITUDE")
	@Expose
	public String toLongitude;
	@JsonProperty("HEXCOLOR")
	@Expose
	public String hexColor;
	@JsonProperty("TOHEXCOLOR")
	
	public String toHexColor;
	@JsonProperty("SCAMBIO1")
	
	public String SCAMBIO1;
	@JsonProperty("NAZIONE")
	
	public String NAZIONE;
	@JsonProperty("SALDO")
	
	public float valuex;
	
	public MatchData(ZonaliBilanBean b,ZonaliDataBean d)
	{
		this.DATA_ORA=b.getDATA_ORA();
		this.ORA=b.getORA();
		this.ZONA_DA=b.getZONA_DA();
		this.ZONA_A=b.getZONA_A();
		this.value=valore(b.getSALDO(),b.getSCAMBIO());
		this.NAZIONE=b.getNAZIONE();
		
		
		
		//------------------------------------
		this.fromZone=d.getFROMZONE();
		this.fromZoneLabel=d.getFROMZONELABEL();
		this.fromLatitude=d.getFROMLATITUDE();
		this.fromLongitude=d.getFROMLONGITUDE();
		this.toZone=d.getTOZONE();
		this.toZoneLabel=d.getTOZONELABEL();
		this.toLatitude=d.getTOLATITUDE();
		this.toLongitude=d.getTOLONGITUDE();
		this.hexColor=d.getHEXCOLOR();
		this.toHexColor=d.getTOHEXCOLOR();
		this.SCAMBIO1=d.getSCAMBIO();
		this.hexColor_1=d.getHexColor_1();
	}

	public float valore(float saldo, float scambio)
	{
		if(scambio!=0)
		{
			return scambio;
		}else {
			return saldo;
		}
		
		
	}
	public String getDATA_ORA() {
		return DATA_ORA;
	}

	public void setDATA_ORA(String dATA_ORA) {
		DATA_ORA = dATA_ORA;
	}

	public String getORA() {
		return ORA;
	}

	public void setORA(String oRA) {
		ORA = oRA;
	}

	public String getZONA_DA() {
		return ZONA_DA;
	}

	public void setZONA_DA(String zONA_DA) {
		ZONA_DA = zONA_DA;
	}

	public String getZONA_A() {
		return ZONA_A;
	}

	public void setZONA_A(String zONA_A) {
		ZONA_A = zONA_A;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getFromZone() {
		return fromZone;
	}

	public void setFromZone(String fromZone) {
		this.fromZone = fromZone;
	}

	public String getFromZoneLabel() {
		return fromZoneLabel;
	}

	public void setFromZoneLabel(String fromZoneLabel) {
		this.fromZoneLabel = fromZoneLabel;
	}

	public String getFromLatitude() {
		return fromLatitude;
	}

	public void setFromLatitude(String fromLatitude) {
		this.fromLatitude = fromLatitude;
	}

	public String getFromLongitude() {
		return fromLongitude;
	}

	public void setFromLongitude(String fromLongitude) {
		this.fromLongitude = fromLongitude;
	}

	public String getToZone() {
		return toZone;
	}

	public void setToZone(String toZone) {
		this.toZone = toZone;
	}

	public String getToZoneLabel() {
		return toZoneLabel;
	}

	public void setToZoneLabel(String toZoneLabel) {
		this.toZoneLabel = toZoneLabel;
	}

	public String getToLatitude() {
		return toLatitude;
	}

	public void setToLatitude(String toLatitude) {
		this.toLatitude = toLatitude;
	}

	public String getToLongitude() {
		return toLongitude;
	}

	public void setToLongitude(String toLongitude) {
		this.toLongitude = toLongitude;
	}

	public String getHexColor() {
		return hexColor;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public String getToHexColor() {
		return toHexColor;
	}

	public void setToHexColor(String toHexColor) {
		this.toHexColor = toHexColor;
	}

	public String getSCAMBIO1() {
		return SCAMBIO1;
	}

	public void setSCAMBIO1(String sCAMBIO1) {
		SCAMBIO1 = sCAMBIO1;
	}

	public String getNAZIONE() {
		return NAZIONE;
	}

	public void setNAZIONE(String nAZIONE) {
		NAZIONE = nAZIONE;
	}

	public float getSALDO() {
		return valuex;
	}

	public void setSALDO(float sALDO) {
		valuex = sALDO;
	}

	@Override
	public String toString() {
		return "MatchData [DATA_ORA=" + DATA_ORA + ", ORA=" + ORA + ", ZONA_DA=" + ZONA_DA + ", ZONA_A=" + ZONA_A
				+ ", value=" + value + ", fromZone=" + fromZone + ", fromZoneLabel=" + fromZoneLabel + ", fromLatitude="
				+ fromLatitude + ", fromLongitude=" + fromLongitude + ", toZone=" + toZone + ", toZoneLabel="
				+ toZoneLabel + ", toLatitude=" + toLatitude + ", toLongitude=" + toLongitude + ", hexColor=" + hexColor
				+ ", toHexColor=" + toHexColor + ", SCAMBIO1=" + SCAMBIO1 + ", NAZIONE=" + NAZIONE + ", SALDO=" + valuex
				+ "]";
	}

	
	
	

	
	
	
}
