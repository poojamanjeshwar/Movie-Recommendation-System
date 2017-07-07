package com.recommenderrest.startUp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;

import com.recommender.dataSource.DataBaseConnection;


/**
 * This class gets loaded on server started, restarted or stopped to create/destroy necessary
 * resources before Application start serving user requests
 * @author hiral
 *
 */
public class Bootstrap extends javax.servlet.http.HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3202648753356988719L;
	

	/**
	 * called when server starts up
	 */
	public void init(ServletConfig config) throws ServletException {
		DataBaseConnection.getInstance();
	} 
	
	/**
	 * called when you stop server or reload
	 */
	public void destroy() {
		
	}

}
