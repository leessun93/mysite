package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUtil;

@WebServlet("/board")
public class BoardController extends HttpServlet {

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		String act = request.getParameter("action");
		if("list".equals(act)) {
			
			//리스트로 이동
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		
		
		
		}else if("read".equals(act)) {
			System.out.println("리드페이지 도킹");
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		
		
		
		
		}else if("modifyForm".equals(act)) {
			//리드--> 모디파이폼
			System.out.println("모디파이폼 도킹");
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		
		
		
		}else if("modify".equals(act)) {
			//모디파이폼--> 수정
			System.out.println("모디파이후 리다이렉트예정");
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
			
			
		}else if("writeForm".equals(act)) {
			//리스트 글쓰기
			System.out.println("롸이트폼 도킹");
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		
		
		
		
		}else if("boardWrite".equals(act)) {
			System.out.println("보드 롸이트후 리스트로 리다이렉트 예정");
			
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");		
		
			
			
		}else if("delete".equals(act)) {
			System.out.println("딜리트 후 리다이렉트 예정");
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
			
			
		}
	
		
	
		

		
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
