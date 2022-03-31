package com.motoreapi.demo.fontiprimarie;

import java.util.ArrayList;

import com.motoreapi.demo.fabbisogno.DayMeasure;
import com.motoreapi.demo.fabbisogno.Type;

public class FontePrimaria {

	 public String fromDate;
	    public String toDate;
	    public String name;
	    public String measureDescription;
	    public String measureUnit;
	    public ArrayList<Type> types;
	    public ArrayList<DayMeasure> dayMeasures;
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
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMeasureDescription() {
			return measureDescription;
		}
		public void setMeasureDescription(String measureDescription) {
			this.measureDescription = measureDescription;
		}
		public String getMeasureUnit() {
			return measureUnit;
		}
		public void setMeasureUnit(String measureUnit) {
			this.measureUnit = measureUnit;
		}
		public ArrayList<Type> getTypes() {
			return types;
		}
		public void setTypes(ArrayList<Type> types) {
			this.types = types;
		}
		public ArrayList<DayMeasure> getDayMeasures() {
			return dayMeasures;
		}
		public void setDayMeasures(ArrayList<DayMeasure> dayMeasures) {
			this.dayMeasures = dayMeasures;
		}
	    
	    
}
