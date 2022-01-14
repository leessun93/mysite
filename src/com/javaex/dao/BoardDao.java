package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.BoardVo;

public class BoardDao {

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
	
		public int boardInsert(BoardVo boardVo) {
			int count = 0;
			getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " insert into board  ";
				query += " values (seq_board_no.nextval,?,?,?,sysdate,?) ";
				
				//쿼리문
				pstmt = conn.prepareStatement(query);
				//바인딩
				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContent());
				pstmt.setInt(3, boardVo.getHit());
				pstmt.setInt(4, boardVo.getUserNo());
				
				count = pstmt.executeUpdate();
				
				} catch (SQLException e) {
					System.out.println("error:" + e);
				} 
			close();
			return count;
		}
		
		public int boardDelete(BoardVo boardVo) {
			int count = 0;
			getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " delete board ";
				query += " where no = ? ";
			
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			
				 pstmt.setInt(1, boardVo.getNo()); // ?(물음표) 중 1번째, 순서중요
		         pstmt.setString(2, vo.getPassword());
			
			
			
			
			close();
			return count;
		}
}
