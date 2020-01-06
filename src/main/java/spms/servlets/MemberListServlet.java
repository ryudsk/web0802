package spms.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MySqlMemberDao;

// UI 출력 코드를 제거하고, UI 생성 및 출력을 JSP에게 위임한다.
@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 컨텍스트 초기화 매개변수 사용
				ServletContext sc = this.getServletContext();
				MySqlMemberDao memberDao = (MySqlMemberDao)sc.getAttribute("memberDao"); //Listener에서 미리생성한 Dao가져오기
			//	request.setAttribute("members", memberDao.selectList()); //Dao결과 request에 보관
				request.setAttribute("viewUrl", "/member/MemberList.jsp"); //호출할 JSP주소 request에 보관
//				response.setContentType("text/html; charset=UTF-8");
//				RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
//				rd.include(request, response);
				
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
