package db;

import java.sql.*;

public class DBHandler {
	static String driver = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=Asia/Seoul";
	static String dbid = "root";
	static String dbpwd = "1234";
	
	
	//회원 검색
	public int list(String userid, String username, String email) throws ClassNotFoundException, SQLException {
		Class.forName
		(driver);
		Connection conn = DriverManager.getConnection(url, dbid, dbpwd);
		
		String sql = "SELECT * FROM TUSER WHERE USERNAME LIKE ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%" + username + "%");
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			String uid = rs.getString("userid");
			String uname = rs.getString("username");
			String uemail = rs.getString("email");
			
			String tag = "<li>" + uid + "," + uname + "," + uemail + "," + "</li>";
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return ;
	}	
	//회원 추가
	public int addUser(String userid, String username, String email) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbid, dbpwd);
		
		String sql = "INSERT INTO TUSER VALUES(?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userid);
		pstmt.setString(2, username);
		pstmt.setString(3, email);
		
		int aftcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return aftcnt;		
	}
}
