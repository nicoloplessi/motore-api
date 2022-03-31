package com.motoreapi.demo.Zonali;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class LegendaZonaliBean {

	
	@JsonProperty("HEXCOLOR")
	public String hexColor;
	@JsonProperty("FROMZONELABEL")	
	public String name;
	@JsonProperty("FROMZONE")	
	public String typeId;
	
	public String getHexcolor() {
		return hexColor;
	}
	
	public LegendaZonaliBean(ZonaliDataBean d)
	{
		this.hexColor=d.getHexColor_1();
		this.name=d.getFROMZONELABEL();
		this.typeId=d.getFROMZONE();
	}
	public LegendaZonaliBean(String hex,String name, String id)
	{
		this.hexColor=hex;
		this.name=name;
		this.typeId=id;
	}
	
	public void setHexcolor(String hexcolor) {
		this.hexColor = hexcolor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	@Override
	public String toString() {
		return "LegendaZonaliBean [hexcolor=" + hexColor + ", name=" + name + ", typeId=" + typeId + "]";
	}
	
	
}
