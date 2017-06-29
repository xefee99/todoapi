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


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Connection connection;
	private UserDao userDao; 
	
    public LoginServlet() {
        super();
        connection = ConnectionUtil.getConnection();
        userDao = new UserDao(connection);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestUtil.setCharacterEncoding(request);
		
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		
		User user = userDao.selectUserByLoginId(loginId);
		
		if (password.equals(user.getPassword())) {
			// 로그인 성공
			String token = UUID.randomUUID().toString();
			userDao.updateUserToken(user.getId(), token);
			
			Map<String, String> result = new HashMap<>();
			result.put("id", String.valueOf(user.getId()));
			result.put("token", token);
			
			ResponseUtil.write(response, HttpServletResponse.SC_OK, result);
			
		} else {
			// 로그인 실패
			Map<String, String> result = new HashMap<>();
			result.put("message", "loginId is not found or password is not match");
			
			ResponseUtil.write(response, HttpServletResponse.SC_NOT_FOUND, result);
		}
	}

}
