package spms.servlets;
//Front Controller

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
//import spms.context.ApplicationContext;
import spms.controls.Controller;
import spms.listeners.ContextLoaderListener;

@SuppressWarnings("serial")
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8"); //응답데이터의 문자집합
		request.setCharacterEncoding("UTF-8"); //요청데이터 한글처리
		
		String servletPath = request.getServletPath(); //URL에서 서블릿 경로 알아내기
		
		try {
			/*
			 * chapter 06. front controller 변경
			 */
//			ServletContext sc = this.getServletContext();
			ApplicationContext ctx = ContextLoaderListener.getApplicationContext();
			
			HashMap<String,Object> model = new HashMap<String,Object>();
			model.put("session", request.getSession());
			
//			Controller pageController = (Controller)sc.getAttribute(servletPath); 
			//서블릿 url을 사용하여 controller 꺼내기
			Controller pageController = (Controller)ctx.getBean(servletPath);
			if(pageController == null) {
				throw new Exception("요청한 서비스를 찾을 수 없습니다.");
			}
			
			if(pageController instanceof DataBinding) { //pageController의 데이터타입이 DataBinding이 될 수 있다면.(형변환 가능 여부)
				prepareRequestData(request, model, (DataBinding)pageController);
			}

			String viewUrl = pageController.execute(model);
			
			for(String key : model.keySet()) {
				request.setAttribute(key, model.get(key));
			}
			
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
				rd.include(request,response);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
	
	private void prepareRequestData(HttpServletRequest request, HashMap<String, Object> model, DataBinding dataBinding) throws Exception {
		Object[] dataBinders = dataBinding.getDataBinders(); //Member instance and "member"
		String dataName = null;
		Class<?> dataType = null;  // Class<?> 모든 타입의 클래스
		Object dataObj = null;
		for(int i=0; i < dataBinders.length; i+=2) {
			dataName = (String)dataBinders[i];
			dataType = (Class<?>) dataBinders[i+1];
			dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
			
			model.put(dataName, dataObj);
		}
	}
}
