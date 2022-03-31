package com.motoreapi.demo.fer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


public class FerBean {
	
	@JsonProperty("DATA_ORA")
	@JsonIgnoreProperties({"DATA_ORA"})
	public String DATA_ORA;
	
	@JsonProperty("CODICE_ZONA")
	@JsonIgnoreProperties({"CODICE_ZONA"})
	public String CODICE_ZONA;
	
	@JsonProperty("ORA")
	@JsonIgnoreProperties({"ORA"})
	public String ORA;
	
	@JsonProperty("ZONA_CK")
	@JsonIgnoreProperties({"ZONA_CK"})
	public String ZONA_CK;
	
	
	@JsonProperty("PERCENTUALE_FER")
	public float value;
	
	
	@JsonProperty("ID")
	public String zone;
	
	
	@JsonProperty("ZONA")
	public String zoneLabel;
	
	
	@JsonProperty("LATITUDINE")
	public String latitude;
	
	
	@JsonProperty("LONGITUDINE")
	public String longitude;
	
	
	@JsonProperty("ZONA_DESCRIZIONE")
	public String zoneDescription;
	
	
	@JsonProperty("HEXCOLOR")
	public String hexColor;
	
	public String getDATA_ORA() {
		return DATA_ORA;
	}
	public void setDATA_ORA(String dATA_ORA) {
		DATA_ORA = dATA_ORA;
	}
	public String getCODICE_ZONA() {
		return CODICE_ZONA;
	}
	public void setCODICE_ZONA(String cODICE_ZONA) {
		CODICE_ZONA = cODICE_ZONA;
	}
	public String getORA() {
		return ORA;
	}
	public void setORA(String oRA) {
		ORA = oRA;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getZoneLabel() {
		return zoneLabel;
	}
	public void setZoneLabel(String zoneLabel) {
		this.zoneLabel = zoneLabel;
	}
	public String getZONA_CK() {
		return ZONA_CK;
	}
	public void setZONA_CK(String zONA_CK) {
		ZONA_CK = zONA_CK;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getZoneDescription() {
		return zoneDescription;
	}
	public void setZoneDescription(String zoneDescription) {
		this.zoneDescription = zoneDescription;
	}
	public String getHexColor() {
		return hexColor;
	}
	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}
	@Override
	public String toString() {
		return "FerBean [DATA_ORA=" + DATA_ORA + ", CODICE_ZONA=" + CODICE_ZONA + ", ORA=" + ORA + ", value=" + value
				+ ", zone=" + zone + ", zoneLabel=" + zoneLabel + ", ZONA_CK=" + ZONA_CK + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", zoneDescription=" + zoneDescription + ", hexColor=" + hexColor + "]";
	}
	
	
}