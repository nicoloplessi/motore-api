package com.motoreapi.demo.news;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class NewsBean {
	
	@Expose
	@JsonProperty("DATEPUBLISHED")
	public String datePublished;
	@Expose
	@JsonProperty("ID")
	public String id;
	@Expose
	@JsonProperty("NEWSID")
	public String newsId;
	@Expose
	@JsonProperty("LINKURL")
	public String linkUrl;
	@Expose
	@JsonProperty("TITLE")
	public String title;
	@Expose
	@JsonProperty("SUBTITLE")
	public String subtitle;
	@Expose
	@JsonProperty("ABSTRACT")
	public String Abstract;
	@Expose
	@JsonProperty("DISCLAIMER")
	public String disclaimer;
	@Expose
	
	public List<Images> images;
	
	
	
	
	
	public String getDatePublished() {
		return datePublished;
	}
	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getAbstract() {
		return Abstract;
	}
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	public List<Images> getImages() {
		return images;
	}
	public void setImages(List<Images> images) {
		this.images = images;
	}
	@Override
	public String toString() {
		return "NewsBean [datePublished=" + datePublished + ", id=" + id + ", newsId=" + newsId + ", linkUrl=" + linkUrl
				+ ", title=" + title + ", subtitle=" + subtitle + ", Abstract=" + Abstract + ", disclaimer="
				+ disclaimer + ", images=" + images.toString() + "]";
	}

	
}
