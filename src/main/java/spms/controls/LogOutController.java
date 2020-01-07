package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

//import spms.annotation.Component;

@Component(value="/auth/logout.do")
public class LogOutController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
//		model.clear();
		HttpSession session = (HttpSession)model.get("session");
		session.invalidate();
		
//		return "redirect:../auth/LogInForm.jsp";
		return "redirect:login.do";
	}
}