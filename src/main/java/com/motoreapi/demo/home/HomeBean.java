package com.motoreapi.demo.home;

import java.util.ArrayList;

public class HomeBean {
	
	    public String lastUpdated;
	    public ArrayList<HomeSection> homeSections;
		public String getLastUpdated() {
			return lastUpdated;
		}
		public void setLastUpdated(String lastUpdated) {
			this.lastUpdated = lastUpdated;
		}
		public ArrayList<HomeSection> getHomeSections() {
			return homeSections;
		}
		public void setHomeSections(ArrayList<HomeSection> homeSections) {
			this.homeSections = homeSections;
		}
	    
	    
	
}
