package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;


@WebServlet("/guest")
public class GuestbookController extends HttpServlet {
	
   

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String act = request.getParameter("action");
	
	if("addList".equals(act)) {
		WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
	}else if("add".equals(act)) {
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		String pass = request.getParameter("pass");
		
		GuestbookDao guestbookDao = new GuestbookDao();
		GuestbookVo guestbookvo = new GuestbookVo();
		
		guestbookvo.setName(name);
		guestbookvo.setPassword(pass);
		guestbookvo.setContent(pass);
		guestbookDao.Insert(guestbookvo);
		
		response.sendRedirect("/mysite/guest?action=addList");
	}else if("deleteForm".equals(act)) {
		WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
	}else if("delete".equals(act)) {
		String pass = request.getParameter("pass");
		int no = Integer.parseInt(request.getParameter("no"));
		
		GuestbookDao guestbookDao = new GuestbookDao();
		GuestbookVo guestbookvo = new GuestbookVo();
		guestbookvo.setNo(no);
		guestbookvo.setPassword(pass);
		
		guestbookDao.delete(guestbookvo);
		
		response.sendRedirect("/mysite/guest?action=addList");
		System.out.println("딜리트했다임마!");
	}

		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
