package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;
import spms.annotation.Component;

public class ApplicationContext {
	Hashtable<String,Object> objTable = new Hashtable<String,Object>(); // 프로퍼티를 저장할 해시테이블 생성
	
	public Object getBean(String key) {	// 프로퍼티용 getter
		return objTable.get(key);
	}
	
	public void addBean(String name, Object obj) {
		objTable.put(name, obj);
	}
	
	//의존객체 주입을 생성자에서 일괄처리 하였으나, 외부 객체 주입을 위해 개별처리 방식으로 변경 -- 생성자는 제거
/*	public ApplicationContext(String propertiesPath) throws Exception {
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));	// 경로의 파일을 읽어들여 내부 맵에 키밸류 보관
		
		prepareObjects(props);	// 프로퍼티에 따른 객체 준비
		prepareAnnotationObjects();
		injectDependency(); // 의존객체 할당
	}*/
	
//	private void prepareAnnotationObjects() throws Exception {
	public void prepareObjectsByAnnotation(String basePackage) throws Exception { // mybatis 용, 어노테이션을 검색할 패키지 이름을 매개변수로 받고, 접근제한자를 public으로
//		Reflections reflector = new Reflections(""); // Reflections 외부 library 사용, 패키지 위치 ""
		Reflections reflector = new Reflections(basePackage);
		
		Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class); // @Compnent 어노테이션이 붙은 클래스 검색
		String key = null;
		
		for(Class<?> clazz : list) {
			key = clazz.getAnnotation(Component.class).value(); // 클래스로부터 어노테이션 이름 얻어오고
			objTable.put(key, clazz.newInstance()); // 이름을 key값으로 클래스 생성해서 해시테이블에 입력
		}
	}
	
//	private void prepareObjects(Properties props) throws Exception {
	public void prepareObjectsByProperties(String propertiesPath) throws Exception { // mybatis 용, 접근제한자 public, Properties 객체를 직접 받는 대신, 파일 경로를 받고 내부에서 Properties 객체 생성
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		Context ctx = new InitialContext();	// JNDI용
		String key = null;
		String value = null;
		
		for (Object item : props.keySet()) {	// 프로퍼티 키 목록
			key = (String)item;					// 키 하나 
			value = props.getProperty(key);		// 밸류 하나 
			if (key.startsWith("jndi.")) {		// 키가 jndi로 시작하면,
				objTable.put(key, ctx.lookup(value)); // InitialContext에서 밸류로 검색, 해시테이블에 저장
			} else {
				objTable.put(key, Class.forName(value).newInstance());	// jndi가 아니면, 클래스 로딩하고 인스턴스 생성해서 키 값으로 저장
			}
		}
	}
	
//	private void injectDependency() throws Exception {
	public void injectDependency() throws Exception { // mybatis,접근제한자 public
		for (String key : objTable.keySet()) {
			if (!key.startsWith("jndi.")) {
				callSetter(objTable.get(key)); // callSetter로 키 값을 넘겨서 셋터 메서드 호출하기
			}
		}
	}
	
	private void callSetter(Object obj) throws Exception {
		Object dependency = null;
		for (Method m : obj.getClass().getMethods()) {	// 키 값 이름의 객체에서 셋터 메소드 찾아내기
			if (m.getName().startsWith("set")) {
				dependency = findObjectByType(m.getParameterTypes()[0]); // 셋터 메소드의 매개변수와 타입을 넘겨서 
				if (dependency != null) {
					m.invoke(obj, dependency);	// 의존객체를 매개변수로 하는 obj를 생성..
				}
			}
		}
	}
	
	private Object findObjectByType(Class<?> type) { // 타입을 받아서, 
		for (Object obj : objTable.values()){ // prepareObj..에서 준비한 객체를 전부 찾아
			if (type.isInstance(obj)) { // 주어진 객체가 해당 클래스 또는 인터페이스의 인스턴스인지 검사
				return obj;
			}
		}
		return null;
	}
}
