package kr.ac.seoultech.todo;

import java.io.IOException;
import java.sql.Connection;

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

@WebServlet("/todo/show")
public class TodoShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Connection connection;
	private TodoDao todoDao;
	
    public TodoShowServlet() {
        super();
        this.connection = ConnectionUtil.getConnection();
        todoDao = new TodoDao(this.connection);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestUtil.setCharacterEncoding(request);
		
		Long loginUserId = SessionUtil.getLoginUserId(request);
		
		String idStr = request.getParameter("id");
		
		try {
			Todo todo = todoDao.selectTodo(Long.parseLong(idStr), loginUserId);
			
			if (todo != null) {
				ResponseUtil.write(response, HttpServletResponse.SC_OK, todo);
			} else {
				ResponseUtil.write(response, HttpServletResponse.SC_NOT_FOUND, todo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtil.write(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
			
		} finally {
			ConnectionUtil.close(connection);
		}
		
	}

}
