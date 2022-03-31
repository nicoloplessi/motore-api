package com.motoreapi.demo.news;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class NewsObj {
	public List<Article> articles;
	public int pageSize;
	public int offSet;
	public int totalPageNumber;
	

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "NewsObj [articles=" + articles + "]";
	}
	
	public  List<Article> getPagination(List<Article> articoli,String pageSize,String pageNumber)
	{	
		

	// Creation
	PagedListHolder page = new PagedListHolder(articoli);
	page.setPageSize(Integer.parseInt(pageSize)); // number of items per page
	page.setPage(Integer.parseInt(pageNumber));      // set to first page
	this.pageSize=Integer.parseInt(pageSize);
	this.totalPageNumber=Integer.parseInt(pageNumber);
	// Retrieval
	totalPageNumber=page.getPageCount(); // number of pages 
	
	return page.getPageList();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffSet() {
		return offSet;
	}

	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}

	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}
	
	
	

}
