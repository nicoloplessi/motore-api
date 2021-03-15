package com.motoreapi.demo.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.motoreapi.demo.model.APIEntity;
import com.motoreapi.demo.service.APIService;


@RestController
@RequestMapping("/api")
public class APIController
{

    @Autowired
    APIService api;
 
    
    
    @GetMapping("/test/{name}")
    public String getPoint(@PathVariable("name") String name)
    {
    	String object = api.getResultSet(name);
    	return object;
    }
    
    
    @GetMapping("/test/param") 
    public String getParam(@RequestParam Map<String,String> allParams)
    {
    	
    	return api.ElaboraParm(allParams);
    	
    }
    
    
    
    @PostMapping("/test/write")
    public String postPoint(@RequestBody String newEmployee) throws JsonParseException, JsonMappingException, IOException
    {
    	List result = api.elaboraJson(newEmployee);
    	result.forEach(System.out::println);
    	System.out.println(result.size());
    	 return result.toString();
    }
    
    @PutMapping("")
    public String putPoint()
    {
    	return null;
    }
 
}
