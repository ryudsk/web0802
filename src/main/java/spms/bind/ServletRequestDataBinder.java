package spms.bind;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

public class ServletRequestDataBinder {
	public static Object bind(ServletRequest request, Class<?> dataType, String dataName) throws Exception {
		
		// 기본 타입(int, long, float, double, boolean, Date, String) 여부 판단하고 객체 생성
		if(isPrimitiveType(dataType)){
			return createValueObject(dataType, request.getParameter(dataName));
		}
		
		Set<String> paramNames = request.getParameterMap().keySet(); //request parameter에서 name을 모두 꺼내서 set에 저장
		Object dataObject = dataType.newInstance(); //전달받은 class Type으로 객체 생성하기
		Method m = null; // Method 변수 선언
		
		for(String paramName : paramNames){
			m = findSetter(dataType, paramName); //dataType class에서 parameter이름과 같은 Setter 메소드 찾기
			if(m != null){
				m.invoke(dataObject, createValueObject(m.getParameterTypes()[0],request.getParameter(paramName)));
														//셋터 메소드의 매개변수 타입	// 매개변수에 들어갈 값
			}	//메소드를 찾았다면, 이전에 생성한 객체에
		}
		
		return dataObject;
		
	}
	// 기본 타입 여부 판단
	private static boolean isPrimitiveType(Class<?> type) {
		if(type.getName().equals("int") || type == Integer.class ||
		   type.getName().equals("long") || type == Long.class ||
		   type.getName().equals("float") || type == Float.class ||
		   type.getName().equals("double") || type == Double.class ||
		   type.getName().equals("boolean") || type == Boolean.class ||
		   type == Date.class || type == String.class) {
		
		return true;
		}
	return false;	
	}
	
	// 기본 타입 객체 생성
	private static Object createValueObject(Class<?> type, String value) {
		if(type.getName().equals("int") || type == Integer.class) {
			return new Integer(value);
			
		} else if (type.getName().equals("float") || type == Float.class){
			return new Float(value);
			
		} else if (type.getName().equals("double") || type == Double.class){
			return new Double(value);
			
		} else if (type.getName().equals("long") || type == Long.class){
			return new Long(value);
			
		} else if (type.getName().equals("boolean") || type == Boolean.class){
			return new Boolean(value);
			
		} else if (type == Date.class) {
			return java.sql.Date.valueOf(value);
			
		} else {
			return value;
		}
	}
	
	private static Method findSetter(Class<?> type, String name) {
		Method[] methods = type.getMethods(); //넘겨받은 클래스(타입)의 메소드를 꺼내서 methods 배열에 담는다.
		
		String propName = null;
		for (Method m : methods) {
			if(!m.getName().startsWith("set")) continue; //메소드 이름이 set으로 시작하지 않으면 패스
			propName = m.getName().substring(3);		//set으로 시작하면, 앞의 3자리를 제외한 나머지 모든 문자열을 propName에 입력
			if(propName.toLowerCase().contentEquals(name.toLowerCase())) { //소문자 처리해서 값이 동일하다면, setter 메소드를 리턴
				return m;
			}
		}
		
		return null;
	}

}