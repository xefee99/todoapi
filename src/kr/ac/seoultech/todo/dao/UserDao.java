package kr.ac.seoultech.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.ac.seoultech.todo.model.User;
import kr.ac.seoultech.todo.util.ConnectionUtil;

public class UserDao {

	private Connection connection;
	public UserDao(Connection connection) {
		this.connection = connection;
	}
	
	public Long createUser(User user) {
		connection = ConnectionUtil.getConnection();
		
		String sql = "insert into tb_user(name, login_id, password, token) values (?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getLoginId());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getToken());
			
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

	public void updateUser(User user) {
		connection = ConnectionUtil.getConnection();
		
		String sql = "update tb_user set name = ? where id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setLong(2, user.getId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}

	public void updateUserPassword(Long id, String password) {
		connection = ConnectionUtil.getConnection();
		
		String sql = "update tb_user set password = ? where id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setLong(2, id);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public void updateUserToken(Long id, String token) {
		connection = ConnectionUtil.getConnection();
		
		String sql = "update tb_user set token = ? where id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, token);
			pstmt.setLong(2, id);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public void deleteUser(Long id) {
		connection = ConnectionUtil.getConnection();
		
		String sql = "delete from tb_user where id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public User selectUser(Long id) {
		connection = ConnectionUtil.getConnection();
		
		String sql = "select id, name, login_id, password, token from tb_user where id = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);
			
			rs = pstmt.executeQuery();
			
			if (!rs.first()) return null;
			
			User user= new User();
			user.setId(rs.getLong("id"));
			user.setLoginId(rs.getString("login_id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setToken(rs.getString("token"));
			
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public List<User> selectUsers(String name) {
		connection = ConnectionUtil.getConnection();
		
		StringBuilder sb = new StringBuilder("select id, name, login_id, password, token from tb_user ");
		if (name != null) {
			sb.append(" where name like ?");
		}
		String sql = sb.toString();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(sql);
			if (name != null) {
				pstmt.setString(1, name);
			}
			
			rs = pstmt.executeQuery();
			
			List<User> users = new ArrayList<>();
			while(rs.next()) {
				User user= new User();
				user.setId(rs.getLong("id"));
				user.setLoginId(rs.getString("login_id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setToken(rs.getString("token"));
				
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}

	public User selectUserByLoginId(String loginId) {
		connection = ConnectionUtil.getConnection();
		
		String sql = "select id, name, login_id, password, token from tb_user where login_id = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, loginId);
			
			rs = pstmt.executeQuery();
			
			if (!rs.first()) return null;
			
			User user= new User();
			user.setId(rs.getLong("id"));
			user.setLoginId(rs.getString("login_id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setToken(rs.getString("token"));
			
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
	public User selectUserByToken(String token) {
		connection = ConnectionUtil.getConnection();
		
		String sql = "select id, name, login_id, password, token from tb_user where token = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, token);
			
			rs = pstmt.executeQuery();
			
			if (!rs.first()) return null;
			
			User user= new User();
			user.setId(rs.getLong("id"));
			user.setLoginId(rs.getString("login_id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setToken(rs.getString("token"));
			
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
		}
	}
	
}
