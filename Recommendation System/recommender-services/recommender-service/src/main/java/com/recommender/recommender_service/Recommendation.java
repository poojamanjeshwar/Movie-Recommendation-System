package com.recommender.recommender_service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public class Recommendation {
	
	
	public List<IMDBData.MovieRecommendation> getMovieRecommendationByPreference(long uid, List<Integer> tagIds) throws ClassNotFoundException, SQLException, IOException, TasteException{
		RecommendationHelper recommendationHelper = new RecommendationHelper();
		if(tagIds!=null && tagIds.size()>0){
			recommendationHelper.updatePreferences(tagIds, uid);
		}
		List<IMDBData.MovieRecommendation> movies = new LinkedList<>();
		Map<Integer,Integer> recommendationItems = recommendationHelper.createMovieFile(uid, null, RecommenderConstant.PREFERENCE);
		Iterator it = recommendationItems.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        movies.add(recommendationHelper.buildIMDBData((int)pair.getKey(),(int)pair.getValue()));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		return movies;
	}
	
	
	public List<IMDBData.MovieRecommendation> getMovieRecommendation(long uid) throws ClassNotFoundException, SQLException, IOException, TasteException{
		List<IMDBData.MovieRecommendation> movies = new LinkedList<>();
		RecommendationHelper recommendationHelper = new RecommendationHelper();
		Map<Integer,Integer> recommendationItems = recommendationHelper.getDataBasedRecommendation("SELECT * FROM recommender.rating");
		Iterator it = recommendationItems.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        movies.add(recommendationHelper.buildIMDBData((int)pair.getKey(),(int)pair.getValue()));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		return movies;
	}
	
	public List<IMDBData.MovieRecommendation> getMovieRecommendationByYear(long uid, List<String> years) throws ClassNotFoundException, SQLException, IOException, TasteException{
		RecommendationHelper recommendationHelper = new RecommendationHelper();
		List<IMDBData.MovieRecommendation> movies = new LinkedList<>();
		Map<Integer,Integer> recommendationItems = recommendationHelper.createMovieFile(uid, years, RecommenderConstant.YEAR);
		Iterator it = recommendationItems.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        movies.add(recommendationHelper.buildIMDBData((int)pair.getKey(),(int)pair.getValue()));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		return movies;
	}
	
	public List<IMDBData.MovieRecommendation> getMovieRecommendationByAge(long uid, Integer ageGroupId) throws ClassNotFoundException, SQLException, IOException, TasteException{
		RecommendationHelper recommendationHelper = new RecommendationHelper();
		List<IMDBData.MovieRecommendation> movies = new LinkedList<>();
		Map<Integer,Integer> recommendationItems = recommendationHelper.createMovieFile(uid, ageGroupId, RecommenderConstant.AGE);
		Iterator it = recommendationItems.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        movies.add(recommendationHelper.buildIMDBData((int)pair.getKey(),(int)pair.getValue()));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		return movies;
	}
	

}
