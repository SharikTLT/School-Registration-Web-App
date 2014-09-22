package com.orangeandbronze.schoolreg.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.orangeandbronze.schoolreg.domain.Faculty;
import com.orangeandbronze.schoolreg.domain.Subject;
import com.orangeandbronze.schoolreg.service.SectionCreationService;

/**
 * Servlet implementation class NewSectionCreation
 */
@WebServlet("/SectionCreation")
public class SectionCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SectionCreationService sectionCreationService = new SectionCreationService();
		
		List<Faculty> facultyList = sectionCreationService.fetchFacultyList();
		List<Subject> subjectList = sectionCreationService.fetchSubjectList();
		
		HttpSession session = request.getSession();
		session.setAttribute("facultyList", facultyList);
		session.setAttribute("subjectList", subjectList);

		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/sectionCreation.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String facultyNumber = request.getParameter("facultyNumber");
		
		HttpSession session = request.getSession();
		List<Faculty> facultyList = (List<Faculty>) session.getAttribute("facultyList");
		for(Faculty faculty: facultyList){
			if(faculty.equals(new Integer(facultyNumber))){
				session.setAttribute("faculty", faculty);
				break;
			}
		}
		response.sendRedirect("/WEB-INF/jsp/sectionCreationResult.jsp");
	}

}
