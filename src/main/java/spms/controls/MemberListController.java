package spms.controls;

import java.util.HashMap;
import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;

@Component(value="/member/list.do")
public class MemberListController implements Controller, DataBinding {
	
	//의존객체 주입(DI) 인스턴스 변수와 셋터 메소드
	MySqlMemberDao memberDao;
	public MemberListController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"orderCond", String.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
//		MemberDao memberDao = (MemberDao)model.get("memberDao");
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("orderCond", model.get("orderCond"));
		
		model.put("members",memberDao.selectList(paramMap));
		return "/member/MemberList.jsp";
	}
}