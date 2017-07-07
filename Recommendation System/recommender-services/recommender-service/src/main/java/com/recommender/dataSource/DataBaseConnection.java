package com.recommender.dataSource;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.recommender.model.Rating;
import com.recommender.recommender_service.NeighbourBasedRatingRecommendation;
import com.recommender.recommender_service.RecommenderConstant;

public enum DataBaseConnection {
	
	 INSTANCE;
	
    private UserBasedRecommender ub = null;
    
    private UserBasedRecommender ub1 = null;

    DataBaseConnection()
    {
        try {
		
		 /*MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName("localhost");
			dataSource.setPortNumber(8889);
			dataSource.setUser("root");
			dataSource.setPassword("root");
			dataSource.setDatabaseName("recommender");
			dataSource.setURL("jdbc:mysql://localhost:8889/recommender?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
			NeighbourBasedRatingRecommendation neighbourBasedRatingRecommendation = 
					new NeighbourBasedRatingRecommendation(dataSource, "UserId", "MovieID", "rating", RecommenderConstant.RATING);
			ub = neighbourBasedRatingRecommendation.getRecommender(3);*/
        }catch(Exception e){
        	System.out.println(e.getStackTrace());
        }
    }

    // Static getter
    public static DataBaseConnection getInstance()
    {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException
    {
    	Class.forName("com.mysql.jdbc.Driver"); 
    	Connection connection=DriverManager.
				 getConnection("jdbc:mysql://localhost:8889/rec?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
						 "root","root");
		return connection;
    }
    
    public UserBasedRecommender getUserBasedRecommender()
    {
        return ub;
    }
    
    public UserBasedRecommender getUserBasedRecommender(String fileName) throws IOException, TasteException
    {
    	NeighbourBasedRatingRecommendation neighbourBasedRatingRecommendation = new NeighbourBasedRatingRecommendation(fileName);
        ub1 = neighbourBasedRatingRecommendation.getRecommender(3);
        return ub1;
    }
	

}
