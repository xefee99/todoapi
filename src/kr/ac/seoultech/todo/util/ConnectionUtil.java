package kr.ac.seoultech.todo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * mysql 최신 버전에서 SSL 추천해서 경고메시지가 출력
	 * ssl 사용안한 설정을 위해 verifyServerCertificate=false 과 useSSL=false 옵션 추가 
	 */
	private final static String URL = "jdbc:mysql://localhost:3306/seoultech?characterEncoding=utf8&verifyServerCertificate=false&useSSL=false";
	private final static String USERNAME = "seoultech";
	private final static String PASSWORD = "seoultech";
	

	public static Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void close(Connection connection) {
		if (connection != null) try { connection.close(); } catch (Exception e) {}
	}
	
}
