package com.uvalimised.servlet;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uvalimised.data.Party;

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

public class PartySearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PartySearchServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection c = null;
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			DriverManager.registerDriver(new AppEngineDriver());
			c = DriverManager
					.getConnection("jdbc:google:rdbms://evalimised-ut-andmebaas:andmebaas/meievalimised");

			String party = request.getParameter("party");
			
			if (party.equals("")) {
				party = "%";
			}
			

			try {
				DriverManager.registerDriver(new AppEngineDriver());
				c = DriverManager
						.getConnection("jdbc:google:rdbms://evalimised-ut-andmebaas:andmebaas/meievalimised");

				String statement = "SELECT * FROM party WHERE party_name LIKE ? ";
				PreparedStatement stmt = c.prepareStatement(statement);
				stmt.setString(1,  "%" + party + "%");

				ResultSet rs = stmt.executeQuery(statement);
				List<Party> result = new ArrayList<Party>();
				while (rs.next()) {
					Party spr = new Party();
					spr.setPartyId(rs.getLong(1));
					spr.setPartyName(rs.getString(2));
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
}