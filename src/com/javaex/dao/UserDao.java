package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	//연결하기
		private void getConnection() {
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);

				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);
				// System.out.println("접속성공");

			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

		//닫기
		public void close() {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
	
		//저장 메소드
		public int userinsert(UserVo userVo) {
			int count = 0;
			getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " insert into users ";
				query += " values(seq_users_no.nextval, ?, ?, ?, ?) ";
				
				
				//쿼리문
				pstmt = conn.prepareStatement(query);
				//바인딩
				pstmt.setString(1, userVo.getId());
				pstmt.setString(2, userVo.getPassword());
				pstmt.setString(3, userVo.getName());
				pstmt.setString(4, userVo.getGender());

				count = pstmt.executeUpdate();

				// 4.결과처리
				System.out.println(count + "건 등록 완료");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			} 
			
			close();

			return count;
		}

		
		
		public UserVo getUser(String id, String password) {
			
			UserVo userVo = null;
			getConnection();
			
			
			try {
				
			
				String query = "";
				query += " select  no, ";
				query += "         name ";
				query += " from users ";
				query += " where id = ? ";
				query += " and password = ? ";
				
				
				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, password);
				
				
				rs = pstmt.executeQuery();
			
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					
					userVo = new UserVo();
					userVo.setNo(no);
					userVo.setName(name);
				}
				System.out.println("로긴저장완료");
				
			}catch (SQLException e) {
				System.out.println("error:" + e);
			} 
				
			close();
			return userVo;
			
			
		}
		
		 
		
		public int userModify(UserVo vo) {
			int count=0;
			
			getConnection();
			
			try {
				//쿼리문 준비
				String query = "";
				query += " update users ";
				query += " set password = ?, ";
				query += "     name = ?, ";
				query += "     gender = ? ";
				query += " where id = ? ";
				query += " and no = ? ";
				
				//쿼리로 만들기
				pstmt = conn.prepareStatement(query);
				
				pstmt.setString(1, vo.getPassword());
				pstmt.setString(2, vo.getName());
				pstmt.setString(3, vo.getGender());
				pstmt.setString(4, vo.getId());
				pstmt.setInt(5, vo.getNo());
				
				count = pstmt.executeUpdate();
					
			}catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			return count;
		}
}
