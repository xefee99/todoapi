package kr.ac.seoultech.todo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.ac.seoultech.todo.model.Todo;
import kr.ac.seoultech.todo.model.TodoSearchCond;
import kr.ac.seoultech.todo.util.ConnectionUtil;

public class TodoDao {
	
	public Long createTodo(Todo todo) {
		Connection connection = ConnectionUtil.getConnection();
		
		String sql = "insert into tb_todo(title, content, create_at, user_id) values (?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, todo.getTitle());
			pstmt.setString(2, todo.getContent());
			pstmt.setTimestamp(3, new java.sql.Timestamp(todo.getCreateAt().getTime()));
			pstmt.setLong(4,  todo.getUserId());
			
			System.out.println("date : " + todo.getCreateAt());
			System.out.println("date : " + todo.getCreateAt().getTime());
			System.out.println("date : " + new java.sql.Date(todo.getCreateAt().getTime()));
			
			pstmt.executeUpdate();
			
			ResultSet tableKeys = pstmt.getGeneratedKeys();
			tableKeys.next();
			Long generatedId = tableKeys.getLong(1);

			System.out.println("generated id : " + generatedId);
			return generatedId;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public void updateTodo(Todo todo) {
		Connection connection = ConnectionUtil.getConnection();
		
		String sql = "update tb_todo set title = ?, content = ? where id = ? and user_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, todo.getTitle());
			pstmt.setString(2, todo.getContent());
			pstmt.setLong(3, todo.getId());
			pstmt.setLong(4, todo.getUserId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public void deleteTodo(Long todoId, Long userId) {
		Connection connection = ConnectionUtil.getConnection();
		
		String sql = "delete from tb_todo where id = ? and user_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, todoId);
			pstmt.setLong(2, userId);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public Todo selectTodo(Long id) {
		Connection connection = ConnectionUtil.getConnection();
		
		String sql = "select id, title, content, create_at, user_id from tb_todo where id = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);
			
			rs = pstmt.executeQuery();
			
			if (!rs.first()) return null;
			
			Todo todo = mappingTodo(rs);
			return todo;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public List<Todo> selectTodoByMaxId(TodoSearchCond searchCond) {
		Connection connection = ConnectionUtil.getConnection();
		
		StringBuilder sb = new StringBuilder("select id, title, content, create_at, user_id from tb_todo ");
		sb.append("where user_id = ? ");

		if (searchCond.getMaxId() != null) {
			sb.append(" and id < ? ");
		}
		sb.append(" order by id desc ");
		sb.append(" limit ?");
		
		String sql = sb.toString();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, searchCond.getUserId());
			if (searchCond.getMaxId() != null) {
				pstmt.setLong(2, searchCond.getMaxId());
				pstmt.setInt(3, searchCond.getLimit());
			} else {
				pstmt.setInt(2, searchCond.getLimit());	
			}
			
			rs = pstmt.executeQuery();
			List<Todo> todoList = mappingTodoList(rs);

			return todoList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
	
	/**
	 * sinceId 이후 limit 갯수만큼 조회
	 * @param searchCond
	 * @return
	 */
	public List<Todo> selectTodoBySinceId(TodoSearchCond searchCond) {
		if (searchCond.getSinceId() == null) throw new RuntimeException("필수 검색조건이 없습니다.");
		
		Connection connection = ConnectionUtil.getConnection();
	
		StringBuilder sb = new StringBuilder("select * from (select id, title, content, create_at, user_id from tb_todo ");
		sb.append("where user_id = ? ");
		sb.append(" and id > ? ");
		sb.append(" order by id asc ");
		sb.append(" limit ? ");
		sb.append(" ) a order by id desc  ");
		
		String sql = sb.toString();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, searchCond.getUserId());
			pstmt.setLong(2, searchCond.getSinceId());
			pstmt.setInt(3, searchCond.getLimit());
			
			rs = pstmt.executeQuery();
			List<Todo> todoList = mappingTodoList(rs);

			return todoList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
		
	}

	
	private List<Todo> mappingTodoList(ResultSet rs) throws SQLException {
		List<Todo> todos = new ArrayList<>();
		while(rs.next()) {
			todos.add(mappingTodo(rs));
		}
		return todos;
	}
	
	private Todo mappingTodo(ResultSet rs) throws SQLException {
		Todo todo = new Todo();
		todo.setId(rs.getLong("id"));
		todo.setTitle(rs.getString("title"));
		todo.setContent(rs.getString("content"));
		
		Date date = new Date(rs.getTimestamp("create_at").getTime());
		todo.setCreateAt(new java.util.Date(date.getTime()));
		todo.setUserId(rs.getLong("user_id"));
		return todo;
	}
	
}

