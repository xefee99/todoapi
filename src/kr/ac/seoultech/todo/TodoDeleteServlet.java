package kr.ac.seoultech.todo;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.seoultech.todo.dao.TodoDao;
import kr.ac.seoultech.todo.util.ConnectionUtil;
import kr.ac.seoultech.todo.util.RequestUtil;
import kr.ac.seoultech.todo.util.ResponseUtil;
import kr.ac.seoultech.todo.util.SessionUtil;


@WebServlet("/todo/delete")
public class TodoDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private TodoDao todoDao;
	private Connection connection;
    public TodoDeleteServlet() {
        super();
        connection = ConnectionUtil.getConnection();
        todoDao = new TodoDao(connection);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestUtil.setCharacterEncoding(request);
		
		Long loginUserId = SessionUtil.getLoginUserId(request);
		
		String idStr = request.getParameter("id");

		try {
			todoDao.deleteTodo(Long.parseLong(idStr), loginUserId);
			ResponseUtil.write(response, HttpServletResponse.SC_OK, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtil.write(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
			
		} finally {
			ConnectionUtil.close(connection);
		}
		
	}

}
