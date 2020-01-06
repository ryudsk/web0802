package spms.servlets;

import java.io.IOException;
//import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("viewUrl","/auth/LogInForm.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MySqlMemberDao memberDao = (MySqlMemberDao)sc.getAttribute("memberDao"); 
			Member loginUser = memberDao.exist(request.getParameter("email"), request.getParameter("password"));
			if(loginUser != null) {
				// HttpSession에 VO 보관
				HttpSession session = request.getSession();
				session.setAttribute("user", loginUser);
				
				request.setAttribute("viewUrl","redirect:../member/list.do");
//				response.sendRedirect("../member/list");
			} else {
				request.setAttribute("viewUrl","/auth/LogInFail.jsp");
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
