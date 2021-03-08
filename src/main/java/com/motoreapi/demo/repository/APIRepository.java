package com.motoreapi.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.motoreapi.demo.model.APIEntity;


@Repository
public interface APIRepository 
		extends JpaRepository<APIEntity, Long> {
	
	APIEntity findByName(String name);
	

}

