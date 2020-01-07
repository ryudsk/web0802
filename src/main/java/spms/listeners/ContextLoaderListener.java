package spms.listeners;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
//import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import spms.context.ApplicationContext;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	static ClassPathXmlApplicationContext applicationContext;

	//spms.servlets.DispatcherServlet.java에서 사용
	public static ClassPathXmlApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		} catch(Throwable e) {
			e.printStackTrace();
		}
//			
//			String resource = "spms/dao/mybatis-config.xml";
//			InputStream inputStream = Resources.getResourceAsStream(resource);
//			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//			
//			applicationContext.addBean("sqlSessionFactory", sqlSessionFactory);
//			
//			ServletContext sc = event.getServletContext();
//			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
//			
//			applicationContext.prepareObjectsByProperties(propertiesPath);
//			applicationContext.prepareObjectsByAnnotation("");
//			applicationContext.injectDependency();
//			
////			properties와 annotation으로 DI - web06 Step2
///*			ServletContext sc = event.getServletContext();	
//  		    String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
//			applicationContext = new ApplicationContext(propertiesPath);
//*/			
///*			하나씩 지정해서 DI web06 - step 1
// * 			InitialContext initialContext = new InitialContext();
//			DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/studydb"); //JDBC(java:comp/env)
//			
//			MySqlMemberDao memberDao = new MySqlMemberDao(); //Dao생성
//			memberDao.setDataSource(ds); //3.DataSource 주입
//			
////			sc.setAttribute("memberDao", memberDao);
//			
//			sc.setAttribute("/auth/login.do", new LogInController().setMemberDao(memberDao));
//			sc.setAttribute("/auth/loginout.do", new LogOutController());
//			sc.setAttribute("/member/list.do", new MemberListController().setMemberDao(memberDao));
//			sc.setAttribute("/member/add.do", new MemberAddController().setMemberDao(memberDao));
//			sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
//			sc.setAttribute("/member/delete.do", new MemberDeleteController().setMemberDao(memberDao));
//*/			
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//4.DataSource사용-톰캣서버에서 자동해제하기 때문(context.xml)
	}
	
}
