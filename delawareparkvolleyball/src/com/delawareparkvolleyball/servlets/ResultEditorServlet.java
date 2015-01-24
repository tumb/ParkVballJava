package com.delawareparkvolleyball.servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ResultEditor")
public class ResultEditorServlet  extends HttpServlet {

	private static final long serialVersionUID = 1L ;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost begins") ; 
		processResultEditRequest(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet begins") ; 
		processResultEditRequest(request, response);
	}

	private void processResultEditRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// For debugging only
		System.out.println("processResultEditRequest of LeagueEditorServlet") ;
		Enumeration<String> parameterNames = request.getParameterNames() ;
		String parameterList = "" ; 
		while(parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement() ; 
			parameterList += "\n" +  name + ", " ;
			String parameter = request.getParameter(name) ; 
			parameterList += parameter ; 
		}
		System.out.println(parameterList) ;
		HttpSession session = request.getSession() ;
		String leagueIdAsString = request.getParameter("league") ; 
		session.setAttribute("leagueId", leagueIdAsString);
		// End of debugging
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("matchResultEntry.jsp");
		requestDispatcher.include(request, response); // TODO learn the difference between include and forward.
//		rd.forward(request, response);
		
	}

	
}
