package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
				query += " values (seq_board_no.nextval,?,?,0,sysdate,?) ";
				
				//쿼리문
				pstmt = conn.prepareStatement(query);
				//바인딩
				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContent());
				pstmt.setInt(3, boardVo.getUserNo());
				
				count = pstmt.executeUpdate();
				
				} catch (SQLException e) {
					System.out.println("error:" + e);
				} 
			close();
			return count;
		}
		
		
		
		public int boardDelete(int i) {
			int count = 0;
			getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " delete board ";
				query += " where no = ? ";
			
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			
				pstmt.setInt(1, i); // ?(물음표) 중 1번째, 순서중요
		       
				count = pstmt.executeUpdate(); 
			
			}catch (SQLException e) {
			    System.out.println("error:" + e);
			}
			//System.out.println("삭크제 완료");
			close();
			return count;
		
		}
		
		public int boardModify(BoardVo boardvo) {
			int count = 0;
			getConnection();
			
			
			try {
				String query = "";
				query += " update board ";
				query += " set title = ?, ";
				query += "     content = ? ";
				query += " where no = ? ";
				
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				
				pstmt.setString(1, boardvo.getTitle()); // ?(물음표) 중 1번째, 순서중요
				pstmt.setString(2, boardvo.getContent()); // ?(물음표) 중 2번째, 순서중요
				pstmt.setInt(3, boardvo.getNo()); // ?(물음표) 중 3번째, 순서중요
				
				count = pstmt.executeUpdate(); // 쿼리문 실행

			
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			
			
			
			close();
			return count;
		}
		
		public int hitModify(int index) {
			int count = 0;
			getConnection();
			
			try {
				String query = "";
				query += " update board ";
				query += " set hit = NVL(hit, 0) + 1 ";
				query += " where no = ? ";
				
				pstmt = conn.prepareStatement(query); 
				
				pstmt.setInt(1, index);
				
				count = pstmt.executeUpdate(); 
				
			}catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			return count;
		}
		
		public List<BoardVo> getList() {
			List<BoardVo> boardList = new ArrayList<BoardVo>();
			getConnection();
			try {
			
				String query = "";
				query += " select  bo.no no, ";
				query += "         title, ";
				query += "         content, ";
				query += "         hit, ";
				query += "         to_char(reg_date, 'yyyy-mm-dd') reg_date, ";
				query += "         bo.user_no user_no, ";
				query += "         name ";
				query += " from board bo, users us ";
				query += " where bo.user_no = us.no ";
				
				pstmt = conn.prepareStatement(query);
					
				rs = pstmt.executeQuery();
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs. getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userno = rs.getInt("user_no");
				String name = rs.getString("name");
				
				BoardVo boardvo = new BoardVo(no, title, content, hit, regDate, userno, name);
				
				boardList.add(boardvo);
				//System.out.println(boardvo);
			}
			}catch (SQLException e) {
				System.out.println("error:" + e);
			}
			close();
			return boardList;
		}

		
		
		
		
		public BoardVo getBoardVo(int index) {
			BoardVo boardvo = null;
			getConnection();
			
			try {
				
				String query = "";
				query += " select  bo.no no, ";
				query += "         title, ";
				query += "         content, ";
				query += "         hit, ";
				query += "         to_char(reg_date, 'yyyy-mm-dd') reg_date, ";
				query += "         bo.user_no user_no, ";
				query += "         name ";
				query += " from board bo, users us ";
				query += " where bo.user_no = us.no ";
				query += " and		bo.no = ? ";
				
				pstmt = conn.prepareStatement(query);
					
				pstmt.setInt(1, index);
				
				rs = pstmt.executeQuery();
				
				
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs. getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userno = rs.getInt("user_no");
				String name = rs.getString("name");
				
				
				boardvo = new BoardVo(no, title, content, hit, regDate, userno, name);
				
			
				//System.out.println(boardvo);
			}
			}catch (SQLException e) {
				System.out.println("error:" + e);
			}

			close();
			return boardvo;
		}
}
