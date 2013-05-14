package com.uvalimised.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uvalimised.data.Candidate;

public class getVotedData extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		String name =req.getParameter("id");
		
		
		//TODO-  serveriga suhtlus
		
		Candidate candidate= new Candidate(firstName, lastName, party, location);
		
		Gson gson = new GsonBuilder().create();
        String categoriesJson = gson.toJson(candidate);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(categoriesJson);
		
	}

}
