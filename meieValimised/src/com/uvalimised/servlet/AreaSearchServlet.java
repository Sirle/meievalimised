package com.uvalimised.servlet;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import com.uvalimised.data.Area;

public class AreaSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AreaSearchServlet() {
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

			String area = request.getParameter("area");
			
			if (area.equals("")) {
				area = "%";
			}
			

			try {
				DriverManager.registerDriver(new AppEngineDriver());
				c = DriverManager
						.getConnection("jdbc:google:rdbms://evalimised-ut-andmebaas:andmebaas/meievalimised");

				String statement = "SELECT * FROM area WHERE area_name LIKE ? ";
				PreparedStatement stmt = c.prepareStatement(statement);
				stmt.setString(1,  "%" + area + "%");

				ResultSet rs = stmt.executeQuery(statement);
				List<Area> result = new ArrayList<Area>();
				while (rs.next()) {
					Area spr = new Area();
					spr.setAreaId(rs.getLong(1));
					spr.setAreaName(rs.getString(2));
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