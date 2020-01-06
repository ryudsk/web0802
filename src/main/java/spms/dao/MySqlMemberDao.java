package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import spms.annotation.Component;
import spms.vo.Member;

@Component("memberDao")
public class MySqlMemberDao implements MemberDao{
	SqlSessionFactory sqlSessionFactory;
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	/*DataSource ds;
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}*/
	
	public List<Member> selectList(HashMap<String, Object> paramMap) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.selectList("spms.dao.MemberDao.selectList", paramMap);
		} finally {
			sqlSession.close();
		}
	}
	
/*	public List<Member> selectList() throws Exception{
		Connection connection = null; //2. DBConnectionPool 사용, 개별 커넥션 처리
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); //3.DataSource사용
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT MNO,MNAME,EMAIL,CRE_DATE"
					+ " FROM MEMBERS"
					+ " ORDER BY MNO ASC");
			ArrayList<Member> members = new ArrayList<Member>();
			while(rs.next()) {
				members.add(new Member()
						.setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL"))
						.setCreatedDate(rs.getDate("CRE_DATE")));
			}
			return members;
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {} //3.DataSource사용
		}
	}*/
	
	public int insert(Member member) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.insert("spms.dao.MemberDao.insert", member);
			sqlSession.commit();
			return count;
		} finally {
			sqlSession.close();
		}
	}	
	
	/*public int insert(Member member) throws Exception {
		Connection connection = null; //2. DBConnectionPool 사용
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection(); //3.DataSource사용
			stmt = connection.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"+" VALUES (?,?,?,NOW(),NOW())");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			
			return stmt.executeUpdate();
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}*/
	
	public int delete(int no) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.delete("spms.dao.MemberDao.delete", no);
			sqlSession.commit();
			return count;
		} finally {
			sqlSession.close();
		}
	}
	/*public int delete(int no) throws Exception{
		Connection connection = null; //2. DBConnectionPool 사용
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("delete from MEMBERS where MNO=?");
			stmt.setInt(1,no);
			
			return stmt.executeUpdate();
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}*/
	
	public Member selectOne(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.selectOne("spms.dao.MemberDao.selectOne", no);
		} finally {
			sqlSession.close();
		}
	}
	
	/*public Member selectOne(int no) throws Exception{
		Connection connection = null; //2. DBConnectionPool 사용
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select MNO, EMAIL, MNAME, CRE_DATE from MEMBERS"
					+ " where MNO=" + no);
			rs.next();
			
			return new Member().setName(rs.getString("MNAME")).setEmail(rs.getString("EMAIL")).setCreatedDate(rs.getDate("CRE_DATE"));
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}*/
	
	public int update(Member member) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			Member origin = sqlSession.selectOne("spms.dao.MemberDao.selectOne", member.getNo());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			if(!member.getEmail().equals(origin.getEmail())) {
				paramMap.put("email", member.getEmail());
			}
			if(!member.getName().equals(origin.getName())) {
				paramMap.put("name", member.getName());
			}
			if(paramMap.size() > 0) {
				paramMap.put("no", member.getNo());
				int count = sqlSession.update("spms.dao.MemberDao.update", paramMap);
				sqlSession.commit();
				return count;
			} else {
				return 0;
			}
		} finally {
			sqlSession.close();
		}
	}
	
	/*public int update(Member member) throws Exception{
		Connection connection = null; //2. DBConnectionPool 사용
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(
					"UPDATE MEMBERS SET EMAIL=?, MNAME=?, MOD_DATE=now()"
							+ " WHERE MNO=?");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			return stmt.executeUpdate();
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}*/
	
	public Member exist(String email, String password) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("email", email);
			paramMap.put("password", password);
			return sqlSession.selectOne("spms.dao.MemberDao.exist", paramMap);
		} finally {
			sqlSession.close();
		}
	}
	/*public Member exist(String email, String password) throws Exception{
		Connection connection = null; //2. DBConnectionPool 사용
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("SELECT MNAME,EMAIL FROM MEMBERS"
					+ " WHERE EMAIL=? AND PWD=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			Member loginUser = null;
			if(rs.next()) {
				loginUser = new Member().setEmail(rs.getString("EMAIL")).setName(rs.getString("MNAME"));
			}
			return loginUser;
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}*/

}
