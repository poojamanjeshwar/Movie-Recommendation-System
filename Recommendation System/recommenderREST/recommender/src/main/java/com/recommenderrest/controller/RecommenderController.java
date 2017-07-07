package com.recommenderrest.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.recommender.recommender_service.IMDBData;
import com.recommender.recommender_service.Recommendation;
import com.recommenderrest.requestTo.RecommenderReqestTo;




@RestController
@RequestMapping(value="/recommenderService")
public class RecommenderController {
	
	
	
	@RequestMapping(method=RequestMethod.PUT, value="/Recommendations/{uid}",consumes="application/json" , produces="application/json")
	public HttpEntity<List<IMDBData.MovieRecommendation>> getRecommendation(@PathVariable Long uid,@RequestBody RecommenderReqestTo recommenderRequestTo){
		List<IMDBData.MovieRecommendation> list = null;
		Recommendation recommendation = new Recommendation();
		if(recommenderRequestTo.getTagIds()!=null && recommenderRequestTo.getTagIds().size()>0){
			try {
				list = recommendation.getMovieRecommendationByPreference(uid, recommenderRequestTo.getTagIds());
			} catch (ClassNotFoundException | SQLException | IOException | TasteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(recommenderRequestTo.getYears()!=null && recommenderRequestTo.getYears().size()>0){
			try {
				list = recommendation.getMovieRecommendationByYear(uid, recommenderRequestTo.getYears());
			} catch (ClassNotFoundException | SQLException | IOException | TasteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				list = recommendation.getMovieRecommendationByPreference(uid, null);
			} catch (ClassNotFoundException | SQLException | IOException | TasteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ResponseEntity<List<IMDBData.MovieRecommendation>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/Recommendations/{uid}/{ageGroupId}", produces="application/json")
	public HttpEntity<List<IMDBData.MovieRecommendation>> getRecommendation(@PathVariable Long uid,@PathVariable Integer ageGroupId){
		List<IMDBData.MovieRecommendation> list = null;
		Recommendation recommendation= new Recommendation();
		try {
			list = recommendation.getMovieRecommendationByAge(uid, ageGroupId);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<List<IMDBData.MovieRecommendation>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/Recommendations/{uid}", produces="application/json")
	public HttpEntity<List<IMDBData.MovieRecommendation>> getRecommendation(@PathVariable Long uid){
		List<IMDBData.MovieRecommendation> list = null;
		Recommendation recommendation= new Recommendation();
		try {
			list = recommendation.getMovieRecommendation(uid);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<List<IMDBData.MovieRecommendation>>(list, HttpStatus.OK);
	}
}
