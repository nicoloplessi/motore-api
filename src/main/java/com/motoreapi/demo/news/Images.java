package com.motoreapi.demo.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class Images {

	@Expose
	@JsonProperty("IMGURL")
	public String imgUrl;
	@Expose
	@JsonProperty("IMGDESC")
	public String imgDesc;
	
}
