package kr.ac.seoultech.todo.filter;

import java.io.IOException;
import java.sql.Connection;

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
import kr.ac.seoultech.todo.util.ConnectionUtil;
import kr.ac.seoultech.todo.util.TodoApiConsts;

@WebFilter(urlPatterns = {"/todo/*", "/logout"})
public class AuthenticationFilter implements Filter {

	private Connection connection;
	private UserDao userDao;

	public AuthenticationFilter() {
		connection = ConnectionUtil.getConnection();
		userDao = new UserDao(connection);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// header 값에서 사용자를 식별할 수 있는 토큰값 조회
		String token = ((HttpServletRequest) request).getHeader("Authorization");
		// 토큰이 없으면 인가되지 않은 요청이므로 종료
		if (token == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		User user = null;
		try {
			user = userDao.selectUserByToken(token);
		} catch (Exception e) {
			// 사용자 조회중 오류 발생하면 종료
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		} finally {
			ConnectionUtil.close(connection);
		}
		
		
		// 토큰으로 조회한 사용자가 없으면 적합하지 않은 토큰이므로 종료
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		// 적합한 사용자일경우 사용자 아이디를 Session에 저장
		request.getSession().setAttribute(TodoApiConsts.KEY_LOGIN_USER_ID, user.getId());

		chain.doFilter(request, response);
	}
	
 

}
