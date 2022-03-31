package com.motoreapi.demo.web;

import java.io.IOException;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.motoreapi.demo.service.APIService;


@RestController
@RequestMapping("/api")
public class APIController
{

    @Autowired
    APIService api;
 
    @GetMapping("/")
    public String homePoint(){
    	return "HelloWorld";
    }
    
    @GetMapping("/{name}")
    public String getPoint(@PathVariable("name") String name, @RequestParam Map<String,String> allParams, @RequestHeader("language_code") String language) throws DataAccessException, IOException
    {
    	String object = api.getResultSet(name,allParams,language);
    	//JSONParser parsed = new JSONParser(object); 
    	//String object= api.getMockfromName(name);
    	return object;
    }
    
    
    @GetMapping("/test/param") 
    public String getParam(@RequestParam Map<String,String> allParams)
    {
    	
    	return api.ElaboraParm(allParams);
    	
    }
    
    @GetMapping("/test/testquerydb")
    public String getData() throws DataAccessException, IOException
    {
    	
    	return api.getResultSet(null,null,null);
    }
    
    @PostMapping("/test/write")
    public String postPoint(@RequestBody String newEmployee) throws JsonParseException, JsonMappingException, IOException
    {
    	return api.elaboraJson(newEmployee,"TEST");
    	
    }
    
    @PutMapping("")
    public String putPoint()
    {
    	return null;
    }
 
}
