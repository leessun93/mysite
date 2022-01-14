package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs= null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	private void getConnection(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		}catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	private void close() {
		try {               
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
	
	
	
	
	
	
	
	
	
	//getList
	
	public List<GuestbookVo> getList() {
		List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();

		
		try {
			getConnection();
			String query ="";
			query += " select  no, ";
			query += "         name, ";
			query += "         password, ";
			query += "         content, ";
			query += "         to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') reg_date ";
			query += " from guestbook ";
			query += " order by reg_date desc ";
			
			//쿼리
			pstmt = conn.prepareStatement(query);
		    
			//바인딩 물음표 없어서 생략
			
			//실행 셀렉트만 익스쿼트 쿼리 나머지는 업데이트
			rs = pstmt.executeQuery();
			// 4.결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs. getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				GuestbookVo guestbookvo = new GuestbookVo(no, name, password, content, regDate);
				guestbookList.add(guestbookvo);
			}
		}catch (SQLException e) {
				System.out.println("error:" + e);
				}
		
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
			
		
		
		
		return guestbookList;
	}
	
	
	
	
	//Insert
	public GuestbookVo Insert(GuestbookVo vo) {

		GuestbookVo guestbookvo = new GuestbookVo();
		
		try {
			getConnection();
		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
	         query += " insert into guestbook ";
	         query += " values (seq_guestbook_no.nextval, ?,?,?, sysdate) ";
	         // System.out.println(query);

	         pstmt = conn.prepareStatement(query); // 쿼리로 만들기

	         pstmt.setString(1, vo.getName()); // ?(물음표) 중 1번째, 순서중요
	         pstmt.setString(2, vo.getPassword() ); // ?(물음표) 중 2번째, 순서중요
	         pstmt.setString(3, vo.getContent()); // ?(물음표) 중 3번째, 순서중요
	     

	         pstmt.executeUpdate(); 
	 
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return guestbookvo;
	}
	
	//Delete
	public int delete(GuestbookVo vo) {
		int count = 0;

		try {
			getConnection();
		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
	         query += " delete guestbook ";
	         query += " where no = ? ";
	         query += " and password= ? ";
	         
	         
	         pstmt = conn.prepareStatement(query); // 쿼리로 만들기
	         
	         pstmt.setInt(1, vo.getNo()); // ?(물음표) 중 1번째, 순서중요
	         pstmt.setString(2, vo.getPassword());

	         count = pstmt.executeUpdate(); 
			
			//실행 셀렉트만 익스쿼트 쿼리 나머지는 업데이트

		
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();

	
		return count;
	
		
	}
}
