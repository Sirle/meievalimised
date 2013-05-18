package com.uvalimised.servlet;

import java.io.IOException;  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.uvalimised.DAO.ConnectionManager;
import com.uvalimised.data.Candidate;
import com.uvalimised.data.User;

/**
 * CandidateServlet
 * @author Robert
 *
 */
public class CandidateServlet extends HttpServlet{
	private static final long serialVersionUID = 6765583424445139002L;

	public CandidateServlet() {
        super();
    }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		Connection c = null;
	    try {
	      DriverManager.registerDriver(new AppEngineDriver());
	      c = DriverManager.getConnection("jdbc:google:rdbms://evalimised-ut-andmebaas:andmebaas/meievalimised");
		
		// Kontrollime, kas kasutaja on sisse logitud. Kui ei ole, suunab vastavale veateatele.
		HttpSession session = req.getSession();
		if (session.getAttribute("user") == null){
			resp.sendRedirect("notloggedin.jsp");
			return;
		}
		
		User user = (User) session.getAttribute("user");
		Candidate candidate = new Candidate();
		
		// Seame kandidaadi andmed
		// Hetkel Faile ei valideeri ja uploadi, tuleb hiljem!
		try{
			String party;
			String loc;
			
			if (req.getParameter("party") != "0") {
				String partySelectValue = req.getParameter("party");
				if (partySelectValue == "1"){
					party = "Reformierakond";
				}
				else if (partySelectValue == "2"){
					party = "Keskerakond";
				} else {
					party = "Ć�ksikkandidaat";
				}
			} else {
				throw new Exception("Erakond valimata!");
			}
			
			if (req.getParameter("location") != "0") {
				String locationSelectValue = req.getParameter("location");
				if (locationSelectValue == "1"){
					loc = "Tallinn";
				}
				else if (locationSelectValue == "2"){
					loc = "Tartu";
				} 
				else if (locationSelectValue == "3"){
					loc = "PĆ¤rnu";
				}
				else if (locationSelectValue == "4"){
					loc = "Haapsalu";
				} else {
					loc = "Paide";
				}
			} else {
				throw new Exception("Piirkond valimata!");
			}
			
			//candidate = new Candidate(user.getFirstName(), user.getLastName(), party, loc, req.getParameter("email"));
		} catch (Exception ex){
			System.out.println(ex);
		}
		
		
		// Andmebaasides toimetamine
		if (user.isCandidate == false){
			try{
				PreparedStatement statement= c.prepareStatement("INSERT INTO candidates (firstname, lastname, party, location, email) VALUES (?, ?, ?, ?, ?)");
				statement.setString(1, candidate.getFirstName());
				statement.setString(2, candidate.getLastName());
				statement.setString(3, candidate.getParty());
				statement.setString(4, candidate.getLocation());
				//statement.setString(6, candidate.getInfo());
				//statement.setString(7, candidate.getPicture());
				
				statement.executeUpdate();
				statement.close();
				user.setIsCandidate(true);
				
				PreparedStatement st2 = c.prepareStatement("INSERT INTO users (iscandidate) VALUES (?) WHERE uname='" + user.getUsername() + "'");
				st2.setBoolean(1, user.getIsCandidate());
				st2.executeUpdate();
				st2.close();
				
			} catch (Exception ex){
				System.out.println("Viga andmete andmebaasi sisestamisel!");
				System.out.println(ex);
			}
		}

		//ConnectionManager.closeConnection(); //Sulgeme
		
		ServletContext sc = getServletContext();
	    sc.getRequestDispatcher("/WebKandidaadiReg.html").forward(req, resp);
    }

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
	}
	
}
