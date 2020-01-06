package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;

@Component(value="/member/delete.do")
public class MemberDeleteController implements Controller, DataBinding {
	
	//의존객체 주입(DI) 인스턴스 변수와 셋터 메소드
	MySqlMemberDao memberDao;
	public MemberDeleteController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[] {
				 "no", Integer.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
//		MemberDao memberDao = (MemberDao)model.get("memberDao");
		Integer no = (Integer)model.get("no");
//		memberDao.delete(Integer.parseInt((String)model.get("no")));
		memberDao.delete(no);
		return "redirect:list.do";
	}

}
