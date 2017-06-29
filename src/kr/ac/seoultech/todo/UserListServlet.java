package kr.ac.seoultech.todo;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

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

/**
 * 
 * 인증이 필요 없는 서비스 (테스트용)
 */
@WebServlet("/user")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserDao userDao;
	private Connection connection;
	
    public UserListServlet() {
        super();
        connection = ConnectionUtil.getConnection();
        userDao = new UserDao(connection);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestUtil.setCharacterEncoding(request);
		
		String name = request.getParameter("name");
		System.out.println("name : " + name);
		
		try {
			List<User> users = userDao.selectUsers(name);
			ResponseUtil.write(response, HttpServletResponse.SC_OK, users, false);
			
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtil.write(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
			
		} finally {
			ConnectionUtil.close(connection);
		} 
		
	}

}
