package spms.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import spms.annotation.Component;
import spms.vo.Project;

@Component("projectDao")
public class MySqlProjectDao implements ProjectDao {
	SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	/*DataSource ds;
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}*/
	
	
	@Override
	public List<Project> selectList(HashMap<String,Object> paramMap) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.selectList("spms.dao.ProjectDao.selectList", paramMap);
		} finally {
			sqlSession.close();
		}
		
		/*Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT PNO,PNAME,STA_DATE,END_DATE,STATE"
					+ " FROM PROJECTS"
					+ " ORDER BY PNO ASC");
			ArrayList<Project> projects = new ArrayList<Project>();
			while(rs.next()) {
				projects.add(new Project()
					.setNo(rs.getInt("PNO"))
					.setTitle(rs.getString("PNAME"))
					.setStartDate(rs.getDate("STA_DATE"))
					.setEndDate(rs.getDate("END_DATE"))
					.setState(rs.getInt("STATE")));
			}
			return projects;
			
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try{if(stmt != null) stmt.close();} catch(Exception e) {}
			try{if(connection != null) connection.close();} catch(Exception e) {} 
		}*/
	}



	@Override
	public int insert(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.insert("spms.dao.ProjectDao.insert",project);
			sqlSession.commit();
			return count;
		} finally {
			sqlSession.close();
		}
		
		/*Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("INSERT INTO PROJECTS(PNAME,CONTENT,STA_DATE,END_DATE,STATE,CRE_DATE,TAGS)"
							+ " VALUES (?,?,?,?,0,NOW(),?)");
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			stmt.setString(5, project.getTags());

			return stmt.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}*/
			
	}



	@Override
	public int delete(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.delete("spms.dao.ProjectDao.delete", no);
			sqlSession.commit();
			return count;
		} finally {
			sqlSession.close();
		}
		
		/*Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("delete from PROJECTS where PNO=?");
			stmt.setInt(1, no);
			
			return stmt.executeUpdate();
		}catch (Exception e) {
			throw e;
		}finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}*/
	}

	@Override
	public Project selectOne(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.selectOne("spms.dao.ProjectDao.selectOne", no);
		} finally {
			sqlSession.close();
		}
		/*Connection connection = null; //2. DBConnectionPool 사용
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("select PNO,PNAME,CONTENT,STA_DATE,END_DATE,STATE,CRE_DATE,TAGS"
					+ " from PROJECTS"
					+ " WHERE PNO = ?");
			stmt.setInt(1, no);
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				return new Project()
						.setNo(rs.getInt("PNO"))
						.setTitle(rs.getString("PNAME"))
						.setContent(rs.getString("CONTENT"))
						.setStartDate(rs.getDate("STA_DATE"))
						.setEndDate(rs.getDate("END_DATE"))
						.setState(rs.getInt("STATE"))
						.setCreatedDate(rs.getDate("CRE_DATE"))
						.setTags(rs.getString("TAGS"));
			} else {
				throw new Exception("해당 번호의 프로젝트를 찾을 수 없습니다.");
			}
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}*/
	}

	@Override
	public int update(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			Project origin = sqlSession.selectOne("spms.dao.ProjectDao.selectOne", project.getNo());
			Hashtable<String,Object> paramMap = new Hashtable<String,Object>();
			
			if(!project.getTitle().equals(origin.getTitle())) {
				paramMap.put("title", project.getTitle());
			}
			if(!project.getContent().equals(origin.getContent())) {
				paramMap.put("content", project.getContent());
			}
			if(project.getStartDate().compareTo(origin.getStartDate()) != 0) {
				paramMap.put("startDate", project.getStartDate());
			}
			if(project.getEndDate().compareTo(origin.getEndDate()) != 0) {
				paramMap.put("endDate", project.getEndDate());
			}
			if(project.getState() != origin.getState()) {
				paramMap.put("state", project.getState());
			}
			if(!project.getTags().equals(origin.getTags())) {
				paramMap.put("tags", project.getTags());
			}
			
			if(paramMap.size() > 0) {
				paramMap.put("no", project.getNo());
				int count = sqlSession.update("spms.dao.ProjectDao.update", paramMap);
				sqlSession.commit();
				return count;
			} else {
				return 0;
			}
		} finally {
			sqlSession.close();
		}
		
		/* Before myBatis dynamic sql by set element 
		 * try {
			int count = sqlSession.update("spms.dao.ProjectDao.update", project);
			sqlSession.commit();
			return count;
		} finally {
			sqlSession.close();
		}*/
		/*Connection connection = null; //2. DBConnectionPool 사용
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("update PROJECTS set PNAME=?,CONTENT=?,STA_DATE=?,END_DATE=?,STATE=?,TAGS=?"
					+ " where PNO=?");
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			stmt.setInt(5, project.getState());
			stmt.setString(6, project.getTags());
			stmt.setInt(7, project.getNo());
			
			return stmt.executeUpdate();
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}*/
	}
}
