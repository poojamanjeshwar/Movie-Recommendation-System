package com.recommenderrest.requestTo;

import java.util.LinkedList;
import java.util.List;

public class RecommenderReqestTo {
	
	private List<Integer> tagIds = new LinkedList<>();
	
	private List<String> years = new LinkedList<>();

	public List<Integer> getTagIds() {
		return tagIds;
	}

	public void setTagIds(List<Integer> tagIds) {
		this.tagIds = tagIds;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	} 
	
	

}
