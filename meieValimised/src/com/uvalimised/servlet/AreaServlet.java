package com.uvalimised.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gson.Gson;
import com.uvalimised.data.Area;

public class AreaServlet extends HttpServlet{
	private static final long serialVersionUID = 6765583424445139002L;

	public AreaServlet() {
        super();
    }
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		 resp.setContentType("text/html; charset=UTF-8");



	  PrintWriter out = resp.getWriter();
	  Connection c = null;
	    try {

	      DriverManager.registerDriver(new AppEngineDriver());
	      c = DriverManager.getConnection("jdbc:google:rdbms://evalimised-ut-andmebaas:andmebaas/meievalimised");
	      String statement ="SELECT * FROM area";  
	      java.sql.PreparedStatement stmt = c.prepareStatement(statement);
	      int success = 2;
	      ResultSet rs  = stmt.executeQuery(statement);
	      List<Area> result = new ArrayList<Area>();
	      while (rs.next()){
	    	  Area spr = new Area();
	    	  spr.setAreaId(rs.getLong(1));
	    	  spr.setAreaName(rs.getString(2));
	    	  result.add(spr);
	      }

	      out.print(new Gson().toJson(result));
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (c != null) 
	          try {
	            c.close();
	            } catch (SQLException ignore) {
	         }
	      } 
	  }
}
