package kr.ac.seoultech.todo;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.seoultech.todo.dao.UserDao;
import kr.ac.seoultech.todo.model.User;
import kr.ac.seoultech.todo.util.RequestUtil;
import kr.ac.seoultech.todo.util.ResponseUtil;


@WebServlet("/user")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserDao userDao;

    public UserListServlet() {
        super();
        userDao = new UserDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestUtil.setCharacterEncoding(request);
		
		String name = request.getParameter("name");
		System.out.println("name : " + name);
		
		List<User> users = userDao.selectUsers(name);
		
		ResponseUtil.write(response, HttpServletResponse.SC_OK, users);
	}

}
