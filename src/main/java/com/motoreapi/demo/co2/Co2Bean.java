package com.motoreapi.demo.co2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;


public class Co2Bean {

	
	@JsonProperty("DATA_ORA")
	@JsonIgnoreProperties({"DATA_ORA"})
	public String DATA_ORA;
	
	@JsonProperty("ORA")
	@JsonIgnoreProperties({"ORA"})
	public String ORA;
	
	@JsonProperty("CODICE_ZONA")
	@JsonIgnoreProperties({"CODICE_ZONA"})
	public String CODICE_ZONA;
	
	
	@JsonProperty("TONNELLATE_CO2")
	public Float value;
	
	
	@JsonProperty("FROMZONE")
	public String zone;
	
	
	@JsonProperty("FROMZONELABEL")
	public String zoneLabel;
	
	
	@JsonProperty("FROMLATITUDE")
	public String latitude;
	
	@JsonProperty("FROMLONGITUDE")
	public String longitude;
	
	@JsonProperty("HEXCOLOR")
	public String hexColor;
	
	@JsonProperty("ZONA_CK")
	@JsonIgnoreProperties({"ZONA_CK"})
	public String ZONA_CK;
	
	@JsonProperty("ZONEDESCRIPTION")
	public String zoneDescription;
	
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
	public String getCODICE_ZONA() {
		return CODICE_ZONA;
	}
	public void setCODICE_ZONA(String cODICE_ZONA) {
		CODICE_ZONA = cODICE_ZONA;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
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
	public String getHexColor() {
		return hexColor;
	}
	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}
	public String getZONA_CK() {
		return ZONA_CK;
	}
	public void setZONA_CK(String zONA_CK) {
		ZONA_CK = zONA_CK;
	}
	public String getZoneDescription() {
		return zoneDescription;
	}
	public void setZoneDescription(String zoneDescription) {
		this.zoneDescription = zoneDescription;
	}
	@Override
	public String toString() {
		return "Co2Bean [DATA_ORA=" + DATA_ORA + ", ORA=" + ORA + ", CODICE_ZONA=" + CODICE_ZONA + ", value=" + value
				+ ", zone=" + zone + ", zoneLabel=" + zoneLabel + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", hexColor=" + hexColor + ", ZONA_CK=" + ZONA_CK + ", zoneDescription=" + zoneDescription + "]";
	}
	
	
	
	
	
	
	

}
