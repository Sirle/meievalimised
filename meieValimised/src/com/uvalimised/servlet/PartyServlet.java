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
import com.uvalimised.data.Party;

public class PartyServlet extends HttpServlet{
	private static final long serialVersionUID = 6765583424445139002L;

	public PartyServlet() {
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
	      String statement ="SELECT * FROM party";  
	      java.sql.PreparedStatement stmt = c.prepareStatement(statement);
	      int success = 2;
	      ResultSet rs  = stmt.executeQuery(statement);
	      List<Party> result = new ArrayList<Party>();
	      while (rs.next()){
	    	  Party spr = new Party();
	    	  spr.setPartyId(rs.getLong(1));
	    	  spr.setPartyName(rs.getString(2));
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
