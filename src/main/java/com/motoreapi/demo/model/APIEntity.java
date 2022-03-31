package com.motoreapi.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="API", schema = "TERNA_APP_DATA")

public class APIEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="Api")
    private String api;
    
    @Column(name="parameter")
    private String parameter;
    
    @Column(name="query")
    private String query;
    
    @Column(name="filter")
    private String filter;
   
    @Column(name="mock")
    private String mock;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getMock() {
		return mock;
	}

	public void setMock(String mock) {
		this.mock = mock;
	}

	@Override
	public String toString() {
		return "APIEntity [id=" + id + ", api=" + api + ", parameter=" + parameter + ", query=" + query + ", filter="
				+ filter + ", mock=" + mock + "]";
	}

	
    
	
    
    
    
    
    
}