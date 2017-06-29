package kr.ac.seoultech.todo;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.seoultech.todo.dao.TodoDao;
import kr.ac.seoultech.todo.model.Todo;
import kr.ac.seoultech.todo.model.TodoSearchCond;
import kr.ac.seoultech.todo.util.ConnectionUtil;
import kr.ac.seoultech.todo.util.RequestUtil;
import kr.ac.seoultech.todo.util.ResponseUtil;
import kr.ac.seoultech.todo.util.SessionUtil;


@WebServlet("/todo")
public class TodoListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private TodoDao todoDao;
	private Connection connection;
	public TodoListServlet() {
		super();
		connection = ConnectionUtil.getConnection();
		todoDao = new TodoDao(connection);
	}
	
	/**
	 * 목록 조회
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestUtil.setCharacterEncoding(request);
		
		Long loginUserId = SessionUtil.getLoginUserId(request);
	
		String maxId = request.getParameter("maxId");
		String sinceId = request.getParameter("sinceId");
		String limit = request.getParameter("limit");
		
		
		TodoSearchCond searchCond = new TodoSearchCond();
		searchCond.setUserId(loginUserId);
		if (limit != null) searchCond.setLimit(Integer.parseInt(limit));
		
		
		try {
			List<Todo> todoList = null;
			if (sinceId != null) {
				// 특정아이디 이후 조회건수 
				searchCond.setSinceId(Long.parseLong(sinceId));
				todoList = todoDao.selectTodoBySinceId(searchCond);
			}
			else {
				// 특정아이디 이전 조회건수
				if (maxId != null) searchCond.setMaxId(Long.parseLong(maxId)); 
				todoList = todoDao.selectTodoByMaxId(searchCond);
			}
			
			ResponseUtil.write(response, HttpServletResponse.SC_OK, todoList);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtil.write(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
			
		} finally {
			ConnectionUtil.close(connection);
		}
		
	}
	
}
