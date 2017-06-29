package kr.ac.seoultech.todo.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	
	private final static String DEFAULT_ENCODING = "utf-8";
	
	public static void setCharacterEncoding(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding(DEFAULT_ENCODING);
	}
	
}
