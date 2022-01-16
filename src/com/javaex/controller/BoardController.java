package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		String act = request.getParameter("action");
		if("list".equals(act)) {
			
			List<BoardVo> boardList = new BoardDao().getList();

			request.setAttribute("boardList", boardList);

			//리스트로 이동
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			 
			//System.out.println(boardList);
		
		}else if("read".equals(act)) {
			System.out.println("리드페이지 도킹");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardVo boardvo = new BoardDao().getBoardVo(no);
			BoardDao boardDao = new BoardDao();
			
			request.setAttribute("boardVo", boardvo);
			boardDao.hitModify(no);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		
		
		
		
		}else if("modifyForm".equals(act)) {
			//리드--> 모디파이폼
			System.out.println("모디파이폼 도킹");

			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		
		
		}else if("modify".equals(act)) {
			//모디파이폼--> 수정
			System.out.println("모디파이후 리다이렉트예정");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			
			BoardVo boardVo = new BoardVo();
			boardVo.setNo(no);
			boardVo.setContent(content);
			boardVo.setTitle(title);
			
			BoardDao boardDao = new BoardDao();
			
			boardDao.boardModify(boardVo);
			
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
			
			
		}else if("writeForm".equals(act)) {
			//리스트 글쓰기
			System.out.println("롸이트폼 도킹");
			
			int no = Integer.parseInt(request.getParameter("no"));

			BoardVo boardVo = new BoardDao().getBoardVo(no);
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		
		
		
		
		}else if("boardWrite".equals(act)) {
			System.out.println("보드 롸이트후 리스트로 리다이렉트 예정");
			
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int userNo = authUser.getNo();
			
			BoardVo boardVo = new BoardVo();
			boardVo.setTitle(title);
			boardVo.setContent(content);
			boardVo.setUserNo(userNo);
			
			BoardDao boardDao = new BoardDao();
			boardDao.boardInsert(boardVo);
			
			
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");		
		
			
			
		}else if("delete".equals(act)) {
			System.out.println("딜리트 후 리다이렉트 예정");
			
	
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			boardDao.boardDelete(no);
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
			//System.out.println(no);
			
			
			
		}
	
		
	
		

		
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
