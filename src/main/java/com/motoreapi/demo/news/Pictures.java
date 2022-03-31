package com.motoreapi.demo.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Pictures {
	
		@Expose
		@JsonProperty("imgUrl")
		public String imgUrl;
		@Expose
		@JsonProperty("imgDesc")
		public String imgDesc;
		
	}

