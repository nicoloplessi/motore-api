package com.motoreapi.demo.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motoreapi.demo.model.APIEntity;
import com.motoreapi.demo.repository.APIRepository;


@Service
public class APIService {
	
	@Autowired
	APIRepository ApiRepo;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public APIEntity getApiFromName(String name)
	{
		APIEntity api = ApiRepo.findByName(name);
		
		return api;
	}
	
	public List elaboraJson(String json) throws JsonParseException, JsonMappingException, IOException
	{
		//ObjectMapper mapper = new ObjectMapper();
		
		//mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		//List<Map<String, Object>> data = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
		JSONObject jo = new JSONObject(json);
		String name = (String)jo.get("name");
		String id=(String)jo.get("id");
		String dato = (String)jo.get("DATO");
		System.out.println(id);
		System.out.println(dato);
		List result = new ArrayList();
		result.add(name);
		result.add(id);
		result.add(dato);
		return result;
	}
	
	public String post()
	{
		
		return null;
	}
	
	public String getResultSet(String name)
	{
		APIEntity api = getApiFromName(name);
		System.out.println(api.toString());
		
		String sql = api.getQuery();
		System.out.println("---"+sql+"---");
		List<Map<String,Object>> o = jdbcTemplate.queryForList(sql);
		String result = o.toString();
		System.out.println("---"+result+"---");
		return result;
		
		
	}
	
	

}
