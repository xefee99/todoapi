package kr.ac.seoultech.todo;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.seoultech.todo.dao.UserDao;
import kr.ac.seoultech.todo.model.User;
import kr.ac.seoultech.todo.util.ConnectionUtil;
import kr.ac.seoultech.todo.util.RequestUtil;
import kr.ac.seoultech.todo.util.ResponseUtil;

@WebServlet("/join")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	private UserDao userDao;
	
    public JoinServlet() {
        super();
        connection = ConnectionUtil.getConnection();
        userDao = new UserDao(connection);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestUtil.setCharacterEncoding(request);
		
		String name = request.getParameter("name");
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		
		User user = new User();
		user.setName(name);
		user.setLoginId(loginId);
		user.setPassword(password);
		
		UUID randomUUID = UUID.randomUUID();
		String token = randomUUID.toString();
		user.setToken(token);
		
		
		try {
			Long createdUserId = userDao.createUser(user);
			
			Map<String, Object> result = new HashMap<>();
			result.put("token", token);
			result.put("id", createdUserId);
			
			ResponseUtil.write(response, HttpServletResponse.SC_OK, result);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtil.write(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
			
		} finally {
			ConnectionUtil.close(connection);
		}
		
	}

}
