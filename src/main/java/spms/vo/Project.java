package spms.vo;

import java.sql.Date;
//import java.util.Date;
//책에는 util.Date이나, argument type mismatch Error 발생함..

public class Project {
	//변수명을 컬럼명으로 하진 않음
	protected int 		no;			//pno
	protected String 	title;		//pname
	protected String 	content;	//content
	protected Date 		startDate;	//sta_date
	protected Date 		endDate;	//end_date
	protected int 		state;		//state
	protected Date 		createdDate;	//cre_date
	protected String 	tags;		//tags

	public int getNo() { return no; }
	public Project setNo(int no) { 
		this.no = no; 
		return this;
	}
	public String getTitle() { return title; }
	public Project setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getContent() { return content; }
	public Project setContent(String content) {
		this.content = content;
		return this;
	}
	public Date getStartDate() { return startDate; }
	public Project setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}
	public Date getEndDate() { return endDate; }
	public Project setEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}
	public int getState() { return state; }
	public Project setState(int state) {
		this.state = state;
		return this;
	}
	public Date getCreatedDate() { return createdDate; }
	public Project setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		return this;
	}
	public String getTags() { return tags; }
	public Project setTags(String tags) {
		this.tags = tags;
		return this;
	}
	
}
