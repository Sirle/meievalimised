package com.uvalimised.servlet;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uvalimised.data.Candidate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uvalimised.DAO.ConnectionManager;

/**
 * Servlet implementation class MainServlet * CandidateSearchServlet * @author
 * Helina
 */

public class CandidateSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CandidateSearchServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection c = null;
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			DriverManager.registerDriver(new AppEngineDriver());
			c = DriverManager
					.getConnection("jdbc:google:rdbms://evalimised-ut-andmebaas:andmebaas/meievalimised");

			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String party = request.getParameter("party");
			String area = request.getParameter("area");
			
			if (firstname.equals("")) {
				firstname = "%";
			}
			if (lastname.equals("")) {
				lastname = "%";
			}
			if (party.equals("")) {
				party = "%";
			}
			if (area.equals("")) {
				area = "%";
			}

			try {
				DriverManager.registerDriver(new AppEngineDriver());
				c = DriverManager
						.getConnection("jdbc:google:rdbms://evalimised-ut-andmebaas:andmebaas/meievalimised");

				String statement = "SELECT * FROM candidate WHERE firstname LIKE ?  AND lastname LIKE ? AND party_id LIKE ? AND area_id LIKE ?";
				PreparedStatement stmt = c.prepareStatement(statement);
				stmt.setString(1,  "%" + firstname + "%");
				stmt.setString(2, "%" + lastname + "%");
				stmt.setString(3, "%" + party + "%");
				stmt.setString(4, "%" + area + "%");

				ResultSet rs = stmt.executeQuery(statement);
				List<Candidate> result = new ArrayList<Candidate>();
				while (rs.next()) {
					Candidate spr = new Candidate();
					spr.setId(rs.getLong(1));
					spr.setFirstName(rs.getString(2));
					spr.setLastName(rs.getString(3));
					spr.setParty(rs.getString(4));
					spr.setLocation(rs.getString(5));
					result.add(spr);
				}

				out.print(new Gson().toJson(result));

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static String createJSON(ResultSet rs, String party, String area) {
		List<Candidate> candidates = new ArrayList<Candidate>();
		try {
			while (rs.next()) {
				Candidate candidate = new Candidate();
				candidate.setFirstName(rs.getString("firstname"));
				candidate.setFirstName(rs.getString("lastname"));
				if (party.equals("") || party == null) {
					candidate.setParty(rs.getString("party_name"));
				}
				if (area.equals("") || area == null) {
					candidate.setLocation(rs.getString("area_name"));
				}
				candidates.add(candidate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().create();
		String candidatesJson = gson.toJson(candidates);
		return candidatesJson;
	}

	// Proovid
	// String region = request.getParameter("Region");
	// String party = request.getParameter("Party");
	// response.getWriter().write("{ id : 1 }");

}
