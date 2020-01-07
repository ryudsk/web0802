package spms.controls;

import java.util.Map;

import org.springframework.stereotype.Component;

//import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@Component(value="/member/add.do")
public class MemberAddController implements Controller, DataBinding {
	
	//의존객체 주입(DI) 인스턴스 변수와 셋터 메소드
	MySqlMemberDao memberDao;
	
	public MemberAddController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	 }
	
	public Object[] getDataBinders() {
		return new Object[] {
			"member", spms.vo.Member.class
		};
	} // Member 인스턴스를 member란 이름으로 맵에 넣어달라. > front에서 객체 생성

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member)model.get("member"); 
		
//		if(model.get("member") == null){ 			// member라는 이름의 객체 존재 여부로 판단불가
		if(member.getEmail() == null){
			return "/member/MemberForm.jsp";
			
		}else { //회원등록을 요청할 때
			memberDao.insert(member);
			return "redirect:list.do";
		}
	}
}
