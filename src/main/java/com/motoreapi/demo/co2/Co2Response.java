package com.motoreapi.demo.co2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.motoreapi.demo.header.Header;

public class Co2Response {
	
	@JsonProperty("")
	Header header;
	
	@JsonProperty("")
	List<Co2Bean> co2Bean;
	
	public Co2Response(Header header, List<Co2Bean> co2Bean) {
		super();
		this.header = header;
		this.co2Bean = co2Bean;
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public List<Co2Bean> getCo2Bean() {
		return co2Bean;
	}
	public void setCo2Bean(List<Co2Bean> co2Bean) {
		this.co2Bean = co2Bean;
	}
	@Override
	public String toString() {
		return "Co2Response [header=" + header + ", co2Bean=" + co2Bean + "]";
	}
	

}
