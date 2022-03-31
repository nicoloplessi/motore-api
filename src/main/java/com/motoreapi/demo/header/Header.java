package com.motoreapi.demo.header;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Header {
	
		@JsonProperty("DATA_TYPE_ID")
		@SerializedName("dataTypeId")
		@Expose
	  	public String dataTypeId;
		@JsonProperty("LABEL")
		@SerializedName("name")
		@Expose
	    public String name;
		@JsonProperty("fromDate")
		@SerializedName("fromDate")
		@Expose
	    public String fromDate;
		@JsonProperty("toDate")
		@SerializedName("toDate")
		@Expose
	    public String toDate;
		@JsonProperty("MEASURE_UNIT")
		@SerializedName("measureUnit")
		@Expose
	    public String measureUnit;
		@JsonProperty("totalAmount")
		@SerializedName("totalAmount")
		@Expose
	    public String totalAmount;
		@JsonProperty("MEASURE_DESCRIPTION")
		@SerializedName("measureDescription")
		@Expose
	    public String measureDescription;
		
		
		public String getDataTypeId() {
			return dataTypeId;
		}
		public void setDataTypeId(String dataTypeId) {
			this.dataTypeId = dataTypeId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getFromDate() {
			return fromDate;
		}
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		public String getToDate() {
			return toDate;
		}
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}
		public String getMeasureUnit() {
			return measureUnit;
		}
		public void setMeasureUnit(String measureUnit) {
			this.measureUnit = measureUnit;
		}
		public String getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getMeasureDescription() {
			return measureDescription;
		}
		public void setMeasureDescription(String measureDescription) {
			this.measureDescription = measureDescription;
		}
		@Override
		public String toString() {
			return "Header [dataTypeId=" + dataTypeId + ", name=" + name + ", fromDate=" + fromDate + ", toDate="
					+ toDate + ", measureUnit=" + measureUnit + ", totalAmount=" + totalAmount + ", measureDescription="
					+ measureDescription + "]";
		}
	    
	    

}
