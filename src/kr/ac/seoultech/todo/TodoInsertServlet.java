package kr.ac.seoultech.todo;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.seoultech.todo.dao.TodoDao;
import kr.ac.seoultech.todo.model.Todo;
import kr.ac.seoultech.todo.util.ConnectionUtil;
import kr.ac.seoultech.todo.util.RequestUtil;
import kr.ac.seoultech.todo.util.ResponseUtil;
import kr.ac.seoultech.todo.util.SessionUtil;


@WebServlet("/todo/insert")
public class TodoInsertServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private TodoDao todoDao;
	private Connection connection;
	public TodoInsertServlet() {
		super();
		connection = ConnectionUtil.getConnection();
		todoDao = new TodoDao(connection);
	}

	/**
	 * 등록
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestUtil.setCharacterEncoding(request);
		
		Long loginUserId = SessionUtil.getLoginUserId(request);
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		Todo todo = new Todo();
		todo.setTitle(title);
		todo.setContent(content);
		todo.setCreateAt(new Date());
		todo.setUserId(loginUserId);
		
		Long id = todoDao.createTodo(todo);
		todo.setId(id);
		
		ResponseUtil.write(response, HttpServletResponse.SC_OK, todo);
		
	}
	
}
