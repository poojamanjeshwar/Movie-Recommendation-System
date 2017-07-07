package com.recommender.recommender_service;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.recommender.dataSource.DataBaseConnection;
import com.recommender.model.Rating;

public class RecommendationHelper {
	
	
	public List<Integer> getPreference(Long uid) throws ClassNotFoundException, SQLException{
		List<Integer> tagsIdList = new LinkedList<Integer>();
		Connection conn = DataBaseConnection.getInstance().getConnection();
		Statement stmt=conn.createStatement();  
		ResultSet rs=stmt.executeQuery(
				"select TagID from "+RecommenderConstant.USER_TAGS+"where UserID="+uid);  
		while(rs.next()){
			tagsIdList.add(rs.getInt(1));
		}
		conn.close(); 
		return tagsIdList;
	}
	
	public List<Rating> getMovies(Long uid) throws SQLException, ClassNotFoundException{
		List<Rating> moviesList = new LinkedList<Rating>();
		Connection conn = DataBaseConnection.getInstance().getConnection();
		Statement stmt=conn.createStatement();  
		ResultSet rs=stmt.executeQuery(
				"SELECT "+ RecommenderConstant.RATING+".UserId, "+ RecommenderConstant.RATING+".MovieID,+"+ RecommenderConstant.RATING+".rating"+
				"FROM " + RecommenderConstant.RATING +
				"INNER JOIN" + 
				"(SELECT "+RecommenderConstant.USER_TAGS+".TagID, "+RecommenderConstant.MOVIE_GENRE+".MovieID"+
				"FROM "+RecommenderConstant.USER_TAGS +
				"INNER JOIN "+ RecommenderConstant.MOVIE_GENRE +
				"ON "+RecommenderConstant.USER_TAGS+".TagID = "+RecommenderConstant.MOVIE_GENRE+".TagID" +
				"WHERE "+ RecommenderConstant.RATING+".UserId = "+uid+") AS temp"+
				"ON "+RecommenderConstant.RATING+".MovieID = temp.MovieID");  
		while(rs.next()){
			Rating r = new Rating();
			r.setUid(rs.getLong(1));
			r.setMovieId(rs.getLong(2));
			r.setRating(rs.getDouble(3));
			moviesList.add(r);
		}
		return moviesList;
	}
	

	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> createMovieFile(Long uid,Object key, String queryName) throws SQLException, ClassNotFoundException, IOException, TasteException{
		
		if(queryName.equals(RecommenderConstant.PREFERENCE)){
			return getDataBasedRecommendation(getbyPreferenceQuery(uid));
		}else if(queryName.equals(RecommenderConstant.YEAR)){
			return getDataBasedRecommendation(getbyYearQuery((List<String>)key));
		}else if(queryName.equals(RecommenderConstant.AGE)){
			return getDataBasedRecommendation(getByAgeGroupdId((Integer)key));
		}
		
		return null;
	}
	
	public void updatePreferences(List<Integer> tagIdList, long uid) throws SQLException, ClassNotFoundException{
		Connection conn = DataBaseConnection.getInstance().getConnection();
		Statement stmt = conn.createStatement();
		String sql = updatePreferenceQuery(tagIdList, uid);
		stmt.executeUpdate(sql);
		conn.close();
	}
	
	public String getbyPreferenceQuery(long uid){
		return "SELECT * FROM (SELECT DISTINCT  Rating.MovieID,Rating.rating " +
				"FROM Rating "+
				"INNER JOIN " +
				"(SELECT genome_scores.TagID, genome_scores.MovieID "+
				"FROM genome_scores "+
				"INNER JOIN user_tags  "+
				"ON user_tags.TagID = genome_scores.TagID "+ 
				"WHERE user_tags.UserId ="+uid+") AS temp "+
				"ON Rating.MovieID = temp.MovieID) AS temp1 ";
	}
	
	private String getbyYearQuery(List<String> years){
		String values = "(";
		int count = 0;
		for(String year: years){
			if(years.size()-1==count){
				values = values+ "'" + year + "')";
			}else{
				values = values + "'" + year +"',";
			}
			count++;
		}
		return "SELECT DISTINCT rating.MovieID,rating.rating "+
				"FROM rating "+
				"INNER JOIN (SELECT MovieID  FROM MOVIE WHERE movie.Movie_Year IN "+values+") AS TEMP "+
				"ON rating.MovieID = TEMP.MovieID ";
	}
	
	private String getByAgeGroupdId(Integer ageGroupdId){
		return "SELECT DISTINCT rating.MovieID,rating.rating "+
				"FROM rating "+
				"RIGHT JOIN (SELECT * FROM USERS WHERE users.ageGroupId="+ageGroupdId+") AS TEMP "+
				"ON rating.UserId = TEMP.UserId";
	}
	
	public String updatePreferenceQuery(List<Integer> tagIdList, long uid){
		String values = "INSERT INTO "+RecommenderConstant.USER_TAGS+
			    " (UserID, TagID)"+
			    " VALUES ";
		int count = 0;
		for(Integer tagId: tagIdList){
			if(tagIdList.size()-1==count){
				values = values + "("+uid+","+tagId+")";
			}else{
				values = values + "("+uid+","+tagId+"),";
			}
			count++;
		}
		return values;
	}
	
	public String getImdbId(int movieId) throws SQLException, ClassNotFoundException{
		Connection conn = DataBaseConnection.getInstance().getConnection();
		Statement stmt=conn.createStatement();  
		ResultSet rs=stmt.executeQuery(
				"select imdbId from link where movieId="+movieId
				);  
		while(rs.next()){
			String id = rs.getString(1);
			while(id.length()<7){
				id = "0"+id;
			}
			id = "tt"+id;
			return id;
		}
		return null;
	}
	
	public IMDBData.MovieRecommendation buildIMDBData(int recommendedItem, int rating) throws ClientProtocolException, IOException, ClassNotFoundException, SQLException{
		IMDBData imdbData = new IMDBData();
		IMDBData.MovieRecommendation imdb= imdbData.getImdb("http://www.omdbapi.com/", "i="+getImdbId(recommendedItem));
		imdb.setUserRating(String.valueOf(rating));
		return imdb;
	}
	
	public List<RecommendedItem>  getFileBasedRecommendation(String fileName , long uid) throws IOException, TasteException{
		List<RecommendedItem> recommendationItems = DataBaseConnection.getInstance().getUserBasedRecommender(fileName).recommend(uid, 25);
		return recommendationItems;
	}
	
	public Map<Integer, Integer> getDataBasedRecommendation(String query) throws TasteException, ClassNotFoundException, SQLException{
		Map<Integer, Integer> recommendationItems = new HashMap<Integer, Integer>();
		Connection conn = DataBaseConnection.getInstance().getConnection();
		Statement stmt=conn.createStatement();
		if(query.equalsIgnoreCase("SELECT * FROM recommender.rating")){
			while(recommendationItems.size()<26){
				ResultSet rs=stmt.executeQuery(
						query + " where MovieID = "+((new Random()).nextInt(500) + 1)+ " AND rating >=3"
						); 
				
				while(rs.next()){
					int id = rs.getInt(2);
					int rating = rs.getInt(3);
					recommendationItems.put(id, rating);
					break;
				}
			}
		}else{
			ResultSet rs=stmt.executeQuery(
					query + " where rating >=3 GROUP BY(MovieID) LIMIT 25"
					);
			while(rs.next()){
				int id = rs.getInt(1);
				int rating = rs.getInt(2);
				recommendationItems.put(id, rating);
			}
		}
		
		
		return recommendationItems;
	}
	
	

}
