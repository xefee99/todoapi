package kr.ac.seoultech.todo;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.seoultech.todo.dao.TodoDao;
import kr.ac.seoultech.todo.model.Todo;
import kr.ac.seoultech.todo.util.RequestUtil;
import kr.ac.seoultech.todo.util.ResponseUtil;
import kr.ac.seoultech.todo.util.TodoApiConsts;


@WebServlet("/todo/insert")
public class TodoInsertServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private TodoDao todoDao;
	
	public TodoInsertServlet() {
		super();
		todoDao = new TodoDao();
	}

	/**
	 * 등록
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestUtil.setCharacterEncoding(request);
		
		Long loginUserId = (Long) request.getAttribute(TodoApiConsts.KEY_LOGIN_USER_ID);
		
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
