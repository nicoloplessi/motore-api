package com.motoreapi.demo.menu;

import java.util.ArrayList;

public class MenuForCat {
	 public String categoryTypeID;
	    public String lastUpdated;
	    public ArrayList<Graph> graphs;
		public String getCategoryTypeID() {
			return categoryTypeID;
		}
		public void setCategoryTypeID(String categoryTypeID) {
			this.categoryTypeID = categoryTypeID;
		}
		public String getLastUpdated() {
			return lastUpdated;
		}
		public void setLastUpdated(String lastUpdated) {
			this.lastUpdated = lastUpdated;
		}
		public ArrayList<Graph> getGraphs() {
			return graphs;
		}
		public void setGraphs(ArrayList<Graph> graphs) {
			this.graphs = graphs;
		}
	    
	    
}
