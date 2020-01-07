package spms.controls;

import java.util.Map;

import org.springframework.stereotype.Component;

//import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@Component(value="/member/update.do")
public class MemberUpdateController implements Controller, DataBinding {
	
	//의존객체 주입(DI) 인스턴스 변수와 셋터 메소드
	MySqlMemberDao memberDao;
	public MemberUpdateController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
				 "no", Integer.class,
				"member", spms.vo.Member.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member)model.get("member");
		
		if(member.getEmail() == null) {
//			model.put("updateUser", memberDao.selectOne(Integer.parseInt((String)model.get("no"))));
			Integer no = (Integer)model.get("no");
			model.put("updateUser", memberDao.selectOne(no));
			return "/member/MemberUpdateForm.jsp";
		}else {
			memberDao.update((Member)model.get("member"));
			return "redirect:list.do";
		}
	}

}
