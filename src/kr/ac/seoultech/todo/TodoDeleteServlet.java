package kr.ac.seoultech.todo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.seoultech.todo.dao.TodoDao;
import kr.ac.seoultech.todo.util.RequestUtil;
import kr.ac.seoultech.todo.util.ResponseUtil;
import kr.ac.seoultech.todo.util.TodoApiConsts;


@WebServlet("/todo/delete")
public class TodoDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private TodoDao todoDao;
    public TodoDeleteServlet() {
        super();
        todoDao = new TodoDao();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestUtil.setCharacterEncoding(request);
		
		Long loginUserId = (Long) request.getAttribute(TodoApiConsts.KEY_LOGIN_USER_ID);
		
		String idStr = request.getParameter("id");

		todoDao.deleteTodo(Long.parseLong(idStr), loginUserId);
		
		ResponseUtil.write(response, HttpServletResponse.SC_OK, null);
	}

}
