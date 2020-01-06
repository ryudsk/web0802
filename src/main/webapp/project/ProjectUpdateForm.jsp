<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>프로젝트정보</title>
</head>
<body>
	<jsp:include page="/Header.jsp" />
	<h1>프로젝트정보</h1>
	<form action='update.do' method='post'>
		<ul>
			<li><label for="no">번호</label>
				<input id="no" type='text' name='no' value='${project.no}' readonly>
			</li>
			<li><label for="title">제목</label> 
				<input id="title" type="text" name="title" size="50" value='${project.title}'>
			</li> 
			<li><label for="content">내용</label>
				<textarea id="content" name="content" rows="5" cols="40">${project.content}</textarea>
			</li>
			<li><label for="sdate">시작일</label> 
				<input id="sdate" type="text" name="startDate" value='${project.startDate}'>
			</li>
			<li><label for="edate">종료일</label> 
				<input id="edate" type="text" name="endDate" value='${project.endDate}'>
			</li>
			<li><label for="state">상태</label>
				<select id="state" name="state">
					<option value="0" ${project.state == 0 ? "selected" : "" }>준비</option>
					<option value="1" ${project.state == 1 ? "selected" : "" }>진행</option>
					<option value="2" ${project.state == 2 ? "selected" : "" }>완료</option>
					<option value="3" ${project.state == 3 ? "selected" : "" }>취소</option>
				</select>
			</li>
			<li><label for="tags">태그</label> 
				<input id="tags" type="text" name="tags" value='${project.tags}' size="50">
			</li>
		</ul>
		<input type='submit' value='저장'>
		<input type='button' value='삭제' onclick='location.href="delete.do?no=<%=request.getParameter("no") %>"'>
		<input type='button' value='취소' onclick='location.href="list.do"'>
	</form>
	<jsp:include page="/Tail.jsp" />
</body>
</html>