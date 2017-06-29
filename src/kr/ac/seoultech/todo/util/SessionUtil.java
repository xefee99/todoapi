package kr.ac.seoultech.todo.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {

	public static Long getLoginUserId(HttpServletRequest request) {
		Long loginUserId = (Long) request.getSession().getAttribute(TodoApiConsts.KEY_LOGIN_USER_ID);
		return loginUserId;
	}
	
}
