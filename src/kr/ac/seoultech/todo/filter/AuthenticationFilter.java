package kr.ac.seoultech.todo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.seoultech.todo.dao.UserDao;
import kr.ac.seoultech.todo.model.User;
import kr.ac.seoultech.todo.util.TodoApiConsts;

@WebFilter(urlPatterns = {"/todo/*", "/logout"})
public class AuthenticationFilter implements Filter {

	private UserDao userDao;

	public AuthenticationFilter() {
		userDao = new UserDao();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String token = ((HttpServletRequest) request).getHeader("Authorization");
		if (token == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		User user = userDao.selectUserByToken(token);
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		request.setAttribute(TodoApiConsts.KEY_LOGIN_USER_ID, user.getId());

		chain.doFilter(request, response);
	}
	
 

}
