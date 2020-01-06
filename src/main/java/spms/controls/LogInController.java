package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@Component(value="/auth/login.do")
public class LogInController implements Controller, DataBinding {

	MySqlMemberDao memberDao;
	public LogInController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
			"loginUser", spms.vo.Member.class	//Member 인스턴스를 생성하여 loginUser라는 이름으로 Map객체에 저장해달라
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member loginUser = (Member)model.get("loginUser");
		
		if(loginUser.getEmail() == null) { //맵에서 객체 여부 체크가 아
			return "/auth/LogInForm.jsp";
		}
		else {
			Member member = memberDao.exist(loginUser.getEmail(),loginUser.getPassword());
			
			if(member != null) {
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			}else {
				return "/auth/LogInFail.jsp";
			}
		}
	}
}