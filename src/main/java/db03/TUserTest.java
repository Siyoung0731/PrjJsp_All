package db03;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TUserTest {
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=Asia/Seoul";
	private static String dbuid = "root";
	private static String dbpwd = "1234";
	
	// tuser, aftcnt 초기화, Scanner 함수를 전역변수로 바꿈
	static Scanner sc = new Scanner(System.in);
	static TUserDTO tuser = null;
	static int aftcnt = 0;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		do {
			System.out.println("=====================================");
			System.out.println("			   회원정보				 ");
			System.out.println("=====================================");
			System.out.println("1. 회원 목록");
			System.out.println("2. 회원 조회");
			System.out.println("3. 회원 추가");
			System.out.println("4. 회원 수정");
			System.out.println("5. 회원 삭제");
			System.out.println("6. 종료");
			
			System.out.println("선택: ");
			String choice = sc.nextLine();
			
			switch(choice) {
			case "1":
			//회원 목록
				ArrayList<TUserDTO> userList = getTUserList();
				displayList(userList);
				break;
			case "2": 
			//회원 조회
				System.out.println("조회할 아이디: ");
				String uid = sc.nextLine();
				tuser = getTUser(uid);
				display(tuser);
				break;
			case "3": 
			//회원 추가
				tuser = inputData();
				aftcnt = addTUser(tuser);
				System.out.println(aftcnt + "건 저장되었습니다.");
				break;
			case "4": 
			//회원 수정
				System.out.println("수정할 아이디 입력");
				String orguid = sc.nextLine(); // 검색할 데이터, 변경대상 X
				System.out.println("수정할 내용 입력");
				tuser = Updatedata();
				aftcnt = uptTuser(orguid, tuser);
				System.out.println(aftcnt + "행 이(가) 업데이트되었습니다.");
				break;
			case "5":
			//회원 삭제
				System.out.println("삭제할 아이디 입력: ");
				String dltid = sc.nextLine();
				aftcnt = dltTUser(dltid);
				System.out.println(aftcnt + "건 삭제되었습니다.");
				sc.nextLine();
				break;
			case "q", "Q": 
			//종료
				System.out.println("프로그램 종료");
				System.exit(0);
				break;
			}
		} while (true); //무한루프
	}

	private static int dltTUser(String dltid) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql = "DELETE FROM TUSER WHERE USERID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, dltid);
		
		int aftcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return aftcnt;
	}

	// 1. ArrayList 로 전체 회원 목록 조회
	private static ArrayList<TUserDTO> getTUserList() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql = "SELECT * FROM TUSER ORDER BY USERID ASC";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		ArrayList<TUserDTO> userList = new ArrayList<>();
		
		while (rs.next()) {
			String userid = rs.getString("userid");
			String username = rs.getString("username");
			String email = rs.getString("email");
			
			TUserDTO tuser = new TUserDTO(userid, username, email);
			// tuser 에 3개의 데이터를 ArrayList인 userList에 추가
			userList.add(tuser);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		// 3개의 데이터를 추가한 userList 를 전달
		return userList;
	}
	//2. SELECT(회원 조회)
	private static TUserDTO getTUser(String uid) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		String sql = "SELECT * FROM TUSER WHERE USERID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);			
		pstmt.setString(1, uid);
		
		TUserDTO tuser = null;
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			String userid = rs.getString("userid");
			String username = rs.getString("username");
			String email = rs.getString("email");
			
			tuser = new TUserDTO(userid, username, email);
		} else {
			
		}
		pstmt.close();
		conn.close();
		rs.close();
		
		return tuser;
	}
	//3. INSERT(회원 추가)
	private static int addTUser(TUserDTO tuser) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql = "INSERT INTO TUSER VALUES(?, ?, ?)";		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, tuser.getUserid());
		pstmt.setString(2, tuser.getUsername());
		pstmt.setString(3, tuser.getEmail());
		
		int aftcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return aftcnt;
	}
	//4. Update 회원 수정
	private static int uptTuser(String orguid, TUserDTO tuser) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql = "UPDATE TUSER SET USERNAME = ?, EMAIL = ? WHERE USERID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, tuser.getUsername());
		pstmt.setString(2, tuser.getEmail());
		pstmt.setString(3, orguid); // default 값
		
		int aftcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return aftcnt;
	}
//--------------------------------------------------------------------------------
	// 1. ArrayList - displayList()
		private static void displayList(ArrayList<TUserDTO> userList) {
			if(userList.size() == 0) {
				System.out.println("조회한 자료가 없음");
				return;
			}
			String msg = "";
			for (TUserDTO tuser : userList) {
				String userid = tuser.getUserid();
				String username = tuser.getUsername();
				String email = tuser.getEmail();
				msg = """
						%s %s %s
						""".formatted(userid, username, email);
				System.out.println(msg);
			}
			System.out.println("Press any key...");
			sc.nextLine();
		}
	// 2. select - display()  tuser == null
	private static void display(TUserDTO tuser) {		
		if (tuser == null) {
			System.out.println("조회된 자료 없음");
		} else {
			String msg = String.format("%s %s %s ", tuser.getUserid(), tuser.getUsername(), tuser.getEmail());
			System.out.println(msg);
		}
	}	
//--------------------------------------------------------------------------------
	//추가 데이터
	private static TUserDTO inputData() {
		System.out.println("아이디: ");
		String userid = sc.nextLine();
		System.out.println("이름: ");
		String username = sc.nextLine();
		System.out.println("이메일: ");
		String email = sc.nextLine();
		
		// 입력 받은 데이터 3개를 tuser 에 담는다
		TUserDTO tuser = new TUserDTO(userid, username, email); 
		// 그 결과값인 tuser 를 다시 전달
		return tuser;
	}
	//수정할 데이터
	private static TUserDTO Updatedata() {
		System.out.println("이름: ");
		String username = sc.nextLine();
		System.out.println("이메일: ");
		String email = sc.nextLine();
		
		TUserDTO tuser = new TUserDTO(username, email);
		return tuser;
	}
}
